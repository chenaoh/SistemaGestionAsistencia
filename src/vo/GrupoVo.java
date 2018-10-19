package vo;


import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;


public class GrupoVo {
	
	private String codigoGrupo;
	private String nombreGrupo;
	private String directorGrupo;
	private Date fechaInicioGrupo;
	private Date fechaFinGrupo;
	private String fechaIni;
	private String fechaFin;
	private String observacion;
	private String estado;
	private boolean editar;
	
	public GrupoVo(String codigoGrupo, String nombreGrupo, String directorGrupo, String fechaIni,
			String fechaFin, String observacion, String estado, boolean editar) {
		super();
		this.codigoGrupo = codigoGrupo;
		this.nombreGrupo = nombreGrupo;
		this.directorGrupo = directorGrupo;
		this.fechaIni=fechaIni;
		this.fechaFin=fechaFin;
		this.observacion = observacion;
		this.estado = estado;
		this.editar=editar;
	}

	public GrupoVo(){
		
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

	public String getCodigoGrupo() {
		return codigoGrupo;
	}

	public void setCodigoGrupo(String codigoGrupo) {
		this.codigoGrupo = codigoGrupo;
	}

	public String getNombreGrupo() {
		return nombreGrupo;
	}

	public void setNombreGrupo(String nombreGrupo) {
		this.nombreGrupo = nombreGrupo;
	}

	public String getDirectorGrupo() {
		return directorGrupo;
	}

	public void setDirectorGrupo(String directorGrupo) {
		this.directorGrupo = directorGrupo;
	}
	
	public String getFechaIni() {
		return fechaIni;
	}

	public void setFechaIni(String fechaIni) {
		this.fechaIni = fechaIni;
		setFechaInicioGrupo(convertirFecha(fechaIni));
	}

	public String getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(String fechaFin) {
		this.fechaFin = fechaFin;
		setFechaFinGrupo(convertirFecha(fechaFin));
	}

	public Date getFechaInicioGrupo() {
		return fechaInicioGrupo;
	}

	public void setFechaInicioGrupo(Date fechaInicioGrupo) {
		this.fechaInicioGrupo = fechaInicioGrupo;
	}

	public Date getFechaFinGrupo() {
		return fechaFinGrupo;
	}

	public void setFechaFinGrupo(Date fechaFinGrupo) {
		this.fechaFinGrupo = fechaFinGrupo;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public boolean isEditar() {
		return editar;
	}

	public void setEditar(boolean editar) {
		this.editar = editar;
	}
		
}
