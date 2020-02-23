package br.com.demo.repository;

import br.com.demo.model.Curso;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CursoRepository extends JpaRepository<Curso,Long> {

    // Nome padrao do Spring (Busca o nome dentro de Curso
    Curso findByNome(String nome);

}
