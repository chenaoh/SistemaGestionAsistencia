package vo;

import java.util.Date;

public class AsistenciaVo {
	
	private String documentoEstudiante;
	private String codigoGrupo;
	private String documentoProfesor;
	private String novedad;
	private Date fechaFalta;
	private String fecha;
	private Integer tiempoFalta;
	private String observacionFalta;
	private String codigo;
	private boolean editar;
	
	public AsistenciaVo(){
		
	}

	public String getDocumentoEstudiante() {
		return documentoEstudiante;
	}

	public void setDocumentoEstudiante(String documentoEstudiante) {
		this.documentoEstudiante = documentoEstudiante;
	}

	public String getCodigoGrupo() {
		return codigoGrupo;
	}

	public void setCodigoGrupo(String codigoGrupo) {
		this.codigoGrupo = codigoGrupo;
	}

	public String getDocumentoProfesor() {
		return documentoProfesor;
	}

	public void setDocumentoProfesor(String documentoProfesor) {
		this.documentoProfesor = documentoProfesor;
	}

	public String getNovedad() {
		return novedad;
	}

	public void setNovedad(String novedad) {
		this.novedad = novedad;
	}

	public Date getFechaFalta() {
		return fechaFalta;
	}

	public void setFechaFalta(Date fechaFalta) {
		this.fechaFalta = fechaFalta;
	}

	public Integer getTiempoFalta() {
		return tiempoFalta;
	}

	public void setTiempoFalta(Integer tiempoFalta) {
		this.tiempoFalta = tiempoFalta;
	}

	public String getObservacionFalta() {
		return observacionFalta;
	}

	public void setObservacionFalta(String observacionFalta) {
		this.observacionFalta = observacionFalta;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public boolean isEditar() {
		return editar;
	}

	public void setEditar(boolean editar) {
		this.editar = editar;
	}
	
	
	
}
