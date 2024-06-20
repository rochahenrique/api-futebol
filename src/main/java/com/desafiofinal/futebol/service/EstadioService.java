package com.desafiofinal.futebol.service;

import com.desafiofinal.futebol.entities.Clube;
import com.desafiofinal.futebol.entities.Estadio;
import com.desafiofinal.futebol.exception.DataConflictException;
import com.desafiofinal.futebol.exception.ResourceNotFoundException;
import com.desafiofinal.futebol.repository.EstadioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EstadioService {

    @Autowired
    private EstadioRepository estadioRepository;

    public Estadio cadastrarEstadio(Estadio estadio){
        if (estadioRepository.existsByNome(estadio.getNome())){
            throw new DataConflictException("Estadio já existe! ");
        }
        return  estadioRepository.save(estadio);
    }


    public Estadio editarEstadio(Long id, Estadio estadioAtualizado){
        Optional<Estadio> estadioExisitente = estadioRepository.findById(id);
        if (!estadioExisitente.isPresent()){
            throw new ResourceNotFoundException("Estádio não encontrado");
        }
        Estadio estadio = estadioExisitente.get();

        if (estadioRepository.existsByNome(estadioAtualizado.getNome()) &&
                !estadio.getId().equals(estadioAtualizado.getId())) {
            throw new DataConflictException("Já existe um estádio com esse nome");
        }

        estadio.setNome(estadioAtualizado.getNome());

        return estadioRepository.save(estadio);

    }

    public Estadio buscarEstadio(Long id){
        return estadioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Estádio não encontrado"));
    }

    public Page<Estadio> listarEstadios(int page, int size, String sort, String order){
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.fromString(order), sort);
        return estadioRepository.findAll(pageable);
    }

}
