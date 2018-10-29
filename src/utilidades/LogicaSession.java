package utilidades;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import vo.PersonaVo;

public class LogicaSession {
	
	public static String retornaImagenUser(){
		FacesContext context = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession)context.getExternalContext().getSession(true); 
		
		return (String) session.getAttribute("rutaImagen");
	}
	
	public static String retornaTipoUser(){
		FacesContext context = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession)context.getExternalContext().getSession(true); 
		
		return (String) session.getAttribute("tipoUser");
	}
	
	public static PersonaVo retornaUser(){
		FacesContext context = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession)context.getExternalContext().getSession(true); 
		
		return (PersonaVo) session.getAttribute("usuarioLogueado");
	}
	
	//Se crean las variables de sesión
	public static void addSession(PersonaVo persona, String rutaImagenUser, String tipoUser) {
		FacesContext context = FacesContext.getCurrentInstance();
	    HttpSession sesion = (HttpSession)context.getExternalContext().getSession(true);
	    sesion.setAttribute("usuarioLogueado", persona);
	    sesion.setAttribute("rutaImagen", rutaImagenUser);
	    sesion.setAttribute("tipoUser", tipoUser);		
	}

	//Se cierra la sesión
	public static void closeSession(){
	    ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
	    ((HttpSession) ctx.getSession(false)).invalidate();		    
	}
	
	public static String getRutaImagenUser(){
		FacesContext context = FacesContext.getCurrentInstance();
	    HttpSession sesion = (HttpSession)context.getExternalContext().getSession(false);
	    String rutaImagenUser="";
	    System.out.println("RutaImagen sesion: "+sesion);
	    if(sesion!=null){
	    	rutaImagenUser= (String) sesion.getAttribute("rutaImagen");	
	    }
	    
	    return rutaImagenUser;
	}
	
	public static String getTipoUsuario(){
		FacesContext context = FacesContext.getCurrentInstance();
	    HttpSession sesion = (HttpSession)context.getExternalContext().getSession(false);
	    String tipoUser="";
	    System.out.println("tipoUser sesion: "+sesion);
	    if(sesion!=null){
	    	tipoUser= (String) sesion.getAttribute("tipoUser");	
	    }		
	    return tipoUser;
	}

	//Recupera el código del usuario logueado
	public static Integer getUserLog(){
	    FacesContext context = FacesContext.getCurrentInstance();
	    HttpSession sesion = (HttpSession)context.getExternalContext().getSession(false);
	    Integer userLog = (Integer)sesion.getAttribute("userLog");
	    
	    return userLog;
	}

}
