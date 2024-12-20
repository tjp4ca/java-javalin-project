package Service;

import DAO.MessageDAO;
import Model.Message;

import java.util.List;

public class MessageService {

    public MessageDAO messageDAO;

    public MessageService(){
        messageDAO = new MessageDAO();
    }

    public MessageService(MessageDAO messageDAO){
        this.messageDAO = messageDAO;
    }

    // Post a Message
    public Message addMessage(Message message){
        return messageDAO.insertMessage(message);
    }
    
    // Get All Messages
    public List<Message> getAllMessages(){
        return messageDAO.getAllMessages();
    }

    // Get Message By Id
    public Message getMessageById(int messageId){
        return messageDAO.getMessageById(messageId);
    }

    // Delete Message By Id
    public Message deleteMessage(int messageId){
        return messageDAO.deleteMessage(messageId);
    }

    // Update Message By Id
    public Message updateMessage(int messageId, String newMessage){
        return messageDAO.updateMessage(messageId, newMessage);
    }

    // Get All Messages By User Id
    public List<Message> getAllMessagesByUserId(int accountId){
        return messageDAO.getAllMessagesByUserId(accountId);
    }
}
