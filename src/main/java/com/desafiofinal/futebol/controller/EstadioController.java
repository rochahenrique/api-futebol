package com.desafiofinal.futebol.controller;



import com.desafiofinal.futebol.entities.Estadio;
import com.desafiofinal.futebol.exception.DataConflictException;
import com.desafiofinal.futebol.exception.ResourceNotFoundException;
import com.desafiofinal.futebol.service.EstadioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/estadios")
public class EstadioController {

    @Autowired
    private EstadioService estadioService;

    @PostMapping
    public ResponseEntity<Estadio> cadastrarEstadio(@Valid @RequestBody Estadio estadio){
        try{
            Estadio novoEstadio = estadioService.cadastrarEstadio(estadio);
            return new ResponseEntity<>(novoEstadio, HttpStatus.CREATED);
        } catch (DataConflictException e){
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Estadio> editarEstadio(@PathVariable Long id, @Valid @RequestBody Estadio estadio) {
        try {
            Estadio estadioEditado = estadioService.editarEstadio(id, estadio);
            return new ResponseEntity<>(estadioEditado, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } catch (DataConflictException e) {
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Estadio> buscarEstadio(@PathVariable Long id){
        try {
            Estadio estadio = estadioService.buscarEstadio(id);
            return new ResponseEntity<>(estadio, HttpStatus.OK);
        } catch (ResourceNotFoundException e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<Page<Estadio>> listarEstadios(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "nome") String sort,
            @RequestParam(defaultValue = "asc") String order){
        Page <Estadio> estadios = estadioService.listarEstadios(page, size,sort, order);
        return new ResponseEntity<>(estadios, HttpStatus.OK);
    }
}
