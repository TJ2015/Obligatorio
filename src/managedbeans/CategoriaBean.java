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
import dominio.datatypes.DataProducto;
import exceptions.NoExisteElAV;
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
	private String[] str = { "hola", "noe" };
	private long idAVDestino;
	private String nombreAV;
	private static final long serialVersionUID = 1L;

	
	
	public long getIdAVDestino() {
		return idAVDestino;
	}

	public void setIdAVDestino(long idAVDestino) {
		this.idAVDestino = idAVDestino;
	}

	public String[] getStr() {
		return str;
	}

	public void setStr(String[] str) {
		this.str = str;
	}

	public IControladorAV getcAV() {
		return cAV;
	}

	public void setcAV(IControladorAV cAV) {
		this.cAV = cAV;
	}

	public AV getAv() {
		return av;
	}

	public void setAv(AV av) {
		this.av = av;
	}

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
			if (cinv.crearCategoria((String)session.getAttribute("nickname"), nombre, idAV)) {
				Url.redireccionarURL("mostrarAV");
			} else {
				Url.redireccionarURL("error");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void moverProductos(String categoria){
		HttpSession session = SesionBean.getSession();
		long idAV = (long) session.getAttribute("idAV");
		String nick = (String) session.getAttribute("nickname");
		DataCategoria cat;
		try {
			cat = cinv.getCategoria(categoria, idAV);
			List<DataProducto> productos=cat.getProductos();
			List<String> nombProds=new ArrayList<>();
			for (DataProducto p:productos){
				nombProds.add(p.getNombre());
			}
			cinv.moverProductos(nick, nombProds, idAV, idAVDestino);
		} catch (NoExisteElAV e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
