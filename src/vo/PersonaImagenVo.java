package vo;

import java.io.InputStream;

import javax.servlet.http.Part;

public class PersonaImagenVo {

	private String documento;
	private String nombre;
	private InputStream imagen;
	
	public PersonaImagenVo(){ 	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public InputStream getImagen() {
		return imagen;
	}

	public void setImagen(InputStream imagen) {
		this.imagen = imagen;
	}

	
}
