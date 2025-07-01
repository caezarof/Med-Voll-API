package br.com.med_voll_api.domain.consulta.validacoes.cancelamento;

import br.com.med_voll_api.domain.consulta.DadosCancelamentoConsulta;
import jakarta.validation.ValidationException;

public interface IValidadorCancelamentoDeConsulta {
    void validar(DadosCancelamentoConsulta consulta) throws ValidationException;
}
