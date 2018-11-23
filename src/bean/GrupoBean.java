package bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;

import dao.EstudianteDao;
import dao.GrupoDao;
import dao.PersonaDao;
import dao.ProfesorDao;
import vo.EstudianteVo;
import vo.GrupoVo;
import vo.ProfesorVo;
//import vo.ProyectoVo;
@ManagedBean
@SessionScoped
public class GrupoBean implements Serializable{

	private String nombreGrupo;
	private String nombreGrupoA;//nombre grupo asociado
	private GrupoVo grupo;
	private String fechaInicio;
	private String fechaFin;
	private String mensajeConfirmacion;
	private ArrayList<String> grupos;
	private GrupoDao grupoDao;
	private EstudianteDao estudianteDao;
	EstudianteBean estudianteBean;
	private ArrayList<String> nombresEstudiantes;
	private ArrayList<String> estudiantes;
	private String grupoId;
	private String mensaje;
	FacesContext context = FacesContext.getCurrentInstance();
	HttpSession session = (HttpSession)context.getExternalContext().getSession(true);
	
	private ArrayList<GrupoVo> listaGrupos=new ArrayList<>();
	private ArrayList<GrupoVo> listaGrupoSeleccionado=new ArrayList<>();
	HashMap<String, ProfesorVo> mapaProfesores=new HashMap<>();
	HashMap<String, GrupoVo> mapaGrupos=new HashMap<>();
	
	List<String> listaDias, listaSeleccionados;
	
	public GrupoBean(){
		grupo=new GrupoVo();
		grupoDao=new GrupoDao();
		estudianteDao=new EstudianteDao();
		estudianteBean=new EstudianteBean();
		itemProfesores=new ArrayList<SelectItem>();
		itemEstudiantesDisponibles=new ArrayList<SelectItem>();
		itemEstudiantesAsociados=new ArrayList<SelectItem>();
		itemGrupos=new ArrayList<SelectItem>();
		listaEstudiantesDisponibles=new ArrayList<>();
		listaEstudiantesAsociados=new ArrayList<>();
		cargarGrupos();	
		cargarListaGrupos();	
		cargarProfesores();
		cargarDatosHashMapGrupos();
		cargarEstudiantes();
	}
	
	
	public void consultarGrupoNombre(){
		setListaGrupos(grupoDao.consultarGrupoNombre(getNombreGrupo()));
	}
	
	public List<String> getListaSeleccionados() {
		return listaSeleccionados;
	}


	public void setListaSeleccionados(List<String> listaSeleccionados) {
		this.listaSeleccionados = listaSeleccionados;
	}


	public List<String> getListaDias() {
		return listaDias;
	}


	public void setListaDias(List<String> listaDias) {
		this.listaDias = listaDias;
	}


	private List<SelectItem> itemProfesores;
	private List<SelectItem> itemEstudiantesDisponibles,itemEstudiantesAsociados;
	private List<SelectItem> itemGrupos;
	private List<String> listaEstudiantesDisponibles,listaEstudiantesAsociados;
	ArrayList<EstudianteVo> listaEstudiantes;
	
	public void cargarListaGrupos(){
		grupos=grupoDao.cargarGrupos();
	}
		
	public void cargarDatosHashMapGrupos() {
		ArrayList<GrupoVo> listGrupos = grupoDao.obtenerListaGrupos();
		grupoDao.cargarDatosHashMapGrupos(listGrupos);
		estudianteBean.cargarDatosHashMapEstudiantes();
	}
	
	public void cargarLista(){
			
	}
	public void cargarestudiantes() {
		setEstudiantes(estudianteDao.obtenerNombres());
		
	}


	
	private void cargarProfesores() {
		ProfesorDao profesorDao=new ProfesorDao();
		ArrayList<ProfesorVo> listaProfesores;
		listaProfesores=profesorDao.obtenerListaProfesores();
		
		if (listaProfesores.size()>0) {
				
			for (int i = 0; i < listaProfesores.size(); i++) {
				itemProfesores.add(new SelectItem(listaProfesores.get(i).getDocumento(),listaProfesores.get(i).getNombre()));
				mapaProfesores.put(listaProfesores.get(i).getDocumento(),listaProfesores.get(i));
			}
			
		}else{
			listaProfesores=new ArrayList<>();
			mensajeConfirmacion="Actualmente no existen profesores registrados para asociarlos al grupo";
		}
	}
	
