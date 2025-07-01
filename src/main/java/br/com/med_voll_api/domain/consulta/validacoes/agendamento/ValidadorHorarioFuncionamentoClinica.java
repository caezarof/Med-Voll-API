package br.com.med_voll_api.domain.consulta.validacoes.agendamento;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import br.com.med_voll_api.domain.consulta.DadosAgendamentoConsulta;
import jakarta.validation.ValidationException;

@Component
public class ValidadorHorarioFuncionamentoClinica implements IValidadorDeAgendamentoDeConsultas{

    public void validar(DadosAgendamentoConsulta dados){
        LocalDateTime dataConsulta = dados.data();
        
        boolean domingo = dataConsulta.getDayOfWeek().equals(DayOfWeek.SUNDAY);
        boolean antesDaAberturaDaClinica = dataConsulta.getHour() < 7;
        boolean depoisDoFuncionamentoDaClinica = dataConsulta.getHour() > 18;

        if (domingo || antesDaAberturaDaClinica || depoisDoFuncionamentoDaClinica) {
            throw new ValidationException("Consulta fora do horário de funcionamento da clínica.");
        }
    }
}
