package br.com.med_voll_api.domain.consulta.validacoes.agendamento;

import org.springframework.stereotype.Component;

import br.com.med_voll_api.domain.consulta.DadosAgendamentoConsulta;
import br.com.med_voll_api.repository.PacienteRepository;
import jakarta.validation.ValidationException;

@Component
public class ValidadorPacienteAtivo implements IValidadorDeAgendamentoDeConsultas{

    private final PacienteRepository repository;

    public ValidadorPacienteAtivo(PacienteRepository repository){
        this.repository = repository;
    }
    
    public void validar(DadosAgendamentoConsulta dados){
        if (dados.idPaciente() == null) {
            return;
        }
        boolean pacienteAtivo = repository.findAtivoById(dados.idPaciente());

        if (!pacienteAtivo) {
            throw new ValidationException("Paciente inativo.");
        }
    }
}
