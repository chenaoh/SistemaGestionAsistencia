package bean;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import dao.EstudianteDao;
import vo.EstudianteVo;
import vo.GrupoVo;
@ManagedBean
@ViewScoped
public class EstudianteBean {
	
	private EstudianteVo estudiante;
	private String fecha;
	private String mensajeConfirmacion;
	private EstudianteDao estudianteDao;
	
	private ArrayList<EstudianteVo> listaEstudiantes=new ArrayList<>();
	
	public EstudianteBean(){
		estudiante=new EstudianteVo();
		estudianteDao=new EstudianteDao();
		cargarEstudiantes();
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

	public EstudianteVo getEstudiante() {
		return estudiante;
	}

	public void setEstudiante(EstudianteVo estudiante) {
		this.estudiante = estudiante;
	}

	public ArrayList<EstudianteVo> getListaEstudiantes() {
		return listaEstudiantes;
	}

	public void setListaEstudiantes(ArrayList<EstudianteVo> listaEstudiantes) {
		this.listaEstudiantes = listaEstudiantes;
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
	
	

}
