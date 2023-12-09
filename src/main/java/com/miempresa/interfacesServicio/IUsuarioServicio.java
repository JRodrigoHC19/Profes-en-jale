package com.miempresa.interfacesServicio;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetailsService;
import com.miempresa.modelo.Usuario;

public interface IUsuarioServicio extends UserDetailsService {
	public List<Usuario> listarUsuarios();
	public int guardar(Usuario usuario);
	public boolean correoExistente(String correo);
	public Optional<Usuario> buscarPorCorreo(String correo);
	public void eliminarUsuario(String correo);
}
