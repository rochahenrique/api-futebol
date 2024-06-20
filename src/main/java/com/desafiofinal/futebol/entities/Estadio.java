package com.desafiofinal.futebol.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


@Entity
public class Estadio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome do estádio é obrigatório")
    @Size(min = 3, message = "Nome do estádio deve ter pelo menos 3 letras")
    private String nome;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotBlank(message = "Nome do estádio é obrigatório") @Size(min = 3, message = "Nome do estádio deve ter pelo menos 3 letras") String getNome() {
        return nome;
    }

    public void setNome(@NotBlank(message = "Nome do estádio é obrigatório") @Size(min = 3, message = "Nome do estádio deve ter pelo menos 3 letras") String nome) {
        this.nome = nome;
    }
}
