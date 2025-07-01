package br.com.med_voll_api.domain.consulta.validacoes.agendamento;

import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import br.com.med_voll_api.domain.consulta.DadosAgendamentoConsulta;
import jakarta.validation.ValidationException;

@Component("ValidadorHorarioAntecedenciaAgendamento")
public class ValidadorHorarioAntecedencia implements IValidadorDeAgendamentoDeConsultas{

    public void validar(DadosAgendamentoConsulta dados){
        LocalDateTime dataConsulta = dados.data();
        LocalDateTime agora = LocalDateTime.now();

        Long diferencaEmMinutos = Duration.between(agora, dataConsulta).toMinutes();

        if (diferencaEmMinutos < 30) {
            throw new ValidationException("A consulta deve ser agendada com antecedencia de no mínimo 30 minutos.");
        }
    }
}
