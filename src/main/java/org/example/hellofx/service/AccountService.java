package org.example.hellofx.service;

import org.example.hellofx.model.Account;
import org.example.hellofx.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    RepositoryImpl repositoryImpl;

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public boolean checkAccountExistByUsername(String value) {
        return accountRepository.existsByUsername(value);
    }

    public boolean checkAccountExistByEmail(String value) {
        return accountRepository.existsByEmail(value);
    }

    public boolean checkAccountExistByPhone(String value) {
        return accountRepository.existsByPhone(value);
    }

    public void createAccount(String username, String password, String email, String phone) {
        Account acc = new Account(null, username, email, phone, password, null);
        for (int i = 0; i < 10; i++) {
            try {
                accountRepository.save(acc);
                break;
            }
            catch (Exception e) {
                continue;
            }
        }
    }

    public Account findAccountByUsernameAndPassword(String username, String passwordHash) {
        Optional<Account> result = accountRepository.findByUsernameAndPasswordHash(username, passwordHash);
        if (result.isPresent()) {
            return result.get();
        }
        return null;
    }

    public int passwordChangeQuery(Account profile, String newPassword) {
        return accountRepository.updatePasswordByUsername(profile.getUsername(), newPassword);
    }

    public void updateAccount(Account account) {
        accountRepository.updateRowByUserId(account.getUserId(), account.getRole(), account.getEmail(), account.getPhone());
    }

    public Account findAccountByUserId(int id) {
        return accountRepository.findByUserId(id).get();
    }

    public boolean existsByEmail(String email) {
        return accountRepository.existsByEmail(email);
    }

    public boolean existsByPhone(String phone) {
        return accountRepository.existsByPhone(phone);
    }

    public List<Account> nativeAccountQuery(String query) {
        return repositoryImpl.executeRawSql(query, Account.class);
    }

    public Optional<Account> findByEmail(String email) {
        return accountRepository.findByEmail(email);
    }

    public void save(Account account) {
        accountRepository.save(account);
    }
}
