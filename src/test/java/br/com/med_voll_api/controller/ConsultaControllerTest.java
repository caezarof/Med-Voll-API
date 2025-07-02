package br.com.med_voll_api.controller;

import br.com.med_voll_api.domain.consulta.AgendaDeConsultas;
import br.com.med_voll_api.domain.consulta.DadosAgendamentoConsulta;
import br.com.med_voll_api.domain.consulta.DadosDetalhamentoConsulta;
import br.com.med_voll_api.domain.medico.Especialidade;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class ConsultaControllerTest {

    @Autowired
    private MockMvc mock;

    @Autowired
    private JacksonTester<DadosAgendamentoConsulta> dadosAgendamentoConsulta;

    @Autowired
    private JacksonTester<DadosDetalhamentoConsulta> dadosDetalhamentoConsulta;

    @MockitoBean
    private AgendaDeConsultas agendaDeConsultas;

    @Test
    @DisplayName("Deveria devolver http 400 quando informações inválidas")
    @WithMockUser
    void agendar_01() throws Exception {
        MockHttpServletResponse response = mock.perform(post("/consultas/agendar"))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Deveria devolver http 200 quando informações válidas")
    @WithMockUser
    void agendar_02() throws Exception {
        LocalDateTime data = LocalDateTime.now().plusHours(2);
        Especialidade especialidade = Especialidade.CARDIOLOGIA;

        DadosDetalhamentoConsulta dadosDetalhamento = new DadosDetalhamentoConsulta(null,
                3L, 5L, data);
        when(agendaDeConsultas.agendar(any())).thenReturn(dadosDetalhamento);

        MockHttpServletResponse response = mock.perform(post("/consultas/agendar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dadosAgendamentoConsulta.write(
                                new DadosAgendamentoConsulta(3L, 5L, data, especialidade)
                                ).getJson()))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());

        String jsonEsperado = dadosDetalhamentoConsulta
                .write(new DadosDetalhamentoConsulta(null, 3L, 5L, data)).getJson();

        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
    }
}