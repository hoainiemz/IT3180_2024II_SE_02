package org.example.hellofx.controller.defaultcontroller;

import org.apache.commons.validator.routines.EmailValidator;
import org.example.hellofx.controller.SignUpController;
import org.example.hellofx.service.DataBaseService;
import org.example.hellofx.ui.JavaFxApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DefaultSignUpController implements SignUpController {
    @Autowired
    private DataBaseService dataBaseService;

    @Override
    public void backToSignInClicked() {
        JavaFxApplication.showLoginScene();
    }

    String usernameCheck(String value) {
        if (value == null || value.isEmpty()) {
            return "Username can't be empty!";
        }
        if (dataBaseService.checkAccountExistByUsername(value)) {
            return "Username already exists!";
        }
        return "OK!";
    }

    String passwordCheck(String value) {
        if (value == null || value.isEmpty()) {
            return "Password can't be empty!";
        }
        return "OK!";
    }

    String emailCheck(String value) {
        if (value == null || value.isEmpty()) {
            return "Email can't be empty!";
        }
        if (dataBaseService.checkAccountExistByEmail(value)) {
            return "Email already exists!";
        }
        if (!EmailValidator.getInstance().isValid(value)) {
            return "Invalid email!";
        }
        return "OK!";
    }

    String phoneCheck(String value) {
        if (value == null || value.isEmpty()) {
            return "Phone number can't be empty!";
        }
        if (dataBaseService.checkAccountExistByPhone(value)) {
            return "Phone number already exists!";
        }
        return "OK!";
    }

    @Override
    public List<String> signUpClicked(String username, String password, String email, String phone) {
        boolean kt = true;
        List<String> res = new ArrayList<String>();
        res.add(usernameCheck(username));
        res.add(passwordCheck(password));
        res.add(emailCheck(email));
        res.add(phoneCheck(phone));
        for (int i = 0; i < res.size(); i++) {
            if (!res.get(i).equals("OK!")) {
                kt = false;
            }
        }
        if (kt) {
            dataBaseService.createAccount(username, password, email, phone);
        }
        return res;
    }
}
