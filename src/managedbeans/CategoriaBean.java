package managedbeans;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.servlet.http.HttpSession;

import dominio.AV;
import dominio.datatypes.DataCategoria;
import negocio.interfases.IControladorAV;
import negocio.interfases.IControladorInventario;
import util.Url;

@ManagedBean
@ViewScoped
// @SessionScoped
public class CategoriaBean implements Serializable {

	@EJB
	IControladorInventario cinv;

	@EJB
	IControladorAV cAV;

	private String nombre;
	private long idAV;
	private AV av;
	private List<DataCategoria> cats = new ArrayList<>();
	private String[] str = { "hola", "noe" };

	public String[] getStr() {
		return str;
	}

	public void setStr(String[] str) {
		this.str = str;
	}

	private static final long serialVersionUID = 1L;

	private String nombreAV;

	public String getNombreAV() {
		return nombreAV;
	}

	public void setNombreAV(String nombreAV) {
		this.nombreAV = nombreAV;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public IControladorInventario getCinv() {
		return cinv;
	}

	public void setCinv(IControladorInventario cinv) {
		this.cinv = cinv;
	}

	public long getIdAV() {
		return idAV;
	}

	public void setIdAV(long idAV) {
		this.idAV = idAV;
	}

	public CategoriaBean() {
		super();
	}

	public List<DataCategoria> getCats() {
		return cats;
	}

	public void setCats(List<DataCategoria> cats) {
		this.cats = cats;
	}

	public List<DataCategoria> mostrarListaCategoria() throws Exception {
		try {

			HttpSession session = SesionBean.getSession();
			idAV = (long) session.getAttribute("idAV");
			cats = cinv.mostrarListaCategoria(idAV);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cats;
	}

	public void crearCategoria(){
		try {
			testStressCategorias();
			HttpSession session = SesionBean.getSession();
			idAV = (long) session.getAttribute("idAV");
			
			if( cinv.crearCategoria(nombre, idAV)) { 	
				Url.redireccionarURL("index");
			} else {
				Url.redireccionarURL("error");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void testStressCategorias(){
		try {
			HttpSession session = SesionBean.getSession();
			idAV = (long) session.getAttribute("idAV");
			boolean bien = true;
			boolean termino = false;
			int cont = 0;
			while (bien && !termino) {
				System.out.println("Vamos a crear la Categoria: " + cont++);
				if( cinv.crearCategoria("Categoria" + cont, idAV)) { 	
					if (cont == 99) {
						termino = true;
					}
				} else {
					bien = false;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}

