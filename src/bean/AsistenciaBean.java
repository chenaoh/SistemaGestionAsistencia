package bean;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import dao.AsistenciaDao;
/*import dao.AsistenciaDao;*/
import dao.EstudianteDao;
import dao.GrupoDao;
import dao.ProfesorDao;
import vo.AsistenciaVo;
import vo.EstudianteVo;
import vo.GrupoVo;
import vo.PersonaVo;
import vo.ProfesorVo;
@ManagedBean
@ViewScoped
public class AsistenciaBean {
	
	private GrupoDao grupoDao;
	private AsistenciaDao asistenciaDao;
	private EstudianteDao estudianteDao;
	
	private ProfesorVo profesor;
	private String fecha;
	private String mensajeConfirmacion;
	private ProfesorDao profesorDao;

	private static ArrayList<String> nombres;
	private static ArrayList<String> asistenciaNombres;
	private ArrayList<AsistenciaVo> listaAsistencias;
	private ArrayList<String> grupos;
	private String grupo;
	private String filtroGrupo;
	private static String documento;
	private String codGrupo="";
	private String observacion;
	private String idGrupo;

	
	public void setCodGrupo(String codGrupo) {
		this.codGrupo = codGrupo;
	}



	public String getCodGrupo() {
		return codGrupo;
	}



	public AsistenciaBean(){
		profesor=new ProfesorVo();
		estudianteDao=new EstudianteDao();
		profesorDao=new ProfesorDao();
		asistenciaDao = new AsistenciaDao();
		nombres = new ArrayList<>();
		grupoDao = new GrupoDao();
		setListaAsistencias(new ArrayList<>());
		cargarListaAsistencias();
		cargarGrupos();
	}
	
	public void editarAsistencia(AsistenciaVo asistenciaVo) {
		System.out.println("***ENTRA AL METODO PARA EDITAR LA ASISTENCIA****");
		cargarDatosHashMapGrupos();
		System.out.println("Grupo: "+asistenciaVo.getCodigoGrupo());
		idGrupo = grupoDao.obtenerId(asistenciaVo.getCodigoGrupo());
		setNombres(estudianteDao.obtenerNombresAsistencia(idGrupo));
		asistenciaVo.setEditar(true);
	}
	
	private void cargarDatosHashMapGrupos() {
		ArrayList<GrupoVo> listGrupos = grupoDao.obtenerListaGrupos();
		grupoDao.cargarDatosHasgMap(listGrupos);
		
	}

	public void guardarAsistencia(AsistenciaVo asistenciaVo) {
		System.out.println("Fecha asistencia: "+asistenciaVo.getFecha());
		cargarDatosHashMapProfesores();
		cargarDatosHashMapEstudiantes();
		String idEstudiante = estudianteDao.obtenerIdUnEstudiante(asistenciaVo.getDocumentoEstudiante());
		String idProfesor = profesorDao.obtenerIDProfesor(asistenciaVo.getDocumentoProfesor());
		String res = asistenciaDao.actualizarAsistencia(asistenciaVo.getCodigo(), idGrupo, idProfesor, idEstudiante, asistenciaVo);
		if(res.equals("ok")) {
			cargarListaAsistencias();
		}
	}
	

	private void cargarDatosHashMapEstudiantes() {
		ArrayList<EstudianteVo> listEstudiantes = estudianteDao.obtenerListaEstudiantes();
		estudianteDao.cargarDatosHashMapEstudiantes(listEstudiantes);
		
	}



	private void cargarDatosHashMapProfesores() {
		ArrayList<ProfesorVo> listProfesores = profesorDao.obtenerListaProfesores();
		profesorDao.cargarDatosHashMapProfesores(listProfesores);
		
	}



	private void cargarListaAsistencias() {
		setListaAsistencias(asistenciaDao.obtenerListaAsistencias());
		
	}



	private void cargarGrupos() {
		setGrupos(grupoDao.cargarGrupos());	
	}



	public void registrarProfesor(){
		System.out.println(profesor.getDocumento());
		System.out.println(profesor.getNombre());
		
		profesor.setPassword(profesor.getDocumento());
		profesor.setEstado("Activo");
		mensajeConfirmacion=profesorDao.registrarProfesor(profesor);
		profesor=new ProfesorVo();
	}
	
