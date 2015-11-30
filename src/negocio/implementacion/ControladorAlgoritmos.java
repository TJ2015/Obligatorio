package negocio.implementacion;

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

import dominio.datatypes.DataUsuario;
import negocio.interfases.IControladorAV;
import negocio.interfases.IControladorAlgoritmos;
import negocio.interfases.IControladorInventario;
import negocio.interfases.IControladorUsuario;

@Stateless
public class ControladorAlgoritmos implements IControladorAlgoritmos{
	
	
	@EJB
	private IControladorUsuario cUsuario;
	
	@EJB
	private IControladorInventario cInventario;
	
	@EJB
	private IControladorAV cAV;
	
	
	@SuppressWarnings("rawtypes")
	public Map<String, Integer> obtenerProductosMasVendidos(){
		Map<String, Integer> lProductos = null;
		try {
			lProductos = obtenerRankingProductos();
			lProductos = ordenarPorValor(lProductos);			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lProductos;
	}
	
	@SuppressWarnings("rawtypes")
	private Map<String, Integer> obtenerRankingProductos(){
		Map<String, Integer> topProductos = new HashMap<String, Integer>();
		try {
			List<DataUsuario> lUsuarios = cUsuario.getUsuarios();
			if (lUsuarios != null && lUsuarios.size() > 0) {
				List<String> lProductosGenericos = cInventario.obtenerNombresProductosGenericos();
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
									if (!existeEnLista(lProductosGenericos, nombreProducto)) {
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
	
	
	private boolean existeEnLista(List<String> lista, String valor)
	{
		boolean existe = false;
		try {
			if (lista != null && lista.size() > 0) {
				for (Iterator iterator = lista.iterator(); iterator.hasNext();) {
					String string = (String) iterator.next();
					if (string.equals(valor)) {
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

}


