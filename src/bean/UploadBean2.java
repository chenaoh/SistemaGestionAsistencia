package bean;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.servlet.http.Part;

import dao.RegistrarImagenDAO;
import vo.PersonaImagenVo;

@ManagedBean
@ViewScoped
public class UploadBean2 {

	 private int filesUploaded = 0;
	 InputStream inputStream = null; 

	    //javax.servlet.http.Part (Servlet 3.0 API)
	    private Part file;
	    private String fileContent;

	    /**
	     * Just prints out file content
	     */
	    public void upload() {
	        try {
	            fileContent = new Scanner(file.getInputStream())
	                    .useDelimiter("\\A").next();
	            System.out.println(fileContent + " uploaded");
	            filesUploaded++;
	            
	            if (file != null) {
	                // prints out some information for debugging
	                System.out.println(file.getName());
	                System.out.println(file.getSize());
	                System.out.println(file.getContentType());
	                 
	                // obtains input stream of the upload file
	                inputStream = file.getInputStream();
	                
	                PersonaImagenVo miP=new PersonaImagenVo();
	                miP.setDocumento("123");
	                miP.setNombre("Imagen");
	                miP.setImagen(inputStream);
	                
	                RegistrarImagenDAO registra=new RegistrarImagenDAO();
	                String resp=registra.agregarPersona(miP);
	                System.out.println("*******************************");
	                System.out.println("REGISTRA LA IMAGEN");
	                System.out.println("*******************************");
	                System.out.println("*******************************");
	                
	            }
	            
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }

	    public int getFilesUploaded() {
	        return filesUploaded;
	    }

	    public Part getFile() {
	        return file;
	    }

	    public void setFile(Part file) {
	        this.file = file;
	    }
	
}
