package DAO;
// importing connection
import Util.ConnectionUtil;
import Model.Account;
// importing SQL utils
import java.sql.*;
import java.util.List;
import java.util.ArrayList;

// Need to register users
// Username !blank & password over 4 characters long
// No duplicate account usernames
public class AccountDAO {
    

    public Account insertAccount(Account account) {
        Connection connection = ConnectionUtil.getConnection();

        try {
            String sql = "INSERT INTO account (username, password) VALUES (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());

            int rowsAffected = preparedStatement.executeUpdate();
            // need to check if the insert is successful
            if (rowsAffected > 0) {
                ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
                    if(pkeyResultSet.next()) {
                        int generated_account_id = pkeyResultSet.getInt(1);
                        return new Account(generated_account_id, account.getUsername(), account.getPassword());
                    }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if(connection !=null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return null;
    }
}
