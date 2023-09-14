package Service;

import java.util.List;

import DAO.MessageDAO;
import Model.Message;

public class MessageService {

    MessageDAO messageDAO;

    public MessageService (){
        messageDAO = new MessageDAO();
    }

    public Message createMessage(Message message) {
        return messageDAO.createMessage(message);
    }
    
    public List<Message> getMessages() {
        return messageDAO.getMessages();
    }
}