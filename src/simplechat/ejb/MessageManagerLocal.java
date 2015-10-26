package simplechat.ejb;

//package ru.reshaka.labs.chat.ejb;
 
import java.util.Date;
import javax.ejb.Local;

import simplechat.web.Message;
//import ru.reshaka.labs.chat.Message;
 
/**
 * Local interface for chat lagic EJB
 * @author Danon
 */
@Local
public interface MessageManagerLocal {
 
    void sendMessage(Message msg);
 
    Message getFirstAfter(Date after);
 
}