package br.com.med_voll_api.controller;

import br.com.med_voll_api.domain.consulta.DadosCancelamentoConsulta;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.med_voll_api.domain.consulta.AgendaDeConsultas;
import br.com.med_voll_api.domain.consulta.DadosAgendamentoConsulta;
import br.com.med_voll_api.domain.consulta.DadosDetalhamentoConsulta;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import java.net.URI;

import org.springframework.http.ResponseEntity;


@RestController
@RequestMapping("/consultas")
@SecurityRequirement(name = "bearer-key")
public class ConsultaController {

    private final AgendaDeConsultas agenda;
    
    public ConsultaController(AgendaDeConsultas agenda){
        this.agenda = agenda;
    }

    @PostMapping("/agendar")
    @Transactional
    public ResponseEntity<DadosDetalhamentoConsulta> agendarConsulta(@RequestBody @Valid DadosAgendamentoConsulta dados, UriComponentsBuilder uriBuilder) {
        DadosDetalhamentoConsulta consulta = agenda.agendar(dados);
        URI uri = uriBuilder.path("/consultas/{id}").buildAndExpand(consulta.id()).toUri();
        return ResponseEntity.created(uri).body(consulta);
    }

    @DeleteMapping("/cancelar")
    @Transactional
    public ResponseEntity<Void> cancelar(@RequestBody @Valid DadosCancelamentoConsulta dados) {
        agenda.cancelar(dados);
        return ResponseEntity.noContent().build();
    }
    
}
