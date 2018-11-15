package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import conexion.Conexion;
import vo.AsistenciaVo;
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
	public ArrayList<AsistenciaVo> obtenerListaAsistencias() {
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		Conexion conexion = new Conexion();
		AsistenciaVo asistenciaVo;
		ArrayList<AsistenciaVo> listaAsistencias = new ArrayList<>();
		
		conn = conexion.getConnection();
		
		String consulta = "SELECT a.codigo, e.nombre as nombre_estu, g.nombre as grupo, p.nombre as nom_prof, a.fecha_falta, a.observacion_falta FROM asistencia a, estudiante e, profesor p, grupo g WHERE a.doc_est_ = e.documento AND a.cod_grupo = g.codigo AND a.doc_prof = p.documento GROUP BY a.codigo";
		try {
			
			statement = conn.prepareStatement(consulta);
			result = statement.executeQuery();
			
			
			while(result.next()) {
				asistenciaVo = new AsistenciaVo();
				asistenciaVo.setCodigo(result.getString("codigo"));
				asistenciaVo.setDocumentoEstudiante(result.getString("nombre_estu"));
				asistenciaVo.setCodigoGrupo(result.getString("grupo"));
				asistenciaVo.setDocumentoProfesor(result.getString("nom_prof"));
				asistenciaVo.setFechaFalta(result.getDate("fecha_falta"));
				asistenciaVo.setFecha(asistenciaVo.getFechaFalta()+"");
				asistenciaVo.setObservacionFalta(result.getString("observacion_falta"));
				listaAsistencias.add(asistenciaVo);
			}
			
			conexion.desconectar();
			
		} catch (SQLException e) {
			System.out.println("Error al obtener la lista de asistencias: "+e.getMessage());
			listaAsistencias = null;
		}
		
		
		return listaAsistencias;
	}
	public String actualizarAsistencia(String codigo, String idGrupo, String idProfesor, String idEstudiante, AsistenciaVo asistenciaVo) {
		String resultado = "";
		Connection connection = null;
		Conexion miConexion = new Conexion();
		connection = miConexion.getConnection();
		System.out.println("VA A ACTUALIZAR ASISTENCIA");
		try {
			String consulta = "UPDATE asistencia "
					+ " SET doc_est_ = ? , cod_grupo=? , doc_prof=? , fecha_falta=? , observacion_falta = ?"
					+ " WHERE codigo = ? ";
			PreparedStatement preStatement = connection.prepareStatement(consulta);
			System.out.println("Fecha: "+asistenciaVo.getFechaFalta());
			preStatement.setString(1, idEstudiante);
			preStatement.setString(2, idGrupo);
			preStatement.setString(3, idProfesor);
			preStatement.setString(4, asistenciaVo.getFecha());
			preStatement.setString(5, asistenciaVo.getObservacionFalta());
			preStatement.setString(6, codigo);
			preStatement.executeUpdate();
			

			resultado = "ok";

			miConexion.desconectar();

		} catch (SQLException e) {
			System.out.println("Error al actualizar la asistencia: "+e.getMessage());
			resultado = "No se pudo actualizar el estudiante";
		}
		return resultado;

	}
	public String eliminarAsistencia(String codigo) {
		Connection connection = null;
		Conexion miConexion = new Conexion();
		connection = miConexion.getConnection();

		String resp = "";
		try {
			String sentencia = "DELETE FROM asistencia WHERE codigo= ? ";

			PreparedStatement statement = connection.prepareStatement(sentencia);
			statement.setString(1, codigo);

			statement.executeUpdate();

			resp = "Se ha eliminado el estudiante exitosamente";
			statement.close();
			miConexion.desconectar();

		} catch (SQLException e) {
			System.out.println(e.getMessage());
			resp = "No se pudo eliminar el estudiante";
		}
		return resp;
	}
	public String cantidadFaltas() {
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		Conexion conexion = new Conexion();
		String cantidad = "";
		
		conn = conexion.getConnection();
		
		String consulta = "SELECT COUNT(*) as cantidad FROM asistencia";
		
		try {
			
			statement = conn.prepareStatement(consulta);
			result = statement.executeQuery();
			
			while(result.next()){
				cantidad = result.getString("cantidad");
			}
			
			System.out.println("CANT_ASISTENCIAS: "+cantidad);
			
			conexion.desconectar();
			
		} catch (SQLException e) {
			System.out.println("Error al obtener la cantidad de asistencias: "+e.getMessage());
		}
		
		return cantidad;
	}
	public ArrayList<AsistenciaVo> filtrarListaFecha(String fecha) {
		System.out.println("ESTA FILTRANDO POR FECHA");
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		Conexion conexion = new Conexion();
		AsistenciaVo asistenciaVo;
		ArrayList<AsistenciaVo> listaAsistencias = new ArrayList<>();
		
		conn = conexion.getConnection();
		
		String consulta = "SELECT a.codigo, e.nombre as nombre_estu, g.nombre as grupo, p.nombre as nom_prof, a.fecha_falta, a.observacion_falta FROM asistencia a, estudiante e, profesor p, grupo g WHERE a.doc_est_ = e.documento AND a.cod_grupo = g.codigo AND a.doc_prof = p.documento AND (a.fecha_falta = ? OR a.cod_grupo = ?) GROUP BY a.codigo";
		try {
			
			statement = conn.prepareStatement(consulta);
			statement.setString(1, fecha);
			result = statement.executeQuery();
			
			
			while(result.next()) {
				asistenciaVo = new AsistenciaVo();
				asistenciaVo.setCodigo(result.getString("codigo"));
				asistenciaVo.setDocumentoEstudiante(result.getString("nombre_estu"));
				asistenciaVo.setCodigoGrupo(result.getString("grupo"));
				asistenciaVo.setDocumentoProfesor(result.getString("nom_prof"));
				asistenciaVo.setFechaFalta(result.getDate("fecha_falta"));
				asistenciaVo.setFecha(asistenciaVo.getFechaFalta()+"");
				asistenciaVo.setObservacionFalta(result.getString("observacion_falta"));
				listaAsistencias.add(asistenciaVo);
			}
			
			conexion.desconectar();
			
		} catch (SQLException e) {
			System.out.println("Error al obtener la lista de asistencias con el filtro por fecha: "+e.getMessage());
			listaAsistencias = null;
		}
		
		
		return listaAsistencias;
	}
}
