package bean;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import dao.EstudianteDao;
import dao.GrupoDao;
import vo.EstudianteVo;
import vo.EstudiantesPtoyectosVo;
import vo.GrupoVo;
@ManagedBean
@ViewScoped
public class EstudianteBean {
	
	private EstudianteVo estudiante;
	private EstudiantesPtoyectosVo estuProyectoVo;
	private String fecha;
	private String mensajeConfirmacion;
	private EstudianteDao estudianteDao;
	private GrupoDao grupoDao;
	private String nombreEstu;
	private String grupo;
	private boolean tipoUser;
	
	private ArrayList<String> nombresEstudiantes =  new ArrayList<>();
	private static ArrayList<EstudianteVo> listaEstudiantes=new ArrayList<>();
	private ArrayList<String> grupos=new ArrayList<>();
	private static ArrayList<EstudiantesPtoyectosVo>  listaEstudiantesAsociados = new ArrayList<>();
	
	FacesContext context = FacesContext.getCurrentInstance();
	HttpSession session = (HttpSession)context.getExternalContext().getSession(true);
	
	
	public EstudianteBean(){
		estudiante=new EstudianteVo();
		estuProyectoVo  = new EstudiantesPtoyectosVo();
		estudianteDao=new EstudianteDao();
		grupoDao=new GrupoDao();
		cargarEstudiantes();
		cargarNombres();
		cargarDatosHashMapEstudiantes();
		cargarEstudiantesAsociados();
		cargarGrupos();
	}
	
	public void cargarEstudiante(){
		System.out.println("INGRESA A CARGAR PROFESOR!!!");
		//FacesContext context = FacesContext.getCurrentInstance();	
		//profesor= (ProfesorVo) context.getExternalContext().getSessionMap().get("profesor");
		
		EstudianteVo estudiante= (EstudianteVo) session.getAttribute("estudiante");
		
		System.out.println("OBTIENE EL PROFESOR");
		if (estudiante!=null) {
			System.out.println("codigo - "+estudiante.getDocumento());
			System.out.println("Nombre - "+estudiante.getNombre());	
		}else{
			System.out.println("ESTUDIANTE= "+estudiante);
		}	
	}
	
	public void consultarEstudiantesGrupos(){
		cargarDatosHashMap();
		String codigo = grupoDao.obtenerId(getGrupo());
		System.out.println("Grupo*****: "+codigo);
		setListaEstudiantes(estudianteDao.consultarEstudianteGrupos(codigo));
	}

	private void cargarDatosHashMap() {
		System.out.println("*******Esta cargando los datos de los grupos**************");
		ArrayList<GrupoVo> listaGrupos = grupoDao.obtenerListaGrupos();
		grupoDao.cargarDatosHasgMap(listaGrupos);
		
	}

	private void cargarGrupos() {
		setGrupos(grupoDao.cargarGrupos());
		
	}

	public void cargarEstudiantesAsociados() {
		setListaEstudiantesAsociados(estudianteDao.consultarEstudiantesAsociados());
		
	}

	public void cargarDatosHashMapEstudiantes() {
		ArrayList<EstudianteVo> estudiantes = estudianteDao.obtenerListaEstudiantes();
		estudianteDao.cargarDatosHashMapEstudiantes(estudiantes);
		
	}

	public void cargarNombres(){
		nombresEstudiantes.clear();
		nombresEstudiantes = estudianteDao.obtenerNombres();
		
		if(nombresEstudiantes!=null){
			mensajeConfirmacion = "No se pudo conectar, verifique la BD esté iniciada.";
		}
	}
	
	private void cargarEstudiantes() {
		listaEstudiantes.clear();
		listaEstudiantes=estudianteDao.obtenerListaEstudiantes();
		
		if (listaEstudiantes==null) {
			mensajeConfirmacion="No se pudo conectar, verifique que la BD esté iniciada.";
		}
	}
	
