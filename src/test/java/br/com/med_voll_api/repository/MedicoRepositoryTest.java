package br.com.med_voll_api.repository;

import br.com.med_voll_api.domain.consulta.Consulta;
import br.com.med_voll_api.domain.dadosComuns.EnderecoDto;
import br.com.med_voll_api.domain.medico.DadosCadastraisMedico;
import br.com.med_voll_api.domain.medico.Especialidade;
import br.com.med_voll_api.domain.medico.Medico;
import br.com.med_voll_api.domain.paciente.DadosCadastraisPaciente;
import br.com.med_voll_api.domain.paciente.Paciente;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class MedicoRepositoryTest {

    @Autowired
    private MedicoRepository repository;
    @Autowired
    private TestEntityManager en;

    @Test
    @DisplayName("Deveria devolver null quando único médico cadastrado não está disponível na data")
    void escolherMedicoAleatorioLivreNaData_01() {
        //given ou arrange
        LocalDateTime proximaSegundaAs10 = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10, 0);

        Medico medico = cadastrarMedico("Medico", "medico@voll.med", "123456", Especialidade.CARDIOLOGIA);
        Paciente paciente = cadastrarPaciente("Paciente", "paciente@email.com", "12345678901");
        cadastrarConsulta(medico, paciente, proximaSegundaAs10);

        //when ou act
        Medico medicoLivre = repository.escolherMedicoAleatorioLivreNaData(Especialidade.CARDIOLOGIA, proximaSegundaAs10);

        //then  ou assert
        assertThat(medicoLivre).isNull();
    }

    @Test
    @DisplayName("Deveria devolver médico quando ele estiver disponível na data")
    void escolherMedicoAleatorioLivreNaData_02() {
        //given ou arrange
        LocalDateTime proximaSegundaAs10 = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10, 0);

        Medico medico = cadastrarMedico("Medico", "medico@voll.med", "123456", Especialidade.CARDIOLOGIA);

        //when ou act
        Medico medicoLivre = repository.escolherMedicoAleatorioLivreNaData(Especialidade.CARDIOLOGIA, proximaSegundaAs10);

        //then ou assert
        assertThat(medicoLivre).isEqualTo(medico);
    }



    private Medico cadastrarMedico(String nome, String mail, String crm, Especialidade especialidade) {
        Medico medico = new Medico(dadosMedico(nome, mail, crm, especialidade));
        en.persist(medico);
        return medico;
    }

    private Paciente cadastrarPaciente(String nome, String mail, String telefone){
        Paciente paciente = new Paciente(dadosPaciente(nome, mail, telefone));
        en.persist(paciente);
        return paciente;
    }

    private void cadastrarConsulta(Medico medico, Paciente paciente, LocalDateTime proximaSegundaAs10){
        Consulta consulta = new Consulta(null, medico, paciente, proximaSegundaAs10, null);
        en.persist(consulta);
    }

    private DadosCadastraisMedico dadosMedico(String nome, String mail, String crm, Especialidade especialidade){
        return new DadosCadastraisMedico(
                nome,
                mail,
                "12345678901",
                crm,
                especialidade,
                "123456789",
                dadosEndereco()
        );
    }
    private DadosCadastraisPaciente dadosPaciente(String nome, String mail, String telefone) {
        return new DadosCadastraisPaciente(
                nome,
                mail,
                telefone,
                dadosEndereco()
        );
    }

    private EnderecoDto dadosEndereco() {
        return new EnderecoDto(
                "rua xpto",
                "123",
                "bloco a",
                "centro",
                "juiz de fora",
                "mg",
                "12345678"
        );
    }


}