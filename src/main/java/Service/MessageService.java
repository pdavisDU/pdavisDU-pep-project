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

    // get message by ID
    public Message getMessagesById(int id) {
        return messageDAO.getMessagesById(id);
    }

    // get message by user
    public List<Message> getMessagesByUser(int account_id){
        return messageDAO.getMessagesByUser(account_id);
    }

    public void deleteMessage(int id){
        // add logic here for if message found or not found
        if(messageDAO.getMessagesById(id) != null) {
            messageDAO.deleteMessage(id);
        } else return;
    }

    public Message updateMessage(Message message) {
        return messageDAO.updateMessage(message);
    }
}