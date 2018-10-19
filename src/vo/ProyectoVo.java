package vo;

public class ProyectoVo {

	//Esta es una prueba de modificación
	private Integer codigoProyecto;
	private Integer numProyecto;
	private String nombreProyecto;
	private String descripcionProyecto;
	private String CodigoGrupo;
	private boolean editar;
	
	public ProyectoVo(){
		
	}

	public Integer getCodigoProyecto() {
		return codigoProyecto;
	}

	public void setCodigoProyecto(Integer codigoProyecto) {
		this.codigoProyecto = codigoProyecto;
	}

	public Integer getNumProyecto() {
		return numProyecto;
	}

	public void setNumProyecto(Integer numProyecto) {
		this.numProyecto = numProyecto;
	}

	public String getNombreProyecto() {
		return nombreProyecto;
	}

	public void setNombreProyecto(String nombreProyecto) {
		this.nombreProyecto = nombreProyecto;
	}

	public String getDescripcionProyecto() {
		return descripcionProyecto;
	}

	public void setDescripcionProyecto(String descripcionProyecto) {
		this.descripcionProyecto = descripcionProyecto;
	}

	public String getCodigoGrupo() {
		return CodigoGrupo;
	}

	public void setCodigoGrupo(String codigoGrupo) {
		CodigoGrupo = codigoGrupo;
	}

	public boolean isEditar() {
		return editar;
	}

	public void setEditar(boolean editar) {
		this.editar = editar;
	}
	
	
	
}
