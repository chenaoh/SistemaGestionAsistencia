package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import conexion.Conexion;
import vo.EstudianteVo;
import vo.EstudiantesPtoyectosVo;
import vo.GrupoVo;
import vo.estudiantesGrupoVo;

public class EstudianteDao {
	
	public static HashMap<String, EstudianteVo> mapaEstudiantes;
	
	public EstudianteDao(){
		mapaEstudiantes = new HashMap<>();
	}

	public EstudianteVo consultarEstudianteLogin(String documento, String password) {
		Connection connection = null;
		Conexion miConexion = new Conexion();
		PreparedStatement statement = null;
		ResultSet result = null;

		EstudianteVo miEstudiante = null;

		connection = miConexion.getConnection();
		
		System.out.println("Documento: "+documento+" , pass: "+password);
		
		try {
			if (connection != null) {
				
				String consulta = "SELECT * FROM estudiante where documento = ? and password = ? ";

				statement = connection.prepareStatement(consulta);

				statement.setString(1, documento);
				statement.setString(2, password);
								
				result = statement.executeQuery();
				System.out.println("continua...");
				if (result.next() == true) {
					miEstudiante = new EstudianteVo();
					miEstudiante.setDocumento(result.getString("documento"));
					miEstudiante.setNombre(result.getString("nombre"));
					miEstudiante.setDireccion(result.getString("direccion"));
					miEstudiante.setTelefono(result.getString("telefono"));
					miEstudiante.setEmail(result.getString("email"));
					miEstudiante.setFechaNacimiento(result.getDate("fecha_nacimiento"));
					miEstudiante.setSexo(result.getString("sexo"));
					miEstudiante.setEstado(result.getString("estado"));
					miEstudiante.setPassword(result.getString("password"));
					miEstudiante.setTipo(result.getString("tipo"));
				}
			}
		} catch (SQLException e) {
			System.out.println("Error en la consulta del usuario Estudiante: " + e.getMessage());
		} finally {
			miConexion.desconectar();
		}
		return miEstudiante;
	}

	public String registrarEstudiante(EstudianteVo estudianteVo) {
		String resultado = "";

		Connection connection = null;
		Conexion conexion = new Conexion();
		PreparedStatement preStatement = null;

		connection = conexion.getConnection();
		String consulta = "INSERT INTO estudiante (documento,nombre,direccion,telefono,email,grupo,fecha_nacimiento,sexo,estado,password,tipo)"
				+ "  VALUES (?,?,?,?,?,?,?,?,?,?,?)";

		try {
			preStatement = connection.prepareStatement(consulta);
			preStatement.setString(1, estudianteVo.getDocumento());
			preStatement.setString(2, estudianteVo.getNombre());
			preStatement.setString(3, estudianteVo.getDireccion());
			preStatement.setString(4, estudianteVo.getTelefono());
			preStatement.setString(5, estudianteVo.getEmail());
			preStatement.setString(6, estudianteVo.getGrupo());
			preStatement.setDate(7, estudianteVo.getFechaNacimiento());
			preStatement.setString(8, estudianteVo.getSexo());
			preStatement.setString(9, estudianteVo.getEstado());
			preStatement.setString(10, estudianteVo.getPassword());
			preStatement.setString(11, estudianteVo.getTipo());
			preStatement.execute();

			resultado = "Registro Exitoso!!!";

		} catch (SQLException e) {
			System.out.println("No se pudo registrar el Estudiante: " + e.getMessage());
			resultado = "No se pudo registrar, verifique nuevamente";
		} finally {
			conexion.desconectar();
		}

		return resultado;
	}

		public ArrayList<EstudianteVo> obtenerListaEstudiantes() {
		Connection connection = null;
		Conexion miConexion = new Conexion();
		PreparedStatement statement = null;
		ResultSet result = null;

		EstudianteVo estudiante = new EstudianteVo();
		ArrayList<EstudianteVo> listaEstudiantes = null;

		connection = miConexion.getConnection();
		
		String consulta = "SELECT * FROM estudiante ";
		System.out.println("***************************************");
		System.out.println(consulta);
		try {
			if (connection != null) {
				listaEstudiantes = new ArrayList<>();
				statement = connection.prepareStatement(consulta);

				result = statement.executeQuery();
				while (result.next() == true) {
					estudiante = new EstudianteVo();
					estudiante.setDocumento(result.getString("documento"));
					estudiante.setNombre(result.getString("nombre"));
					estudiante.setDireccion(result.getString("direccion"));
					estudiante.setTelefono(result.getString("telefono"));
					estudiante.setEmail(result.getString("email"));
					estudiante.setGrupo(result.getString("grupo"));
					estudiante.setFechaNacimiento(result.getDate("fecha_nacimiento"));
					estudiante.setSexo(result.getString("sexo"));
					estudiante.setEstado(result.getString("estado"));
					estudiante.setPassword(result.getString("password"));
					estudiante.setTipo(result.getString("tipo"));
					estudiante.setFecha(estudiante.getFechaNacimiento()+"");//se agrega fecha a la variable temporal
					listaEstudiantes.add(estudiante);
				}
				
			}
		} catch (SQLException e) {
			System.out.println("Error en la consulta del Estudiante: " + e.getMessage());
		} finally {
			miConexion.desconectar();
		}
		return listaEstudiantes;
	}

