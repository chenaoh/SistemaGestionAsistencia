package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import conexion.Conexion;
import vo.EstudianteVo;
import vo.ProfesorVo;

public class ProfesorDao {

	public ProfesorVo consultarProfesorLogin(String documento, String password) {
		Connection connection = null;
		Conexion miConexion = new Conexion();
		PreparedStatement statement = null;
		ResultSet result = null;

		ProfesorVo miProfesor = null;

		connection = miConexion.getConnection();
		
		System.out.println("Documento: "+documento+" , pass: "+password);
		
		try {
			if (connection != null) {
				
				String consulta = "SELECT * FROM profesor where documento = ? and password = ? and estado='activo'";

				statement = connection.prepareStatement(consulta);

				statement.setString(1, documento);
				statement.setString(2, password);
								
				result = statement.executeQuery();
				System.out.println("continua...");
				if (result.next() == true) {
					miProfesor = new ProfesorVo();
					miProfesor.setDocumento(result.getString("documento"));
					miProfesor.setNombre(result.getString("nombre"));
					miProfesor.setTelefono(result.getString("telefono"));
					miProfesor.setProfesion(result.getString("profesion"));
					miProfesor.setSexo(result.getString("sexo"));
					miProfesor.setEmail(result.getString("email"));
					miProfesor.setPerfil(result.getString("perfil"));
					miProfesor.setAsesoria(result.getString("asesoria"));
					miProfesor.setEstado(result.getString("estado"));
					miProfesor.setTipo(result.getString("tipo"));
					miProfesor.setPassword(result.getString("password"));
				}
			}
		} catch (SQLException e) {
			System.out.println("Error en la consulta del usuario Profesor: " + e.getMessage());
		} finally {
			miConexion.desconectar();
		}
		return miProfesor;
	}

	public String registrarProfesor(ProfesorVo profesor) {
		String resultado = "";

		Connection connection = null;
		Conexion conexion = new Conexion();
		PreparedStatement preStatement = null;

		connection = conexion.getConnection();
		String consulta = "INSERT INTO profesor (documento,nombre,telefono,profesion,sexo,email,perfil,asesoria,estado,tipo,password)"
				+ "  VALUES (?,?,?,?,?,?,?,?,?,?,?)";

		try {
			preStatement = connection.prepareStatement(consulta);
			preStatement.setString(1, profesor.getDocumento());
			preStatement.setString(2, profesor.getNombre());
			preStatement.setString(3, profesor.getTelefono());
			preStatement.setString(4, profesor.getProfesion());
			preStatement.setString(5, profesor.getSexo());
			preStatement.setString(6, profesor.getEmail());
			preStatement.setString(7, profesor.getPerfil());
			preStatement.setString(8, profesor.getAsesoria());
			preStatement.setString(9, profesor.getEstado());
			preStatement.setString(10, profesor.getTipo());
			preStatement.setString(11, profesor.getPassword());
			preStatement.execute();

			resultado = "Registro Exitoso!!!";

		} catch (SQLException e) {
			System.out.println("No se pudo registrar el Profesor: " + e.getMessage());
			resultado = "No se pudo registrar, verifique nuevamente";
		} finally {
			conexion.desconectar();
		}

		return resultado;
	}

	public ArrayList<ProfesorVo> obtenerListaProfesores() {
		Connection connection = null;
		Conexion miConexion = new Conexion();
		PreparedStatement statement = null;
		ResultSet result = null;

		ProfesorVo profesor = new ProfesorVo();
		ArrayList<ProfesorVo> listaProfesores = null;

		connection = miConexion.getConnection();
		
		String consulta = "SELECT * FROM profesor ";
		System.out.println("***************************************");
		System.out.println(consulta);
		try {
			if (connection != null) {
				listaProfesores = new ArrayList<>();
				statement = connection.prepareStatement(consulta);

				result = statement.executeQuery();
				while (result.next() == true) {
					profesor = new ProfesorVo();
					profesor.setDocumento(result.getString("documento"));
					profesor.setNombre(result.getString("nombre"));
					profesor.setTelefono(result.getString("telefono"));
					profesor.setProfesion(result.getString("profesion"));
					profesor.setSexo(result.getString("sexo"));
					profesor.setEmail(result.getString("email"));
					profesor.setPerfil(result.getString("perfil"));
					profesor.setAsesoria(result.getString("asesoria"));
					profesor.setEstado(result.getString("estado"));
					profesor.setPassword(result.getString("password"));
					profesor.setTipo(result.getString("tipo"));
					listaProfesores.add(profesor);
				}
			}
		} catch (SQLException e) {
			System.out.println("Error en la consulta del Profesor: " + e.getMessage());
		} finally {
			miConexion.desconectar();
		}
		return listaProfesores;

	}

