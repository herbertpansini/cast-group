package br.com.cast.avaliacao.service.mapper;

import org.mapstruct.Mapper;

import br.com.cast.avaliacao.model.Categoria;
import br.com.cast.avaliacao.service.dto.CategoriaDto;

@Mapper(componentModel = "spring")
public interface CategoriaMapper extends EntityMapper<CategoriaDto, Categoria> {

}