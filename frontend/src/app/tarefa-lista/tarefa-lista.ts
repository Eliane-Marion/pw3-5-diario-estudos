import { Component, OnInit, inject, signal } from '@angular/core';
import { DatePipe } from '@angular/common';
import { TarefaService } from '../service/tarefa.service';
import { Tarefa } from '../model/tarefa.model';

@Component({
  selector: 'app-tarefa-lista',
  imports: [DatePipe],
  templateUrl: './tarefa-lista.html',
  styleUrl: './tarefa-lista.css'
})
export class TarefaLista implements OnInit {
  private service = inject(TarefaService);

  tarefas = signal<Tarefa[]>([]);
  erro = signal('');

  ngOnInit(): void {
    this.carregar();
  }

  carregar(): void {
    this.service.listar().subscribe({
      next: (dados) => {
        this.tarefas.set(dados);
        this.erro.set('');
      },
      error: () => this.erro.set('Não foi possível carregar as tarefas. O backend está rodando?')
    });
  }

  concluir(tarefa: Tarefa): void {
    this.service.concluir(tarefa.id!).subscribe({
      next: () => this.carregar(),
      error: () => this.erro.set('Erro ao concluir a tarefa.')
    });
  }

  excluir(tarefa: Tarefa): void {
    if (!confirm(`Excluir a tarefa "${tarefa.titulo}"?`)) return;
    this.service.excluir(tarefa.id!).subscribe({
      next: () => this.carregar(),
      error: () => this.erro.set('Erro ao excluir a tarefa.')
    });
  }
}