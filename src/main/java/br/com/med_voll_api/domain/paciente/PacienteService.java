package br.com.med_voll_api.domain.paciente;

import org.springframework.stereotype.Service;

import br.com.med_voll_api.repository.PacienteRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class PacienteService {

    private final PacienteRepository pacienteRepository;

    public PacienteService(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    public Paciente achaPaciente(Long id){
        return pacienteRepository.findById(id).orElseThrow(() -> 
                            new EntityNotFoundException("Paciente não encontrado"));
    }

}
