package org.example.hellofx.controller;

import org.example.hellofx.model.Account;
import org.example.hellofx.model.Resident;
import org.springframework.stereotype.Component;

@Component
public interface ProfileController {
    /**
     * user logged in succesfull, tell application to log in
     * @param profile
     */
    public void logInRequest(Account profile);

    /**
     *
     * @return current username
     */
    public String getProfileNameRequest();

    /**
     * user logged out, get back to the login page
     */
    public void logOutRequest();

    /**
     * check if logged in or not
     */
    public boolean isLoggedIn();

    /**
     * user filled the password change form, send the request to the database
     * @return a string, state of the request
     */
    public String passwordChangeRequest(String currentPassword, String newPassword, String confirmPassword);

    /**
     * profile getter
     * @return current profile
     */
    public Account getProfile();

    /**
     * resident getter
     * @return current resident
     */
    public Resident getResident();

    /**
     * resident change request
     * @param resident
     */
    public void residentProfileUpdateRequest(Resident resident);

    /**
     * check if identity card whether existed or not
     * @param identityCard
     * @return true / false
     */
    public boolean checkIdentityCardValidity(String identityCard);

    /**
     * check if email is whether existed or not
     * @param email
     * @return true / false
     */
    public boolean checkEmailValidity(String email);
    /**
     * check if phone is whether existed or not
     * @param phone
     * @return true / false
     */
    public boolean checkPhoneValidity(String phone);

    /**
     * Account change request
     * @param account
     */
    public void accountProfileUpdateRequest(Account account);

    /**
     * find Account by user id
     * @param userId
     */
    public Account findAccountByUserId(int userId);

    /**
     * find Resident by user id
     * @param userId
     */
    public Resident findResidentByUserId(int userId);
}
