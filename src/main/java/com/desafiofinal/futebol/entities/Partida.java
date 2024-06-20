package com.desafiofinal.futebol.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Entity
public class Partida {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "clube_casa_id", nullable = false)
    private Clube clubeCasa;

    @ManyToOne
    @JoinColumn(name = "clube_visitante_id", nullable = false)
    private Clube clubeVisitante;

    @NotBlank(message = "Estádio é obrigatório")
    private String estadio;

    @NotNull(message = "Data e hora da partida são obrigatórias")
    private LocalDateTime dataHora;

    @Min(value = 0, message = "Gols do clube da casa não podem ser negativos")
    private int golsClubeCasa;

    @Min(value = 0, message = "Gols do clube visitante não podem ser negativos")
    private int golsClubeVisitante;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Clube getClubeCasa() {
        return clubeCasa;
    }

    public void setClubeCasa(Clube clubeCasa) {
        this.clubeCasa = clubeCasa;
    }

    public Clube getClubeVisitante() {
        return clubeVisitante;
    }

    public void setClubeVisitante(Clube clubeVisitante) {
        this.clubeVisitante = clubeVisitante;
    }

    public @NotBlank(message = "Estádio é obrigatório") String getEstadio() {
        return estadio;
    }

    public void setEstadio(@NotBlank(message = "Estádio é obrigatório") String estadio) {
        this.estadio = estadio;
    }

    public @NotNull(message = "Data e hora da partida são obrigatórias") LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(@NotNull(message = "Data e hora da partida são obrigatórias") LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    @Min(value = 0, message = "Gols do clube da casa não podem ser negativos")
    public int getGolsClubeCasa() {
        return golsClubeCasa;
    }

    public void setGolsClubeCasa(@Min(value = 0, message = "Gols do clube da casa não podem ser negativos") int golsClubeCasa) {
        this.golsClubeCasa = golsClubeCasa;
    }

    @Min(value = 0, message = "Gols do clube visitante não podem ser negativos")
    public int getGolsClubeVisitante() {
        return golsClubeVisitante;
    }

    public void setGolsClubeVisitante(@Min(value = 0, message = "Gols do clube visitante não podem ser negativos") int golsClubeVisitante) {
        this.golsClubeVisitante = golsClubeVisitante;
    }
}
