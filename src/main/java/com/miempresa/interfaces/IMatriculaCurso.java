package com.miempresa.interfaces;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.miempresa.modelo.Curso;
import com.miempresa.modelo.Matricula;
import com.miempresa.modelo.MatriculaCurso;

@Repository
public interface IMatriculaCurso extends JpaRepository<MatriculaCurso, Integer> {
	List<MatriculaCurso> findByMatricula(Matricula matricula);

	List<MatriculaCurso> findByCurso(Curso curso);
	
}