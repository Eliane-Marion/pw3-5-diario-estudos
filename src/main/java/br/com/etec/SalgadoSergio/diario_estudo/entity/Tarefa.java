package br.com.etec.SalgadoSergio.diario_estudo.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TBL_TAREFA")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tarefa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_TAREFA")
    private Long id;

    @NotBlank(message = "O título é obrigatório")
    @Size(max = 255, message = "O título deve ter no máximo 255 caracteres")
    @Column(name = "TX_TITULO")
    private String titulo;

    @NotBlank(message = "A matéria é obrigatória")
    @Size(max = 255, message = "A matéria deve ter no máximo 255 caracteres")
    @Column(name = "TX_MATERIA")
    private String materia;

    @NotNull(message = "A data de entrega é obrigatória")
    @Column(name = "DT_ENTREGA")
    private LocalDate dataEntrega;

    @Pattern(regexp = "PENDENTE|CONCLUIDA", message = "O status deve ser PENDENTE ou CONCLUIDA")
    @Column(name = "TP_STATUS")
    private String status;

    @Column(name = "TP_PRIORIDADE")
    private String prioridade;

}
