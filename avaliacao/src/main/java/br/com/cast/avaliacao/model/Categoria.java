package br.com.cast.avaliacao.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "categoria")
public class Categoria implements Serializable {

	private static final long serialVersionUID = 1L;

	public Categoria() {

	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "codigo", nullable = false)
	private Long codigo; 
	
	@Column(name = "descricao", nullable = false, length = 50)
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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Categoria)) return false;
		Categoria categoria = (Categoria) o;
		return codigo.equals(categoria.codigo) &&
				descricao.equals(categoria.descricao);
	}

	@Override
	public int hashCode() {
		return Objects.hash(codigo, descricao);
	}
}
