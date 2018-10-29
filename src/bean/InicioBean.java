package bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import utilidades.LogicaSession;


@ManagedBean
public class InicioBean {
	
	private String rutaImagenUser;
	private String tipoUser;
	//FacesContext context = FacesContext.getCurrentInstance();
	//HttpSession session = (HttpSession)context.getExternalContext().getSession(true); 
	
	public InicioBean(){

//		rutaImagenUser= (String) session.getAttribute("rutaImagen");
//		tipoUser= (String) session.getAttribute("tipoUser");
		rutaImagenUser= LogicaSession.getRutaImagenUser();		
		tipoUser= LogicaSession.getTipoUsuario();
		System.out.println("RUTA IMAGEN: "+rutaImagenUser);
		System.out.println("TIPO USER: "+tipoUser);
	}
	
	public void metodoInicial(){
		System.out.println("INGRESA AL METODO INICIAL ");
	}
	
	public String redirige(){
		System.out.println("INGRESA AL METODO REDIRIGE ");
		rutaImagenUser= LogicaSession.getRutaImagenUser();		
		tipoUser= LogicaSession.getTipoUsuario();
		System.out.println("RUTA IMAGEN: "+rutaImagenUser);
		System.out.println("TIPO USER: "+tipoUser);
		String pag="";
		if(tipoUser==null){
			pag="login.jsf?faces-redirect=true";
		}else{
			pag="#";
		}
		
		return pag;
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
	
	

}
