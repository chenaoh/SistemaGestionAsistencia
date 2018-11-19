package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import conexion.Conexion;
import sun.dc.pr.PRError;
import vo.GrupoVo;
import vo.ProyectoVo;

public class ProyectoDao {
	
	private HashMap<String , ProyectoVo> mapaProyectos;
	
	public ProyectoDao(){
		mapaProyectos = new HashMap<>();
	}

	public String registrarProyecto(ProyectoVo proyecto) {
		String resultado = "";

		Connection connection = null;
		Conexion conexion = new Conexion();
		PreparedStatement preStatement = null;

		connection = conexion.getConnection();
		String consulta = "INSERT INTO proyecto (numero,nombre,descripcion,grupo)"
				+ "  VALUES (?,?,?,?)";

		try {
			preStatement = connection.prepareStatement(consulta);
			preStatement.setInt(1, proyecto.getNumProyecto());
			preStatement.setString(2, proyecto.getNombreProyecto());
			preStatement.setString(3, proyecto.getDescripcionProyecto());
			preStatement.setString(4, proyecto.getCodigoGrupo());
			preStatement.execute();

			resultado = "Registro Exitoso!!!";

		} catch (SQLException e) {
			System.out.println("No se pudo registrar el Proyecto: " + e.getMessage());
			resultado = "No se pudo registrar, verifique nuevamente";
		} finally {
			conexion.desconectar();
		}

		return resultado;
	}

	public ArrayList<ProyectoVo> obtenerListaProyecto() {
		Connection connection = null;
		Conexion miConexion = new Conexion();
		PreparedStatement statement = null;
		ResultSet result = null;

		ProyectoVo proyecto = new ProyectoVo();
		ArrayList<ProyectoVo> listaProyectos = null;

		connection = miConexion.getConnection();
		
		String consulta = "SELECT * FROM proyecto ";
		System.out.println("***************************************");
		System.out.println(consulta);
		try {
			if (connection != null) {
				listaProyectos = new ArrayList<>();
				statement = connection.prepareStatement(consulta);

				result = statement.executeQuery();

				while (result.next() == true) {
					proyecto = new ProyectoVo();
					proyecto.setCodigoProyecto(result.getInt("codigo"));
					proyecto.setNumProyecto(result.getInt("numero"));
					proyecto.setNombreProyecto(result.getString("nombre"));
					proyecto.setDescripcionProyecto(result.getString("descripcion"));
					proyecto.setCodigoGrupo(result.getString("grupo"));
					listaProyectos.add(proyecto);
				}
				
			}
		} catch (SQLException e) {
			System.out.println("Error en la consulta del Proyecto: " + e.getMessage());
		} finally {
			miConexion.desconectar();
		}
		return listaProyectos;
	}


	public String actualizarProyecto(ProyectoVo proyecto) {
		String resultado = "";
		Connection connection = null;
		Conexion miConexion = new Conexion();
		connection = miConexion.getConnection();
		System.out.println("VA A ACTUALIZAR PROYECTO");
		System.out.println("Codigo : "+proyecto.getCodigoProyecto());
		System.out.println("Numero : "+proyecto.getNumProyecto());
		System.out.println("Nombre : "+proyecto.getNombreProyecto());
		System.out.println("Descripcion : "+proyecto.getDescripcionProyecto());
		System.out.println("Grupo : "+proyecto.getCodigoGrupo());
		try {
			String consulta = "UPDATE proyecto "
					+ " SET numero = ? , nombre=? , descripcion=? , grupo=? "
					+ " WHERE codigo= ? ";
			PreparedStatement preStatement = connection.prepareStatement(consulta);

			preStatement.setInt(1, proyecto.getNumProyecto());
			preStatement.setString(2, proyecto.getNombreProyecto());
			preStatement.setString(3, proyecto.getDescripcionProyecto());
			preStatement.setString(4, proyecto.getCodigoGrupo());
			preStatement.setInt(5, proyecto.getCodigoProyecto());
			preStatement.executeUpdate();

			resultado = "Se ha Actualizado el Proyecto satisfactoriamente";

			miConexion.desconectar();

		} catch (SQLException e) {
			System.out.println(e);
			resultado = "No se pudo actualizar el Proyecto";
		}
		return resultado;
	}

