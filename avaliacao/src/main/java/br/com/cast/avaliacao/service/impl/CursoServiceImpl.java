package br.com.cast.avaliacao.service.impl;

import java.time.LocalDate;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import br.com.cast.avaliacao.repository.CursoRepository;
import br.com.cast.avaliacao.service.CursoService;
import br.com.cast.avaliacao.service.dto.CursoDto;
import br.com.cast.avaliacao.service.mapper.CursoMapper;

@Service
@Transactional
public class CursoServiceImpl implements CursoService {
	
	private final CursoRepository cursoRepository;
	private final CursoMapper cursoMapper;

	@Autowired
	public CursoServiceImpl(CursoRepository cursoRepository,
							CursoMapper cursoMapper) {
		this.cursoRepository = cursoRepository;
		this.cursoMapper = cursoMapper;
	}

	@Override
	@Transactional(readOnly = true)
	public Page<CursoDto> findAll(Pageable pageable) {
		return this.cursoRepository.findAll(pageable).map(this.cursoMapper::toDto);
	}
	
	@Override
	public Page<CursoDto> findByAssuntoContainingIgnoreCase(String assunto, Pageable pageable) {
		return this.cursoRepository.findByAssuntoContainingIgnoreCase(assunto, pageable).map(this.cursoMapper::toDto);
	}

	@Override
	@Transactional(readOnly = true)
	public CursoDto findById(Long codigo) {
		return this.cursoMapper.toDto( this.cursoRepository.findById(codigo).orElseThrow(()->
			new ResponseStatusException(HttpStatus.NO_CONTENT, String.format("Curso %d não encontrado", codigo))) );
	}

	@Override
	public CursoDto save(CursoDto cursoDto) {
		if (this.valid(cursoDto)) {
			return this.cursoMapper.toDto( this.cursoRepository.save( this.cursoMapper.toEntity(cursoDto) ) );
		}
		return null;
	}

	@Override
	public CursoDto update(Long codigo, CursoDto cursoDto) {
		CursoDto cursoUpdate = this.findById(codigo);
		BeanUtils.copyProperties(cursoDto, cursoUpdate, "codigo");
		return this.cursoMapper.toDto( this.cursoRepository.save( this.cursoMapper.toEntity(cursoUpdate) ) );
	}

	@Override
	public void delete(Long codigo) {
		this.cursoRepository.deleteById(codigo);
	}
	
	private boolean valid(CursoDto cursoDto) {
		
		if (this.validateDataInicioMenorQueDataAtual(cursoDto.getDataInicio())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("data de início %s menor que a data atual.", cursoDto.getDataInicio().toString()));
		}
		if (this.validateInterval(cursoDto.getDataInicio(), cursoDto.getDataTermino())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Existe(m) curso(s) planejados(s) dentro do período informado.");
		}
		return true;
	}
	
	private boolean validateDataInicioMenorQueDataAtual(LocalDate dataInicio) {
		return dataInicio.isBefore(LocalDate.now());
	}
	
	private boolean validateInterval(LocalDate dataInicio, LocalDate dataTermino) {
		return this.cursoRepository.findByPeriodo(dataInicio, dataTermino) > 0;
	}
}
