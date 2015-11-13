package util;

import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import dominio.datatypes.DataChat;
import dominio.datatypes.DataUsuario;


@ServerEndpoint(value="/chat", configurator=ServletAwareConfig.class)
public class ChatEndpoint  {

	static Set<Session> SESSIONES = Collections.synchronizedSet(new HashSet<Session>());
	
	static Map<String, DataChat> USUARIOS = Collections.synchronizedMap(new HashMap<String, DataChat>());
	
	private EndpointConfig config;

    @OnOpen
    public void onOpen(Session session, EndpointConfig config) {
        this.config = config;
        try {
        	SESSIONES.add(session);
        	HttpSession httpSession = (HttpSession) config.getUserProperties().get("httpSession");
        	DataUsuario dataUsuario = (DataUsuario) httpSession.getAttribute("dataUsuario");
            long idAV =(long) httpSession.getAttribute("idAV");
            byte[] imagen = util.Imagenes.convertirInputStreamToArrayByte(dataUsuario.getImagen());
            DataChat dataChat = new DataChat(dataUsuario.getNombre(), idAV, new Date().toString(), imagen);
            USUARIOS.put(session.getId(), dataChat);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
    }
    

    @OnClose
    public void close(Session session) {
    	try {
    		System.out.println("Cierra " + session.getId());
        	SESSIONES.remove(session);
        	USUARIOS.remove(session.getId());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
    }

    @OnError
    public void onError(Throwable error) {
    	System.out.println("Error");
    }

    @OnMessage
    public void onMessage(String message, Session session) {
    	System.out.println(message);
    	DataChat usuarioActual = USUARIOS.get(session.getId());
    	DataChat usuarioAux = null;
    	Iterator<Session> iter = SESSIONES.iterator();
    	Session sessionAux = null;
    	while (iter.hasNext()) {
			try {
				sessionAux = iter.next();
				usuarioAux = USUARIOS.get(sessionAux.getId());
				if (usuarioActual != null && usuarioAux != null && usuarioActual.getIdAV() == usuarioAux.getIdAV()) {
					sessionAux.getBasicRemote().sendObject(usuarioAux.serializar());
				}
			} catch (Exception e) {
				e.printStackTrace();
			};
		}
    }
}    
