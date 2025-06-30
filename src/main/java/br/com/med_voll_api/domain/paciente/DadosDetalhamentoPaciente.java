package br.com.med_voll_api.domain.paciente;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.med_voll_api.domain.dadosComuns.Endereco;

public record DadosDetalhamentoPaciente(    
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    Long id,
    String nome,
    String email,
    String telefone,
    Endereco endereco) {

        public DadosDetalhamentoPaciente(Paciente medico)
    {
        this(medico.getId(), medico.getNome(), medico.getEmail(), medico.getTelefone(), medico.getEndereco());
    }
}
