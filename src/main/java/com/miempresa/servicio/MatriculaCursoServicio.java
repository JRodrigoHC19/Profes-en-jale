package com.miempresa.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.miempresa.interfaces.IMatriculaCurso;
import com.miempresa.interfacesServicio.IMatriculaCursoServicio;
import com.miempresa.modelo.Curso;
import com.miempresa.modelo.Matricula;
import com.miempresa.modelo.MatriculaCurso;

@Service
public class MatriculaCursoServicio implements IMatriculaCursoServicio{
	
	@Autowired
    private IMatriculaCurso repo;
	
	@Override
    public int guardar(MatriculaCurso matriculaCurso) {
    	MatriculaCurso matriculacursoGuardada = repo.save(matriculaCurso);
        return (matriculacursoGuardada != null) ? 1 : 0;
    }
	
	@Override
    public List<MatriculaCurso> listarPorMatricula(Matricula matricula) {
        return repo.findByMatricula(matricula);
    }

	@Override
    public List<MatriculaCurso> listarPorCurso(Curso curso) {
        return repo.findByCurso(curso);
    }
	
	@Override
    public void guardarMatriculaCurso(MatriculaCurso matriculaCurso) {
        repo.save(matriculaCurso);
    }
}
