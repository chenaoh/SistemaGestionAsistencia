package bean;

import javax.faces.bean.ManagedBean;

import dao.PerfilDao;
import vo.PersonaVo;

@ManagedBean
public class PerfilBean {
	private static PersonaVo perfil;
	
	private String documentoPerfil;
	private PerfilDao perfildao;
	
	public PerfilBean(){
		perfildao=new PerfilDao();
		System.out.println("PerfilBean");
		System.out.println(getDocumentoPerfil());
	}
	
	public void recibirDocumento(String documento, String tipo) {
		System.out.println("tttttttttttttttttttttttttttttttttttttttttttttttttttttttttttt");
		System.out.println("Documento llegada: "+documento);
		this.documentoPerfil = documento;	
		System.out.println(getDocumentoPerfil()+"hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh");
		obtenerPerfilPersona(getDocumentoPerfil(), tipo);
	}
	
	public void obtenerPerfilPersona(String doc, String tipo){
		setPerfil(perfildao.obtenerPerfil(doc, tipo));
		System.out.println("ppppppppppppppppppppppppppppppppppppppppppppppppp");
		System.out.println(getPerfil());
		System.out.println("Nombre: "+getPerfil().getNombre());
		System.out.println("ddddddddddddddddddddddddddddddddd");
		System.out.println(doc);
	}
	
	public String getDocumentoPerfil() {
		return documentoPerfil;
	}

	public void setDocumentoPerfil(String documentoPerfil) {
		this.documentoPerfil = documentoPerfil;
	}

	public PersonaVo getPerfil() {
		return perfil;
	}

	public void setPerfil(PersonaVo perfil) {
		this.perfil = perfil;
	}
}


