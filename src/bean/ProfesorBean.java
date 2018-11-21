package bean;

import java.util.ArrayList;
import java.util.Map;
import java.util.Properties;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;
import javax.swing.SwingConstants;

import dao.ProfesorDao;
import vo.EstudianteVo;
import vo.ProfesorVo;
import vo.ProfesoresGruposVo;
@ManagedBean
@ViewScoped
public class ProfesorBean {
	
	private String nomProf;
	private ProfesorVo profesor;
	private String mensajeConfirmacion;
	private ProfesorDao profesorDao;
	ProfesorVo prof2 = new ProfesorVo();
	private ArrayList<ProfesorVo> listaProfesores=new ArrayList<>();
	FacesContext context = FacesContext.getCurrentInstance();
	HttpSession session = (HttpSession)context.getExternalContext().getSession(true);
	LoginBean login  = new LoginBean();
	
	private ArrayList<String> nombresProfesores = new ArrayList<>();
	private static ArrayList<ProfesoresGruposVo> listaProfesoresAsociados = new ArrayList<>();

	public ProfesorBean(){
		profesor=new ProfesorVo();
		profesorDao=new ProfesorDao();
		cargarProfesores();
		cargarNombres();
		cargarProfesoresAsociados();
	}
	
	public void filtrarProfesor(){
		System.out.println("nombre: "+nomProf);
		setListaProfesores(profesorDao.filtrarProfesor(getNomProf()));
	}
	
	private void cargarProfesores() {
		System.out.println("INGRESA A CARGAR PROFESORES");
		listaProfesores.clear();
		listaProfesores=profesorDao.obtenerListaProfesores();
		
		if (listaProfesores==null) {
			mensajeConfirmacion="No se pudo conectar, verifique que la BD esté iniciada.";
		}
	}
	
	public void cargarProfesor(){
		System.out.println("INGRESA A CARGAR PROFESOR!!!");
		//FacesContext context = FacesContext.getCurrentInstance();	
		//profesor= (ProfesorVo) context.getExternalContext().getSessionMap().get("profesor");
		
		ProfesorVo profesor= (ProfesorVo) session.getAttribute("profesor");
		
		System.out.println("OBTIENE EL PROFESOR");
		if (profesor!=null) {
			System.out.println("codigo - "+profesor.getDocumento());
			System.out.println("Nombre - "+profesor.getNombre());	
		}else{
			System.out.println("PROFESOR= "+profesor);
		}	
	}
	
	public void cargarProfesoresAsociados(){
		setListaProfesoresAsociados(profesorDao.consultarProfesoresAsociados());
	}
	
	public void cargarNombres() {
		nombresProfesores.clear();
		nombresProfesores = profesorDao.obtenerNombres();
		
		if (listaProfesores==null) {
			mensajeConfirmacion="No se pudo conectar, verifique que la BD esté iniciada.";
		}
	}
	
	public void registrarProfesor(){
		System.out.println(profesor.getDocumento());
		System.out.println(profesor.getNombre());
		
		profesor.setPassword(profesor.getDocumento());
		profesor.setEstado("Activo");
		mensajeConfirmacion=profesorDao.registrarProfesor(profesor);
		if(mensajeConfirmacion!=null){
			login.calcularPanelEstadisticas();
			enviarCorreo(profesor);
		}
		profesor=new ProfesorVo();
	}
	
