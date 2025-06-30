package br.com.med_voll_api.domain.consulta.validacoes;

import org.springframework.stereotype.Component;

import br.com.med_voll_api.domain.consulta.DadosAgendamentoConsulta;
import br.com.med_voll_api.repository.ConsultaRepository;
import jakarta.validation.ValidationException;

@Component
public class ValidarMedicoComConsultaMarcadaEmMesmoHorario implements IValidadorDeAgendamentoDeConsultas{
    private final ConsultaRepository repository;

    public ValidarMedicoComConsultaMarcadaEmMesmoHorario(ConsultaRepository repository) {
        this.repository = repository;
    }

    public void validar(DadosAgendamentoConsulta dados){
        boolean medicoPossuiConsultaMarcadaEmMesmoHorario = repository.existsByMedicoIdAndData(dados.idMedico(), dados.data());

        if (medicoPossuiConsultaMarcadaEmMesmoHorario) {
            throw new ValidationException("O médico escolhido já possui consulta marcada no horário desejado.");
        }
    }
}
