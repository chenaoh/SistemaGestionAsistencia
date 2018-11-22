package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import conexion.Conexion;
import vo.PersonaVo;

public class PerfilDao {
	public PersonaVo obtenerPerfil(String documento, String tipo) {
		System.out.println("PerfilDao    "+documento);
		Connection connection = null;
		Conexion miConexion = new Conexion();
		PreparedStatement statement = null;
		ResultSet result = null;

		PersonaVo miEstudiante = null;

		connection = miConexion.getConnection();
		
		try {
			if (connection != null) {
				String consulta = "";
				if(tipo.equals("estudiante")){
					consulta = "SELECT * FROM estudiante where documento = ?";
				}else{
					consulta = "SELECT * FROM profesor where documento = ?";
				}

				statement = connection.prepareStatement(consulta);	

				statement.setString(1, documento);
				result = statement.executeQuery();
				if (result.next() == true) {
					miEstudiante = new PersonaVo();
					miEstudiante.setDocumento(result.getString("documento"));
					miEstudiante.setNombre(result.getString("nombre"));
					//miEstudiante.setDireccion(result.getString("direccion"));
					miEstudiante.setTelefono(result.getString("telefono"));
					miEstudiante.setEmail(result.getString("email"));
					//miEstudiante.setFechaNacimiento(result.getDate("fecha_nacimiento"));
					miEstudiante.setSexo(result.getString("sexo"));
					miEstudiante.setEstado(result.getString("estado"));
					//miEstudiante.setPassword(result.getString("password"));
					miEstudiante.setTipo(result.getString("tipo"));
				}
			}
		} catch (SQLException e) {
			System.out.println("Error en la consulta del usuario Estudiante: " + e.getMessage());
		} finally {
			miConexion.desconectar();
		}
		System.out.println("55555555555555555555555555555555555555555555555555555");
		System.out.println(miEstudiante);
		return miEstudiante;
	}

}