	private void enviarCorreo(ProfesorVo profesor2) {
		Properties propiedad = new Properties();
		propiedad.setProperty("mail.smtp.host", "smtp.gmail.com");
		propiedad.setProperty("mail.smtp.starttls.enable", "true");
        propiedad.setProperty("mail.smtp.port", "587");
        propiedad.setProperty("mail.smtp.auth", "true");
		
		Session sesion = Session.getDefaultInstance(propiedad);
		
		String correoEnvia = "adsisga@gmail.com";
		String contraseña = "adsi1598667";
		String destinatario = profesor2.getEmail(); 
		String asunto = "Registro de Profesor";
		String mensaje = "Estimado(a)  "+profesor2.getNombre()+"\n";
		mensaje+="Usted fue registrado en la plataforma SGA(Sistema Gestion de Asistencias) \n\n";
		mensaje+="A continuación encontará los datos del Registro: "+"\n\n";
		mensaje+="    	Documento: "+profesor2.getDocumento()+"\n\n";
		mensaje+="    	Nombre: "+profesor2.getNombre()+"\n\n";
		mensaje+="    	Teléfono: "+profesor2.getTelefono()+"\n\n";
		mensaje+="    	Profesión: "+profesor2.getProfesion()+"\n\n";
		mensaje+="    	Sexo: "+profesor2.getSexo()+"\n\n";
		mensaje+="    	Email: "+profesor2.getEmail()+"\n\n";
		mensaje+="    	Perfil: "+profesor2.getPerfil()+"\n\n";
		mensaje+="    	Asesoria: "+profesor2.getAsesoria()+"\n\n";
		mensaje+="    	Estado: "+profesor2.getEstado()+"\n\n";
		mensaje+="Para verificar su registro ingrese a http://localhost:8080/SistemaGestionAsistencia/pages/login.jsf"+"\n\n\n	";
		mensaje+="********NO RESPONDER - Mensaje Generado Automáticamente********";
		
		MimeMessage mail = new MimeMessage(sesion);
		try {
			mail.setFrom(new InternetAddress(correoEnvia));
			mail.addRecipient(Message.RecipientType.TO, new InternetAddress(destinatario));
			mail.setSubject(asunto);
			mail.setText(mensaje);
			
			Transport transporte = sesion.getTransport("smtp");
			transporte.connect(correoEnvia,contraseña);
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

	public String editarProfesor(String documento){
		System.out.println("VA A EDITAR PROFESOR");

		 profesor=profesorDao.obtenerProfesor(documento);
		
			System.out.println("codigo - "+profesor.getDocumento());
			System.out.println("Nombre - "+profesor.getNombre());
		 
			//ExternalContext contexto=FacesContext.getCurrentInstance().getExternalContext();
		//	Map<String, Object> map=contexto.getRequestMap();
		//	map.put("profesor", profesor);
			session.setAttribute("profesor", profesor);
						
		return "editar_profesor.jsf";
	}
	
	
	public String guardarProfesor(ProfesorVo profesor){
		System.out.println("VA A GUARDAR PROFESOR EN METODO 2");
		System.out.println("codigo - "+profesor);
		mensajeConfirmacion=profesorDao.actualizarProfesor(profesor);
		//profesor.setEditar(false);
		return "consulta_profesores.jsf?faces-redirect=true";
	}
	
	public void mandarObjeto(ProfesorVo profesorVo2){
		System.out.println("Entra a mandar el objeto");
		prof2 = profesorVo2;
	}
	
	public void eliminarObjeto(int cod) {
		System.out.println("CODIGO LLEGADA*****: "+cod);
		switch (cod) {
		case 1:
			eliminarProfesor(prof2);
			break;
		default:
			break;
		}
		
	}

	public void eliminarProfesor(ProfesorVo profesor){
		System.out.println("VA A ELIMINAR PROFESOR");
		System.out.println("codigo - "+profesor.getDocumento());
		System.out.println("Nombre - "+profesor.getNombre());
		mensajeConfirmacion=profesorDao.eliminarProfesor(profesor.getDocumento());
		listaProfesores.remove(profesor);
	}
	
	public String perfilProfesor(String documento){
		System.out.println("VA A CONSULTAR PERFIL DE UN PROFESOR");
		
		profesor=profesorDao.obtenerProfesor(documento);
		
		System.out.println("codigo - "+profesor.getDocumento());
		System.out.println("Nombre - "+profesor.getNombre());
		
		session.setAttribute("profesor", profesor);
		
		return "perfil_profesor.jsf";
	}

	public ArrayList<ProfesorVo> getListaProfesores() {
		return listaProfesores;
	}

	public void setListaProfesores(ArrayList<ProfesorVo> listaProfesores) {
		this.listaProfesores = listaProfesores;
	}

	public ProfesorVo getProfesor() {
		return profesor;
	}

	public void setProfesor(ProfesorVo profesor) {
		this.profesor = profesor;
	}

	public String getMensajeConfirmacion() {
		return mensajeConfirmacion;
	}


	public void setMensajeConfirmacion(String mensajeConfirmacion) {
		this.mensajeConfirmacion = mensajeConfirmacion;
	}

	public String getNomProf() {
		return nomProf;
	}

	public void setNomProf(String nomProf) {
		this.nomProf = nomProf;
	}
	
	public ArrayList<ProfesoresGruposVo> getListaProfesoresAsociados() {
		return listaProfesoresAsociados;
	}
	
	private void setListaProfesoresAsociados(ArrayList<ProfesoresGruposVo> listaProfesoresAsociados) {
		this.listaProfesoresAsociados = listaProfesoresAsociados;		
	}
}
