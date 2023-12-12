package com.miempresa.modelo;

import jakarta.persistence.*;
import java.util.Date; 

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "matricula",
    indexes = {@Index(name = "idx_alumno_matricula", columnList = "alumno"),
               @Index(name = "idx_codigo_matricula", columnList = "codigo")})
public class Matricula {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

	@Column(name = "codigo")
    private String codigo;
	
	@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "alumno", referencedColumnName = "nombres")
    private Usuario alumno;

    @Column(name = "costo_total")
    private Double costo_total;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "fec_matricula")
    private Date fec_matricula;

	public Matricula() {
		super();
	}

	public Matricula(int id, String codigo, Usuario alumno, Double costo_total, Date fec_matricula) {
		super();
		this.id = id;
		this.codigo = codigo;
		this.alumno = alumno;
		this.costo_total = costo_total;
		this.fec_matricula = fec_matricula;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public Usuario getAlumno() {
		return alumno;
	}

	public void setAlumno(Usuario alumno) {
		this.alumno = alumno;
	}

	public Double getCosto_total() {
		return costo_total;
	}

	public void setCosto_total(Double costo_total) {
		this.costo_total = costo_total;
	}

	public Date getFec_matricula() {
		return fec_matricula;
	}

	public void setFec_matricula(Date fec_matricula) {
		this.fec_matricula = fec_matricula;
	}
}