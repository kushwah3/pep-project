package Service;

import Model.Account;
import DAO.AccountDAO;

public class AccountService {
    AccountDAO accountDAO;

    public AccountService() {
        accountDAO = new AccountDAO();
    }

    public AccountService(AccountDAO accountDAO){
        this.accountDAO = accountDAO;
    }

    public Account addUser(Account account) {
        return accountDAO.createUser(account);
    }

    public Account loginUser(Account account) {
        return accountDAO.loginUser(account);
    }

    public boolean userExists(int account_id) {
        return accountDAO.userExists(account_id);
    }
}
