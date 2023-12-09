package com.miempresa.servicio;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.miempresa.interfaces.IUsuario;
import com.miempresa.interfacesServicio.IUsuarioServicio;
import com.miempresa.modelo.Usuario;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


@Service
public class UsuarioServicio implements IUsuarioServicio {
	
	@Autowired
    private IUsuario repo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
    public List<Usuario> listarUsuarios() {
        return repo.findAll();
    }
	
	@Override
    public boolean correoExistente(String correo) {
        return repo.findByCorreo(correo).isPresent();
    }
	
	@Override
    public int guardar(Usuario usuario) {
        if (correoExistente(usuario.getCorreo())) {
            return -1;
        }

        usuario.setContrasenia(passwordEncoder.encode(usuario.getContrasenia()));
        Usuario usuarioGuardado = repo.save(usuario);
        return (usuarioGuardado != null) ? 1 : 0;
    }
    
    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        Usuario usuario = repo.findByCorreo(correo)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + correo));

        return User.withUsername(usuario.getCorreo())
                .password(usuario.getContrasenia())
                .roles(usuario.getRol())
                .build();
    }
    
    @Override
    public Optional<Usuario> buscarPorCorreo(String correo) {
        return repo.findByCorreo(correo);
    }
    
    public void eliminarUsuario(String correo) {
        SecurityContextHolder.clearContext();

        Optional<Usuario> usuarioOptional = repo.findByCorreo(correo);
        usuarioOptional.ifPresent(usuario -> repo.delete(usuario));
    }
}
 