package com.desafiofinal.futebol.repository;

import com.desafiofinal.futebol.entities.Estadio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstadioRepository extends JpaRepository<Estadio, Long> {
    boolean existsByNome(String nome);
    Page<Estadio> findAll(Pageable pageable);
}
