package br.com.cast.avaliacao.service.mapper;

import org.mapstruct.Mapper;

import br.com.cast.avaliacao.model.Curso;
import br.com.cast.avaliacao.service.dto.CursoDto;

@Mapper(componentModel = "spring", uses = {CategoriaMapper.class})
public interface CursoMapper extends EntityMapper<CursoDto, Curso> {

}