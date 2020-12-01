package br.com.cast.avaliacao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.cast.avaliacao.service.CursoService;
import br.com.cast.avaliacao.service.dto.CursoDto;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/curso")
public class CursoController {
	
	@Autowired
	private CursoService cursoService;
	
	@GetMapping
	public ResponseEntity<Page<CursoDto>> findAll(@Param("assunto") String assunto, Pageable pageable) {
		if (!assunto.isEmpty()) {
			return new ResponseEntity<Page<CursoDto>>(this.cursoService.findByAssuntoContainingIgnoreCase(assunto, pageable), HttpStatus.OK);
		}
		return new ResponseEntity<Page<CursoDto>>(this.cursoService.findAll(pageable), HttpStatus.OK);
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<CursoDto> findById(@PathVariable Long codigo) {
		return new ResponseEntity<CursoDto>(this.cursoService.findById(codigo), HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<CursoDto> save(@Valid @RequestBody CursoDto cursoDto) {
		return new ResponseEntity<CursoDto>(this.cursoService.save(cursoDto), HttpStatus.CREATED);
	}
	
	@PutMapping("/{codigo}")
	public ResponseEntity<CursoDto> update(@PathVariable Long codigo, @Valid @RequestBody CursoDto cursoDto) {
		return new ResponseEntity<CursoDto>(this.cursoService.update(codigo, cursoDto), HttpStatus.OK);
	}
	
	@DeleteMapping("/{codigo}")
	public ResponseEntity<Void> delete(@PathVariable Long codigo) {
		this.cursoService.delete(codigo);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
}
