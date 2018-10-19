package bean;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

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
	private ArrayList<ProyectoVo> listaProyectos=new ArrayList<>();
	private List<SelectItem> itemGrupos;
	HashMap<String, GrupoVo> mapaGrupos=new HashMap<>();
	
	public ProyectoBean(){
		proyecto=new ProyectoVo();
		proyectoDao=new ProyectoDao();
		itemGrupos=new ArrayList<SelectItem>();
		cargarGrupos();
		cargarProyectos();
		
	}
	
	private void cargarGrupos() {
		GrupoDao grupoDao=new GrupoDao();
		ArrayList<GrupoVo> listaGrupos;
		listaGrupos=grupoDao.obtenerListaGrupos();
		
		if (listaGrupos.size()>0) {
				
			for (int i = 0; i < listaGrupos.size(); i++) {
				itemGrupos.add(new SelectItem(listaGrupos.get(i).getCodigoGrupo(),listaGrupos.get(i).getNombreGrupo()));
				mapaGrupos.put(listaGrupos.get(i).getCodigoGrupo(),listaGrupos.get(i));
				System.out.println(listaGrupos.get(i).getCodigoGrupo()+" - "+listaGrupos.get(i).getNombreGrupo());
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
			nombreGrupo=mapaGrupos.get(codigoGrupo).getNombreGrupo();	
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

}
