package com.example.demoback_end_java.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

import com.example.demoback_end_java.model.Tarefa;
import com.example.demoback_end_java.repository.TarefaRepository;

@RestController
@RequestMapping("/api/tarefas")
@CrossOrigin(origins = "*")
public class TarefaController {

    private final TarefaRepository repositorio;

    public TarefaController(TarefaRepository repositorio) {
        this.repositorio = repositorio;
    }

    // Consultar todas as tarefas
    @GetMapping
    public ResponseEntity<List<Tarefa>> listarTodas() {
        return ResponseEntity.ok(repositorio.findAll());
    }

    // Consultar tarefa por ID
    @GetMapping("/{id}")
    public ResponseEntity<Tarefa> buscarPorId(@PathVariable Long id) {
        return repositorio.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Criar nova tarefa (uma por vez)
    @PostMapping
    public ResponseEntity<Tarefa> criarTarefa(@Valid @RequestBody Tarefa tarefa) {
        Tarefa salva = repositorio.save(tarefa);
        return ResponseEntity.status(HttpStatus.CREATED).body(salva);
    }

    // Criar várias tarefas de uma vez (lote)
    @PostMapping("/lote")
    public ResponseEntity<List<Tarefa>> criarTarefas(@Valid @RequestBody List<Tarefa> tarefas) {
        List<Tarefa> salvas = repositorio.saveAll(tarefas);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvas);
    }

    // Atualizar tarefa existente
    @PutMapping("/{id}")
    public ResponseEntity<Tarefa> atualizarTarefa(@PathVariable Long id, @Valid @RequestBody Tarefa tarefa) {
        Optional<Tarefa> tarefaOpt = repositorio.findById(id);
        if (tarefaOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Tarefa existente = tarefaOpt.get();
        existente.setNomeTarefa(tarefa.getNomeTarefa());
        existente.setDataEntrega(tarefa.getDataEntrega());
        existente.setResponsavel(tarefa.getResponsavel());
        repositorio.save(existente);
        return ResponseEntity.ok(existente);
    }

    // Remover tarefa por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerTarefa(@PathVariable Long id) {
        if (repositorio.existsById(id)) {
            repositorio.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    // Remover todas as tarefas
    @DeleteMapping
    public ResponseEntity<Void> removerTodasTarefas() {
        repositorio.deleteAll();
        return ResponseEntity.noContent().build();
    }
}