	private void cargarEstudiantes() {
		EstudianteDao estudianteDao=new EstudianteDao();

		listaEstudiantes=estudianteDao.obtenerListaEstudiantes();
		estudianteDao.cargarDatosHashMapEstudiantes(listaEstudiantes);
		System.out.println("VA A CARGAR ESTUDIANTES DESPUES DEL EVENTO!");
		itemEstudiantesDisponibles.clear();
		if (listaEstudiantes.size()>0) {
				
			for (int i = 0; i < listaEstudiantes.size(); i++) {
				if (listaEstudiantes.get(i).getGrupo().equals("0")) {
					System.out.println("LLENA ESTUDIANTE: "+new SelectItem(listaEstudiantes.get(i).getDocumento()+" - "+listaEstudiantes.get(i).getNombre()));
					itemEstudiantesDisponibles.add(new SelectItem(listaEstudiantes.get(i).getDocumento(),listaEstudiantes.get(i).getNombre()));	
				}
			}
			
		}else{
			listaEstudiantes=new ArrayList<>();
			mensajeConfirmacion="Actualmente no existen estudiantes registrados para asociarlos al grupo";
			System.out.println("************************POR SI LAS MOSCAS**********************");
		}
	}
	
	public String obtenerNombreProfesor(String documento){
		String nombreProfesor="";
		System.out.println("CODIGO PROYECTO: "+documento);
		System.out.println("Mapa: "+mapaProfesores);
		
		if (mapaProfesores.get(documento)!=null) {
			nombreProfesor=mapaProfesores.get(documento).getNombre();	
		}else{
			nombreProfesor="El profesor no Existe";
		}
		
		return nombreProfesor;
	}

	private void cargarGrupos() {
		listaGrupos.clear();
		listaGrupos=grupoDao.obtenerListaGrupos();
		
		
		if (listaGrupos!=null) {
			for (int i = 0; i < listaGrupos.size(); i++) {
				itemGrupos.add(new SelectItem(listaGrupos.get(i).getCodigo(),listaGrupos.get(i).getNombre()));
				mapaGrupos.put(listaGrupos.get(i).getCodigo(),listaGrupos.get(i));
			}			
			
		}else{
			mensajeConfirmacion="Actualmente no existen grupos registrados para asociarlos al Proyecto";
		}
	}
	
	public void consultarEstudiantesGrupo(AjaxBehaviorEvent event){
		System.out.println("INGRESA AL METODO DEL CAMBIO");
		System.out.println("CODIGO: "+grupoId);

		cargarEstudiantes();
		itemEstudiantesAsociados.clear();
		if (mapaGrupos!=null) {
			grupo=mapaGrupos.get(grupoId);
			listaGrupoSeleccionado.clear();
			listaGrupoSeleccionado.add(grupo);
			System.out.println("CODIGO: "+grupo.getCodigo());
			System.out.println("NOMBRE: "+grupo.getNombre());
			System.out.println("OBSERVACION : "+grupo.getObservacion());		
			System.out.println("ITEM ASOCIADOSSSS");
			
			listaDias=new ArrayList<>();
			listaDias.add("Opcion1");
			listaDias.add("Opcion2");
			listaDias.add("Opcion3");
			listaDias.add("Opcion4");
			listaDias.add("Opcion5");
			
			for (int i = 0; i < listaEstudiantes.size(); i++) {
				if (listaEstudiantes.get(i).getGrupo().equals(grupoId)) {
					itemEstudiantesAsociados.add(new SelectItem(listaEstudiantes.get(i).getDocumento(),listaEstudiantes.get(i).getNombre()));
					System.out.println(listaEstudiantes.get(i).getDocumento()+" - "+listaEstudiantes.get(i).getNombre());
					System.out.println("TAMAÑO ITEM ASOCIADOS: "+itemEstudiantesAsociados.size());
				}
			}
		}
	}	
		
