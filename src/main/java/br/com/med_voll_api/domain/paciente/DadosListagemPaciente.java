package br.com.med_voll_api.domain.paciente;

public record DadosListagemPaciente(
    Long id,
    String nome,
    String email
) {
     public DadosListagemPaciente(Paciente paciente){
        this(paciente.getId(), paciente.getNome(), paciente.getEmail());
    }
}
