package br.com.med_voll_api.domain.consulta.validacoes;

import org.springframework.stereotype.Component;

import br.com.med_voll_api.domain.consulta.DadosAgendamentoConsulta;
import br.com.med_voll_api.repository.MedicoRepository;
import jakarta.validation.ValidationException;

@Component
public class ValidadorMedicoAtivo implements IValidadorDeAgendamentoDeConsultas{

    private final MedicoRepository repository;

    public ValidadorMedicoAtivo(MedicoRepository repository) {
        this.repository = repository;
    }

    public void validar(DadosAgendamentoConsulta dados) {
        if (dados.idMedico() == null) {
            return;
        }

        boolean medicoAtivo = repository.findAtivoById(dados.idMedico());

        if (!medicoAtivo) {
            throw new ValidationException("Médico inativo.");
        }
    }
}
