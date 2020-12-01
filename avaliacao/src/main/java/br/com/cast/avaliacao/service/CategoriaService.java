package br.com.cast.avaliacao.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.cast.avaliacao.service.dto.CategoriaDto;

public interface CategoriaService {
	
	List<CategoriaDto> findAll();
	
	Page<CategoriaDto> findAll(Pageable pageable);
	
	Page<CategoriaDto> findByDescricaoContainingIgnoreCase(String descricao, Pageable pageable);

	CategoriaDto findById(Long codigo);

	CategoriaDto save(CategoriaDto categoriaDto);

	CategoriaDto update(Long codigo, CategoriaDto categoriaDto);
   
	void delete(Long codigo);
}