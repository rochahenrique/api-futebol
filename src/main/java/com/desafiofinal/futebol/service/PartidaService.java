package com.desafiofinal.futebol.service;

import com.desafiofinal.futebol.entities.Clube;
import com.desafiofinal.futebol.entities.Partida;
import com.desafiofinal.futebol.exception.DataConflictException;
import com.desafiofinal.futebol.exception.ResourceNotFoundException;
import com.desafiofinal.futebol.repository.ClubeRepository;
import com.desafiofinal.futebol.repository.PartidaRepository;
import jakarta.servlet.http.Part;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PartidaService {
    private static final Logger logger = LoggerFactory.getLogger(PartidaService.class);

    @Autowired
    private PartidaRepository partidaRepository;

    @Autowired
    private ClubeRepository clubeRepository;

    public Partida cadastrarPartida(Partida partida) {
        verificarDadosPartida(partida);
        partida.setClubeCasa(clubeRepository.findById(partida.getClubeCasa().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Clube da casa não encontrado")));
        partida.setClubeVisitante(clubeRepository.findById(partida.getClubeVisitante().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Clube visitante não encontrado")));
        return partidaRepository.save(partida);
    }

    public Partida editarPartida(Long id, Partida partidaAtualizada) {
        Partida partidaExistente = partidaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Partida não encontrada"));

        verificarDadosPartida(partidaAtualizada);

        partidaExistente.setClubeCasa(clubeRepository.findById(partidaAtualizada.getClubeCasa().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Clube da casa não encontrado")));
        partidaExistente.setClubeVisitante(clubeRepository.findById(partidaAtualizada.getClubeVisitante().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Clube visitante não encontrado")));
        partidaExistente.setEstadio(partidaAtualizada.getEstadio());
        partidaExistente.setDataHora(partidaAtualizada.getDataHora());
        partidaExistente.setGolsClubeCasa(partidaAtualizada.getGolsClubeCasa());
        partidaExistente.setGolsClubeVisitante(partidaAtualizada.getGolsClubeVisitante());

        return partidaRepository.save(partidaExistente);
    }

    private void verificarDadosPartida(Partida partida) {
        if (partida.getClubeCasa().getId().equals(partida.getClubeVisitante().getId())) {
            throw new IllegalArgumentException("Os dois clubes não podem ser iguais");
        }


        Clube clubeCasa = clubeRepository.findById(partida.getClubeCasa().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Clube da casa não encontrado"));
        Clube clubeVisitante = clubeRepository.findById(partida.getClubeVisitante().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Clube visitante não encontrado"));


        if (!clubeCasa.isAtivo() || !clubeVisitante.isAtivo()) {
            throw new DataConflictException("Um dos clubes está inativo");
        }

        LocalDateTime partidaDataHora = partida.getDataHora();
        LocalDateTime clubeCasaDataCriacao = clubeCasa.getDataCriacao().atStartOfDay();
        LocalDateTime clubeVisitanteDataCriacao = clubeVisitante.getDataCriacao().atStartOfDay();

        logger.info("Data e hora da partida: {}", partidaDataHora);
        logger.info("Data de criação do clube casa: {}", clubeCasaDataCriacao);
        logger.info("Data de criação do clube visitante: {}", clubeVisitanteDataCriacao);


        if (partidaDataHora.isBefore(clubeCasaDataCriacao) || partidaDataHora.isBefore(clubeVisitanteDataCriacao)) {
            throw new DataConflictException("A data da partida não pode ser anterior à data de criação de um dos clubes");
        }


        if (partidaDataHora.isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException("A data e hora da partida devem ser no passado");
        }


        LocalDateTime startDateTime = partidaDataHora.minusHours(48);
        LocalDateTime endDateTime = partidaDataHora.plusHours(48);

        logger.info("Verificando partidas próximas entre {} e {}", startDateTime, endDateTime);

        List<Partida> partidasCasaProximas = partidaRepository.findByClubeCasaIdAndDataHoraBetween(
                clubeCasa.getId(), startDateTime, endDateTime);
        List<Partida> partidasVisitanteProximas = partidaRepository.findByClubeVisitanteIdAndDataHoraBetween(
                clubeVisitante.getId(), startDateTime, endDateTime);

        if (!partidasCasaProximas.isEmpty() || !partidasVisitanteProximas.isEmpty()) {
            for (Partida p : partidasCasaProximas) {
                logger.info("Partida próxima encontrada para clube casa: ID {}, Data e Hora {}", p.getId(), p.getDataHora());
            }
            for (Partida p : partidasVisitanteProximas) {
                logger.info("Partida próxima encontrada para clube visitante: ID {}, Data e Hora {}", p.getId(), p.getDataHora());
            }
            throw new DataConflictException("Um dos clubes já tem uma partida marcada em um intervalo menor que 48 horas");
        }


        List<Partida> partidasNoEstadio = partidaRepository.findByEstadioAndDataHora(partida.getEstadio(), partidaDataHora);

        if (!partidasNoEstadio.isEmpty()) {
            logger.error("Já existe uma partida marcada para este estádio nesta data e horário");
            throw new DataConflictException("Já existe uma partida marcada para este estádio nesta data e horário");
        }
    }

    public void removerPartida(Long id){
        Partida partidaExistente = partidaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Partida não encontrada"));
        partidaRepository.delete(partidaExistente);
    }

    public Partida buscarPartidaPorId(Long id){
        return partidaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Partida não encontrada"));
    }

    public Page<Partida> listarPartidas(Long clubeCasaId, Long clubeVisitanteId, String estadio, int page, int size, String sort, String order) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.fromString(order), sort);
        if (clubeCasaId != null && estadio != null) {
            return partidaRepository.findByClubeCasaIdOrClubeVisitanteIdAndEstadio(clubeCasaId, clubeVisitanteId, estadio, pageable);
        } else if (clubeCasaId != null) {
            return partidaRepository.findByClubeCasaIdOrClubeVisitanteId(clubeCasaId, clubeVisitanteId, pageable);
        } else if (estadio != null) {
            return partidaRepository.findByEstadio(estadio, pageable);
        } else {
            return partidaRepository.findAll(pageable);
        }
    }

}
