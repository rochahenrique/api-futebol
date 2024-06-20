package com.desafiofinal.futebol.repository;

import com.desafiofinal.futebol.entities.Clube;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface ClubeRepository extends JpaRepository<Clube, Long> {
    boolean existsByNomeAndEstado(String nome, String estado);

    Page<Clube> findByNomeContainingAndEstadoContainingAndAtivo(String nome, String estado, boolean ativo, Pageable pageable);
    Page<Clube> findByNomeContainingAndEstadoContaining(String nome, String estado, Pageable pageable);
    Page<Clube> findByNomeContaining(String nome, Pageable pageable);
    Page<Clube> findByEstadoContaining(String estado, Pageable pageable);
    Page<Clube> findByAtivo(boolean ativo, Pageable pageable);

}