	public String actualizarEstudiante(EstudianteVo estudiante) {
		String resultado = "";
		Connection connection = null;
		Conexion miConexion = new Conexion();
		connection = miConexion.getConnection();
		System.out.println("VA A ACTUALIZAR EL ESTUDIANTE");
		try {
			String consulta = "UPDATE estudiante "
					+ " SET nombre = ? , direccion=? , telefono=? , email=? , fecha_nacimiento= ? , sexo= ?, estado=? "
					+ " WHERE documento= ? ";
			PreparedStatement preStatement = connection.prepareStatement(consulta);

			preStatement.setString(1, estudiante.getNombre());
			preStatement.setString(2, estudiante.getDireccion());
			preStatement.setString(3, estudiante.getTelefono());
			preStatement.setString(4, estudiante.getEmail());
			preStatement.setDate(5, estudiante.getFechaNacimiento());
			preStatement.setString(6, estudiante.getSexo());
			preStatement.setString(7, estudiante.getEstado());
			preStatement.setString(8, estudiante.getDocumento());
			preStatement.executeUpdate();

			resultado = "Se ha Actualizado el Estudiante satisfactoriamente";

			miConexion.desconectar();

		} catch (SQLException e) {
			System.out.println(e);
			resultado = "No se pudo actualizar el estudiante";
		}
		return resultado;
	}

