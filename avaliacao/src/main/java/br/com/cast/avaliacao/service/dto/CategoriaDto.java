package br.com.cast.avaliacao.service.dto;

import java.io.Serializable;

public class CategoriaDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long codigo;
	private String descricao;

	public Long getCodigo() {
		return this.codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}
