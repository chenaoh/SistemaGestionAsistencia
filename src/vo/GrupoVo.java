package vo;


import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;


public class GrupoVo {
	
	private String codigo;
	private String nombre;
	private String directorGrupo;
	private Date fechaInicioGrupo;
	private Date fechaFinGrupo;
	private String fechaIni;
	private String fechaFin;
	private String observacion;
	private String estado;
	private boolean editar;
	
	public GrupoVo(String codigo, String nombre, String directorGrupo, String fechaIni,
			String fechaFin, String observacion, String estado, boolean editar) {
		super();
		this.codigo = codigo;
		this.nombre = nombre;
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

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigoGrupo) {
		this.codigo = codigoGrupo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombreGrupo) {
		this.nombre = nombreGrupo;
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
