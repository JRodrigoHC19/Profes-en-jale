package com.miempresa.modelo;

import java.util.Date; 

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "curso",
    indexes = {@Index(name = "idx_profesor_curso", columnList = "profesor"),
    		   @Index(name = "idx_nombre_curso", columnList = "nombre") })
public class Curso {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "profesor", referencedColumnName = "id", nullable = false)
	private Usuario profesor;

    @Column(name = "nombre")
	@NotBlank(message = "Campo obligatorio")
    @Size(min = 0, max= 30, message = "El nombre no pueden sobrepasar los 30 caracteres")
    private String nombre;
    
    @Column(name = "descripcion")
	@NotBlank(message = "Campo obligatorio")
    @Size(min = 0, max= 200, message = "La descripcion no puede sobrepasar los 200 caracteres")
    private String descripcion;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "fec_ini")
    @NotNull(message = "Campo obligatorio")
    private Date fec_ini;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "fec_fin")
    @NotNull(message = "Campo obligatorio")
    private Date fec_fin;
    
    @Column(name = "costo")
    @NotNull(message = "Campo obligatorio")
    private Double costo;
    
    @Column(name = "vacantes")
    @NotNull(message = "Campo obligatorio")
    private Integer vacantes;

	public Curso() {
		super();
	}

	public Curso(int id, Usuario profesor,
			@NotBlank(message = "Campo obligatorio") @Size(min = 0, max = 30, message = "El nombre no pueden sobrepasar los 30 caracteres") String nombre,
			@NotBlank(message = "Campo obligatorio") @Size(min = 0, max = 200, message = "La descripcion no puede sobrepasar los 200 caracteres") String descripcion,
			@NotNull(message = "Campo obligatorio") Date fec_ini, @NotNull(message = "Campo obligatorio") Date fec_fin,
			@NotNull(message = "Campo obligatorio") Double costo,
			@NotNull(message = "Campo obligatorio") Integer vacantes) {
		super();
		this.id = id;
		this.profesor = profesor;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.fec_ini = fec_ini;
		this.fec_fin = fec_fin;
		this.costo = costo;
		this.vacantes = vacantes;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Usuario getProfesor() {
		return profesor;
	}

	public void setProfesor(Usuario profesor) {
		this.profesor = profesor;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Date getFec_ini() {
		return fec_ini;
	}

	public void setFec_ini(Date fec_ini) {
		this.fec_ini = fec_ini;
	}

	public Date getFec_fin() {
		return fec_fin;
	}

	public void setFec_fin(Date fec_fin) {
		this.fec_fin = fec_fin;
	}

	public Double getCosto() {
		return costo;
	}

	public void setCosto(Double costo) {
		this.costo = costo;
	}

	public Integer getVacantes() {
		return vacantes;
	}

	public void setVacantes(Integer vacantes) {
		this.vacantes = vacantes;
	}
}