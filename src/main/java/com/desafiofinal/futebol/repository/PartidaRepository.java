package com.desafiofinal.futebol.repository;

import com.desafiofinal.futebol.entities.Partida;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PartidaRepository extends JpaRepository<Partida, Long> {
    List<Partida> findByClubeCasaIdAndDataHoraBetween(Long clubeCasaId, LocalDateTime startDateTime, LocalDateTime endDateTime);
    List<Partida> findByClubeVisitanteIdAndDataHoraBetween(Long clubeVisitanteId, LocalDateTime startDateTime, LocalDateTime endDateTime);
    List<Partida> findByEstadioAndDataHora(String estadio, LocalDateTime dataHora);
    Page<Partida> findByClubeCasaIdOrClubeVisitanteId(Long clubeCasaId, Long clubeVisitanteId, Pageable pageable);
    Page<Partida> findByEstadio(String estadio, Pageable pageable);
    Page<Partida> findByClubeCasaIdOrClubeVisitanteIdAndEstadio(Long clubeCasaId, Long clubeVisitanteId, String estadio, Pageable pageable);
}
