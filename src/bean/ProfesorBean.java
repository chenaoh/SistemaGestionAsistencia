package bean;

import java.util.ArrayList;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import dao.ProfesorDao;
import vo.EstudianteVo;
import vo.ProfesorVo;
@ManagedBean
@ViewScoped
public class ProfesorBean {
	
	private String nomProf;
	private ProfesorVo profesor;
	private String mensajeConfirmacion;
	private ProfesorDao profesorDao;
	private ArrayList<ProfesorVo> listaProfesores=new ArrayList<>();
	FacesContext context = FacesContext.getCurrentInstance();
	HttpSession session = (HttpSession)context.getExternalContext().getSession(true);
	LoginBean login  = new LoginBean();

	public ProfesorBean(){
		profesor=new ProfesorVo();
		profesorDao=new ProfesorDao();
		cargarProfesores();
	}
	
	public void filtrarProfesor(){
		System.out.println("nombre: "+nomProf);
		setListaProfesores(profesorDao.filtrarProfesor(getNomProf()));
	}
	
	private void cargarProfesores() {
		System.out.println("INGRESA A CARGAR PROFESORES");
		listaProfesores.clear();
		listaProfesores=profesorDao.obtenerListaProfesores();
		
		if (listaProfesores==null) {
			mensajeConfirmacion="No se pudo conectar, verifique que la BD esté iniciada.";
		}
	}
	
	public void cargarProfesor(){
		System.out.println("INGRESA A CARGAR PROFESOR!!!");
		//FacesContext context = FacesContext.getCurrentInstance();	
		//profesor= (ProfesorVo) context.getExternalContext().getSessionMap().get("profesor");
		
		ProfesorVo profesor= (ProfesorVo) session.getAttribute("profesor");
		
		System.out.println("OBTIENE EL PROFESOR");
		if (profesor!=null) {
			System.out.println("codigo - "+profesor.getDocumento());
			System.out.println("Nombre - "+profesor.getNombre());	
		}else{
			System.out.println("PROFESOR= "+profesor);
		}	
	}
	
	public void registrarProfesor(){
		System.out.println(profesor.getDocumento());
		System.out.println(profesor.getNombre());
		
		profesor.setPassword(profesor.getDocumento());
		profesor.setEstado("Activo");
		mensajeConfirmacion=profesorDao.registrarProfesor(profesor);
		if(mensajeConfirmacion!=null){
			login.calcularPanelEstadisticas();
		}
		profesor=new ProfesorVo();
	}
	
	public String editarProfesor(String documento){
		System.out.println("VA A EDITAR PROFESOR");

		 profesor=profesorDao.obtenerProfesor(documento);
		
			System.out.println("codigo - "+profesor.getDocumento());
			System.out.println("Nombre - "+profesor.getNombre());
		 
			//ExternalContext contexto=FacesContext.getCurrentInstance().getExternalContext();
		//	Map<String, Object> map=contexto.getRequestMap();
		//	map.put("profesor", profesor);
			session.setAttribute("profesor", profesor);
						
		return "editar_profesor.jsf";
	}
	
	
	public String guardarProfesor(ProfesorVo profesor){
		System.out.println("VA A GUARDAR PROFESOR EN METODO 2");
		System.out.println("codigo - "+profesor);
		mensajeConfirmacion=profesorDao.actualizarProfesor(profesor);
		//profesor.setEditar(false);
		return "consulta_profesores.jsf?faces-redirect=true";
	}
	
	public void eliminarProfesor(ProfesorVo profesor){
		System.out.println("VA A ELIMINAR PROFESOR");
		System.out.println("codigo - "+profesor.getDocumento());
		System.out.println("Nombre - "+profesor.getNombre());
		mensajeConfirmacion=profesorDao.eliminarProfesor(profesor.getDocumento());
		listaProfesores.remove(profesor);
	}
	
	public String perfilProfesor(String documento){
		System.out.println("VA A CONSULTAR PERFIL DE UN PROFESOR");
		
		profesor=profesorDao.obtenerProfesor(documento);
		
		System.out.println("codigo - "+profesor.getDocumento());
		System.out.println("Nombre - "+profesor.getNombre());
		
		session.setAttribute("profesor", profesor);
		
		return "perfil_profesor.jsf";
	}

	public ArrayList<ProfesorVo> getListaProfesores() {
		return listaProfesores;
	}

	public void setListaProfesores(ArrayList<ProfesorVo> listaProfesores) {
		this.listaProfesores = listaProfesores;
	}

	public ProfesorVo getProfesor() {
		return profesor;
	}

	public void setProfesor(ProfesorVo profesor) {
		this.profesor = profesor;
	}

	public String getMensajeConfirmacion() {
		return mensajeConfirmacion;
	}


	public void setMensajeConfirmacion(String mensajeConfirmacion) {
		this.mensajeConfirmacion = mensajeConfirmacion;
	}

	public String getNomProf() {
		return nomProf;
	}

	public void setNomProf(String nomProf) {
		this.nomProf = nomProf;
	}
}
