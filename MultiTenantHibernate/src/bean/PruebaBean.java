package bean;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;

import DAO.DAO;

@ManagedBean
@SessionScoped
public class PruebaBean implements Serializable {
	
	@EJB
	DAO dao;
	
	public PruebaBean(){}
	
	public void persistir() {
		dao.persistir();
	}
	
}
