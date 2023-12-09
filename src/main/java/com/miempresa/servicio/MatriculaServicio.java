package com.miempresa.servicio;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.miempresa.interfaces.IMatricula;
import com.miempresa.interfacesServicio.IMatriculaServicio;
import com.miempresa.modelo.Curso;
import com.miempresa.modelo.Matricula;
import com.miempresa.modelo.MatriculaCurso;
import com.miempresa.modelo.Usuario;

@Service
public class MatriculaServicio implements IMatriculaServicio {
	
	@Autowired
    private IMatricula repo;
	
	@Autowired
	private CursoServicio cursoServicio;

    @Autowired
    private MatriculaCursoServicio matriculaCursoServicio;
    
    @Autowired
    private UsuarioServicio usuarioServicio;

    @Override
    public List<Matricula> listar() {
    	return (List<Matricula>)repo.findAll();
    }

    @Override
    public Optional<Matricula> listarId(int id) {
        return repo.findById(id);
    }

    @Override
    public int guardar(Matricula matricula) {
        String codigoMatricula = generarCodigoMatricula();
        matricula.setCodigo(codigoMatricula);

        Matricula matriculaGuardada = repo.save(matricula);
        return (matriculaGuardada != null) ? 1 : 0;
    }

    private String generarCodigoMatricula() {
        Long cantidadMatriculas = repo.count();
        return "MAT" + (cantidadMatriculas + 1);
    }

    @Override
    public List<Matricula> buscarPorCodigo(String codigo) {
        return repo.findByCodigoContaining(codigo);
    }
    
    @Override
    public void matricularUsuario(List<Curso> cursosSeleccionados, Double totalCosto, String correoUsuarioActual) {
        Optional<Usuario> optionalUsuarioActual = usuarioServicio.buscarPorCorreo(correoUsuarioActual);

        if (optionalUsuarioActual.isPresent()) {
            Usuario usuarioActual = optionalUsuarioActual.get();
            Matricula matricula = new Matricula();
            matricula.setAlumno(usuarioActual);
            matricula.setCosto_total(totalCosto);
            matricula.setFec_matricula(new Date());
            guardar(matricula);
            
            for (Curso curso : cursosSeleccionados) {
                MatriculaCurso matriculaCurso = new MatriculaCurso();
                matriculaCurso.setMatricula(matricula);
                
                Curso cursoPersistente = cursoServicio.obtenerCursoPorId(curso.getId());
                
                matriculaCurso.setCurso(cursoPersistente);
                matriculaCurso.setCosto(cursoPersistente.getCosto());

                matriculaCursoServicio.guardar(matriculaCurso);
            }

        }
    }

    
    @Override
    public List<Matricula> listarMatriculasPorAlumno(String correoUsuarioActual) {
        Optional<Usuario> optionalUsuarioActual = usuarioServicio.buscarPorCorreo(correoUsuarioActual);

        if (optionalUsuarioActual.isPresent()) {
            Usuario alumno = optionalUsuarioActual.get();
            return repo.findByAlumno(alumno);
        } else {
            return Collections.emptyList();
        }
    }

    
    @Override
    public List<Matricula> buscarMatriculaPorCodigo(String codigo) {
        return repo.findByCodigoContaining(codigo);
    }

    
}
