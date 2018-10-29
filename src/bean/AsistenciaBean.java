package bean;


import javax.faces.bean.ManagedBean;

import dao.ProfesorDao;
import vo.ProfesorVo;
@ManagedBean
public class AsistenciaBean {
	
	private ProfesorVo profesor;
	private String fecha;
	private String mensajeConfirmacion;
	private ProfesorDao profesorDao;
	
	public AsistenciaBean(){
		profesor=new ProfesorVo();
		profesorDao=new ProfesorDao();
	}
	
	public void registrarProfesor(){
		System.out.println(profesor.getDocumento());
		System.out.println(profesor.getNombre());
		
		profesor.setPassword(profesor.getDocumento());
		profesor.setEstado("Activo");
		mensajeConfirmacion=profesorDao.registrarProfesor(profesor);
		profesor=new ProfesorVo();
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
	
	

}
