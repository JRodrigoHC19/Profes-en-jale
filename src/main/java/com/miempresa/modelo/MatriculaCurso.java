package com.miempresa.modelo;

import jakarta.persistence.*;

@Entity
@Table(name = "matricula_curso",
    indexes = {@Index(name = "idx_matricula_matricula_curso", columnList = "matricula"),
    		  @Index(name = "idx_curso_matricula_curso", columnList = "curso")})
public class MatriculaCurso {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "matricula", referencedColumnName = "id")
	private Matricula matricula;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "curso", referencedColumnName = "id", nullable = false)
	private Curso curso;

    @Column(name = "costo")
    private Double costo;

	public MatriculaCurso() {
		super();
	}

	public MatriculaCurso(int id, Matricula matricula, Curso curso, Double costo) {
		super();
		this.id = id;
		this.matricula = matricula;
		this.curso = curso;
		this.costo = costo;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Matricula getMatricula() {
		return matricula;
	}

	public void setMatricula(Matricula matricula) {
		this.matricula = matricula;
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	public Double getCosto() {
		return costo;
	}

	public void setCosto(Double costo) {
		this.costo = costo;
	}
}

