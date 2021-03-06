package br.com.cast.avaliacao.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.cast.avaliacao.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
	
	Page<Categoria> findByDescricaoContainingIgnoreCase(String descricao, Pageable pageable);

	Categoria findByDescricao(String descricao);
}