	public String eliminarProyecto(ProyectoVo proyecto) {
		Connection connection = null;
		Conexion miConexion = new Conexion();
		connection = miConexion.getConnection();

		String resp = "";
		try {
			String sentencia = "DELETE FROM proyecto WHERE codigo= ? ";

			PreparedStatement statement = connection.prepareStatement(sentencia);
			statement.setInt(1, proyecto.getCodigoProyecto());

			statement.executeUpdate();

			resp = "Se ha eliminado el Proyecto exitosamente";
			statement.close();
			miConexion.desconectar();

		} catch (SQLException e) {
			System.out.println(e.getMessage());
			resp = "No se pudo eliminar el Proyecto";
		}
		return resp;
	}

	public ArrayList<String> consultarNombresProyectos() {
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		Conexion conexion = new Conexion();
		ProyectoVo miProyectoVo;
		ArrayList<String> proyectos = new ArrayList<>();
		
		conn =  conexion.getConnection();
		
		String consulta = "SELECT * FROM proyecto";
		
		try {
			
			statement = conn.prepareStatement(consulta);
			result = statement.executeQuery();
			
			while(result.next()==true) {
				miProyectoVo = new ProyectoVo();
				miProyectoVo.setNombreProyecto(result.getString("nombre"));
				proyectos.add(miProyectoVo.getNombreProyecto());
			}
			
		} catch (SQLException e) {
			System.out.println("Error al cargar la lista de los nombres de los proyectos: "+e.getMessage());
			proyectos = null;
		}
				
		return proyectos;
	}

	public void cargarDatosHashMapProyectos(ArrayList<ProyectoVo> listaProyectos) {
		for (int i = 0; i < listaProyectos.size(); i++) {
			mapaProyectos.put(listaProyectos.get(i).getNombreProyecto(), listaProyectos.get(i));
		}
			
		System.out.println("****Mapa proyectos****: "+mapaProyectos);
	}

	public int obtenerIdProyecto(String nombreProyecto) {
		ProyectoVo miProyecto = mapaProyectos.get(nombreProyecto);
		int idProyecto = miProyecto.getCodigoProyecto();
		return idProyecto;
	}

	public String registrarAsociacionDeEstudiantes(ArrayList<String> idEstudiante, int idProyecto) {
		String resultado = "";
		Connection conn = null;
		PreparedStatement statement = null;
		Conexion conexion  = new Conexion();
		
		conn = conexion.getConnection();
		
		
		
		try {
			
			for (int i = 0; i < idEstudiante.size(); i++) {
				System.out.println("Estudiante "+i+"-"+idEstudiante.get(i));
				String consulta = "INSERT INTO proyecto_estudiantes VALUES (?,?)";
				statement = conn.prepareStatement(consulta);
				statement.setInt(1, idProyecto);
				statement.setString(2, idEstudiante.get(i));
				statement.execute();
			}
			
			resultado = "ok";
			
			
		} catch (SQLException e) {
			System.out.println("Error al registrar la asociacion: "+e.getMessage());
			resultado = "error";
		}
		
		conexion.desconectar();
		
		return resultado;
		
	}

	public String consultarAsociacion(ArrayList<String> idEstudiante) {
		Connection conn = null;
		PreparedStatement statement = null;
		Conexion conexion = new Conexion();
		ResultSet result = null;
		String res="";
		
		conn = conexion.getConnection();
		
		try {
			
			for (int i = 0; i < idEstudiante.size(); i++) {
				String consulta = "SELECT * FROM proyecto_estudiantes WHERE doc_estudiante = ?";
				statement = conn.prepareStatement(consulta);
				statement.setString(1, idEstudiante.get(i));
				result = statement.executeQuery();
				
				if(result.next()==true) {
					res="existe";
					break;
				}else {
					res="no existe";
				}
				
				System.out.println("REspuesta de asocicacion: ******jjsdfhsdjfd"+res);
				
			}
			
			conexion.desconectar();
			
		} catch (SQLException e) {
			System.out.println("Error al verificar la existencia de la asociacion: "+e.getMessage());
		}
		
		return res;
	}

