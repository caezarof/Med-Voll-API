package br.com.med_voll_api.domain.consulta.validacoes;

import br.com.med_voll_api.domain.consulta.DadosAgendamentoConsulta;
import br.com.med_voll_api.repository.ConsultaRepository;
import jakarta.validation.ValidationException;

public class ValidarPacienteComConsultaMarcadaEmMesmoHorario implements IValidadorDeAgendamentoDeConsultas{

    private final ConsultaRepository repository;

    public ValidarPacienteComConsultaMarcadaEmMesmoHorario(ConsultaRepository repository) {
        this.repository = repository;
    }

    public void validar(DadosAgendamentoConsulta dados) {
        boolean medicoPossuiConsultaMarcadaEmMesmoHorario = repository.existsByPacienteIdAndData(dados.idPaciente(),
                dados.data());

        if (medicoPossuiConsultaMarcadaEmMesmoHorario) {
            throw new ValidationException("O paciente escolhido já possui consulta marcada no horário desejado.");
        }
    }
}