	public void filtrarGrupo(){
		ArrayList<GrupoVo> listaGrupos = grupoDao.obtenerListaGrupos();
		grupoDao.cargarDatosHasgMap(listaGrupos);
		setCodGrupo( grupoDao.obtenerId(getGrupo()));
		setNombres(estudianteDao.obtenerNombresAsistencia(codGrupo));
	}
	
	public void registrarAsistencias(){
		System.out.println("*****ENTRA A REGISTRAR ASISTENCIA******");
		System.out.println("Documento del profesor: "+documento);
		System.out.println("Estudiantes seleccionados: "+getAsistenciaNombres());
		ArrayList<EstudianteVo> estudiantes = estudianteDao.obtenerListaEstudiantes();
		estudianteDao.cargarDatosHashMapEstudiantes(estudiantes);
		ArrayList<String> documentos = estudianteDao.obtenerIdEstudiante(getAsistenciaNombres());
		System.out.println("**Documentos EStudintes**: "+documentos);
		String res = asistenciaDao.registrarAsistencia(documentos, documento, getCodGrupo(),getObservacion(),getFecha());	
		
		if(res.equals("ok")){
			LoginBean login = new LoginBean();
			login.calcularPanelEstadisticas();
			System.out.println("YEAH");
		}
	}
	
	
	public void eliminarAsistencia(AsistenciaVo asistencia){
		//System.out.println("VA A ELIMINAR ESTUDIANTE");
		//System.out.println("codigo - "+estudiante.getDocumento());
		//System.out.println("Nombre - "+estudiante.getNombre());
		mensajeConfirmacion=asistenciaDao.eliminarAsistencia(asistencia.getCodigo());
		listaAsistencias.remove(asistencia);
	}
	
	public void filtrarAsistenciasFechaYGrupo() {
		String grupo="";
		System.out.println("Grupo: "+getFiltroGrupo());
		
		if(getFecha().equals("") && getFiltroGrupo()==null){
			setListaAsistencias(asistenciaDao.obtenerListaAsistencias());
		}else if(!(getFecha().equals("")) && getFiltroGrupo()==null) {
			System.out.println("ESTA FILTRANDO SOLO POR FECHA: "+getFecha());
			setListaAsistencias(asistenciaDao.filtrarListaFecha(getFecha(), "", 1));
		}else if(getFecha().equals("") && getFiltroGrupo()!=null) {
			cargarDatosHashMapGrupos();
			grupo = grupoDao.obtenerId(getFiltroGrupo());
			setListaAsistencias(asistenciaDao.filtrarListaFecha("", grupo, 2));
		}else {
			cargarDatosHashMapGrupos();
			grupo = grupoDao.obtenerId(getFiltroGrupo());
			setListaAsistencias(asistenciaDao.filtrarListaFecha(getFecha(), grupo, 3));
		}
		
		
		
		
		
	}

	public ProfesorVo getProfesor() {
		return profesor;
	}

	public void setProfesor(ProfesorVo profesor) {
		this.profesor = profesor;
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

	public ArrayList<String> getNombres() {
		return nombres;
	}

	public void setNombres(ArrayList<String> nombres) {
		this.nombres = nombres;
	}

	public ArrayList<String> getAsistenciaNombres() {
		return asistenciaNombres;
	}

	public void setAsistenciaNombres(ArrayList<String> asistenciaNombres) {
		this.asistenciaNombres = asistenciaNombres;
	}

	public ArrayList<String> getGrupos() {
		return grupos;
	}

	public void setGrupos(ArrayList<String> grupos) {
		this.grupos = grupos;
	}

	public String getGrupo() {
		return grupo;
	}

	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}



	public void recibirDocumento(String documento) {
		System.out.println("Documento llegada: "+documento);
		this.documento = documento;
		System.out.println(" // "+this.documento);
		
	}



	public String getObservacion() {
		return observacion;
	}



	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}



	public ArrayList<AsistenciaVo> getListaAsistencias() {
		return listaAsistencias;
	}



	public void setListaAsistencias(ArrayList<AsistenciaVo> listaAsistencias) {
		this.listaAsistencias = listaAsistencias;
	}



	public String getFiltroGrupo() {
		return filtroGrupo;
	}



	public void setFiltroGrupo(String filtroGrupo) {
		this.filtroGrupo = filtroGrupo;
	}
	
}