	public ArrayList<GrupoVo> getListaGrupoSeleccionado() {
		return listaGrupoSeleccionado;
	}

	public void setListaGrupoSeleccionado(ArrayList<GrupoVo> listaGrupoSeleccionado) {
		this.listaGrupoSeleccionado = listaGrupoSeleccionado;
	}

	/*public void asociarEstudiantes(){
		System.out.println("VA A ASOCIAR ESTUDIANTES");
		for (int i = 0; i < listaEstudiantesDisponibles.size(); i++) {
			System.out.println(listaEstudiantesDisponibles.get(i));
		}
		listaEstudiantesAsociados=listaEstudiantesDisponibles;
		
		System.out.println("LISTA ASOCIADOS");
		for (int i = 0; i < listaEstudiantesAsociados.size(); i++) {
			System.out.println(listaEstudiantesDisponibles.get(i));
		}
	}*/
	
	public void quitarEstudiantes(){
		System.out.println("VA A QUITAR ESTUDIANTES");
		for (int i = 0; i < listaEstudiantesDisponibles.size(); i++) {
			System.out.println(listaEstudiantesDisponibles.get(i));
		}
	}

	public void registrarGrupo(){
		System.out.println("VA A REGISTRAR GRUPO");
		System.out.println(grupo.getCodigo());
		System.out.println(grupo.getNombre());
		System.out.println(grupo.getDirectorGrupo());
		System.out.println(grupo.getObservacion());
		System.out.println(fechaInicio);
		System.out.println(fechaFin);

		grupo.setEstado("Activo");
		mensajeConfirmacion=grupoDao.registrarGrupo(grupo);
		grupo=new GrupoVo();
	}

	public void eliminarGrupo(GrupoVo grupo){
		System.out.println("VA A ELIMINAR GRUPO");
		System.out.println("codigo - "+grupo.getCodigo());
		System.out.println("Nombre - "+grupo.getCodigo());
		mensajeConfirmacion=grupoDao.eliminarGrupo(grupo);
		listaGrupos.remove(grupo);
	}

	public void editarGrupo(GrupoVo grupo){
		grupo.setEditar(true);
		System.out.println("VA A EDITAR GRUPO");
		System.out.println("codigo - "+grupo.getCodigo());
		System.out.println("Nombre - "+grupo.getCodigo());
		
	}
	
	public void guardarGrupo(GrupoVo grupo){
		System.out.println("VA A GUARDAR GRUPO");
		System.out.println("codigo - "+grupo.getCodigo());
		System.out.println("Nombre - "+grupo.getCodigo());
		mensajeConfirmacion=grupoDao.actualizarGrupo(grupo);
		grupo.setEditar(false);
	}
	
	public String perfilGrupo(String codigo){
		System.out.println("Codigo de grupo: "+codigo);
		System.out.println("VA A CONSULTAR EL PERFIL DEL GRUPO");
		
		grupo=grupoDao.obtenerGrupo(codigo);
		System.out.println("***********************Grupos*****************");
		System.out.println("Fecha i: "+grupo.getFechaIni());
		System.out.println("Fecha F: "+grupo.getFechaFin());
		System.out.println("Director de grupo: "+grupo.getDirectorGrupo());
		System.out.println("Observacion: "+grupo.getObservacion());
		System.out.println("Estado: "+grupo.getEstado());
		
		System.out.println("codigo - "+grupo.getCodigo());
		System.out.println("Nombre - "+grupo.getNombre());
		
		session.setAttribute("grupo", grupo);
		
		return "Perfil_Grupo.jsf";
	}
	
	
	public GrupoVo getGrupo() {
		return grupo;
	}

	public void setGrupo(GrupoVo grupo) {
		this.grupo = grupo;
	}

	public String getMensajeConfirmacion() {
		return mensajeConfirmacion;
	}

	public void setMensajeConfirmacion(String mensajeConfirmacion) {
		this.mensajeConfirmacion = mensajeConfirmacion;
	}

