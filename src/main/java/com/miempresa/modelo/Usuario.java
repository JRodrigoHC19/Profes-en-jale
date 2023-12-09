package com.miempresa.modelo;


import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "usuario", indexes = {@Index(name = "idx_nombres", columnList = "nombres")})
public class Usuario {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
	
	@Column(name = "nombres")
	@NotBlank(message = "Campo obligatorio")
	@Size(min = 0, max= 60, message = "Los nombres no pueden sobrepasar los 60 caracteres")
    private String nombres;
	
	@Column(name = "apellidos")
	@NotBlank(message = "Campo obligatorio")
	@Size(min = 0, max= 60, message = "Los apellidos no pueden sobrepasar los 60 caracteres")
    private String apellidos;
	
	@Column(name = "dni")
	@NotBlank(message = "Campo obligatorio")
	@Size(min = 0, max= 10, message = "El dni no puede sobrepasar los 10 caracteres")
    private String dni;
	
	@Column(name = "correo")
	@NotBlank(message = "Campo obligatorio")
	@Email(message = "Ingrese un correo valido")
    private String correo;
	
	@Column(name = "contrasenia")
	@NotBlank(message = "Campo obligatorio")
    private String contrasenia;
	
	@Column(name = "genero")
	@NotBlank(message = "Campo obligatorio")
    private String genero;
	
	@Column(name = "direccion")
	@NotBlank(message = "Campo obligatorio")
	@Size(min = 0, max= 60, message = "La direccion no puede sobrepasar los 60 caracteres")
    private String direccion;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "fecha_nac")
    @NotNull(message = "Campo obligatorio")
    private Date fecha_nac;

	@Column(name = "rol")
	@NotBlank(message = "Campo obligatorio")
    private String rol;
	
	@OneToMany(mappedBy = "profesor", cascade = CascadeType.MERGE)
	private List<Curso> cursosDictados;

	@OneToMany(mappedBy = "alumno", cascade = CascadeType.MERGE)
	private List<Matricula> matriculas;

	public Usuario() {
		super();
	}

	public Usuario(int id,
			@NotBlank(message = "Campo obligatorio") @Size(min = 0, max = 60, message = "Los nombres no pueden sobrepasar los 60 caracteres") String nombres,
			@NotBlank(message = "Campo obligatorio") @Size(min = 0, max = 60, message = "Los apellidos no pueden sobrepasar los 60 caracteres") String apellidos,
			@NotBlank(message = "Campo obligatorio") @Size(min = 0, max = 10, message = "El dni no puede sobrepasar los 10 caracteres") String dni,
			@NotBlank(message = "Campo obligatorio") @Email(message = "Ingrese un correo valido") String correo,
			@NotBlank(message = "Campo obligatorio") String contrasenia,
			@NotBlank(message = "Campo obligatorio") String genero,
			@NotBlank(message = "Campo obligatorio") @Size(min = 0, max = 60, message = "La direccion no puede sobrepasar los 60 caracteres") String direccion,
			@NotNull(message = "Campo obligatorio") Date fecha_nac, @NotBlank(message = "Campo obligatorio") String rol,
			List<Curso> cursosDictados, List<Matricula> matriculas) {
		super();
		this.id = id;
		this.nombres = nombres;
		this.apellidos = apellidos;
		this.dni = dni;
		this.correo = correo;
		this.contrasenia = contrasenia;
		this.genero = genero;
		this.direccion = direccion;
		this.fecha_nac = fecha_nac;
		this.rol = rol;
		this.cursosDictados = cursosDictados;
		this.matriculas = matriculas;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getContrasenia() {
		return contrasenia;
	}

	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public Date getFecha_nac() {
		return fecha_nac;
	}

	public void setFecha_nac(Date fecha_nac) {
		this.fecha_nac = fecha_nac;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	public List<Curso> getCursosDictados() {
		return cursosDictados;
	}

	public void setCursosDictados(List<Curso> cursosDictados) {
		this.cursosDictados = cursosDictados;
	}

	public List<Matricula> getMatriculas() {
		return matriculas;
	}

	public void setMatriculas(List<Matricula> matriculas) {
		this.matriculas = matriculas;
	}

	
}
