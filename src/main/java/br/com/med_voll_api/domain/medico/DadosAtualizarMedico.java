package br.com.med_voll_api.domain.medico;

import br.com.med_voll_api.domain.dadosComuns.EnderecoDto;

public record DadosAtualizarMedico(
    String telefone,
    String email,
    EnderecoDto endereco
) {

}
