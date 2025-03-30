package org.example.hellofx.controller.defaultcontroller;

import org.example.hellofx.SpringBootFxApplication;
import org.example.hellofx.controller.ProfileController;
import org.example.hellofx.model.Account;
import org.example.hellofx.model.Resident;
import org.example.hellofx.service.DataBaseService;
import org.example.hellofx.repository.AccountRepository;
import org.example.hellofx.ui.JavaFxApplication;
import org.example.hellofx.ui.theme.ResidentScene;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DefaultProfileController implements ProfileController {
    @Autowired
    private DataBaseService dataBaseHandler;
    private Account profile;
    private Resident resident;
    @Autowired
    private AccountRepository accountRepository;

//    public DefaultProfileController(DataBaseHandler dataBaseHandler) {
//        this.dataBaseHandler = dataBaseHandler;
//    }

    public void logInRequest (Account profile) {
        this.profile = profile;
        resident = dataBaseHandler.findResidentByAccount(profile);
//        System.out.println("Logged in with profile: " + profile);
        JavaFxApplication.showHomeScene();
    }

    public String getProfileNameRequest() {
        if (profile != null) {
            return profile.getUsername();
        }
        return null;
    }

    public void logOutRequest(){
        this.profile = null;
        this.resident = null;
        ((ResidentScene) SpringBootFxApplication.context.getBean(ResidentScene.class)).reset();
//        System.out.println("Logged out succesfully!");
        JavaFxApplication.showLoginScene();
    }

    public String passwordChangeRequest(String currentPassword, String newPassword, String confirmPassword) {
        assert isLoggedIn() == true;
        if (newPassword.equals(confirmPassword) == false) {
            return "New password do not match!";
        }
        if (getCurrentPassword().equals(currentPassword) == false) {
            return "Current password do not match!";
        }
        int cnt = dataBaseHandler.passwordChangeQuery(profile, newPassword);
        if (cnt == 0) {
            return "Failed to change password, please try again later!";
        }
        profile.setPasswordHash(newPassword);
        return "Password changed successfully!";
    }

    public boolean isLoggedIn() {
        return profile != null;
    }

    private String getCurrentPassword() {
        return profile.getPasswordHash();
    }

    public Resident getResident() {
        return resident;
    }

    public void residentProfileUpdateRequest(Resident resident) {
        dataBaseHandler.updateResident(resident);
        this.resident = dataBaseHandler.findResidentByAccount(profile);
    }

    public Account getProfile() {
        return profile;
    }

    public boolean checkIdentityCardValidity(String identityCard) {
        return dataBaseHandler.checkResidentExistByIdentityCard(identityCard);
    }

    public boolean checkEmailValidity(String email) {
        return accountRepository.existsByEmail(email);
    }

    public boolean checkPhoneValidity(String phone) {
        return accountRepository.existsByPhone(phone);
    }

    @Override
    public void accountProfileUpdateRequest(Account account) {
        dataBaseHandler.updateAccount(account);
        if (profile.getUserId().equals(account.getUserId())) {
            profile = accountRepository.findByUserId(account.getUserId()).get();
        }
    }

    @Override
    public Account findAccountByUserId(int userId) {
        return dataBaseHandler.findAccountByUserId(userId);
    }

    @Override
    public Resident findResidentByUserId(int userId) {
        return dataBaseHandler.findResidentByUserId(userId);
    }
}