	public String actualizarProfesor(ProfesorVo profesor) {
		String resultado = "";
		Connection connection = null;
		Conexion miConexion = new Conexion();
		connection = miConexion.getConnection();
		System.out.println("VA A ACTUALIZAR EL PROFESOR "+profesor.getDocumento());
		try {
			String consulta = "UPDATE profesor "
					+ " SET nombre = ? , telefono=? , profesion=? , sexo= ?, email=? , perfil= ? , asesoria= ? , estado=?, tipo=? "
					+ " WHERE documento= ? ";
			PreparedStatement preStatement = connection.prepareStatement(consulta);

			preStatement.setString(1, profesor.getNombre());
			preStatement.setString(2, profesor.getTelefono());
			preStatement.setString(3, profesor.getProfesion());
			preStatement.setString(4, profesor.getSexo());
			preStatement.setString(5, profesor.getEmail());
			preStatement.setString(6, profesor.getPerfil());
			preStatement.setString(7, profesor.getAsesoria());
			preStatement.setString(8, profesor.getEstado());			
			preStatement.setString(9, profesor.getTipo());
			preStatement.setString(10, profesor.getDocumento());
			preStatement.executeUpdate();

			resultado = "Se ha Actualizado el Profesor satisfactoriamente";

			miConexion.desconectar();

		} catch (SQLException e) {
			System.out.println(e);
			resultado = "No se pudo actualizar el Profesor";
		}
		return resultado;
	}

	public String eliminarProfesor(String documento) {
		Connection connection = null;
		Conexion miConexion = new Conexion();
		connection = miConexion.getConnection();

		String resp = "";
		try {
			String sentencia = "DELETE FROM profesor WHERE documento= ? ";

			PreparedStatement statement = connection.prepareStatement(sentencia);
			statement.setString(1,documento);

			statement.executeUpdate();

			resp = "Se ha eliminado el Profesor exitosamente";
			statement.close();
			miConexion.desconectar();

		} catch (SQLException e) {
			System.out.println(e.getMessage());
			resp = "No se pudo eliminar el Profesor";
		}
		return resp;
	}

	public ProfesorVo obtenerProfesor(String documento) {
		Connection connection = null;
		Conexion miConexion = new Conexion();
		PreparedStatement statement = null;
		ResultSet result = null;

		ProfesorVo profesor=null;

		connection = miConexion.getConnection();
		
		String consulta = "SELECT * FROM profesor where documento = '"+documento+"'";
		System.out.println("***************************************");
		System.out.println(consulta);
		try {
			if (connection != null) {
				
				statement = connection.prepareStatement(consulta);

				result = statement.executeQuery();
				if (result.next() == true) {
					profesor = new ProfesorVo();
					profesor.setDocumento(result.getString("documento"));
					profesor.setNombre(result.getString("nombre"));
					profesor.setTelefono(result.getString("telefono"));
					profesor.setProfesion(result.getString("profesion"));
					profesor.setSexo(result.getString("sexo"));
					profesor.setEmail(result.getString("email"));
					profesor.setPerfil(result.getString("perfil"));
					profesor.setAsesoria(result.getString("asesoria"));
					profesor.setEstado(result.getString("estado"));
					profesor.setPassword(result.getString("password"));
					profesor.setTipo(result.getString("tipo"));
				}
			}
		} catch (SQLException e) {
			System.out.println("Error en la consulta del Profesor: " + e.getMessage());
		} finally {
			miConexion.desconectar();
		}
		return profesor;

	}

	public ArrayList<ProfesorVo> consultarProfesorNombre(String nomProf) {
		Connection conn=null;
		PreparedStatement statement=null;
		ResultSet result=null;
		Conexion conexion=new Conexion();
		ProfesorVo miProfesor;
		ArrayList<ProfesorVo> profesor=new ArrayList<>();
		
		conn= conexion.getConnection();
		
		String consulta = "SELECT * FROM profesor WHERE nombre like '%"+nomProf+"%'";
		
	
		try {
			
			statement = conn.prepareStatement(consulta);
			result = statement.executeQuery();
			
			while (result.next()== true) {
				miProfesor=new ProfesorVo();
				miProfesor.setDocumento(result.getString("documento"));
				miProfesor.setNombre(result.getString("nombre"));
				miProfesor.setTelefono(result.getString("telefono"));
				miProfesor.setProfesion(result.getString("profesion"));
				miProfesor.setSexo(result.getString("sexo"));
				miProfesor.setEmail(result.getString("email"));
				miProfesor.setPerfil(result.getString("perfil"));
				miProfesor.setAsesoria(result.getString("asesoria"));
				miProfesor.setEstado(result.getString("estado"));
				miProfesor.setTipo(result.getString("tipo"));
				
				profesor.add(miProfesor);
			}
			
			conexion.desconectar();
			
		} catch (SQLException e) {
			System.out.println("error al obtener el profesor: "+e.getMessage());
			profesor=null;
		}
		return profesor;
	}

}
