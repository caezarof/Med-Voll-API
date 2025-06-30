package br.com.med_voll_api.controller;

import java.net.URI;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.med_voll_api.domain.paciente.DadosAtualizarPaciente;
import br.com.med_voll_api.domain.paciente.DadosCadastraisPaciente;
import br.com.med_voll_api.domain.paciente.DadosDetalhamentoPaciente;
import br.com.med_voll_api.domain.paciente.DadosListagemPaciente;
import br.com.med_voll_api.domain.paciente.Paciente;
import br.com.med_voll_api.domain.paciente.PacienteService;
import br.com.med_voll_api.repository.PacienteRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping({ "/pacientes", "/pacientes/" })
public class PacienteController {

    private PacienteRepository repository;
    private final PacienteService service;

    public PacienteController(PacienteRepository repository, PacienteService service) {
        this.repository = repository;
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosDetalhamentoPaciente> getPaciente(@PathVariable @Positive Long id) {
        Paciente paciente = service.achaPaciente(id);
        return ResponseEntity.ok(new DadosDetalhamentoPaciente(paciente));
    }

    @GetMapping("/listar")
    public ResponseEntity<Page<DadosListagemPaciente>> listarPacientes() {
        return listarPacientes(0);
    }

    @GetMapping("/listar/{page}") // (@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao)
    public ResponseEntity<Page<DadosListagemPaciente>> listarPacientes(@PathVariable @PositiveOrZero int page) {
        Pageable paginacao = PageRequest.of(page, 10, Sort.by("nome"));
        Page<DadosListagemPaciente> pacientes = repository.findAllByAtivoTrue(paginacao)
                .map(DadosListagemPaciente::new);
        return ResponseEntity.ok(pacientes);
    }

    @Transactional
    @PostMapping("/cadastrar")
    public ResponseEntity<DadosDetalhamentoPaciente> cadastrarPaciente(
            @Valid @RequestBody DadosCadastraisPaciente dados, UriComponentsBuilder uriBuilder) {
        Paciente paciente = new Paciente(dados);
        repository.save(paciente);
        URI uri = uriBuilder.path("/pacientes/{id}").buildAndExpand(paciente.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosDetalhamentoPaciente(paciente));
    }

    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<DadosDetalhamentoPaciente> atualizarPaciente(@PathVariable @Positive Long id,
            @RequestBody @Valid DadosAtualizarPaciente dados) {
                Paciente paciente = service.achaPaciente(id);
        paciente.atualizarInformacoes(dados);
        return ResponseEntity.ok(new DadosDetalhamentoPaciente(paciente));
    }
    
    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPaciente(@PathVariable @Positive Long id){
        Paciente paciente = service.achaPaciente(id);
        paciente.excluir();
        return ResponseEntity.noContent().build();
    }

}
