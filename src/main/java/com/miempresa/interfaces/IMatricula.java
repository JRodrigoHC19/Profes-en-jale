package com.miempresa.interfaces;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.miempresa.modelo.Matricula;
import com.miempresa.modelo.Usuario;

@Repository
public interface IMatricula extends JpaRepository<Matricula, Integer> {
	List<Matricula> findByCodigoContaining(String codigo);
	List<Matricula> findByAlumno(Usuario alumno);
	
}