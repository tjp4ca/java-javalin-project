package Service;

import DAO.AccountDAO;

import Model.Account;

public class AccountService {
    private AccountDAO accountDAO;

    public AccountService(){
        accountDAO = new AccountDAO();
    }

    public AccountService(AccountDAO accountDAO){
        this.accountDAO = accountDAO;
    }

    public Account addAccount(Account account) {
        return accountDAO.insertAccount(account);
    }

    public Account verifyLogin(String username, String password) {

//        /**

        /**
        if((account.getUsername().equals(username)) && (account.getPassword().equals(password))){
            return account;
        } else {
            return null;
        }
        */

//        */

        Account account = accountDAO.getLoginCredentials(username, password);
        return account;

    }
}
