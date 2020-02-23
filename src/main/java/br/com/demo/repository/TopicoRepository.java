package br.com.demo.repository;

import br.com.demo.model.Topico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TopicoRepository extends JpaRepository<Topico,Long> {

    // Nome padrao do Spring (Busca o nome dentro do relacionamento curso dentro de Topico
    Page<Topico> findByCursoNome(String nomeCurso, Pageable paginacao);

    // Se quiser utilizar nome de Metodo sem ser o Padrao do Spring
    @Query("SELECT t FROM Topico t WHERE t.curso.nome = :nomeCurso")
    List<Topico> carregaPorNomeCurso(@Param("nomeCurso") String nomeCurso);
}
