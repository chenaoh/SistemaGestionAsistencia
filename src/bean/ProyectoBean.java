package bean;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import dao.EstudianteDao;
import dao.GrupoDao;
import dao.ProfesorDao;
import dao.ProyectoDao;
import vo.GrupoVo;
import vo.ProfesorVo;
import vo.ProyectoVo;
@ManagedBean
@ViewScoped
public class ProyectoBean {
	
	private ProyectoVo proyecto;
	private String mensajeConfirmacion;
	private ProyectoDao proyectoDao;
	private EstudianteDao estudianteDAO;
	private ArrayList<ProyectoVo> listaProyectos=new ArrayList<>();
	private List<SelectItem> itemGrupos;
	HashMap<String, GrupoVo> mapaGrupos=new HashMap<>();
	private ArrayList<String> proyectos;
	private ArrayList<String> estudiantes;
	private ArrayList<String> nombresEstudiantes;
	private String nombreProyecto;
	private String mensaje;
	EstudianteBean estudianteBean;
	private String filtro;
	private String nombreGrupo;
	private boolean consultaGrupo;
	
	public ProyectoBean(){
		proyecto=new ProyectoVo();
		proyectoDao=new ProyectoDao();
		estudianteDAO = new EstudianteDao();
		itemGrupos=new ArrayList<SelectItem>();
		estudianteBean = new EstudianteBean();
		setProyectos(new ArrayList<>());
		setEstudiantes(new ArrayList<>());
		cargarGrupos();
		cargarProyectos();
		cargarNombreProyectos();
		cargarEstudiantes();
		cargarDatosHashMapProyectos();
		
	}
	
	public void cargarDatosHashMapProyectos() {
		ArrayList<ProyectoVo> listProyectos = proyectoDao.obtenerListaProyecto();
		proyectoDao.cargarDatosHashMapProyectos(listProyectos);
		estudianteBean.cargarDatosHashMapEstudiantes();
	}
	
	public void consultarProyectoNombre(){
		if(filtro.equals("N")){
		setConsultaGrupo(false);
		setListaProyectos(proyectoDao.consultarProyectoNombre(getNombreProyecto()));
		}else if(filtro.equals("G")){
		setConsultaGrupo(true);
		setListaProyectos(proyectoDao.consultarProyectoGrupo(getNombreProyecto()));
		}
	}

	private void cargarEstudiantes() {
		setEstudiantes(estudianteDAO.obtenerNombres());
		
	}

	public void cargarNombreProyectos() {
		setProyectos(proyectoDao.consultarNombresProyectos());
		
	}

	private void cargarGrupos() {
		GrupoDao grupoDao=new GrupoDao();
		ArrayList<GrupoVo> listaGrupos;
		listaGrupos=grupoDao.obtenerListaGrupos();
		
		if (listaGrupos.size()>0) {
				
			for (int i = 0; i < listaGrupos.size(); i++) {
				itemGrupos.add(new SelectItem(listaGrupos.get(i).getCodigoGrupo(),listaGrupos.get(i).getNombreGrupo()));
				mapaGrupos.put(listaGrupos.get(i).getCodigoGrupo(),listaGrupos.get(i));
				System.out.println(listaGrupos.get(i).getCodigoGrupo()+" - "+listaGrupos.get(i).getNombreGrupo());
			}
			
		}else{
			listaGrupos=new ArrayList<>();
			mensajeConfirmacion="Actualmente no existen grupos registrados para asociarlos al Proyecto";
		}
	}
	
	public String obtenerNombreGrupo(String codigoGrupo){
		String nombreGrupo="";
		System.out.println("CODIGO PROYECTO: "+codigoGrupo);
		System.out.println("Mapa: "+mapaGrupos);
		
		if (mapaGrupos.get(codigoGrupo)!=null) {
			nombreGrupo=mapaGrupos.get(codigoGrupo).getNombreGrupo();	
		}else{
			nombreGrupo="El grupo no Existe";
		}
		
		return nombreGrupo;
	}
	
	private void cargarProyectos() {
		System.out.println("INGRESA A CARGAR PROYECTOS");
		listaProyectos.clear();
		listaProyectos=proyectoDao.obtenerListaProyecto();
		
		if (listaProyectos==null) {
			mensajeConfirmacion="No se pudo conectar, verifique que la BD esté iniciada.";
		}
	}
	
