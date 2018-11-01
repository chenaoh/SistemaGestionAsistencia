package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import conexion.Conexion;
import vo.GrupoVo;

public class AsistenciaDao {
	public String fecha;
	public String registrarAsistencia(ArrayList<String> documentos, String profesor, String grupo, String observacion) {
		String resultado = "";

		Connection connection = null;
		Conexion conexion = new Conexion();
		PreparedStatement preStatement = null;

		connection = conexion.getConnection();
		String consulta = "INSERT INTO asistencia (doc_est_,cod_grupo,doc_prof,fecha_falta,observacion_falta)"
				+ "  VALUES (?,?,?,?,?)";

		try {
			Date myDate = new Date();
			fecha = new SimpleDateFormat("yyyy-MM-dd").format(myDate);
			
			for (int i = 0; i < documentos.size(); i++) {
				preStatement = connection.prepareStatement(consulta);
				preStatement.setString(1, documentos.get(i));
				preStatement.setString(2, grupo);
				preStatement.setString(3, profesor);
				preStatement.setString(4, fecha);
				preStatement.setString(5, observacion);
				preStatement.execute();
			}
			
			resultado = "ok";
			
		} catch (SQLException e) {
			System.out.println("No se pudo registrar la asistencia: " + e.getMessage());
			resultado = "No se pudo registrar, verifique nuevamente";
		} finally {
			conexion.desconectar();
		}

		return resultado;
	}
}
