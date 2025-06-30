package br.com.med_voll_api.domain.paciente;

import br.com.med_voll_api.domain.dadosComuns.EnderecoDto;
import jakarta.validation.constraints.NotNull;

public record DadosAtualizarPaciente(
        @NotNull Long id,
        String telefone,
        String email,
        EnderecoDto endereco) {
}
