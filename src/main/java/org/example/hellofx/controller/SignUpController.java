package org.example.hellofx.controller;


import org.example.hellofx.service.AccountService;
import org.example.hellofx.ui.JavaFxApplication;
import org.example.hellofx.ui.theme.defaulttheme.ForgotPasswordScene;
import org.example.hellofx.ui.theme.defaulttheme.LoginScene;
import org.example.hellofx.validator.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SignUpController{
    @Autowired
    private AccountService accountService;

    @Autowired
    private EmailValidator emailValidator;
    
    public void backToSignInClicked() {
        JavaFxApplication.showThemeScene(LoginScene.class);
    }

    String usernameCheck(String value) {
        if (value == null || value.isEmpty()) {
            return "Tên tài khoản không được bỏ trống!";
        }
        if (accountService.checkAccountExistByUsername(value)) {
            return "Tên tài khoản đã được sử dụng!";
        }
        return "OK!";
    }

    String passwordCheck(String value) {
        if (value == null || value.isEmpty()) {
            return "Mật khẩu không được bỏ trống!";
        }
        return "OK!";
    }

    String phoneCheck(String value) {
        if (value == null || value.isEmpty()) {
            return "Số điện thoại không được để trống!";
        }
        if (accountService.checkAccountExistByPhone(value)) {
            return "Số điện thoại đã được sử dụng cho tài khoản khác!";
        }
        return "OK!";
    }

    public List<String> signUpClicked(String username, String password, String email, String phone) {
        boolean kt = true;
        List<String> res = new ArrayList<String>();
        res.add(usernameCheck(username));
        res.add(passwordCheck(password));
        res.add(emailValidator.emailSignupCheck(email));
        res.add(phoneCheck(phone));
        for (int i = 0; i < res.size(); i++) {
            if (!res.get(i).equals("OK!")) {
                kt = false;
            }
        }
        if (kt) {
            accountService.createAccount(username, password, email, phone);
        }
        return res;
    }

    public void forgotPasswordClicked() {
        JavaFxApplication.showThemeScene(ForgotPasswordScene.class);
    }
}
