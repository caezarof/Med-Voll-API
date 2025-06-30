package br.com.med_voll_api.domain.paciente;

import br.com.med_voll_api.domain.dadosComuns.EnderecoDto;
import jakarta.persistence.Column;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record DadosCadastraisPaciente(

        @NotBlank String nome,

        @NotBlank @Email String email,

        @NotNull @NotBlank @Size(max = 20) @Pattern(regexp = "^\\(\\d{2}\\)\\s9\\d{4}-\\d{4}$", message = "Telefone deve seguir o formato (XX) 9XXXX-XXXX") @Column(nullable = false) String telefone,

        @NotNull @Valid EnderecoDto endereco) {

    public DadosCadastraisPaciente(Paciente paciente) {
        this(paciente.getNome(), paciente.getEmail(), paciente.getTelefone(), new EnderecoDto(paciente.getEndereco()));
    }
}
