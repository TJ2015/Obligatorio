package managedbeans;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
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

	public void crearCategoria() throws Exception {
		try {
			HttpSession session = SesionBean.getSession();
			idAV = (long) session.getAttribute("idAV");
			if (cinv.crearCategoria(nombre, idAV)) {
				Url.redireccionarURL("mostrarAV");
			} else {
				Url.redireccionarURL("error");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<DataCategoria> getCats() {
		return cats;
	}

	public void setCats(List<DataCategoria> cats) {
		this.cats = cats;
	}

	public List<DataCategoria> mostrarListaCategoria() {
		try {
			HttpSession session = SesionBean.getSession();
			idAV = (long) session.getAttribute("idAV");
			cats = cinv.mostrarListaCategoria(idAV);
		} catch (Exception e) {
			// TODO 505
			e.printStackTrace();
		}
		return cats;
	}

}
