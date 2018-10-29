package vo;

public class ProfesorVo extends PersonaVo{

	private String profesion;
	private String perfil;
	private String asesoria;	
	
	public ProfesorVo(){
		
	}

	public String getProfesion() {
		return profesion;
	}

	public void setProfesion(String profesion) {
		this.profesion = profesion;
	}

	public String getPerfil() {
		return perfil;
	}

	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}

	public String getAsesoria() {
		return asesoria;
	}

	public void setAsesoria(String asesoria) {
		this.asesoria = asesoria;
	}

	
}
