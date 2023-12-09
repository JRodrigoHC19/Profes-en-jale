package com.miempresa.interfacesServicio;

import java.util.List;

import com.miempresa.modelo.Curso;
import com.miempresa.modelo.Matricula;
import com.miempresa.modelo.MatriculaCurso;

public interface IMatriculaCursoServicio {
	public int guardar(MatriculaCurso matriculaCurso);
	public List<MatriculaCurso> listarPorMatricula(Matricula matricula);
	List<MatriculaCurso> listarPorCurso(Curso curso);
	void guardarMatriculaCurso(MatriculaCurso matriculaCurso);
	
	
}
