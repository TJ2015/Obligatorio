package negocio.implementacion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import dominio.Producto;
import dominio.datatypes.DataProducto;
import dominio.datatypes.DataProductoVendido;
import dominio.datatypes.DataUsuario;
import negocio.interfases.IControladorAV;
import negocio.interfases.IControladorAlgoritmos;
import negocio.interfases.IControladorInventario;
import negocio.interfases.IControladorUsuario;
import persistencia.interfases.IInventarioDAO;

@Stateless
public class ControladorAlgoritmos implements IControladorAlgoritmos{
	
	
	@EJB
	private IControladorUsuario cUsuario;
	
	@EJB
	private IControladorInventario cInventario;
	
	@EJB
	private IControladorAV cAV;
	
	@EJB
	private IInventarioDAO invDAO;

	public ControladorAlgoritmos() {
	}

	@Override
	public List<DataProducto> recomendar(String usuario) {
		List<Producto> prods = invDAO.getAllProducto("master");
		List<DataProducto> dprods = new ArrayList<>();
		int i = 0;
		for (Producto prod : prods) {

			if (i > 5)
				break;

			dprods.add(prod.getDataProducto());
			i++;
		}

		return dprods;
	}
	
	@Override
	public List<DataProductoVendido> obtenerProductosMasVendidos(int cantidad, boolean distinguir){
		List<DataProductoVendido> lProductosVendidos = null;
		try {
			Map<String, Integer> lProductos = obtenerRankingProductos(distinguir);
			if (lProductos != null && lProductos.size() > 0) {
				lProductos = ordenarPorValor(lProductos);
				lProductosVendidos = new ArrayList<DataProductoVendido>();
				int contador = cantidad == 0 ? lProductos.size() + 1 : cantidad ;
				for(Map.Entry<String, Integer> p : lProductos.entrySet()){
					if (contador > 0) {
						lProductosVendidos.add(new DataProductoVendido(p.getKey(), p.getValue()));
						contador--;
					}
					else{
						break;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lProductosVendidos;
	}
	
	@SuppressWarnings("rawtypes")
	private Map<String, Integer> obtenerRankingProductos(boolean distinguir){
		Map<String, Integer> topProductos = new HashMap<String, Integer>();
		try {
			List<DataUsuario> lUsuarios = cUsuario.getUsuarios();
			if (lUsuarios != null && lUsuarios.size() > 0) {
				List<String> lProductosGenericos = invDAO.buscarNombresProductosGenericos();
				for (Iterator iterator = lUsuarios.iterator(); iterator.hasNext();) {
					DataUsuario dataUsuario = (DataUsuario) iterator.next();
					List<String> lAlmacenes = dataUsuario.getAVs();
					for (Iterator iterator2 = lAlmacenes.iterator(); iterator2.hasNext();) {
						String nombreAV = (String) iterator2.next();
						List<String> lProductos = cInventario.obtenerNombresProductos(dataUsuario.getNick(), nombreAV);
						if (lProductos != null && lProductos.size() > 0) {
							for (Iterator iterator3 = lProductos.iterator(); iterator3.hasNext();) {
								String nombreProducto = (String) iterator3.next();
								if (nombreProducto != null) {
									nombreProducto = nombreProducto.toUpperCase();
									if (!distinguir || !existeEnLista(lProductosGenericos, nombreProducto)) {
										Object repeticion = topProductos.get(nombreProducto); 
										if (repeticion != null) {
											int nuevoValor = ((int)repeticion);
											nuevoValor++;
											topProductos.remove(nombreProducto);
											topProductos.put(nombreProducto, nuevoValor);
										}
										else{
											topProductos.put(nombreProducto, 1);
										}																		
									}
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();	
		}
		return topProductos;
	}
	
	
	@SuppressWarnings("rawtypes")
	private boolean existeEnLista(List<String> lista, String valor)
	{
		boolean existe = false;
		try {
			if (lista != null && lista.size() > 0) {
				for (Iterator iterator = lista.iterator(); iterator.hasNext();) {
					String string = (String) iterator.next();
					if (string.toUpperCase().equals(valor.toUpperCase())) {
						existe = true;
						break;
					}
				}				
			}
		} catch (Exception e) {
			System.out.println("Error al verificar si xiste un elemento en la lista");
		}
		return existe;
	}
	
	
	private static <K, V extends Comparable<? super V>> Map<K, V> ordenarPorValor( Map<K, V> map )
	{
	    List<Map.Entry<K, V>> list = new LinkedList<>( map.entrySet() );
	    Collections.sort( list, new Comparator<Map.Entry<K, V>>()
	    {
	        @Override
	        public int compare( Map.Entry<K, V> o1, Map.Entry<K, V> o2 )
	        {
	            return (o1.getValue()).compareTo( o2.getValue() );
	        }
	    } );
	
	    Map<K, V> result = new LinkedHashMap<>();
	    Collections.reverse(list);
	    for (Map.Entry<K, V> entry : list)
	    {
	        result.put( entry.getKey(), entry.getValue() );
	    }
	    
	    return result;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<DataProducto> obtenerProductosConMenosStock(long idAV){
		List<DataProducto> lProductos = null;
		try {
			List<DataProducto> lDataProductos = cInventario.getProductosOrdenadosPorStock(idAV);
			if (lDataProductos != null && lDataProductos.size() > 0) {
				int cantidad = 3;
				lProductos = new ArrayList<>();
				for (Iterator iterator = lDataProductos.iterator(); iterator.hasNext();) {
					if (cantidad > 0) {
						lProductos.add((DataProducto) iterator.next());
						cantidad--;
					}
					else{
						break;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lProductos;
	}
}


