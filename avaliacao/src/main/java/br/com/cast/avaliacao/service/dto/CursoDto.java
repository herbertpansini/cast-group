package br.com.cast.avaliacao.service.dto;

import java.io.Serializable;
import java.time.LocalDate;

public class CursoDto implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Long codigo;
	private String assunto;
	private LocalDate dataInicio;
	private LocalDate dataTermino;
	private Integer quantidadeAlunosPorTurma;
	private CategoriaDto categoria;

	public Long getCodigo() {
		return this.codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getAssunto() {
		return this.assunto;
	}

	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}

	public LocalDate getDataInicio() {
		return this.dataInicio;
	}

	public void setDataInicio(LocalDate dataInicio) {
		this.dataInicio = dataInicio;
	}

	public LocalDate getDataTermino() {
		return this.dataTermino;
	}

	public void setDataTermino(LocalDate dataTermino) {
		this.dataTermino = dataTermino;
	}

	public Integer getQuantidadeAlunosPorTurma() {
		return this.quantidadeAlunosPorTurma;
	}

	public void setQuantidadeAlunosPorTurma(Integer quantidadeAlunosPorTurma) {
		this.quantidadeAlunosPorTurma = quantidadeAlunosPorTurma;
	}

	public CategoriaDto getCategoria() {
		return this.categoria;
	}

	public void setCategoria(CategoriaDto categoria) {
		this.categoria = categoria;
	}
}
