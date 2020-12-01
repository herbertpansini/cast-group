package br.com.cast.avaliacao.service.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import br.com.cast.avaliacao.model.Categoria;
import br.com.cast.avaliacao.repository.CategoriaRepository;
import br.com.cast.avaliacao.service.CategoriaService;
import br.com.cast.avaliacao.service.dto.CategoriaDto;
import br.com.cast.avaliacao.service.mapper.CategoriaMapper;

@Service
@Transactional
public class CategoriaServiceImpl implements CategoriaService {
	
	private final CategoriaRepository categoriaRepository;
	private final CategoriaMapper categoriaMapper;

	@Autowired
	public CategoriaServiceImpl(CategoriaRepository categoriaRepository,
								CategoriaMapper categoriaMapper) {
		this.categoriaRepository = categoriaRepository;
		this.categoriaMapper = categoriaMapper;
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<CategoriaDto> findAll() {
		return this.categoriaMapper.toDto( this.categoriaRepository.findAll() );
	}

	@Override
	@Transactional(readOnly = true)
	public Page<CategoriaDto> findAll(Pageable pageable) {
		return this.categoriaRepository.findAll(pageable).map(this.categoriaMapper::toDto);
	}
	
	@Override
	public Page<CategoriaDto> findByDescricaoContainingIgnoreCase(String descricao, Pageable pageable) {
		return this.categoriaRepository.findByDescricaoContainingIgnoreCase(descricao, pageable).map(this.categoriaMapper::toDto);
	}

	@Override
	@Transactional(readOnly = true)
	public CategoriaDto findById(Long codigo) {
		return this.categoriaMapper.toDto( this.categoriaRepository.findById(codigo).orElseThrow(()->
			new ResponseStatusException(HttpStatus.NO_CONTENT, String.format("Categoria %d não encontrada.", codigo))) );
	}

	@Override
	public CategoriaDto save(CategoriaDto categoriaDto) {
		Categoria categoria = this.categoriaMapper.toEntity(categoriaDto);
		return this.categoriaMapper.toDto( this.categoriaRepository.save(categoria) );
	}

	@Override
	public CategoriaDto update(Long codigo, CategoriaDto categoriaDto) {
		CategoriaDto categoriaUpdate = this.findById(codigo);
		BeanUtils.copyProperties(categoriaDto, categoriaUpdate, "codigo");
		return this.categoriaMapper.toDto( this.categoriaRepository.save( this.categoriaMapper.toEntity(categoriaUpdate) ) );
	}

	@Override
	public void delete(Long codigo) {
		this.categoriaRepository.deleteById(codigo);
	}	
}