	public String getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(String fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public String getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(String fechaFin) {
		this.fechaFin = fechaFin;
	}

	public ArrayList<GrupoVo> getListaGrupos() {
		return listaGrupos;
	}

	public void setListaGrupos(ArrayList<GrupoVo> listaGrupos) {
		this.listaGrupos = listaGrupos;
	}

	public List<SelectItem> getItemProfesores() {
		return itemProfesores;
	}

	public void setItemProfesores(List<SelectItem> itemProfesores) {
		this.itemProfesores = itemProfesores;
	}

	public List<SelectItem> getItemEstudiantesDisponibles() {
		return itemEstudiantesDisponibles;
	}

	public void setItemEstudiantesDisponibles(List<SelectItem> itemEstudiantesDisponibles) {
		this.itemEstudiantesDisponibles = itemEstudiantesDisponibles;
	}

	public List<String> getListaEstudiantesDisponibles() {
		return listaEstudiantesDisponibles;
	}

	public void setListaEstudiantesDisponibles(List<String> listaEstudiantesDisponibles) {
		this.listaEstudiantesDisponibles = listaEstudiantesDisponibles;
	}

	public List<SelectItem> getItemEstudiantesAsociados() {
		return itemEstudiantesAsociados;
	}

	public void setItemEstudiantesAsociados(List<SelectItem> itemEstudiantesAsociados) {
		this.itemEstudiantesAsociados = itemEstudiantesAsociados;
	}

	public List<SelectItem> getItemGrupos() {
		return itemGrupos;
	}

	public void setItemGrupos(List<SelectItem> itemGrupos) {
		this.itemGrupos = itemGrupos;
	}

	public String getGrupoId() {
		return grupoId;
	}

	public void setGrupoId(String grupoId) {
		this.grupoId = grupoId;
	}

	public List<String> getListaEstudiantesAsociados() {
		return listaEstudiantesAsociados;
	}

	public void setListaEstudiantesAsociados(List<String> listaEstudiantesAsociados) {
		this.listaEstudiantesAsociados = listaEstudiantesAsociados;
	}
	public String getNombreGrupo() {
		return nombreGrupo;
	}

	public void setNombreGrupo(String nombreGrupo) {
		this.nombreGrupo = nombreGrupo;
	}

	public String getNombreGrupoA() {
		return nombreGrupoA;
	}

	public void setNombreGrupoA(String nombreGrupoA) {
		this.nombreGrupoA = nombreGrupoA;
	}

	public ArrayList<String> getGrupos() {
		return grupos;
	}

	public void setGrupos(ArrayList<String> grupos) {
		this.grupos = grupos;
	}
	
	public void asociarEstudiantes(){
		String res = grupoDao.consultarAsociacion(nombresEstudiantes);
		if(res.equals("no existe")) {
			ArrayList<GrupoVo> listGrupos = grupoDao.obtenerListaGrupos();
			grupoDao.cargarDatosHasgMap(listGrupos);
			String idGrupo = grupoDao.obtenerId(getNombreGrupoA());
			registrarAsociacionDeEstudiantes(nombresEstudiantes,idGrupo);
		}else {
			setMensaje("Uno o los estudiantes ya se encuentra asociados a un Grupo");
		}
	}
	
	public void desasociarEstudiantes(String nombre){
		String doc = estudianteDao.obtenerIdUnEstudiante(nombre);
		System.out.println("Documento estudiante***: "+doc);
		
		String res = grupoDao.desasociarEstudiantes(doc);
		
		if(res.equals("ok")){
			estudianteBean.cargarListaAsociadosGrupos();
		}
	}



	private void registrarAsociacionDeEstudiantes(ArrayList<String> idEstudiante, String idGrupo) {
		String res = grupoDao.registrarAsociacionDeEstudiantes(idEstudiante,idGrupo);
		if(res.equals("ok")){
			setMensaje("Registro Exitoso!!!");
			estudianteBean.cargarListaAsociadosGrupos();
		}else{
			setMensaje("Registro Erroneo");
		}
		
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

	public ArrayList<String> getEstudiantes() {
		return estudiantes;
	}

	public void setEstudiantes(ArrayList<String> estudiantes) {
		this.estudiantes = estudiantes;
	}
	
	
}
