package br.com.med_voll_api.domain.consulta;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.com.med_voll_api.domain.consulta.validacoes.IValidadorDeAgendamentoDeConsultas;
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

    public AgendaDeConsultas(ConsultaRepository consultaRepository, PacienteService pacienteService, MedicoService medicoService, PacienteRepository pacienteRepository, MedicoRepository medicoRepository,
    List<IValidadorDeAgendamentoDeConsultas> validadores){
        this.consultaRepository = consultaRepository;
        this.pacienteService = pacienteService;
        this.medicoService = medicoService;
        this.pacienteRepository = pacienteRepository;
        this.medicoRepository = medicoRepository;
        this.validadores = validadores;
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
}
