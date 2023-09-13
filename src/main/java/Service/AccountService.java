package Service;
import Model.Account;
import DAO.AccountDAO;

public class AccountService {
    private final AccountDAO accountDAO;

    public AccountService() {
        accountDAO = new AccountDAO();
    }

    public Account insertAccount(Account account) {
        // add validation logic here
        if (account.getPassword().length() >= 4 && account.getUsername() !="" ){
            return accountDAO.insertAccount(account);
        }
        return null;
    }
    public Account loginAccount (Account account) {
        return accountDAO.loginAccount(account);
    }
}
