package com.desafiofinal.futebol.service;


import com.desafiofinal.futebol.entities.Clube;
import com.desafiofinal.futebol.exception.DataConflictException;
import com.desafiofinal.futebol.exception.ResourceNotFoundException;
import com.desafiofinal.futebol.repository.ClubeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClubeService {

    @Autowired
    private ClubeRepository clubeRepository;

    public Clube cadastrarClube(Clube clube){
        if (clubeRepository.existsByNomeAndEstado(clube.getNome(), clube.getEstado())){
            throw new DataConflictException("Clube já existe no estado");
        }
        return  clubeRepository.save(clube);
    }

    public Clube editarClube(Long id, Clube clubeAtualizado){
        Optional<Clube> clubeExistente = clubeRepository.findById(id);
        if (!clubeExistente.isPresent()){
            throw new ResourceNotFoundException("Clube não encontrado");
        }
        Clube clube = clubeExistente.get();

        if (clubeRepository.existsByNomeAndEstado(clubeAtualizado.getNome(), clubeAtualizado.getEstado()) &&
                !clube.getId().equals(clubeAtualizado.getId())) {
            throw new DataConflictException("Já existe um clube com esse nome e estado");
        }

        clube.setNome(clubeAtualizado.getNome());
        clube.setEstado(clubeAtualizado.getEstado());
        clube.setDataCriacao(clubeAtualizado.getDataCriacao());
        clube.setAtivo(clubeAtualizado.isAtivo());

        return clubeRepository.save(clube);

    }

    public void inativarClube(Long id){
        Optional <Clube> clubeExistente = clubeRepository.findById(id);
        if (!clubeExistente.isPresent()){
            throw new ResourceNotFoundException("Clube não encontrado");
        }
        Clube clube = clubeExistente.get();
        clube.setAtivo(false);
        clubeRepository.save(clube);
    }

    public Clube buscarClube(Long id){
        return clubeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Clube não encontrado"));
    }

    public Page<Clube> listarClubes(String nome, String estado, Boolean ativo, Pageable pageable){
        if(nome != null && estado != null && ativo != null){
            return clubeRepository.findByNomeContainingAndEstadoContainingAndAtivo(nome, estado, ativo, pageable);
        } else if (nome != null && estado !=null){
            return clubeRepository.findByNomeContainingAndEstadoContaining(nome, estado, pageable);
        } else if (nome!= null) {
            return clubeRepository.findByNomeContaining(nome, pageable);
        } else if (estado != null){
            return clubeRepository.findByEstadoContaining(estado, pageable);
        } else if (ativo != null){
            return clubeRepository.findByAtivo(ativo, pageable);
        } else{
            return clubeRepository.findAll(pageable);
        }
    }




}
