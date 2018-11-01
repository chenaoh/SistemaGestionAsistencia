package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import conexion.Conexion;
import vo.GrupoVo;

public class GrupoDao {
	
	private static HashMap<String, GrupoVo> mapaGrupos;
	
	public GrupoDao(){
		mapaGrupos=new HashMap<>();
	}

	public String registrarGrupo(GrupoVo grupoVo) {
		String resultado = "";

		Connection connection = null;
		Conexion conexion = new Conexion();
		PreparedStatement preStatement = null;

		connection = conexion.getConnection();
		String consulta = "INSERT INTO grupo (codigo,nombre,director_grupo,fecha_inicio,fecha_fin,observacion,estado)"
				+ "  VALUES (?,?,?,?,?,?,?)";

		try {
			preStatement = connection.prepareStatement(consulta);
			preStatement.setString(1, grupoVo.getCodigoGrupo());
			preStatement.setString(2, grupoVo.getNombreGrupo());
			preStatement.setString(3, grupoVo.getDirectorGrupo());
			preStatement.setDate(4, grupoVo.getFechaInicioGrupo());
			preStatement.setDate(5, grupoVo.getFechaFinGrupo());
			preStatement.setString(6, grupoVo.getObservacion());
			preStatement.setString(7, grupoVo.getEstado());
			preStatement.execute();

			resultado = "Registro Exitoso!!!";

		} catch (SQLException e) {
			System.out.println("No se pudo registrar el Grupo: " + e.getMessage());
			resultado = "No se pudo registrar, verifique nuevamente";
		} finally {
			conexion.desconectar();
		}

		return resultado;
	}

	public ArrayList<GrupoVo> obtenerListaGrupos() {
		Connection connection = null;
		Conexion miConexion = new Conexion();
		PreparedStatement statement = null;
		ResultSet result = null;

		GrupoVo miGrupo = new GrupoVo();
		ArrayList<GrupoVo> listaGrupos = null;

		connection = miConexion.getConnection();
		
		String consulta = "SELECT * FROM grupo ";
		System.out.println("***************************************");
		System.out.println(consulta);
		try {
			if (connection != null) {
				listaGrupos = new ArrayList<>();
				statement = connection.prepareStatement(consulta);

				result = statement.executeQuery();

				while (result.next() == true) {
					miGrupo = new GrupoVo();
					miGrupo.setCodigoGrupo(result.getString("codigo"));
					miGrupo.setNombreGrupo(result.getString("nombre"));
					miGrupo.setDirectorGrupo(result.getString("director_grupo"));
					miGrupo.setFechaInicioGrupo(result.getDate("fecha_inicio"));
					miGrupo.setFechaFinGrupo(result.getDate("fecha_fin"));
					miGrupo.setObservacion(result.getString("observacion"));
					miGrupo.setEstado(result.getString("estado"));
					miGrupo.setFechaIni(miGrupo.getFechaInicioGrupo()+"");
					miGrupo.setFechaFin(miGrupo.getFechaFinGrupo()+"");
					listaGrupos.add(miGrupo);
				}
				
			}
		} catch (SQLException e) {
			System.out.println("Error en la consulta del Grupo: " + e.getMessage());
		} finally {
			miConexion.desconectar();
		}
		return listaGrupos;
	}

	public String actualizarGrupo(GrupoVo grupo) {
		String resultado = "";
		Connection connection = null;
		Conexion miConexion = new Conexion();
		connection = miConexion.getConnection();
		System.out.println("VA A ACTUALIZAR GRUPO");
		try {
			String consulta = "UPDATE grupo "
					+ " SET nombre = ? , director_grupo=? , fecha_inicio=? , fecha_fin=? , observacion= ? , estado= ? "
					+ " WHERE codigo= ? ";
			PreparedStatement preStatement = connection.prepareStatement(consulta);

			preStatement.setString(1, grupo.getNombreGrupo());
			preStatement.setString(2, grupo.getDirectorGrupo());
			preStatement.setDate(3, grupo.getFechaInicioGrupo());
			preStatement.setDate(4, grupo.getFechaFinGrupo());
			preStatement.setString(5, grupo.getObservacion());
			preStatement.setString(6, grupo.getEstado());
			preStatement.setString(7, grupo.getCodigoGrupo());
			preStatement.executeUpdate();

			resultado = "Se ha Actualizado el grupo satisfactoriamente";

			miConexion.desconectar();

		} catch (SQLException e) {
			System.out.println(e);
			resultado = "No se pudo actualizar el grupo";
		}
		return resultado;
	}

