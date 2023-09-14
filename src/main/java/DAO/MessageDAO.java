package DAO;
import java.sql.*;
import java.sql.Connection;
import java.sql.PreparedStatement;

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

    
}
