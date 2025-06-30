package br.com.med_voll_api.domain.consulta;

import java.time.LocalDateTime;

import br.com.med_voll_api.domain.medico.Especialidade;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

public record DadosAgendamentoConsulta(
    Long idMedico,

    @NotNull
    Long idPaciente,

    @NotNull
    @Future
    LocalDateTime data,
    
    Especialidade especialidade
) {

}