	public String desasociarEstudiantes(String doc) {
		Connection conn = null;
		PreparedStatement statement = null;
		Conexion conexion = new Conexion();
		String res ="";
		
		conn = conexion.getConnection();
		
		String consulta = "DELETE FROM proyecto_estudiantes WHERE doc_estudiante = ?";
		
		try {
			
			statement = conn.prepareStatement(consulta);
			statement.setString(1, doc);
			statement.execute();
		
			res = "ok";
			
			
			
		} catch (SQLException e) {
			System.out.println("Error al desasociar al estudiante: "+e.getMessage());
			res = "error";
		}
		
		conexion.desconectar();
		
		return res;
	}
	
	public ArrayList<ProyectoVo> consultarProyectoNombre(String nombreProyecto) {
		Connection connection = null;
		Conexion miConexion = new Conexion();
		PreparedStatement statement = null;
		ResultSet result = null;

		ProyectoVo proyecto = new ProyectoVo();
		ArrayList<ProyectoVo> listaProyectos = null;

		connection = miConexion.getConnection();
		
		String consulta = "SELECT * FROM proyecto WHERE nombre like '%"+nombreProyecto+"%'";
		System.out.println(consulta);
		try {
			if (connection != null) {
				listaProyectos = new ArrayList<>();
				statement = connection.prepareStatement(consulta);

				result = statement.executeQuery();

				while (result.next() == true) {
					proyecto = new ProyectoVo();
					proyecto.setCodigoProyecto(result.getInt("codigo"));
					proyecto.setNumProyecto(result.getInt("numero"));    
					proyecto.setNombreProyecto(result.getString("nombre"));
					proyecto.setDescripcionProyecto(result.getString("descripcion"));
					proyecto.setCodigoGrupo(result.getString("grupo"));
					listaProyectos.add(proyecto);
				}
				
			}
		} catch (SQLException e) {
			System.out.println("Error en la consulta del Proyecto: " + e.getMessage());
		} finally {
			miConexion.desconectar();
		}
		return listaProyectos;
	}
	
	public ArrayList<ProyectoVo> consultarProyectoGrupo(String nombreProyecto) {
		Connection connection = null;
		Conexion miConexion = new Conexion();
		PreparedStatement statement = null;
		ResultSet result = null;

		ProyectoVo proyecto = new ProyectoVo();
		ArrayList<ProyectoVo> listaProyectos = null;

		connection = miConexion.getConnection();
		
		
		String consulta = "SELECT p.codigo, p.numero, p.nombre, p.descripcion, g.nombre FROM proyecto p inner join grupo g on p.grupo=g.codigo WHERE g.nombre like '%"+nombreProyecto+"%'";
		System.out.println(consulta);
		try {
			if (connection != null) {
				listaProyectos = new ArrayList<>();
				statement = connection.prepareStatement(consulta);

				result = statement.executeQuery();

				while (result.next() == true) {
					proyecto = new ProyectoVo();
					proyecto.setCodigoProyecto(result.getInt("p.codigo"));
					proyecto.setNumProyecto(result.getInt("p.numero"));    
					proyecto.setNombreProyecto(result.getString("p.nombre"));
					proyecto.setDescripcionProyecto(result.getString("p.descripcion"));
					proyecto.setNombreGrupo(result.getString("g.nombre"));
					listaProyectos.add(proyecto);
				}
				
			}
		} catch (SQLException e) {
			System.out.println("Error en la consulta del Proyecto: " + e.getMessage());
		} finally {
			miConexion.desconectar();
		}
		return listaProyectos;
	}

	public String cantidadProyectos() {
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		Conexion conexion = new Conexion();
		String cantidad = "";
		
		conn = conexion.getConnection();
		
		String consulta = "SELECT COUNT(*) as cantidad FROM proyecto";
		
		try {
			
			statement = conn.prepareStatement(consulta);
			result = statement.executeQuery();
			
			while(result.next()){
				cantidad = result.getString("cantidad");
			}
			
			System.out.println("CANT_PROYECTOS: "+cantidad);
			
			conexion.desconectar();
			
		} catch (SQLException e) {
			System.out.println("Error al obtener la cantidad de proyectos: "+e.getMessage());
		}
		
		return cantidad;
	}


}
