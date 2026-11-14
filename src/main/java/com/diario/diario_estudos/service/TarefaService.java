package com.diario.diario_estudos.service;

import com.diario.diario_estudos.model.Tarefa;
import com.diario.diario_estudos.repository.TarefaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class TarefaService {

    private final TarefaRepository repository;

    public TarefaService(TarefaRepository repository) {
        this.repository = repository;
    }

    public List<Tarefa> listar() {
        return repository.findAll();
    }

    public Tarefa buscarPorId(Long id) {
        return repository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Tarefa não encontrada: " + id));
    }

    public Tarefa salvar(Tarefa tarefa) {
        if (tarefa.getDataEntrega() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A data de entrega é obrigatória.");
        }
        if (tarefa.getStatus() == null) {
            tarefa.setStatus("PENDENTE");
        }
        tarefa.setPrioridade(calcularPrioridade(tarefa.getDataEntrega()));
        return repository.save(tarefa);
    }

    public Tarefa atualizar(Long id, Tarefa dados) {
        Tarefa existente = buscarPorId(id);
        existente.setTitulo(dados.getTitulo());
        existente.setMateria(dados.getMateria());
        existente.setDataEntrega(dados.getDataEntrega());
        if (dados.getStatus() != null) {
            existente.setStatus(dados.getStatus());
        }
        return salvar(existente); 
    }

    public void deletar(Long id) {
        buscarPorId(id); 
        repository.deleteById(id);
    }

    public Tarefa concluir(Long id) {
        Tarefa tarefa = buscarPorId(id);
        tarefa.setStatus("CONCLUIDA");
        return repository.save(tarefa);
    }

    private String calcularPrioridade(LocalDate dataEntrega) {
        long dias = ChronoUnit.DAYS.between(LocalDate.now(), dataEntrega);
        if (dias <= 2) return "ALTA";
        if (dias <= 5) return "MEDIA";
        return "BAIXA";
    }
}