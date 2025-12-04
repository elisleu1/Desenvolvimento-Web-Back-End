package com.example.demoback_end_java.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;

@Entity
@Table(name = "tarefas")
public class Tarefa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome da tarefa é obrigatório")
    @Column(name = "nome_tarefa", nullable = false)   // coluna no banco
    private String nomeTarefa;   // <-- agora o campo se chama "nomeTarefa"

    @NotNull(message = "A data de entrega é obrigatória")
    @Column(name = "data_entrega")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataEntrega;

    @NotBlank(message = "O responsável é obrigatório")
    @Column(nullable = false)
    private String responsavel;

    public Tarefa() { }

    public Tarefa(String nomeTarefa, LocalDate dataEntrega, String responsavel) {
        this.nomeTarefa = nomeTarefa;
        this.dataEntrega = dataEntrega;
        this.responsavel = responsavel;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNomeTarefa() { return nomeTarefa; }
    public void setNomeTarefa(String nomeTarefa) { this.nomeTarefa = nomeTarefa; }

    public LocalDate getDataEntrega() { return dataEntrega; }
    public void setDataEntrega(LocalDate dataEntrega) { this.dataEntrega = dataEntrega; }

    public String getResponsavel() { return responsavel; }
    public void setResponsavel(String responsavel) { this.responsavel = responsavel; }
}