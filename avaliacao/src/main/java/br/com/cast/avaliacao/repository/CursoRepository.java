package br.com.cast.avaliacao.repository;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.cast.avaliacao.model.Curso;

public interface CursoRepository extends JpaRepository<Curso, Long> {
	
	Page<Curso> findByAssuntoContainingIgnoreCase(String assunto, Pageable pageable);
	
	@Query("SELECT COUNT(c.codigo) " +
		   "FROM Curso c " + 
		   "WHERE (?1 BETWEEN c.dataInicio AND c.dataTermino) " +
		   "   OR (?2 BETWEEN c.dataInicio AND c.dataTermino)")
	Long findByPeriodo(LocalDate dataInicio, LocalDate dataTermino);
}