package util;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;
import javax.websocket.EndpointConfig;
import javax.websocket.HandshakeResponse;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpoint;
import javax.websocket.server.ServerEndpointConfig;

import com.google.gson.Gson;

import dominio.datatypes.DataChat;
import dominio.datatypes.DataUsuario;
import dominio.datatypes.DataUsuarioSocial;


@ServerEndpoint(value="/chat", configurator=ServletAwareConfig.class)
public class ChatEndpoint  {

	static Set<Session> SESSIONES = Collections.synchronizedSet(new HashSet<Session>());
	
	static Map<String, DataChat> USUARIOS = Collections.synchronizedMap(new HashMap<String, DataChat>());
	

    @OnOpen
    public void onOpen(Session session, EndpointConfig config) {
        try {
        	SESSIONES.add(session);
        	HttpSession httpSession = (HttpSession) config.getUserProperties().get("httpSession");
        	DataUsuario dataUsuario = (DataUsuario) httpSession.getAttribute("dataUsuario");
            long idAV =(long) httpSession.getAttribute("idAV");
            DataChat dataChat = new DataChat(dataUsuario.getNick(), dataUsuario.getNombre() + " " + dataUsuario.getApellido() , idAV, new Date().toString());
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
    	Gson gson = new Gson();
		DataChat mensaje = gson.fromJson(message, DataChat.class);
    	System.out.println(session.getId());
    	DataChat usuarioActual = USUARIOS.get(session.getId());
    	Iterator<Session> iter = SESSIONES.iterator();
    	while (iter.hasNext()) {
			try {
				Session sessionAux = iter.next();
				DataChat usuarioAux = new DataChat(USUARIOS.get(sessionAux.getId()));
				if (usuarioActual != null && usuarioAux != null && usuarioActual.getIdAV() == usuarioAux.getIdAV()) {
					usuarioAux.setMensaje(mensaje.getMensaje());
					usuarioAux.setNombreCompleto(mensaje.getNombreCompleto());
					usuarioAux.setSoyYo(usuarioAux.getNickUsuario().equals(mensaje.getNickUsuario()));
					usuarioAux.setNickUsuario(mensaje.getNickUsuario());
					usuarioAux.setFecha(Fecha.convertirParaChat(new Date(), "dd-MM-yyyy - HH:mm:ss"));
					sessionAux.getBasicRemote().sendObject(usuarioAux.serializar());
				}
			} catch (Exception e) {
				e.printStackTrace();
			};
		}
    }
}    
