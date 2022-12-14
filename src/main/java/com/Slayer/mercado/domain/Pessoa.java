package com.Slayer.mercado.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.validator.constraints.br.CPF;

import com.Slayer.mercado.domain.enums.Nivel;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public abstract class Pessoa implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Integer id;
	protected String nome;
	@JsonFormat(pattern = "dd/MM/yyyy")
	protected LocalDate dataCriacao = LocalDate.now();
	@CPF
	@Column(unique = true)
	protected String cpf;

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "NIVEL")
	protected Set<Integer> nivel = new HashSet<>();

	
	public Pessoa() {
		addNivel(Nivel.FUNCIONARIO);
	}

	public Pessoa(Integer id, String nome, @CPF String cpf) {
		super();
		this.id = id;
		this.nome = nome;
		this.cpf = cpf;
		addNivel(Nivel.FUNCIONARIO);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Set<Nivel> getNivel() {
		return nivel.stream().map(x -> Nivel.toEnum(x)).collect(Collectors.toSet());
	}

	public void addNivel(Nivel nivel) {
		this.nivel.add(nivel.getCodigo());
	}

	public LocalDate getDataCriacao() {
		return dataCriacao;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pessoa other = (Pessoa) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
