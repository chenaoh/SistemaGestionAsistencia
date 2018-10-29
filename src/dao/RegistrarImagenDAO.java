package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import conexion.Conexion;
import vo.PersonaImagenVo;
import vo.PersonaVo;

public class RegistrarImagenDAO {

	public String agregarPersona(PersonaImagenVo miPersona) {
		String resultado = "";

		Connection connection = null;
		Conexion conexion = new Conexion();
		PreparedStatement preStatement = null;

		connection = conexion.getConnection();
		String consulta = "INSERT INTO persona_imagen (documento,nombre,imagen)"
				+ "  VALUES (?,?,?)";

		try {
			preStatement = connection.prepareStatement(consulta);
			preStatement.setString(1, miPersona.getDocumento());
			preStatement.setString(2, miPersona.getNombre());
			
			if (miPersona.getImagen() != null) {
	                // fetches input stream of the upload file for the blob column
				preStatement.setBlob(3, miPersona.getImagen());
	        }
			
			 int row = preStatement.executeUpdate();
	         if (row > 0) {
	        	 resultado = "File uploaded and saved into database";
	          }


		} catch (SQLException e) {
			System.out.println("No se pudo registra la persona: " + e.getMessage());
			resultado = "No se pudo registrar";
		} finally {
			conexion.desconectar();
		}

		return resultado;
	}
	
	
}
