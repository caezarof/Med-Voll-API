package br.com.med_voll_api.domain.medico;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import br.com.med_voll_api.domain.consulta.DadosAgendamentoConsulta;
import br.com.med_voll_api.repository.MedicoRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;

@Service
public class MedicoService {

    private final MedicoRepository repository;

    public MedicoService(MedicoRepository repository){
        this.repository = repository;
    }
    public Medico achaMedico(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Médico não encontrado."));
    }
    public Medico escolherMedico(DadosAgendamentoConsulta dados) {

        if (dados.idMedico() != null) {
            return achaMedico(dados.idMedico());
        }

        if (dados.especialidade() == null) {
            throw new ValidationException("Em caso de não atribuição de um médico, é necessário informar uma especialidade.");
        }

        if (!medicoDisponivelNaData(dados.data(), dados.especialidade())) {
            throw new ValidationException("Não há nenhum médico disponível nessa data.");
        }

        return repository.escolherMedicoAleatorioLivreNaData(dados.especialidade(), dados.data());
    }

    public boolean medicoDisponivelNaData(LocalDateTime data, Especialidade especialidade) {
        return repository.checarExisteMedicoDisponivelNaData(data, especialidade);
    }

}
