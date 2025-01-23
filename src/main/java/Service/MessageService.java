package Service;

import Model.Message;
import DAO.MessageDAO;
import DAO.AccountDAO;

import java.util.List;

public class MessageService {
    MessageDAO messageDAO;
    AccountDAO accountDAO;

    public MessageService() {
        messageDAO = new MessageDAO();
        accountDAO = new AccountDAO();
    }

    public MessageService(MessageDAO messageDAO, AccountDAO accountDAO){
        this.messageDAO = messageDAO;
        this.accountDAO = accountDAO;
    }

    public Message createMessage(Message message) {
        if (accountDAO.userExists(message.getPosted_by())) {
            return messageDAO.createMessage(message);
        }
        return null;
    }

    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }

    public Message getMessageById(int message_id) {
        return messageDAO.getMessageById(message_id);
    }

    public Message deleteMessage(int message_id) {
        Message message = messageDAO.getMessageById(message_id);
        messageDAO.deleteMessage(message_id);
        return message;
    }

    public Message updateMessage(int message_id, Message message) {
        boolean ret = messageDAO.updateMessage(message_id, message);
        if (ret) {
            return messageDAO.getMessageById(message_id);
        }
        return null;
    }

    public List<Message> getAllMessagesByUserId(int posted_by) {
        return messageDAO.getAllMessagesByUser(posted_by);
    }
}
