package com.miempresa.interfacesServicio;

import java.util.List;
import java.util.Optional;

import com.miempresa.modelo.Curso;
import com.miempresa.modelo.Matricula;

public interface IMatriculaServicio {
	public List<Matricula> listar();
	public Optional<Matricula> listarId(int id);
	public int guardar(Matricula matricula);
	public List<Matricula> buscarPorCodigo(String codigo);
	public void matricularUsuario(List<Curso> cursosSeleccionados, Double totalCosto, String correoUsuarioActual);
	public List<Matricula> listarMatriculasPorAlumno(String correoUsuarioActual);
	List<Matricula> buscarMatriculaPorCodigo(String codigo);
	
}
