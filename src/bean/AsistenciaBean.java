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
	private ArrayList<String> grupos;
	private String grupo;
	private static String documento;
	private String codGrupo="";
	private String observacion;
	
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
		
		cargarGrupos();
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
		String res = asistenciaDao.registrarAsistencia(documentos, documento, getCodGrupo(),getObservacion());	
		
		if(res.equals("ok")){
			System.out.println("YEAH");
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
	
}
