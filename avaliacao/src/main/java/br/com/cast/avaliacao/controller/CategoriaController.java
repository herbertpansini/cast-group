package br.com.cast.avaliacao.controller;

import br.com.cast.avaliacao.service.CategoriaService;
import br.com.cast.avaliacao.service.dto.CategoriaDto;
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

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/categoria")
public class CategoriaController {

	@Autowired
	private CategoriaService categoriaService;
	

	@GetMapping("/all")
	public ResponseEntity<List<CategoriaDto>> findAll() {
		return new ResponseEntity<List<CategoriaDto>>(this.categoriaService.findAll(), HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<Page<CategoriaDto>> findAll(@Param("descricao") String descricao, Pageable pageable) {
		if (!descricao.isEmpty()) {
			return new ResponseEntity<Page<CategoriaDto>>(this.categoriaService.findByDescricaoContainingIgnoreCase(descricao, pageable), HttpStatus.OK);
		}
		return new ResponseEntity<Page<CategoriaDto>>(this.categoriaService.findAll(pageable), HttpStatus.OK);
	}
	
	@GetMapping("{codigo}")
	public ResponseEntity<CategoriaDto> findById(@PathVariable Long codigo) {
		return new ResponseEntity<CategoriaDto>(this.categoriaService.findById(codigo), HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<CategoriaDto> save(@Valid @RequestBody CategoriaDto categoriaDto) {
		return new ResponseEntity<CategoriaDto>(this.categoriaService.save(categoriaDto), HttpStatus.CREATED);
	}
	
	@PutMapping("{codigo}")
	public ResponseEntity<CategoriaDto> update(@PathVariable Long codigo, @Valid @RequestBody CategoriaDto categoriaDto) {
		return new ResponseEntity<CategoriaDto>(this.categoriaService.update(codigo, categoriaDto), HttpStatus.OK);
	}
	
	@DeleteMapping("{codigo}")
	public ResponseEntity<Void> delete(@PathVariable Long codigo) {
		this.categoriaService.delete(codigo);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
}
