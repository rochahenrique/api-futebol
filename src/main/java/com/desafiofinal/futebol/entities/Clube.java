package com.desafiofinal.futebol.entities;

import com.desafiofinal.futebol.validation.EstadoValido;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;


import java.time.LocalDate;

@Entity
public class Clube {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 2, message = "Nome deve ter pelo menos 2 letras")
    private String nome;

    @NotBlank(message = "Sigla do estado é obrigatória")
    @EstadoValido(message = "Nome deve ter pelo menos 2 letras e ser um estado brasileiro válido")
    private String estado;

    @NotNull(message = "Data de criação é obrigatória")
    @PastOrPresent(message = "Data de criação não pode ser no futuro")
    private LocalDate dataCriacao;


    private boolean ativo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

}
