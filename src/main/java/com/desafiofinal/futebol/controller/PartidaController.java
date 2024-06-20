package com.desafiofinal.futebol.controller;


import com.desafiofinal.futebol.entities.Partida;
import com.desafiofinal.futebol.exception.DataConflictException;
import com.desafiofinal.futebol.exception.ResourceNotFoundException;
import com.desafiofinal.futebol.service.PartidaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/partidas")
public class PartidaController {
    @Autowired
    private PartidaService partidaService;

    @PostMapping
    public ResponseEntity<Partida> cadastrarPartida(@Valid @RequestBody Partida partida) {
        try {
            Partida novaPartida = partidaService.cadastrarPartida(partida);
            return new ResponseEntity<>(novaPartida, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } catch (DataConflictException e) {
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Partida> editarPartida(@PathVariable Long id, @Valid @RequestBody Partida partida){
        try {
            Partida partidaAtualizada = partidaService.editarPartida(id, partida);
            return new ResponseEntity<>(partidaAtualizada, HttpStatus.OK);
        } catch (IllegalArgumentException e){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } catch (ResourceNotFoundException e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } catch (DataConflictException e){
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerPartida(@PathVariable Long id){
        try {
            partidaService.removerPartida(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (ResourceNotFoundException e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Partida> buscarPartidaPorId(@PathVariable Long id){
        try {
            Partida partida = partidaService.buscarPartidaPorId(id);
            return new ResponseEntity<>(partida, HttpStatus.OK);
        } catch (ResourceNotFoundException e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping
    public ResponseEntity<Page<Partida>> listarPartidas(
            @RequestParam(required = false) Long clubeId,
            @RequestParam(required = false) Long visitanteId,
            @RequestParam(required = false) String estadio,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "id") String sort,
            @RequestParam(defaultValue = "asc") String order) {
        Page<Partida> partidas = partidaService.listarPartidas(clubeId, visitanteId,  estadio, page, size, sort, order);
        return new ResponseEntity<>(partidas, HttpStatus.OK);
        }
}
