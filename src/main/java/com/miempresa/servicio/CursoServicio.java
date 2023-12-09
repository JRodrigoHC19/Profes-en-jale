package com.miempresa.servicio;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.miempresa.interfaces.ICurso;
import com.miempresa.interfacesServicio.ICursoServicio;
import com.miempresa.interfacesServicio.IUsuarioServicio;
import com.miempresa.modelo.Curso; 
import com.miempresa.modelo.Usuario;

@Service
public class CursoServicio implements ICursoServicio {

    @Autowired
    private ICurso repo;

    @Autowired
    private IUsuarioServicio usuarioServicio;

    @Override
    public List<Curso> listarCursos() {
        return repo.findAll();
    }

    @Override
    public List<Curso> listarCursosPorIds(List<Integer> ids) {
        return repo.findAllById(ids);
    }
    
    @Override
    public int guardar(Curso curso) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String correoUsuarioActual = authentication.getName();

        Optional<Usuario> optionalUsuarioActual = usuarioServicio.buscarPorCorreo(correoUsuarioActual);

        if (optionalUsuarioActual.isPresent()) {
            Usuario usuarioActual = optionalUsuarioActual.get();

            curso.setProfesor(usuarioActual);
            Curso cursoGuardado = repo.save(curso);
            return (cursoGuardado != null) ? 1 : 0;
        } else {
            return 0;
        }
    }
    
    @Override
    public List<Curso> listarCursosPorProfesor(String correoProfesor) {
        Optional<Usuario> optionalUsuario = usuarioServicio.buscarPorCorreo(correoProfesor);

        if (optionalUsuario.isPresent()) {
            Usuario profesor = optionalUsuario.get();
            return repo.findByProfesor(profesor);
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    public Curso obtenerCursoPorId(int id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public void eliminar(int id) {
        repo.deleteById(id);
    }
}
