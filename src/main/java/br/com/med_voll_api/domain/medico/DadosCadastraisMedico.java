package br.com.med_voll_api.domain.medico;

import br.com.med_voll_api.domain.dadosComuns.EnderecoDto;
import jakarta.persistence.Column;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record DadosCadastraisMedico (
    @NotBlank
    String nome,

    @NotBlank
    @Email
    String email,

    @NotNull
    @NotBlank
    @Size(max = 20)
    @Pattern(regexp = "^\\(\\d{2}\\)\\s9\\d{4}-\\d{4}$", message = "Telefone deve seguir o formato (XX) 9XXXX-XXXX")
    @Column(nullable = false)
    String telefone,
    
    @NotBlank
    @Pattern(regexp = "\\d{4,6}", message = "O CPF deve conter exatamente 11 dígitos numéricos.")
    String crm,

    @NotNull
    Especialidade especialidade,
    
    @NotBlank
    String cellphone,

    @NotNull
    @Valid
    EnderecoDto endereco
){
    public DadosCadastraisMedico(Medico medico) {
        this(
            medico.getNome(),
            medico.getEmail(),
            medico.getTelefone(),
            medico.getCrm(),
            medico.getEspecialidade(),
            medico.getCellphone(),
            new EnderecoDto(medico.getEndereco())
        );
    }
}
