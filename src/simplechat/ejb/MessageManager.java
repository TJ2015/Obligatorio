package simplechat.ejb;

 
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.ejb.Singleton;
import javax.ejb.Startup;


import simplechat.web.Message;
//import ru.reshaka.labs.chat.Message;

 
/**
 * Simple chat logic
 * @author Danon
 */
@Singleton
@Startup
public class MessageManager implements MessageManagerLocal {
 
    private final List<Message> messages = Collections.synchronizedList(new LinkedList());
 
    @Override
    public void sendMessage(Message msg) {
        messages.add(msg);
        msg.setDateSent(new Date());
    }
 
    @Override
    public Message getFirstAfter(Date after) {
        if(messages.isEmpty())
            return null;
        if(after == null)
            return messages.get(0);
        for(Message m : messages) {
            if(m.getDateSent().after(after))
                return m;
        }
        return null;
    }
 
}