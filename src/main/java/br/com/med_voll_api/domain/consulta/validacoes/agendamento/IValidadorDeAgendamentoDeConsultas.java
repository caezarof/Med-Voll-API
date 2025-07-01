package br.com.med_voll_api.domain.consulta.validacoes.agendamento;

import br.com.med_voll_api.domain.consulta.DadosAgendamentoConsulta;

public interface IValidadorDeAgendamentoDeConsultas {

    void validar(DadosAgendamentoConsulta dados);
}
