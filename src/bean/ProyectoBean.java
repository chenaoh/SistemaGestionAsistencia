package bean;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import dao.EstudianteDao;
import dao.GrupoDao;
import dao.ProfesorDao;
import dao.ProyectoDao;
import vo.GrupoVo;
import vo.ProfesorVo;
import vo.ProyectoVo;
@ManagedBean
@ViewScoped
public class ProyectoBean {
	
	private ProyectoVo proyecto;
	private String mensajeConfirmacion;
	private ProyectoDao proyectoDao;
	private EstudianteDao estudianteDAO;
	private ArrayList<ProyectoVo> listaProyectos=new ArrayList<>();
	private List<SelectItem> itemGrupos;
	HashMap<String, GrupoVo> mapaGrupos=new HashMap<>();
	private ArrayList<String> proyectos;
	private ArrayList<String> estudiantes;
	private ArrayList<String> nombresEstudiantes;
	private String nombreProyecto;
	private String mensaje;
	EstudianteBean estudianteBean;
	private String filtro;
	private String nombreGrupo;
	private boolean consultaGrupo;
	private LoginBean login;
	
	public ProyectoBean(){
		proyecto=new ProyectoVo();
		proyectoDao=new ProyectoDao();
		estudianteDAO = new EstudianteDao();
		itemGrupos=new ArrayList<SelectItem>();
		estudianteBean = new EstudianteBean();
		login = new LoginBean();
		setProyectos(new ArrayList<>());
		setEstudiantes(new ArrayList<>());
		cargarGrupos();
		cargarProyectos();
		cargarNombreProyectos();
		cargarEstudiantes();
		cargarDatosHashMapProyectos();
		
	}
	
	public void cargarDatosHashMapProyectos() {
		ArrayList<ProyectoVo> listProyectos = proyectoDao.obtenerListaProyecto();
		proyectoDao.cargarDatosHashMapProyectos(listProyectos);
		estudianteBean.cargarDatosHashMapEstudiantes();
	}
	
	public void consultarProyectoNombre(){
		if(filtro.equals("N")){
		setConsultaGrupo(false);
		setListaProyectos(proyectoDao.consultarProyectoNombre(getNombreProyecto()));
		}else if(filtro.equals("G")){
		setConsultaGrupo(true);
		setListaProyectos(proyectoDao.consultarProyectoGrupo(getNombreProyecto()));
		}
	}

	private void cargarEstudiantes() {
		setEstudiantes(estudianteDAO.obtenerNombres());
		
	}

	public void cargarNombreProyectos() {
		setProyectos(proyectoDao.consultarNombresProyectos());
		
	}

	private void cargarGrupos() {
		GrupoDao grupoDao=new GrupoDao();
		ArrayList<GrupoVo> listaGrupos;
		listaGrupos=grupoDao.obtenerListaGrupos();
		
		if (listaGrupos.size()>0) {
				
			for (int i = 0; i < listaGrupos.size(); i++) {
				itemGrupos.add(new SelectItem(listaGrupos.get(i).getCodigo(),listaGrupos.get(i).getNombre()));
				mapaGrupos.put(listaGrupos.get(i).getCodigo(),listaGrupos.get(i));
				System.out.println(listaGrupos.get(i).getCodigo()+" - "+listaGrupos.get(i).getNombre());
			}
			
		}else{
			listaGrupos=new ArrayList<>();
			mensajeConfirmacion="Actualmente no existen grupos registrados para asociarlos al Proyecto";
		}
	}
	
	public String obtenerNombreGrupo(String codigoGrupo){
		String nombreGrupo="";
		System.out.println("CODIGO PROYECTO: "+codigoGrupo);
		System.out.println("Mapa: "+mapaGrupos);
		
		if (mapaGrupos.get(codigoGrupo)!=null) {
			nombreGrupo=mapaGrupos.get(codigoGrupo).getNombre();	
		}else{
			nombreGrupo="El grupo no Existe";
		}
		
		return nombreGrupo;
	}
	
	private void cargarProyectos() {
		System.out.println("INGRESA A CARGAR PROYECTOS");
		listaProyectos.clear();
		listaProyectos=proyectoDao.obtenerListaProyecto();
		
		if (listaProyectos==null) {
			mensajeConfirmacion="No se pudo conectar, verifique que la BD esté iniciada.";
		}
	}
	
	public void registrarProyecto(){
		System.out.println(proyecto.getNumProyecto());
		System.out.println(proyecto.getNombreProyecto());
		System.out.println(proyecto.getDescripcionProyecto());
		System.out.println(proyecto.getCodigoProyecto());
		
		mensajeConfirmacion=proyectoDao.registrarProyecto(proyecto);
		if(mensajeConfirmacion!=null){
			login.calcularPanelEstadisticas();
		}
		proyecto=new ProyectoVo();
	}
	
	public void eliminarProyecto(ProyectoVo proyecto){
		System.out.println("VA A ELIMINAR PROYECTO");
		mensajeConfirmacion=proyectoDao.eliminarProyecto(proyecto);
		listaProyectos.remove(proyecto);
	}

	public void editarProyecto(ProyectoVo proyecto){
		proyecto.setEditar(true);
		System.out.println("VA A EDITAR PROYECTO");
		
	}
	
	public void guardarProyecto(ProyectoVo proyecto){
		System.out.println("VA A GUARDAR PROYECTO");
		mensajeConfirmacion=proyectoDao.actualizarProyecto(proyecto);
		proyecto.setEditar(false);
		
	}
	