	public String eliminarEstudiante(EstudianteVo estudiante) {
		Connection connection = null;
		Conexion miConexion = new Conexion();
		connection = miConexion.getConnection();

		String resp = "";
		try {
			String sentencia = "DELETE FROM estudiante WHERE documento= ? ";

			PreparedStatement statement = connection.prepareStatement(sentencia);
			statement.setString(1, estudiante.getDocumento());

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

	public ArrayList<String> obtenerNombres() {
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		Conexion conexion = new Conexion();
		EstudianteVo miEstudiante;
		ArrayList<String> listaNombres = new ArrayList<>();
		
		conn = conexion.getConnection();
		
		String consulta = "SELECT * FROM estudiante";
		
		try {
			
			statement = conn.prepareStatement(consulta);
			result = statement.executeQuery();
			
			while(result.next()==true){
				miEstudiante = new EstudianteVo();
				miEstudiante.setNombre(result.getString("nombre"));
				listaNombres.add(miEstudiante.getNombre());
				
			}
			
			conexion.desconectar();
			
		} catch (SQLException e) {
			System.out.println("Error en la lista de los nombres de los estudiantes: "+e.getMessage());
			listaNombres = null;
		}
		
		return listaNombres;
	}

	public ArrayList<EstudianteVo> consultarEstudianteNombre(String nombreEstu) {
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		Conexion conexion = new Conexion();
		EstudianteVo miEstudiante;
		ArrayList<EstudianteVo> estudiante = new ArrayList<>();
		
		conn = conexion.getConnection();
		System.out.println("Nombre estudiante: "+nombreEstu);
		
		String consulta = "SELECT * FROM estudiante WHERE nombre LIKE '%"+nombreEstu+"%'";
		
		try {
			
			statement = conn.prepareStatement(consulta);
			result = statement.executeQuery();
			
			while(result.next()==true){
				miEstudiante = new EstudianteVo();
				miEstudiante.setDocumento(result.getString("documento"));
				miEstudiante.setNombre(result.getString("nombre"));
				miEstudiante.setDireccion(result.getString("direccion"));
				miEstudiante.setTelefono(result.getString("telefono"));
				miEstudiante.setEmail(result.getString("email"));
				miEstudiante.setGrupo(result.getString("grupo"));
				miEstudiante.setFechaNacimiento(result.getDate("fecha_nacimiento"));
				miEstudiante.setFecha(miEstudiante.getFechaNacimiento()+"");
				miEstudiante.setSexo(result.getString("sexo"));
				miEstudiante.setEstado(result.getString("estado"));
				estudiante.add(miEstudiante);
				
			}
			
			conexion.desconectar();
			
		} catch (SQLException e) {
			System.out.println("Error en la lista de los nombres de los estudiantes: "+e.getMessage());
			estudiante = null;
		}
		
		return estudiante;
	}

	public void cargarDatosHashMapEstudiantes(ArrayList<EstudianteVo> listaEstudiantes) {
		for (int i = 0; i < listaEstudiantes.size(); i++) {
			mapaEstudiantes.put(listaEstudiantes.get(i).getNombre(), listaEstudiantes.get(i));
		}
		
		System.out.println("****MAPA ESTUDIANTES****: "+mapaEstudiantes);
	}

	public ArrayList<String> obtenerIdEstudiante(ArrayList<String> nombresEstudiantes) {		
		System.out.println("Nombres estudiantes: "+nombresEstudiantes);
		System.out.println("*****Mapa estudiantes****: "+mapaEstudiantes);
		ArrayList<String> listDocumentosEstu = new ArrayList<>();
		for (int i = 0; i < nombresEstudiantes.size(); i++) {
			EstudianteVo miEstudiante = mapaEstudiantes.get(nombresEstudiantes.get(i));
			listDocumentosEstu.add(miEstudiante.getDocumento());
			
		}
		
		return listDocumentosEstu;
	}

	public ArrayList<EstudiantesPtoyectosVo> consultarEstudiantesAsociados() {
		
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		EstudiantesPtoyectosVo estuProyectos;
		ArrayList<EstudiantesPtoyectosVo> listaEstudiantesProyectos = new ArrayList<>();
		Conexion conexion = new Conexion();
		
		conn = conexion.getConnection();
		
		String consulta = "SELECT e.nombre as nombre, p.nombre as nombreP FROM proyecto_estudiantes pe, estudiante e, proyecto p WHERE pe.cod_proyecto = p.codigo AND pe.doc_estudiante = e.documento ";
		
		try {
			
			statement = conn.prepareStatement(consulta);
			result = statement.executeQuery();
			
			while(result.next()){
				estuProyectos = new EstudiantesPtoyectosVo();
				estuProyectos.setCod_proyecto(result.getString("nombreP"));
				estuProyectos.setDocEstudiante(result.getString("nombre"));
				listaEstudiantesProyectos.add(estuProyectos);
			}
			
			conexion.desconectar();
			
			
		} catch (SQLException e) {
			System.out.println("Error al traer la lista de estudiantes asociados: "+e.getMessage());
			listaEstudiantesProyectos = null;
		}
		
		return listaEstudiantesProyectos;
	}

	public String obtenerIdUnEstudiante(String nombre) {
		EstudianteVo miEstudiante = mapaEstudiantes.get(nombre);
		String doc = miEstudiante.getDocumento();
		return doc;
	}

	public ArrayList<EstudianteVo> consultarEstudianteGrupos(String codigo) {
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		EstudianteVo estudiante;
		ArrayList<EstudianteVo> listaEstudiantes = new ArrayList<>();
		Conexion conexion = new Conexion();
		
		conn = conexion.getConnection();
		
		String consulta = "SELECT e.*, g.nombre as nombreg FROM  estudiante e, grupo g WHERE g.codigo = ? AND e.grupo = g.codigo ";
		
		try {
			
			statement = conn.prepareStatement(consulta);
			statement.setString(1, codigo);
			result = statement.executeQuery();
			
			while(result.next()){
				estudiante = new EstudianteVo();
				estudiante.setDocumento(result.getString("documento"));
				estudiante.setNombre(result.getString("nombre"));
				estudiante.setDireccion(result.getString("direccion"));
				estudiante.setTelefono(result.getString("telefono"));
				estudiante.setEmail(result.getString("email"));
				estudiante.setGrupo(result.getString("grupo"));
				estudiante.setFechaNacimiento(result.getDate("fecha_nacimiento"));
				estudiante.setFecha(estudiante.getFechaNacimiento()+"");
				estudiante.setSexo(result.getString("sexo"));
				estudiante.setEstado(result.getString("estado"));
				listaEstudiantes.add(estudiante);
				
			}
			
			conexion.desconectar();
			
			
		} catch (SQLException e) {
			System.out.println("Error al traer la lista de estudiantes asociados a grupos: "+e.getMessage());
			listaEstudiantes = null;
		}
		
		return listaEstudiantes;
	}

	public ArrayList<String> obtenerNombresAsistencia(String grupo) {
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		Conexion conexion = new Conexion();
		EstudianteVo miEstudiante;
		ArrayList<String> listaNombres = new ArrayList<>();
		
		conn = conexion.getConnection();
		
		String consulta = "SELECT * FROM estudiante WHERE grupo = ?";
		
		try {
			
			statement = conn.prepareStatement(consulta);
			statement.setString(1, grupo);
			result = statement.executeQuery();
			
			while(result.next()==true){
				miEstudiante = new EstudianteVo();
				miEstudiante.setNombre(result.getString("nombre"));
				listaNombres.add(miEstudiante.getNombre());
				
			}
			
			conexion.desconectar();
			
		} catch (SQLException e) {
			System.out.println("Error en la lista de los nombres de los estudiantes: "+e.getMessage());
			listaNombres = null;
		}
		
		return listaNombres;
	}
	
	
	public EstudianteVo obtenerEstudiante(String documento) {
		Connection connection = null;
		Conexion miConexion = new Conexion();
		PreparedStatement statement = null;
		ResultSet result = null;

		EstudianteVo estudiante=null;

		connection = miConexion.getConnection();
		
		String consulta = "SELECT * FROM estudiante where documento = '"+documento+"'";
		System.out.println("***************************************");
		System.out.println(consulta);
		try {
			if (connection != null) {
				
				statement = connection.prepareStatement(consulta);

				result = statement.executeQuery();
				if (result.next() == true) {
					estudiante = new EstudianteVo();
					estudiante.setDocumento(result.getString("documento"));
					estudiante.setNombre(result.getString("nombre"));
					estudiante.setDireccion(result.getString("direccion"));
					estudiante.setTelefono(result.getString("telefono"));
					estudiante.setEmail(result.getString("email"));
					estudiante.setGrupo(result.getString("grupo"));
					estudiante.setFechaNacimiento(result.getDate("fecha_nacimiento"));
					estudiante.setSexo(result.getString("sexo"));
					estudiante.setEstado(result.getString("estado"));
					estudiante.setPassword(result.getString("password"));
					estudiante.setTipo(result.getString("tipo"));
					estudiante.setFecha(estudiante.getFechaNacimiento()+"");
				}
			}
		} catch (SQLException e) {
			System.out.println("Error en la consulta del Profesor: " + e.getMessage());
		} finally {
			miConexion.desconectar();
		}
		return estudiante;
		

	}

	public String cantidadEstudiantes() {
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		Conexion conexion = new Conexion();
		String cantidad = "";
		
		conn = conexion.getConnection();
		
		String consulta = "SELECT COUNT(*) as cantidad FROM estudiante";
		
		try {
			
			statement = conn.prepareStatement(consulta);
			result = statement.executeQuery();
			
			while(result.next()){
				cantidad = result.getString("cantidad");
			}
			
			System.out.println("CANT_ESTUDIANTES: "+cantidad);
			
			conexion.desconectar();
			
		} catch (SQLException e) {
			System.out.println("Error al obtener la cantidad de estudiantes: "+e.getMessage());
		}
		
		return cantidad;
	}

	public ArrayList<String> obtenerCorreosEstudiantes(ArrayList<String> nombresEstudiantes) {
		EstudianteVo estudianteVo;
		ArrayList<String> correos = new ArrayList<>();
		for(int i = 0; i<nombresEstudiantes.size(); i++){
			estudianteVo = mapaEstudiantes.get(nombresEstudiantes.get(i));
			correos.add(estudianteVo.getEmail());
			
		}
		return correos;
	}
	
public ArrayList<estudiantesGrupoVo>  cargarListaAsociados() {
		
		
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		estudiantesGrupoVo estuGrupos;
		ArrayList<estudiantesGrupoVo> listaGruposAsociados= new ArrayList<>();
		Conexion conexion = new Conexion();
		
		conn = conexion.getConnection();
		
		String consulta = "SELECT e.nombre as nombre, p.nombre as nombreG FROM grupo_estudiantes pe, estudiante e, grupo p WHERE pe.cod_grupo = p.codigo AND pe.doc_estudiante = e.documento ";
		
		try {
			
			statement = conn.prepareStatement(consulta);
			result = statement.executeQuery();
			
			while(result.next()){
				estuGrupos = new estudiantesGrupoVo();
				estuGrupos.setCod_grupo(result.getString("nombreG"));
				estuGrupos.setDocEstudiante(result.getString("nombre"));
				listaGruposAsociados.add(estuGrupos);
			}
			
			conexion.desconectar();
			
			
		} catch (SQLException e) {
			System.out.println("Error al traer la lista de estudiantes asociados: "+e.getMessage());
			listaGruposAsociados = null;
		}
		
		System.out.println("********************************************************************************************************************");
		System.out.println("////////////////////////////////////////////////////////////////////////////////////////////////////////////////////");
		System.out.println(listaGruposAsociados);
		return listaGruposAsociados;
}

}
