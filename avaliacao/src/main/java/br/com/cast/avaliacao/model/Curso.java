package br.com.cast.avaliacao.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "curso")
public class Curso implements Serializable {

	private static final long serialVersionUID = 1L;

	public Curso() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "codigo", nullable = false)
	private Long codigo;
	
	@Column(name = "assunto", nullable = false, length = 50)
	private String assunto;
	
	@Column(name = "data_inicio", nullable = false)
	private LocalDate dataInicio;
	
	@Column(name = "data_termino", nullable = false)
	private LocalDate dataTermino;
	
	@Column(name = "quantidade_alunos_por_turma")
	private Integer quantidadeAlunosPorTurma;
	
	@ManyToOne
    @JoinColumn(name = "codigo_categoria", nullable = false)
	private Categoria categoria;

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

	public Categoria getCategoria() {
		return this.categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Curso)) return false;
		Curso curso = (Curso) o;
		return codigo.equals(curso.codigo) &&
				assunto.equals(curso.assunto) &&
				dataInicio.equals(curso.dataInicio) &&
				dataTermino.equals(curso.dataTermino) &&
				quantidadeAlunosPorTurma.equals(curso.quantidadeAlunosPorTurma) &&
				categoria.equals(curso.categoria);
	}

	@Override
	public int hashCode() {
		return Objects.hash(codigo, assunto, dataInicio, dataTermino, quantidadeAlunosPorTurma, categoria);
	}
}
