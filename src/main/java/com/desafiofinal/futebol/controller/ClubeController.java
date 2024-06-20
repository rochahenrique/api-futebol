package com.desafiofinal.futebol.controller;

import com.desafiofinal.futebol.entities.Clube;
import com.desafiofinal.futebol.exception.DataConflictException;
import com.desafiofinal.futebol.exception.ResourceNotFoundException;
import com.desafiofinal.futebol.service.ClubeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clubes")
public class ClubeController {

    @Autowired
    private ClubeService clubeService;

    @PostMapping
    public ResponseEntity<Clube> cadastrarClube(@Valid @RequestBody Clube clube){
        try{
            Clube novoClube = clubeService.cadastrarClube(clube);
            return new ResponseEntity<>(novoClube, HttpStatus.CREATED);
        } catch (DataConflictException e){
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Clube> editarClube(@PathVariable Long id, @Valid @RequestBody Clube clube){
        try{
            Clube clubeEditado = clubeService.editarClube(id, clube);
            return new ResponseEntity<>(clubeEditado, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } catch (DataConflictException e){
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> inativarClube(@PathVariable Long id){
        try{
            clubeService.inativarClube(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (ResourceNotFoundException e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Clube> buscarClube (@PathVariable Long id){
        try {
            Clube clube = clubeService.buscarClube(id);
            return new ResponseEntity<>(clube, HttpStatus.OK);
        } catch (ResourceNotFoundException e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<Page<Clube>> listarClubes(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String estado,
            @RequestParam(required = false) Boolean ativo,
            Pageable pageable){
        Page<Clube> clubes = clubeService.listarClubes(nome, estado, ativo, pageable);
        return new ResponseEntity<>(clubes, HttpStatus.OK);
    }

}
