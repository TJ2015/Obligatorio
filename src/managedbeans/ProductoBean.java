package managedbeans;

import java.io.IOException;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.primefaces.model.UploadedFile;

import negocio.interfases.IControladorInventario;
import util.Url;


@ManagedBean
public class ProductoBean implements Serializable {
	@EJB
	IControladorInventario cinv;
	
	private String nombre;
	private String descripcion;
	private double precio;
	private String categoria;
	private String atributos;
	private int stock;
	private static final long serialVersionUID = 1L;
	
	//para descripcion producto
	private long idAV;
	
	/*************************************************************************/
	/*************************** BRYAN ***************************************/
	
	private UploadedFile file;
	
	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}
	
	/*************************** BRYAN ***************************************/
	/*************************************************************************/
	
	
	public ProductoBean(long idAV) {
			super();
			this.idAV = idAV;
		}


	public ProductoBean(){}
	
	
	
	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public String getDescripcion() {
		return descripcion;
	}


	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}


	public double getPrecio() {
		return precio;
	}


	public void setPrecio(double precio) {
		this.precio = precio;
	}


	public String getCategoria() {
		return categoria;
	}


	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}


	public String getAtributos() {
		return atributos;
	}


	public void setAtributos(String atributosList) {
		this.atributos = atributosList;
	}


	public long getIdAV() {
		return idAV;
	}


	public void setIdAV(long idAV) {
		this.idAV = idAV;
	}
	
	public int getStock() {
		return stock;
	}


	public void setStock(int stock) {
		this.stock = stock;
	}


	public void crearProductoDescripción() {
		try {
			
			testStressProducto();
			
			/******************************************************/
			/****************** BRYAN ************************************/
			/******************************************************/
			idAV = 1; //HARDCODE
			
			cinv.crearProducto(nombre, descripcion, precio, categoria, atributos, idAV, stock, file);
			//cinv.setStockProducto(nombre, idAV, stock);
			Url.redireccionarURL("index");
		} catch (Exception e) {
			e.printStackTrace();
			Url.redireccionarURL("error");
		}
		
	}
	
	
	public void testStressProducto() {
		try {
			//HttpSession session = SesionBean.getSession();
			//idAV = (long) session.getAttribute("idAV");
			boolean bien = true;
			boolean termino = false;
			int cont = 0;
			idAV = 1; //HARDCODE
			while (bien && !termino) {
				System.out.println("Vamos a crear El producto: " + cont++);
				cinv.crearProducto(nombre + cont, descripcion, precio, categoria, atributos, idAV, stock, file);
				if (cont == 99) {
					termino = true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}



}
