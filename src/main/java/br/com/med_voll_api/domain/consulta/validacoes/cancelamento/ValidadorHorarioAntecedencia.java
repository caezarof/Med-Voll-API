package br.com.med_voll_api.domain.consulta.validacoes.cancelamento;

import br.com.med_voll_api.domain.consulta.Consulta;
import br.com.med_voll_api.domain.consulta.DadosCancelamentoConsulta;
import br.com.med_voll_api.repository.ConsultaRepository;
import jakarta.validation.ValidationException;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component("ValidadorHorarioAntecedenciaCancelamento")
public class ValidadorHorarioAntecedencia implements IValidadorCancelamentoDeConsulta{

    private final ConsultaRepository repository;

    public ValidadorHorarioAntecedencia(ConsultaRepository repository){
        this.repository = repository;
    }
    @Override
    public void validar(DadosCancelamentoConsulta dados) throws ValidationException {
        Consulta consulta = repository.getReferenceById(dados.idConsulta());
        LocalDateTime now = LocalDateTime.now();
        Long diferencaEmHoras = Duration.between(now, consulta.getData()).toHours();

        if (diferencaEmHoras < 24){
            throw new ValidationException("A consulta só pode ser cancelada com antecedência mínima de 24 horas.");
        }
    }
}
