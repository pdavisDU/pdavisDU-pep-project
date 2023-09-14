package DAO;
// importing connection
import Util.ConnectionUtil;
import Model.Account;
// importing SQL utils
import java.sql.*;


// Need to register users
// Username !blank & password over 4 characters long
// No duplicate account usernames
public class AccountDAO {
    

    public Account insertAccount(Account account) {
        Connection connection = ConnectionUtil.getConnection();
        Account newAccount = null;

        try {
            String sql = "INSERT INTO account (username, password) VALUES (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());

           preparedStatement.executeUpdate();
            // need to check if the insert is successful
            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
                    if(pkeyResultSet.next()) {
                        int generated_account_id = pkeyResultSet.getInt(1);
                        newAccount = new Account(generated_account_id, account.getUsername(), account.getPassword());
                    }
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        // } finally {
        //     try {
        //         if(connection !=null) {
        //             connection.close();
        //         }
        //     } catch (SQLException e) {
        //         System.out.println(e.getMessage());
        //     }
        }
        return newAccount;
    }

    public Account loginAccount(Account account) {
        Connection connection = ConnectionUtil.getConnection();
        Account login = null;

        try {
            String sql = "SELECT * FROM account WHERE username = ? AND password = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                // int generated_account_id = resultSet.getInt("account_id");
                login = new Account(resultSet.getInt("account_id"), resultSet.getString("username"), resultSet.getString("password"));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return login;
    }
}