	public void registrarEstudiante(){
		System.out.println("VA A REGISTRAR ESTUDIANTE");
		System.out.println(estudiante.getDocumento());
		System.out.println(estudiante.getNombre());		
		System.out.println(estudiante.getDireccion());
		System.out.println(estudiante.getEmail());
		System.out.println(estudiante.getSexo());
		System.out.println(estudiante.getTelefono());
		System.out.println(estudiante.getFechaNacimiento());
		System.out.println(estudiante.getFechaNacimiento());
		
	/*	
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	       Date parsed;//objeto tipo javaUtil
			try {
				//convierte la fecha string a dateJavaUtil
				parsed = (Date) format.parse(fecha);
				//convierte javaUtil a date
				java.sql.Date sql = new java.sql.Date(parsed.getTime());
				System.out.println("fecha: "+sql);
				
				//envio la fecha en formato date al objeto para almacenar en bd
				estudiante.setFechaNacimiento(sql);
			} catch (ParseException e) {
				e.printStackTrace();
			}
	*/
		estudiante.setPassword(estudiante.getDocumento());
		estudiante.setTipo("estudiante");
		estudiante.setEstado("Activo");
		estudiante.setGrupo("0");
		mensajeConfirmacion=estudianteDao.registrarEstudiante(estudiante);
		estudiante=new EstudianteVo();
	}
	

	public void editarEstudiante(EstudianteVo estudiante){
		System.out.println("VA A EDITAR ESTUDIANTE");
		System.out.println("codigo - "+estudiante.getDocumento());
		System.out.println("Nombre - "+estudiante.getNombre());
		estudiante.setEditar(true);
	}
	
	public void guardarEstudiante(EstudianteVo estudiante){
		System.out.println("VA A GUARDAR GRUPO");
		System.out.println("codigo - "+estudiante.getDocumento());
		System.out.println("Nombre - "+estudiante.getNombre());
		mensajeConfirmacion=estudianteDao.actualizarEstudiante(estudiante);
		estudiante.setEditar(false);
		
	}
	
	public void eliminarEstudiante(EstudianteVo estudiante){
		System.out.println("VA A ELIMINAR ESTUDIANTE");
		System.out.println("codigo - "+estudiante.getDocumento());
		System.out.println("Nombre - "+estudiante.getNombre());
		mensajeConfirmacion=estudianteDao.eliminarEstudiante(estudiante);
		listaEstudiantes.remove(estudiante);
	}
	
	public String perfilEstudiante(String documento){
		System.out.println("VA A CONSULTAR PERFIL DE UN ESTUDIANTE");
		
		estudiante=estudianteDao.obtenerEstudiante(documento);
		
		System.out.println("codigo - "+estudiante.getDocumento());
		System.out.println("Nombre - "+estudiante.getNombre());
		
		session.setAttribute("estudiante", estudiante);
		
		return "perfil_estudiante.jsf";
	}
	
	
	public void consultarEstudiante(){
		setListaEstudiantes(estudianteDao.consultarEstudianteNombre(getNombreEstu()));
	}

	public void setListaEstudiantes(ArrayList<EstudianteVo> listaEstudiantes) {
		this.listaEstudiantes = listaEstudiantes;
	}

	public EstudianteVo getEstudiante() {
		return estudiante;
	}

	public void setEstudiante(EstudianteVo estudiante) {
		this.estudiante = estudiante;
	}

	public ArrayList<EstudianteVo> getListaEstudiantes() {
		return listaEstudiantes;
	}

	public ArrayList<String> getNombresEstudiantes() {
		return nombresEstudiantes;
	}

	public void setNombresEstudiantes(ArrayList<String> nombresEstudiantes) {
		this.nombresEstudiantes = nombresEstudiantes;
	}

	public String getFecha() {
		return fecha;
	}



	public void setFecha(String fecha) {
		this.fecha = fecha;
	}



	public String getMensajeConfirmacion() {
		return mensajeConfirmacion;
	}



	public void setMensajeConfirmacion(String mensajeConfirmacion) {
		this.mensajeConfirmacion = mensajeConfirmacion;
	}

	public String getNombreEstu() {
		return nombreEstu;
	}

	public void setNombreEstu(String nombreEstu) {
		this.nombreEstu = nombreEstu;
	}

	public boolean isTipoUser() {
		return tipoUser;
	}

	public void setTipoUser(boolean tipoUser) {
		this.tipoUser = tipoUser;
	}

	public ArrayList<EstudiantesPtoyectosVo> getListaEstudiantesAsociados() {
		return listaEstudiantesAsociados;
	}

	public void setListaEstudiantesAsociados(ArrayList<EstudiantesPtoyectosVo> listaEstudiantesAsociados) {
		this.listaEstudiantesAsociados = listaEstudiantesAsociados;
	}

	public String getGrupo() {
		return grupo;
	}

	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}

	public ArrayList<String> getGrupos() {
		return grupos;
	}

	public void setGrupos(ArrayList<String> grupos) {
		this.grupos = grupos;
	}
	
	

}
