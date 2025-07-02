package br.com.med_voll_api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.med_voll_api.domain.medico.DadosAtualizarMedico;
import br.com.med_voll_api.domain.medico.DadosCadastraisMedico;
import br.com.med_voll_api.domain.medico.DadosDetalhamentoMedico;
import br.com.med_voll_api.domain.medico.DadosListagemMedico;
import br.com.med_voll_api.domain.medico.Medico;
import br.com.med_voll_api.domain.medico.MedicoService;
import br.com.med_voll_api.repository.MedicoRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

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
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping({ "/medicos", "/medicos/" })
@SecurityRequirement(name = "bearer-key")
public class MedicoController {

    private final MedicoRepository repository;
    private final MedicoService service;
    
    public MedicoController(MedicoRepository repository, MedicoService service) {
        this.repository = repository;
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosDetalhamentoMedico> getMedico(@PathVariable @Positive Long id) {
        Medico medico = service.achaMedico(id);

        return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
    }

    @GetMapping("/listar")
    public ResponseEntity<Page<DadosListagemMedico>> listar() {
        return listar(0);
    }

    @GetMapping("/listar/{page}") // (@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao)
    public ResponseEntity<Page<DadosListagemMedico>> listar(@PathVariable @PositiveOrZero int page) {
        Pageable paginacao = PageRequest.of(page, 10, Sort.by("nome"));
        Page<DadosListagemMedico> medicos = repository.findAllByAtivoTrue(paginacao).map(DadosListagemMedico::new);
        return ResponseEntity.ok(medicos);
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMedico(@PathVariable @Positive Long id) {
        Medico medico = service.achaMedico(id);
        medico.excluir();
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/cadastrar")
    @Transactional
    public ResponseEntity<DadosDetalhamentoMedico> criarMedico(@Valid @RequestBody DadosCadastraisMedico dados,
            UriComponentsBuilder uriBuilder) {
        Medico medico = new Medico(dados);
        repository.save(medico);
        URI uri = uriBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosDetalhamentoMedico(medico));
    }

    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<DadosDetalhamentoMedico> atualizar(@RequestBody @Valid DadosAtualizarMedico dados,
            @PathVariable @Positive Long id) {
        Medico medico = service.achaMedico(id);
        medico.atualizarInformacoes(dados);
        return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
    }
}
