	package com.miempresa.controlador;
	
	import java.util.List;
	import java.util.Optional;
	
	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.security.core.Authentication;
	import org.springframework.security.core.context.SecurityContextHolder;
	import org.springframework.stereotype.Controller;
	import org.springframework.ui.Model;
	import org.springframework.validation.BindingResult;
	import org.springframework.web.bind.annotation.GetMapping;
	import org.springframework.web.bind.annotation.ModelAttribute;
	import org.springframework.web.bind.annotation.PathVariable;
	import org.springframework.web.bind.annotation.PostMapping;
	import org.springframework.web.bind.annotation.RequestMapping;
	import org.springframework.web.bind.annotation.RequestParam;
	import com.miempresa.modelo.Curso;
	import com.miempresa.modelo.Matricula;
	import com.miempresa.modelo.MatriculaCurso;
	import com.miempresa.modelo.Usuario;
	import com.miempresa.servicio.CursoServicio;
	import com.miempresa.servicio.MatriculaCursoServicio;
	import com.miempresa.servicio.MatriculaServicio;
	import com.miempresa.servicio.UsuarioServicio;
	
	import jakarta.servlet.http.HttpServletRequest;
	import jakarta.validation.Valid;
	
	@Controller
	@RequestMapping
	public class controlador {
		
		@Autowired
	    private UsuarioServicio usuarioServicio;
		
		@Autowired
	    private CursoServicio cursoServicio;
		
		@Autowired
	    private MatriculaServicio matriculaServicio;
			
		@Autowired
	    private MatriculaCursoServicio matriculaCursoServicio;
	    
	    private Double totalCosto;
	    
	    private List<Curso> cursosSeleccionados;
		
		@GetMapping("/")
	    public String index() {
	        return "index";
	    }
		
		@GetMapping("/login")
	    public String loginForm() {
	        return "login";
	    }
		
	    @GetMapping("/registro")
	    public String registroForm(Model model) {
	        model.addAttribute("usuario", new Usuario());
	        return "registro";
	    }
	
	    @PostMapping("/registro")
	    public String registroSubmit(@ModelAttribute @Valid Usuario usuario, BindingResult bindingResult, Model model) {
	        if (bindingResult.hasErrors()) {
	            model.addAttribute("usuario", usuario);
	            return "registro";
	        }
	
	        int resultado = usuarioServicio.guardar(usuario);
	        if (resultado == -1) {
	            model.addAttribute("errorCorreoDuplicado", true);
	            return "redirect:/registro?errorCorreoDuplicado=true";
	        }
	
	        model.addAttribute("registroExitoso", true);
	        return "redirect:/login?registroExitoso=true";
	    }
	    
	    @GetMapping("/detallesUsuario")
	    public String detallesUsuario(Model model) {
	        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	        String correoUsuarioActual = authentication.getName();
	        
	        Optional<Usuario> usuarioActual = usuarioServicio.buscarPorCorreo(correoUsuarioActual);
	        usuarioActual.ifPresent(usuario -> model.addAttribute("usuario", usuario));
	
	        return "detallesUsuario";
	    }
	
	    @PostMapping("/eliminarUsuario")
	    public String eliminarUsuario(Model model, HttpServletRequest request) {
	        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	        String correoUsuarioActual = authentication.getName();
	        SecurityContextHolder.clearContext();
	
	        Optional<Usuario> usuarioOptional = usuarioServicio.buscarPorCorreo(correoUsuarioActual);
	        usuarioOptional.ifPresent(usuario -> usuarioServicio.eliminarUsuario(correoUsuarioActual));
	        request.getSession().invalidate();
	
	        return "redirect:/login?logout";
	    }
	
	    @GetMapping("/errorAcceso")
	    public String errorAcceso() {
	        return "errorAcceso";
	    }
	    
	    @GetMapping("/listaUsuarios")
	    public String listaUsuarios(Model model) {
	        List<Usuario> usuarios = usuarioServicio.listarUsuarios();
	        model.addAttribute("usuarios", usuarios);
	        return "listaUsuarios";
	    }
	    
	    @GetMapping("/listaCursos")
	    public String listaCursos(Model model) {
	        List<Curso> cursos = cursoServicio.listarCursos();
			
			int totalVacantes = 0;
			int cursosActivos = 0;
			for (Curso curso : cursos) { totalVacantes += curso.getVacantes();}
			for (Curso curso : cursos) { 
				if (curso.getVacantes() > 0){
					cursosActivos++;
				}
			}

			model.addAttribute("CursosActivos", cursosActivos);
			model.addAttribute("TotalVacantes", totalVacantes);
			model.addAttribute("cursos", cursos);
	        return "listaCursos";
	    }
	    
	    @GetMapping("/listaMatriculas")
	    public String listaMatriculas(Model model) {
	        List<Matricula> matriculas = matriculaServicio.listar();
	        model.addAttribute("matriculas", matriculas);
	        return "listaMatriculas";
	    }
	    
	    @GetMapping("/listaCursosUsuario")
	    public String listaCursosUsuario(Model model) {
	        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	        String correoUsuarioActual = authentication.getName();
			
			
			
			
	        List<Curso> misCursos = cursoServicio.listarCursosPorProfesor(correoUsuarioActual);
			
			int totalVacantes = 0;
			int cursosCompletos = 0;
			for (Curso curso : misCursos) { totalVacantes += curso.getVacantes();}

			for (Curso curso : misCursos) { 
				if (curso.getVacantes() == 0){
					cursosCompletos++;
				}
			}
			
			model.addAttribute("TotalVacantes", totalVacantes);
			model.addAttribute("CursosCompletos", cursosCompletos);
	        model.addAttribute("misCursos", misCursos);
	        return "listaCursosUsuario";
	    }
	    
	    @GetMapping("/listaMatriculasUsuario")
	    public String listaMatriculasUsuario(Model model) {
	        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	        String correoUsuarioActual = authentication.getName();
	
	        List<Matricula> misMatriculas = matriculaServicio.listarMatriculasPorAlumno(correoUsuarioActual);
	        model.addAttribute("misMatriculas", misMatriculas);
	
	        return "listaMatriculasUsuario";
	    }
	    
	    @GetMapping("/listaMatriculasCursoUsuario")
	    public String listaMatriculasCursoUsuario(@RequestParam("codigoMatricula") String codigoMatricula, Model model) {
	        List<Matricula> matriculas = matriculaServicio.buscarPorCodigo(codigoMatricula);
	
	        if (!matriculas.isEmpty()) {
	            Matricula matricula = matriculas.get(0);
	            List<MatriculaCurso> matriculaCursos = matriculaCursoServicio.listarPorMatricula(matricula);
	
	            model.addAttribute("matricula", matricula);
	            model.addAttribute("matriculaCursos", matriculaCursos);
	
	            return "listaMatriculasCursoUsuario";
	        } else {
	            return "redirect:/listaMatriculasUsuario";
	        }
	    }
	    
	    @GetMapping("/formularioCurso")
	    public String mostrarFormularioRegistro(@RequestParam(name = "id", required = false) Integer id, Model model) {
	        if (id != null) {
	            Curso curso = cursoServicio.obtenerCursoPorId(id);
	            model.addAttribute("curso", curso);
	        } else {
	            model.addAttribute("curso", new Curso());
	        }
	        return "formularioCurso";
	    }
	
	    @PostMapping("/guardarCurso")
	    public String guardarCurso(@ModelAttribute("curso") @Valid Curso curso, BindingResult bindingResult, Model model) {
	        if (bindingResult.hasErrors()) {
	            model.addAttribute("curso", curso);
	            return "formularioCurso";
	        }

	        // Antes de guardar el curso, actualiza las entidades relacionadas
	        actualizarEntidadesRelacionadas(curso);

	        int resultado = cursoServicio.guardar(curso);

	        if (resultado == 1) {
	            return "redirect:/listaCursosUsuario";
	        } else {
	            return "redirect:/formularioCurso";
	        }
	    }
	    
	    private void actualizarEntidadesRelacionadas(Curso curso) { 
	        List<MatriculaCurso> matriculaCursos = matriculaCursoServicio.listarPorCurso(curso);
 
	        for (MatriculaCurso matriculaCurso : matriculaCursos) { 
	            matriculaCurso.setCosto(curso.getCosto());
 
	            matriculaCursoServicio.guardarMatriculaCurso(matriculaCurso);
	        }
	    }

	    
	    @GetMapping("/editarCurso/{id}")
	    public String editarCurso(@PathVariable int id, Model model) {
	        Curso curso = cursoServicio.obtenerCursoPorId(id);
	        model.addAttribute("curso", curso);
	        return "formularioCurso";
	    }
	
	    @GetMapping("/eliminarCurso/{id}")
	    public String eliminarCurso(@PathVariable int id) {
	        cursoServicio.eliminar(id);
	        return "redirect:/listaCursosUsuario";
	    }
	    
	    @GetMapping("/seleccionCursos")
	    public String seleccionCursos(Model model) {
	        List<Curso> cursos = cursoServicio.listarCursos();
	        model.addAttribute("cursos", cursos);
	        return "seleccionCursos";
	    }
	
	    @PostMapping("/procesarSeleccion")
	    public String procesarSeleccion(@RequestParam(name = "cursosSeleccionados", required = false) List<Integer> idsCursosSeleccionados, Model model) {
	        if (idsCursosSeleccionados == null || idsCursosSeleccionados.isEmpty()) {
	            List<Curso> cursos = cursoServicio.listarCursos();
	        	model.addAttribute("errorNoCursosSeleccionados", true);
	            model.addAttribute("cursos", cursos);
	            return "seleccionCursos";
	        } else {
	        	cursosSeleccionados = cursoServicio.listarCursosPorIds(idsCursosSeleccionados);
	            totalCosto = cursosSeleccionados.stream().mapToDouble(Curso::getCosto).sum();
	            model.addAttribute("cursosSeleccionados", cursosSeleccionados);
	            model.addAttribute("totalCosto", totalCosto);
	            return "detallesMatricula";
	        }
	    }
	
	    @PostMapping("/matricular")
	    public String matricular(Model model) {
	        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	        String correoUsuarioActual = authentication.getName();
	        matriculaServicio.matricularUsuario(cursosSeleccionados, totalCosto, correoUsuarioActual);
	        model.addAttribute("matriculaExitosa", true);
	        List<Curso> cursos = cursoServicio.listarCursos();
	        model.addAttribute("cursos", cursos);
	        return "seleccionCursos";
	    }
	}