package br.com.med_voll_api.domain.consulta;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import br.com.med_voll_api.domain.consulta.validacoes.cancelamento.IValidadorCancelamentoDeConsulta;
import org.springframework.stereotype.Service;

import br.com.med_voll_api.domain.consulta.validacoes.agendamento.IValidadorDeAgendamentoDeConsultas;
import br.com.med_voll_api.domain.medico.Medico;
import br.com.med_voll_api.domain.medico.MedicoService;
import br.com.med_voll_api.domain.paciente.Paciente;
import br.com.med_voll_api.domain.paciente.PacienteService;
import br.com.med_voll_api.repository.ConsultaRepository;
import br.com.med_voll_api.repository.MedicoRepository;
import br.com.med_voll_api.repository.PacienteRepository;
import jakarta.validation.ValidationException;

@Service
public class AgendaDeConsultas {

    private final MedicoRepository medicoRepository;
    private final PacienteRepository pacienteRepository;
    private final ConsultaRepository consultaRepository;
    private final PacienteService pacienteService;
    private final MedicoService medicoService;
    private final List<IValidadorDeAgendamentoDeConsultas> validadores;
    private final List<IValidadorCancelamentoDeConsulta> validadoresDeCancelamento;

    public AgendaDeConsultas(ConsultaRepository consultaRepository, PacienteService pacienteService, MedicoService medicoService, PacienteRepository pacienteRepository, MedicoRepository medicoRepository,
    List<IValidadorDeAgendamentoDeConsultas> validadores, List<IValidadorCancelamentoDeConsulta> validadoresDeCancelamento){
        this.consultaRepository = consultaRepository;
        this.pacienteService = pacienteService;
        this.medicoService = medicoService;
        this.pacienteRepository = pacienteRepository;
        this.medicoRepository = medicoRepository;
        this.validadores = validadores;
        this.validadoresDeCancelamento = validadoresDeCancelamento;
    }

    public DadosDetalhamentoConsulta agendar(DadosAgendamentoConsulta dados){
        Paciente paciente = pacienteService.achaPaciente(dados.idPaciente());
        Medico medico = medicoService.escolherMedico(dados);

        List<String> erros = validadores.stream()
            .map(v -> {
                try {
                    v.validar(dados);
                    return null;
                } catch (ValidationException e) {
                    return e.getMessage();
                }
            }).filter(Objects::nonNull)
                .collect(Collectors.toList());

        if (!erros.isEmpty()) {
            throw new ValidationException("Erros de validação: " + String.join("; ", erros));
        }
        Consulta consulta = new Consulta(paciente, medico, dados.data());
        consultaRepository.save(consulta);
        return new DadosDetalhamentoConsulta(consulta);
    }

    public void cancelar(DadosCancelamentoConsulta dados){
        if (!consultaRepository.existsById(dados.idConsulta())){
            throw new ValidationException("Id da consulta fornecida não existe.");
        }

        validadoresDeCancelamento.forEach(v -> v.validar(dados));
        Consulta consulta = consultaRepository.getReferenceById(dados.idConsulta());
        consulta.cancelar(dados.motivo());
    }
}
