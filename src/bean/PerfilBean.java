package bean;

import java.security.Permissions;
import java.util.Properties;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;

import dao.PerfilDao;
import vo.PersonaVo;

@ManagedBean
@SessionScoped
public class PerfilBean {
	private static PersonaVo perfil;
	FacesContext context = FacesContext.getCurrentInstance();
	HttpSession session = (HttpSession)context.getExternalContext().getSession(true);
	private String passNew;
	private String confirmPass;
	private String mensaje;
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
		System.out.println("Password: "+getPerfil().getPassword());
		System.out.println("ddddddddddddddddddddddddddddddddd");
		System.out.println(doc);
	}
	
	public String cambiarContrasena(String doc, String tipo) {
	
		System.out.println("Documento del Profesor***:"+doc);
		PersonaVo miPersona = perfildao.obtenerPerfil(doc, tipo);
		
		session.setAttribute("objeto", miPersona);
		
		
		return "cambiar_contraseñaProf.jsf";
		
	}
	
	public String actualizarContrasena(PersonaVo persona) {
		String pagina = "";
		System.out.println("Persona documento: "+persona.getDocumento());
		System.out.println("Contraseña nueva: "+getPassNew());
		System.out.println("Confirmacion de contraseña: "+getConfirmPass());
		if(getPassNew().equals(getConfirmPass())) {
			String msj = perfildao.cambiarContrasena(persona.getDocumento(), persona.getTipo(), getConfirmPass());
			if(msj.equals("ok")) {
				enviarCorreo(persona, getConfirmPass());
				pagina = "perfil.jsf?faces-redirect=true";
			}
		}else {
			setMensaje("Las contraseñas no coinciden");
			pagina = "";
		}
		
		return pagina;
	}
	
	private void enviarCorreo(PersonaVo persona, String pass) {
		Properties propiedad = new Properties();
		propiedad.setProperty("mail.smtp.host", "smtp.gmail.com");
		propiedad.setProperty("mail.smtp.starttls.enable", "true");
        propiedad.setProperty("mail.smtp.port", "587");
        propiedad.setProperty("mail.smtp.auth", "true");
		
		Session sesion = Session.getDefaultInstance(propiedad);
		
		String correoEnvia = "adsisga@gmail.com";
		String contrasena = "adsi1598667";
		String destinatario = persona.getEmail(); 
		String asunto = "Cambio de Contraseña";
		String mensaje = "Estimado(a)  "+persona.getTipo()+" "+persona.getNombre()+"\n";
		mensaje+="Usted ha realizado el cambio de contraseña, a continuacion le presento los datos de la actualización: "+"\n\n";
		mensaje+="        Contraseña Anterior: "+persona.getPassword()+"\n\n";
		mensaje+="        Contraseña Nueva: "+pass+"\n\n";
		mensaje+="Para verificar el cambio de contraseña ingrese a http://localhost:8080/SistemaGestionAsistencia/pages/login.jsf"+"\n\n\n	";
		mensaje+="********NO RESPONDER - Mensaje Generado Automáticamente********";
		
		
		MimeMessage mail = new MimeMessage(sesion);
		try {
			mail.setFrom(new InternetAddress(correoEnvia));
			mail.addRecipient(Message.RecipientType.TO, new InternetAddress(destinatario));
			mail.setSubject(asunto);
			mail.setText(mensaje);
			
			Transport transporte = sesion.getTransport("smtp");
			transporte.connect(correoEnvia,contrasena);
			transporte.sendMessage(mail, mail.getRecipients(Message.RecipientType.TO));
			transporte.close();
			
			System.out.println("Correos enviados exitosamente: "+destinatario);
		
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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

	public String getPassNew() {
		return passNew;
	}

	public void setPassNew(String passNew) {
		this.passNew = passNew;
	}

	public String getConfirmPass() {
		return confirmPass;
	}

	public void setConfirmPass(String confirmPass) {
		this.confirmPass = confirmPass;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
}


