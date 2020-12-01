package br.com.cast.avaliacao.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.cast.avaliacao.service.dto.CursoDto;

public interface CursoService {
	
	Page<CursoDto> findAll(Pageable pageable);
	
	Page<CursoDto> findByAssuntoContainingIgnoreCase(String assunto, Pageable pageable);

	CursoDto findById(Long codigo);

	CursoDto save(CursoDto cursoDto);

	CursoDto update(Long codigo, CursoDto cursoDto);
   
	void delete(Long codigo);
}