	public void asociarEstudiantes(){
		ArrayList<String> idEstudiante = estudianteDAO.obtenerIdEstudiante(getNombresEstudiantes());
		ArrayList<String> correos = estudianteDAO.obtenerCorreosEstudiantes(getNombresEstudiantes());
		String res = proyectoDao.consultarAsociacion(idEstudiante);
		if(res.equals("no existe")) {
			int idProyecto = proyectoDao.obtenerIdProyecto(getNombreProyecto());
			registrarAsociacionDeEstudiantes(idEstudiante,idProyecto);
			enviarCorreos(correos, getNombreProyecto(), getNombresEstudiantes(), idProyecto, idEstudiante);
			estudianteBean.cargarEstudiantesAsociados();
		}else {
			setMensaje("Uno o los estudiantes ya se encuentra asociados a un proyecto");
		}
	}
	
	private void enviarCorreos(ArrayList<String> correos, String nombreProyecto, ArrayList<String> estudiantes, int idProyecto, ArrayList<String> idEstudiante) {
		for (int i = 0; i < correos.size(); i++) {
			Properties propiedad = new Properties();
			propiedad.setProperty("mail.smtp.host", "smtp.gmail.com");
			propiedad.setProperty("mail.smtp.starttls.enable", "true");
	        propiedad.setProperty("mail.smtp.port", "587");
	        propiedad.setProperty("mail.smtp.auth", "true");
			
			Session sesion = Session.getDefaultInstance(propiedad);
			
			String correoEnvia = "adsisga@gmail.com";
			String contraseña = "adsi1598667";
			String destinatario = correos.get(i); 
			String asunto = "Asociacion de Proyecto";
			String mensaje = "Estimado(a)  "+estudiantes.get(i)+"\n";
			mensaje+="Usted fue añadido al proyecto "+getNombreProyecto()+"\n\n";
			mensaje+="A continuacion encontará los datos de la asociación: "+"\n\n";
			mensaje+="    Código del proyecto: "+idProyecto+"\n\n";
			mensaje+="    Nombre del Proyecto: "+getNombreProyecto()+"\n\n";
			mensaje+="    Documento del Estudiante: "+idEstudiante.get(i)+"\n\n";
			mensaje+="    Nombre del Estudiante: "+estudiantes.get(i)+"\n\n";
			mensaje+="Para verificar su asociación ingrese a http://localhost:8080/SistemaGestionAsistencia/pages/login.jsf"+"\n\n\n	";
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
				
				System.out.println("Correos enviados exitosamente: "+correos.get(i));
			
			} catch (AddressException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}

	public void desasociarEstudiantes(String nombre){
		String doc = estudianteDAO.obtenerIdUnEstudiante(nombre);
		System.out.println("Documento estudiante***: "+doc);
		
		String res = proyectoDao.desasociarEstudiantes(doc);
		
		if(res.equals("ok")){
			estudianteBean.cargarEstudiantesAsociados();
		}
	}

	private void registrarAsociacionDeEstudiantes(ArrayList<String> idEstudiante, int idProyecto) {
		String res = proyectoDao.registrarAsociacionDeEstudiantes(idEstudiante,idProyecto);
		if(res.equals("ok")){
			setMensaje("Registro Exitoso!!!");
		}else{
			setMensaje("Registro Erroneo");
		}
		
	}

	public ProyectoVo getProyecto() {
		return proyecto;
	}

	public void setProyecto(ProyectoVo proyecto) {
		this.proyecto = proyecto;
	}


	public String getMensajeConfirmacion() {
		return mensajeConfirmacion;
	}



	public void setMensajeConfirmacion(String mensajeConfirmacion) {
		this.mensajeConfirmacion = mensajeConfirmacion;
	}

	public ArrayList<ProyectoVo> getListaProyectos() {
		return listaProyectos;
	}

	public void setListaProyectos(ArrayList<ProyectoVo> listaProyectos) {
		this.listaProyectos = listaProyectos;
	}
	
	public List<SelectItem> getItemGrupos() {
		return itemGrupos;
	}

	public void setItemGrupos(List<SelectItem> itemGrupos) {
		this.itemGrupos = itemGrupos;
	}

	public ArrayList<String> getProyectos() {
		return proyectos;
	}

	public void setProyectos(ArrayList<String> proyectos) {
		this.proyectos = proyectos;
	}

	public ArrayList<String> getEstudiantes() {
		return estudiantes;
	}

	public void setEstudiantes(ArrayList<String> estudiantes) {
		this.estudiantes = estudiantes;
	}

	public String getNombreProyecto() {
		return nombreProyecto;
	}

	public void setNombreProyecto(String nombreProyecto) {
		this.nombreProyecto = nombreProyecto;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public ArrayList<String> getNombresEstudiantes() {
		return nombresEstudiantes;
	}

	public void setNombresEstudiantes(ArrayList<String> nombresEstudiantes) {
		this.nombresEstudiantes = nombresEstudiantes;
	}
	public boolean isConsultaGrupo() {
		return consultaGrupo;
	}

	public void setConsultaGrupo(boolean consultaGrupo) {
		this.consultaGrupo = consultaGrupo;
	}

	public String getNombreGrupo() {
		return nombreGrupo;
	}

	public void setNombreGrupo(String nombreGrupo) {
		this.nombreGrupo = nombreGrupo;
	}

	public String getFiltro() {
		return filtro;
	}

	public void setFiltro(String filtro) {
		this.filtro = filtro;
	}

}
