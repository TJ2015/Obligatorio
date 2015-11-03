package managedbeans;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.servlet.ServletRequestContext;
import org.primefaces.model.UploadedFile;

import dominio.datatypes.DataAV;
import dominio.datatypes.DataUsuario;
import negocio.interfases.IControladorUsuario;

@ManagedBean
@SessionScoped
public class UsuarioBean implements Serializable 
{
	private static final long serialVersionUID = 1L;

	@EJB
	IControladorUsuario controlUsuario;

	private String nombre;
	private String apellido;
	private String nick;
	private String password;
	private String email;
	private Date fechaNacimiento;
	private List<DataAV> AVs = new ArrayList<>();
	private DataUsuario dusu;

	private boolean logueado = false;
	
	private byte[] imagenes;
	
	private Part imagen;
	
	public Part getImagen() {
		return imagen;
	}

	public void setImagen(Part imagen) {
		this.imagen = imagen;
	}
	
	
	
	private String contenedorImagen;
	
	
	
	/* 
	public void cargarImagen() {
	    try {
	    	
	    	File directorio = new File("C://sqlite//jar//001"); 
			directorio.mkdir();
			
			System.out.println("entra al servlet de ServletUploadFile");
			String url="C://imagenes//"; // UBICACION DONDE SE GUARDAN LOS ARCHIVOS
			//BOLEANO QUE INDICA SI LO QUE SE ENVIA ES EN FORMA DE MUTIPART O SEA EN VARIAS PARTES
			boolean isMultipart = true; //ServletFileUpload.isMultipartContent(request);
			
			//ServletRequestContext src = new ServletRequestContext(request);

			if (isMultipart) {
				System.out.println("ES MULTIPART");
				DiskFileItemFactory factory = new DiskFileItemFactory(); //SE CREA LA FABRICA
			    factory.setSizeThreshold(1024); //IDENTIFICA LA TASA DE TRANSFERENCIA - FLUJO DE TRNSFERENCIA
			    factory.setRepository(new File(url)); // EL REPOSITORIO INDICA DONDE SE VA A ALMACENAR EL ARCHIVO EN NUESTRO CASO ES LA VARIABLE URL
				
			    //SE CREA EL SERVLET FILE UPLOAD CON LA FABRICA ANTES CREADA
			    ServletFileUpload upload = new ServletFileUpload(factory);
			    
			    try {
			        //SE CREA LA LISTA DE ITEMS 
					List<FileItem> items = upload.parseRequest(src);
					//SE RECORRE LA LISTA
			        for(FileItem item : items){
			        	System.out.println(url+item.getName());
			        	File file = new File(url, item.getName()); //SE CREA UN FILE CON LA URL + EL NOMBRE DEL ARCHIVO
			        	item.write(file); //SE ESCRIBE EL ARCHIVO FILE
			        }
			        
			    }catch (Exception e) {
					e.printStackTrace();
				}
			}
			else{
				System.out.println("NO ES MULTIPLIPART");
			}
	    	
	    	System.out.println("cargar imagen");
	    	
	    	InputStream img = imagen.getInputStream();
			File f = new File("sapo.jpg");
			ImageIO.write(img, "JPEG", f);
	    	File al = new File();
	    	System.out.println("cargar imagen");
	    	
	    	//Image bufferImagen = new Image(imagen.getInputStream());
	    	InputStream input = imagen.getInputstream();
	    	Path folder = Paths.get("C:/imagenes/");
	    	String filename = FilenameUtils.getBaseName(imagen.getName()); 
	    	String extension = FilenameUtils.getExtension(imagen.getName());
	    	Path file = Files.createTempFile(folder, imagen.getFileName() + "-", ".jpg");
	    	
	    	
    		//contenedorImagen = new Scanner(imagen.getInputStream()).useDelimiter("\\A").next();
    		System.out.println(contenedorImagen);
	    } catch (IOException e) {
	    	e.printStackTrace();
		}
	}
	
	
	public void guardarImagen()
	{

		File directorio = new File("C://sqlite//jar//001"); 
		directorio.mkdir();
		
		System.out.println("entra al servlet de ServletUploadFile");
		String url="C://sqlite//jar//001"; // UBICACION DONDE SE GUARDAN LOS ARCHIVOS
		//BOLEANO QUE INDICA SI LO QUE SE ENVIA ES EN FORMA DE MUTIPART O SEA EN VARIAS PARTES
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		
		ServletRequestContext src = new ServletRequestContext(request);

		if (isMultipart) {
			System.out.println("ES MULTIPART");
			DiskFileItemFactory factory = new DiskFileItemFactory(); //SE CREA LA FABRICA
		    factory.setSizeThreshold(1024); //IDENTIFICA LA TASA DE TRANSFERENCIA - FLUJO DE TRNSFERENCIA
		    factory.setRepository(new File(url)); // EL REPOSITORIO INDICA DONDE SE VA A ALMACENAR EL ARCHIVO EN NUESTRO CASO ES LA VARIABLE URL
			
		    //SE CREA EL SERVLET FILE UPLOAD CON LA FABRICA ANTES CREADA
		    ServletFileUpload upload = new ServletFileUpload(factory);
		    
		    try {
		        //SE CREA LA LISTA DE ITEMS 
				List<FileItem> items = upload.parseRequest(src);
				//SE RECORRE LA LISTA
		        for(FileItem item : items){
		        	System.out.println(url+item.getName());
		        	File file = new File(url, item.getName()); //SE CREA UN FILE CON LA URL + EL NOMBRE DEL ARCHIVO
		        	item.write(file); //SE ESCRIBE EL ARCHIVO FILE
		        }
		        
		    }catch (Exception e) {
				e.printStackTrace();
			}
		}
		else{
			System.out.println("NO ES MULTIPLIPART");
		}
		
	}
	
	*/
	
	
	public UsuarioBean() {

	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public List<DataAV> getAVs() {
		return AVs;
	}

	public void setAVs(List<DataAV> aVs) {
		AVs = aVs;
	}

	public DataUsuario getDusu() {
		return dusu;
	}

	public void setDusu(DataUsuario dusu) {
		this.dusu = dusu;
	}

	public boolean isLogueado() {
		return logueado;
	}

	public void setLogueado(boolean logueado) {
		this.logueado = logueado;
	}

	public void login() throws IOException 
	{
		try {
			DataUsuario dataUsuario = controlUsuario.login(nick, password);
			if (dataUsuario != null) {
				logueado = true;
				HttpSession session = SesionBean.getSession();
				session.setAttribute("nickname", nick);
				session.setAttribute("dataUsuario", dataUsuario);
				FacesContext.getCurrentInstance().getExternalContext().dispatch("/av_crear.xhtml");
			} else {
				FacesContext.getCurrentInstance().getExternalContext().dispatch("/error.xhtml");
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void logout() 
	{
		HttpSession session = SesionBean.getSession();
		session.invalidate();
	}

	public void registroUsuario() 
	{
		try {
			imagen.write("C://imagenes//" + imagen.getSubmittedFileName());
			System.out.println("Guardo la Imagen");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			DataUsuario dataUsuario = controlUsuario.registrarUsuario(nombre, apellido, nick, password, email, fechaNacimiento);
			if (dataUsuario != null) {
				logueado = true;
				FacesContext.getCurrentInstance().getExternalContext().dispatch("/index.xhtml");
			} 
			else {
				FacesContext.getCurrentInstance().getExternalContext().dispatch("/error.xhtml");
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void mostrarListaAV() 
	{
		try {
			AVs = controlUsuario.mostrarListaAv(nick);
			FacesContext.getCurrentInstance().getExternalContext().dispatch("/verListaAV.xhtml");

		} catch (IOException e) {
			try {
				FacesContext.getCurrentInstance().getExternalContext().dispatch("/error.xhtml");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}
	
	public boolean existeUsuarioLogeado()
	{
		boolean existeUsuario = false;
		try {
			HttpSession session = SesionBean.getSession();
			DataUsuario dataUsuario = (DataUsuario)session.getAttribute("dataUsuario");
			existeUsuario = dataUsuario != null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return existeUsuario;
	}

	public byte[] getImagenes() {
		return imagenes;
	}

	public void setImagenes(byte[] imagenes) {
		this.imagenes = imagenes;
	}

}
