package br.com.med_voll_api.repository;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.med_voll_api.domain.medico.Especialidade;
import br.com.med_voll_api.domain.medico.Medico;

public interface MedicoRepository extends JpaRepository<Medico, Long>{

    Page<Medico> findAllByAtivoTrue(Pageable paginacao);

    @Query("""
            SELECT m from Medico m
            WHERE m.especialidade = :especialidade
            AND m.id not in(
                SELECT c.medico.id FROM Consulta c
                WHERE c.data = :data
            )
            ORDER BY rand()
            LIMIT 1
            """)
    Medico escolherMedicoAleatorioLivreNaData(Especialidade especialidade, LocalDateTime data);


    @Query("""
            SELECT m.ativo
            FROM Medico m
            WHERE m.id = :idMedico
            """)
    boolean findAtivoById(Long idMedico);

    @Query("""
            SELECT CASE WHEN COUNT(m) > 0 THEN true ELSE false END
            FROM Medico m 
            WHERE m.ativo = true
            AND m.especialidade = :especialidade
            AND m.id NOT IN(
            Select c.medico.id
            FROM Consulta c
            WHERE c.data = :data
            )
            """)
        //AND c.motivoCancelamento is NULL
    boolean checarExisteMedicoDisponivelNaData(LocalDateTime data, Especialidade especialidade);
}
