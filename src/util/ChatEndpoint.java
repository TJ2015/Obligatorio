package util;

import java.io.IOException;
import java.util.Collections;
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


@ServerEndpoint(value="/chat", configurator=ServletAwareConfig.class)
public class ChatEndpoint  {

	static Set<Session> SESSIONES = Collections.synchronizedSet(new HashSet<Session>());
	
	static Map<String, String> USUARIOS = Collections.synchronizedMap(new HashMap<String, String>());
	
	private EndpointConfig config;

    @OnOpen
    public void onOpen(Session session, EndpointConfig config) {
        this.config = config;
        try {
        	SESSIONES.add(session);
        	HttpSession httpSession = (HttpSession) config.getUserProperties().get("httpSession");
            String nickusuario =(String) httpSession.getAttribute("nickname");
            long idAV =(long) httpSession.getAttribute("idAV");
            USUARIOS.put(session.getId(), Long.toString(idAV));
            System.out.println(nickusuario);
            System.out.println(idAV);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
        
    }
    

    @OnClose
    public void close(Session session) {
    	System.out.println("Cierra " + session.getId());
    	SESSIONES.remove(session);
    	USUARIOS.remove(session.getId());
    }

    @OnError
    public void onError(Throwable error) {
    	System.out.println("Error");
    }

    @OnMessage
    public void onMessage(String message, Session session) {
    	System.out.println(message);
    	String avActual = USUARIOS.get(session.getId());
    	String avUsuario = null;
    	Iterator<Session> iter = SESSIONES.iterator();
    	Session sessionAux = null;
    	while (iter.hasNext()) {
			try {
				sessionAux = iter.next();
				avUsuario = USUARIOS.get(sessionAux.getId());
				if (avActual != null && avActual.equals(avUsuario)) {
					sessionAux.getBasicRemote().sendText(message);
				}
			} catch (IOException e) {
				e.printStackTrace();
			};
		}
    }
}    
