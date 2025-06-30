package br.com.med_voll_api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.med_voll_api.domain.consulta.AgendaDeConsultas;
import br.com.med_voll_api.domain.consulta.DadosAgendamentoConsulta;
import br.com.med_voll_api.domain.consulta.DadosDetalhamentoConsulta;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/consultas")
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
    
}
