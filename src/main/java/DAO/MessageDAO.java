package DAO;
import java.sql.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import Model.Message;
import Util.ConnectionUtil;

public class MessageDAO {
    // create message
    // delete messages
    // get all messages
    // get all messages for user
    // get message by ID
    // update message

    public Message createMessage(Message message){
        Connection connection = ConnectionUtil.getConnection();
        Message newMessage = null;
        try {
            //Write SQL logic here
            String sql = "INSERT INTO message (posted_by, message_text, time_posted_epoch) VALUES (?, ?, ?)" ;
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            //write preparedStatement's setString and setInt methods here.
            preparedStatement.setInt(1, message.getPosted_by());
            preparedStatement.setString(2, message.getMessage_text()); 
            preparedStatement.setLong(3, message.getTime_posted_epoch()); 

            preparedStatement.executeUpdate();
            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();

            if (pkeyResultSet.next() && message.getMessage_text()!="" && message.getMessage_text().length()<255){
                int message_id = pkeyResultSet.getInt("message_id");

                newMessage = new Message(message_id, message.getPosted_by(), message.getMessage_text(), message.getTime_posted_epoch());
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return newMessage;
    }

    public List<Message> getMessages() {
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try {
            String sql = "SELECT * FROM message";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                int message_id = resultSet.getInt("message_id");
                int posted_by = resultSet.getInt("posted_by");
                String message_text = resultSet.getString("message_text");
                long time_posted_epoch = resultSet.getInt("time_posted_epoch");
                Message message = new Message(message_id, posted_by, message_text, time_posted_epoch);

                messages.add(message);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return messages;
    }
    // get messages by ID
    public Message getMessagesById(int id) {
        Connection connection = ConnectionUtil.getConnection();
        Message messageId = null;
        try {
            String sql = "SELECT * FROM message WHERE message_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                int message_id = resultSet.getInt("message_id");
                int posted_by = resultSet.getInt("posted_by");
                String message_text = resultSet.getString("message_text");
                long time_posted_epoch = resultSet.getInt("time_posted_epoch");
                messageId = new Message(message_id, posted_by, message_text, time_posted_epoch);

            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return messageId;
    }

    public List <Message> getMessagesByUser(int account_id){
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try {
            String sql = "SELECT * FROM message WHERE posted_by = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, account_id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                int message_id = resultSet.getInt("message_id");
                int posted_by = resultSet.getInt("posted_by");
                String message_text = resultSet.getString("message_text");
                long time_posted_epoch = resultSet.getInt("time_posted_epoch");
                Message message = new Message(message_id, posted_by, message_text, time_posted_epoch);

                messages.add(message);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return messages;
    }

    public void deleteMessage(int id){
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "DELETE FROM message WHERE message_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.executeQuery();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public Message updateMessage(Message message){
        Connection connection = ConnectionUtil.getConnection();
        Message newMessage = null;
        try {
            //Write SQL logic here
            String sql = "INSERT INTO message (posted_by, message_text, time_posted_epoch) VALUES (?, ?, ?)" ;
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            //write preparedStatement's setString and setInt methods here.
            preparedStatement.setInt(1, message.getPosted_by());
            preparedStatement.setString(2, message.getMessage_text()); 
            preparedStatement.setLong(3, message.getTime_posted_epoch()); 

            preparedStatement.executeUpdate();
            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();

            if (pkeyResultSet.next() && message.getMessage_text()!="" && message.getMessage_text().length()<255){
                int message_id = pkeyResultSet.getInt("message_id");

                newMessage = new Message(message_id, message.getPosted_by(), message.getMessage_text(), message.getTime_posted_epoch());
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return newMessage;
    }
}