	public String eliminarGrupo(GrupoVo grupo) {
		Connection connection = null;
		Conexion miConexion = new Conexion();
		connection = miConexion.getConnection();

		String resp = "";
		try {
			String sentencia = "DELETE FROM grupo WHERE codigo= ? ";

			PreparedStatement statement = connection.prepareStatement(sentencia);
			statement.setString(1, grupo.getCodigoGrupo());

			statement.executeUpdate();

			resp = "Se ha eliminado el grupo exitosamente";
			statement.close();
			miConexion.desconectar();

		} catch (SQLException e) {
			System.out.println(e.getMessage());
			resp = "No se pudo eliminar el grupo";
		}
		return resp;
	}

	public ArrayList<String> cargarGrupos() {
		Connection connection = null;
		Conexion miConexion = new Conexion();
		connection = miConexion.getConnection();
		ResultSet result = null;
		
		GrupoVo grupoVo;
		ArrayList<String> grupos = new ArrayList<>();

		String resp = "";
		try {
			String sentencia = "SELECT * FROM grupo";

			PreparedStatement statement = connection.prepareStatement(sentencia);
			result = statement.executeQuery();
		
			while(result.next()==true){
				grupoVo= new GrupoVo();
				grupoVo.setNombreGrupo(result.getString("nombre"));
				grupos.add(grupoVo.getNombreGrupo());
			}
			
			miConexion.desconectar();

		} catch (SQLException e) {
			System.out.println("Error al cargar los grupos en estudiantes"+e.getMessage());
			grupos=null;
		}
		return grupos;
		
	}
	
	public ArrayList<GrupoVo> consultarGrupoNombre(String nombreGrupo) {
		Connection conn=null;
		PreparedStatement statement=null;
		ResultSet result=null;
		Conexion conexion=new Conexion();
		GrupoVo miGrupo;
		ArrayList<GrupoVo> grupo=new ArrayList<>();
		
		conn= conexion.getConnection();
		
		String consulta = "SELECT * FROM grupo WHERE nombre like '%"+nombreGrupo+"%'";
		
	
		try {
			
			statement = conn.prepareStatement(consulta);
			result = statement.executeQuery();
			
			while (result.next()== true) {
				miGrupo = new GrupoVo();
				miGrupo.setCodigoGrupo(result.getString("codigo"));
				miGrupo.setNombreGrupo(result.getString("nombre"));
				miGrupo.setDirectorGrupo(result.getString("director_grupo"));
				miGrupo.setFechaInicioGrupo(result.getDate("fecha_inicio"));
				miGrupo.setFechaFinGrupo(result.getDate("fecha_fin"));
				miGrupo.setObservacion(result.getString("observacion"));
				miGrupo.setEstado(result.getString("estado"));
				miGrupo.setFechaIni(miGrupo.getFechaInicioGrupo()+"");
				miGrupo.setFechaFin(miGrupo.getFechaFinGrupo()+"");
				grupo.add(miGrupo);
			}
			
			conexion.desconectar();
			
		} catch (SQLException e) {
			System.out.println("error al obtener el profesor: "+e.getMessage());
			grupo=null;
		}
		return grupo;
	}

	public void cargarDatosHasgMap(ArrayList<GrupoVo> listaGrupos) {
		for (int i = 0; i < listaGrupos.size(); i++) {
			mapaGrupos.put(listaGrupos.get(i).getNombreGrupo(), listaGrupos.get(i));
		}
		
	}

	public String obtenerId(String grupo) {
		System.err.println("MAPA GRUPOS*******: "+mapaGrupos);
		GrupoVo grupoVo = mapaGrupos.get(grupo);
		String codigo = grupoVo.getCodigoGrupo();
		
		return codigo;
	}

}
