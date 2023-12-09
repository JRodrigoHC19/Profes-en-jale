package com.miempresa.interfacesServicio;

import java.util.List;
import com.miempresa.modelo.Curso;

public interface ICursoServicio {
	public List<Curso> listarCursos();
	public List<Curso> listarCursosPorIds(List<Integer> ids);
	public int guardar(Curso curso);
	public List<Curso> listarCursosPorProfesor(String correoProfesor);
	public Curso obtenerCursoPorId(int id);
	public void eliminar(int id);
}
