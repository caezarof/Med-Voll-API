package br.com.med_voll_api.domain.dadosComuns;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record EnderecoDto (

    @NotBlank
    String logradouro,

  
    String numero,
    String complemento,

    @NotBlank
    String bairro,

    @NotBlank
    String cidade,

    @NotBlank
    String uf,

    @NotBlank
    @Pattern(regexp = "\\d{8}")
    String cep
){

    public EnderecoDto(Endereco endereco) {
        this(endereco.getLogradouro(), endereco.getNumero(), endereco.getComplemento(), endereco.getBairro(), 
        endereco.getCidade(), endereco.getUf(), endereco.getCep());
    }

}
