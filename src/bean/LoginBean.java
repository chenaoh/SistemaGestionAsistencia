package bean;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import dao.EstudianteDao;
import dao.PersonaDao;
import dao.ProfesorDao;
import utilidades.LogicaSession;
import vo.EstudianteVo;
import vo.PersonaVo;
import vo.ProfesorVo;

@ManagedBean
@RequestScoped
public class LoginBean {
	
	private PersonaVo miPersonaVo;
	private EstudianteVo miEstudianteVo;
	private ProfesorVo miProfesorVo;
	private EstudianteDao miEstudianteDao;
	private ProfesorDao miProfesorDao;
	PersonaDao miPersonaDao;
	private String mensaje;
	private String navegacion;
	private boolean validado=false;
	private String tipoUsuario;
	private String tipoUser;
	private static boolean ver;
	private String rutaImagenUser;
	FacesContext context = FacesContext.getCurrentInstance();
	HttpSession session = (HttpSession)context.getExternalContext().getSession(true);
	
	public LoginBean(){
		miPersonaVo=new PersonaVo();
		miPersonaDao=new PersonaDao();
		
		miEstudianteDao=new EstudianteDao();
		miEstudianteVo=new EstudianteVo();
		
		miProfesorDao=new ProfesorDao();
		miProfesorVo=new ProfesorVo();
				
	}
	
	public String validarIngreso(){
		String resp="";
		
		System.out.println("*****************************************************");
		System.out.println("Documento: "+miPersonaVo.getDocumento());
		System.out.println("Nombre: "+miPersonaVo.getPassword());
		PersonaVo persona=null;
		
		if (tipoUsuario.equals("profesor")) {
			
			System.out.println("Profesor");
			persona=miProfesorDao.consultarProfesorLogin(miPersonaVo.getDocumento(), miPersonaVo.getPassword());
			setVer(true);
			
		}else{
			System.out.println("Estudiante");
			persona=miEstudianteDao.consultarEstudianteLogin(miPersonaVo.getDocumento(), miPersonaVo.getPassword());
			setVer(false);
		}
	
		if (persona!=null) {
		
			resp="inicio.jsf";
			mensaje="";
			miPersonaVo=persona;
			System.out.println("USUARIO VALIDO: "+miPersonaVo.getNombre()+" "+miPersonaVo.getTipo());
			
			if(persona.getSexo().equals("M")){
				
				if (miPersonaVo.getTipo().equals("admin")) {
					setRutaImagenUser("../resources/img/administrator.png");
					setTipoUser("Administrador");	
				}else{
					if (miPersonaVo.getTipo().equals("profesor")) {
						setRutaImagenUser("../resources/img/hombreUser.png");
						setTipoUser("Profesor");
					}else{
						setTipoUser("Estudiante");
						setRutaImagenUser("../resources/img/alumno.png");
					}
				}
			}else{
				setRutaImagenUser("../resources/img/administradora.png");
				if (miPersonaVo.getTipo().equals("admin")) {
					setTipoUser("Administradora");	
				}else{
					if (miPersonaVo.getTipo().equals("profesor")) {
						setRutaImagenUser("../resources/img/profesora.png");
						setTipoUser("Profesora");
					}else{
						setTipoUser("Estudiante");
						setRutaImagenUser("../resources/img/alumna.png");
					}
				}
			}
			
			LogicaSession.addSession(persona, getRutaImagenUser(), getTipoUser());
			
			//session.setAttribute("usuarioLogueado", persona);
			//session.setAttribute("rutaImagen", getRutaImagenUser());
			//session.setAttribute("tipoUser", getTipoUser()	);			
		}else{
			resp="#";
			mensaje="El usuario no es Valido, Verifique nuevamente...";
			System.out.println("USUARIO NO VALIDO");
		}
		System.out.println("*****************************************************");
		return resp;	
	}
	
	public String cerrarSesion(){
		System.out.println("******************************************************");
		String ruta="", tipo="";
		ruta= LogicaSession.getRutaImagenUser();
		tipo= LogicaSession.getTipoUsuario();
		System.out.println("Ruta Antes: "+ruta);
		System.out.println("Tipo Antes: "+tipo);
		System.out.println("******************************************************");

		LogicaSession.closeSession();
		
		ruta= LogicaSession.getRutaImagenUser();
		tipo= LogicaSession.getTipoUsuario();
		System.out.println("Ruta Despues: "+ruta);
		System.out.println("Tipo Despues: "+tipo);
		return "login.jsf?faces-redirect=true";
	}
	
	
	public PersonaVo getMiPersonaVo() {
		return miPersonaVo;
	}

	public void setMiPersonaVo(PersonaVo miPersonaVo) {
		this.miPersonaVo = miPersonaVo;
	}

	public PersonaDao getMiPersonaDao() {
		return miPersonaDao;
	}

	public void setMiPersonaDao(PersonaDao miPersonaDao) {
		this.miPersonaDao = miPersonaDao;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public String getNavegacion() {
		return navegacion;
	}

	public void setNavegacion(String navegacion) {
		this.navegacion = navegacion;
	}

	public boolean isValidado() {
		return validado;
	}

	public void setValidado(boolean validado) {
		this.validado = validado;
	}

	public String getTipoUsuario() {
		return tipoUsuario;
	}

	public void setTipoUsuario(String tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}

	public String getRutaImagenUser() {
		return rutaImagenUser;
	}

	public void setRutaImagenUser(String rutaImagenUser) {
		this.rutaImagenUser = rutaImagenUser;
	}

	public String getTipoUser() {
		return tipoUser;
	}

	public void setTipoUser(String tipoUser) {
		this.tipoUser = tipoUser;
	}

	public boolean isVer() {
		return ver;
	}

	public void setVer(boolean ver) {
		this.ver = ver;
	}

	
}
