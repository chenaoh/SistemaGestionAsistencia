package vo;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class EstudianteVo extends PersonaVo{
	
	private String direccion;
	private String grupo;
	private Date fechaNacimiento;
	private String fecha;
	private boolean editar;
	
	public EstudianteVo(String direccion, String fecha, boolean editar) {
		super();
		this.direccion = direccion;
		this.fecha = fecha;
		this.editar = editar;
	}

	public EstudianteVo(){
			
	}
	
	public Date convertirFecha(String fecha){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		   java.util.Date parsed;
	       Date fechaSql=null;
			try {
				parsed = (java.util.Date) format.parse(fecha);
				fechaSql = new Date(parsed.getTime());
			} catch (ParseException e) {
				e.printStackTrace();
			}
		
		return fechaSql;
	}
	
	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public boolean isEditar() {
		return editar;
	}

	public void setEditar(boolean editar) {
		this.editar = editar;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
		setFechaNacimiento(convertirFecha(fecha));
	}

	public String getGrupo() {
		return grupo;
	}

	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}


}