	public void registrarProyecto(){
		System.out.println(proyecto.getNumProyecto());
		System.out.println(proyecto.getNombreProyecto());
		System.out.println(proyecto.getDescripcionProyecto());
		System.out.println(proyecto.getCodigoProyecto());
		
		mensajeConfirmacion=proyectoDao.registrarProyecto(proyecto);
		proyecto=new ProyectoVo();
	}
	
	public void eliminarProyecto(ProyectoVo proyecto){
		System.out.println("VA A ELIMINAR PROYECTO");
		mensajeConfirmacion=proyectoDao.eliminarProyecto(proyecto);
		listaProyectos.remove(proyecto);
	}

	public void editarProyecto(ProyectoVo proyecto){
		proyecto.setEditar(true);
		System.out.println("VA A EDITAR PROYECTO");
		
	}
	
	public void guardarProyecto(ProyectoVo proyecto){
		System.out.println("VA A GUARDAR PROYECTO");
		mensajeConfirmacion=proyectoDao.actualizarProyecto(proyecto);
		proyecto.setEditar(false);
		
	}
	
	public void asociarEstudiantes(){
		ArrayList<String> idEstudiante = estudianteDAO.obtenerIdEstudiante(getNombresEstudiantes());
		String res = proyectoDao.consultarAsociacion(idEstudiante);
		if(res.equals("no existe")) {
			int idProyecto = proyectoDao.obtenerIdProyecto(getNombreProyecto());
			registrarAsociacionDeEstudiantes(idEstudiante,idProyecto);
			estudianteBean.cargarEstudiantesAsociados();
		}else {
			setMensaje("Uno o los estudiantes ya se encuentras asociados a un proyecto");
		}
	}
	
	public void desasociarEstudiantes(String nombre){
		String doc = estudianteDAO.obtenerIdUnEstudiante(nombre);
		System.out.println("Documento estudiante***: "+doc);
		
		String res = proyectoDao.desasociarEstudiantes(doc);
		
		if(res.equals("ok")){
			estudianteBean.cargarEstudiantesAsociados();
		}
	}

	private void registrarAsociacionDeEstudiantes(ArrayList<String> idEstudiante, int idProyecto) {
		String res = proyectoDao.registrarAsociacionDeEstudiantes(idEstudiante,idProyecto);
		if(res.equals("ok")){
			setMensaje("Registro Exitoso!!!");
		}else{
			setMensaje("Registro Erroneo");
		}
		
	}

	public ProyectoVo getProyecto() {
		return proyecto;
	}

	public void setProyecto(ProyectoVo proyecto) {
		this.proyecto = proyecto;
	}


	public String getMensajeConfirmacion() {
		return mensajeConfirmacion;
	}



	public void setMensajeConfirmacion(String mensajeConfirmacion) {
		this.mensajeConfirmacion = mensajeConfirmacion;
	}

	public ArrayList<ProyectoVo> getListaProyectos() {
		return listaProyectos;
	}

	public void setListaProyectos(ArrayList<ProyectoVo> listaProyectos) {
		this.listaProyectos = listaProyectos;
	}
	
	public List<SelectItem> getItemGrupos() {
		return itemGrupos;
	}

	public void setItemGrupos(List<SelectItem> itemGrupos) {
		this.itemGrupos = itemGrupos;
	}

	public ArrayList<String> getProyectos() {
		return proyectos;
	}

	public void setProyectos(ArrayList<String> proyectos) {
		this.proyectos = proyectos;
	}

	public ArrayList<String> getEstudiantes() {
		return estudiantes;
	}

	public void setEstudiantes(ArrayList<String> estudiantes) {
		this.estudiantes = estudiantes;
	}

	public String getNombreProyecto() {
		return nombreProyecto;
	}

	public void setNombreProyecto(String nombreProyecto) {
		this.nombreProyecto = nombreProyecto;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public ArrayList<String> getNombresEstudiantes() {
		return nombresEstudiantes;
	}

	public void setNombresEstudiantes(ArrayList<String> nombresEstudiantes) {
		this.nombresEstudiantes = nombresEstudiantes;
	}
	public boolean isConsultaGrupo() {
		return consultaGrupo;
	}

	public void setConsultaGrupo(boolean consultaGrupo) {
		this.consultaGrupo = consultaGrupo;
	}

	public String getNombreGrupo() {
		return nombreGrupo;
	}

	public void setNombreGrupo(String nombreGrupo) {
		this.nombreGrupo = nombreGrupo;
	}

	public String getFiltro() {
		return filtro;
	}

	public void setFiltro(String filtro) {
		this.filtro = filtro;
	}

}
