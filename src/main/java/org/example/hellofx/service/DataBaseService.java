package org.example.hellofx.service;

import org.example.hellofx.dto.ResidentBillPaymentDTO;
import org.example.hellofx.model.Account;
import org.example.hellofx.model.Bill;
import org.example.hellofx.model.Payment;
import org.example.hellofx.model.Resident;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface DataBaseService {

    /**
     * getLoginState: User login and DataBaseHandler check if the information mathch or not, return a Profile instance
     * @param username
     * @param password
     * @return a profile of the matched account
     */
    public Account findAccountByUsernameAndPassword(String username, String password);

    /**
     * change the profile's password
     * @param profile
     * @param newPassword
     * @return the number of accounts changed
     */
    public int passwordChangeQuery(Account profile, String newPassword);

    /**
     * get all accounts
     * @return a list of all accounts
     */
    public List<Account> getAllAccounts();

    /**
     * check if there are any account with given username
     * @return
     */
    public boolean checkAccountExistByUsername( String value);
    /**
     * check if there are any account with given email
     * @return
     */
    public boolean checkAccountExistByEmail( String value);
    /**
     * check if there are any account with given phone
     * @return
     */
    public boolean checkAccountExistByPhone( String value);

    /**
     * user signed up
     * @param username
     * @param password
     * @param email
     * @param phone
     */
    public void createAccount(String username, String password, String email, String phone);

    /**
     * the user with Account profile and Resident resident want to take a resident query.
     * @param profile
     * @param resident
     * @return List of resident
     */
    public List<Resident> residentsQuery(Account profile, Resident resident);

    /**
     * find resident by Account
     * @param profile
     * @return the matched resident
     */
    public Resident findResidentByAccount(Account profile);

    /**
     * return all non-null house id
     * @return List of all house id
     */
    List<String> findDistinctNonNullHouseId(Account profile, Resident resident);

    /**
     * query with the following string
     * @param query
     * @return the result of the query
     */
    List<Resident> nativeResidentQuery(String query);

    /**
     * query with the following string
     * @param query
     * @return the result of the query
     */
    List<Account> nativeAccountQuery(String query);

    /**
     * Query the Account with user_id = id
     * @param id
     * @return
     */
    Account findAccountByUserId(int id);

    /**
     * Query the Resident with user_id = id
     * @param id
     * @return
     */
    Resident findResidentByUserId(int id);

    /**
     * check if there are any resident with given identiry card
     * @param identityCard
     * @return
     */
    boolean checkResidentExistByIdentityCard(String identityCard);

    /**
     * update a row
     * @param resident
     */
    public void updateResident(Resident resident);

    /**
     * update a row
     * @param account
     */
    public void updateAccount(Account account);

    /**
     * very important
     * @param residentId
     * @return
     */
    public List<ResidentBillPaymentDTO> getResidentPayments(Integer residentId);

    @Transactional
    public void saveAllPayments(List<Payment> payments);

    @Transactional
    public void updateBill(Bill bill);

    @Transactional
    public void deletePayments(List<Integer> dsOut);
}
