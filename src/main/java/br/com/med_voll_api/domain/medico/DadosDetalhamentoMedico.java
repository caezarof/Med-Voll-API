package br.com.med_voll_api.domain.medico;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.med_voll_api.domain.dadosComuns.Endereco;

public record DadosDetalhamentoMedico(
    
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    Long id,
    String nome,
    String email,
    String crm,
    String telefone,
    Especialidade especialidade,
    Endereco endereco
) {

    public DadosDetalhamentoMedico(Medico medico)
    {
        this(medico.getId(), medico.getNome(), medico.getEmail(), medico.getCrm(), medico.getTelefone(), medico.getEspecialidade(), medico.getEndereco());
    }
}
