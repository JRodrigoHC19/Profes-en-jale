package com.miempresa.interfaces;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.miempresa.modelo.Usuario;

@Repository
public interface IUsuario extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByCorreo(String correo);
	Optional<Usuario> findByNombres(String nombres);
}
