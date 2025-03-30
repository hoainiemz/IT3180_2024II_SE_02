package org.example.hellofx.controller.defaultcontroller;

import org.example.hellofx.controller.LoginController;
import org.example.hellofx.model.Account;
import org.example.hellofx.controller.ProfileController;
import org.example.hellofx.service.DataBaseService;
import org.example.hellofx.ui.JavaFxApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DefaultLoginController implements LoginController {
    @Autowired
    DataBaseService dataBaseHandler;
    @Autowired
    ProfileController profileController;

    public String loginButtonClicked(String username, String password) {
        Account response = dataBaseHandler.findAccountByUsernameAndPassword(username, password);
        if (response != null) {
            profileController.logInRequest(response);
        }
        return response != null ? response.toString() : null;
    }

    public void signUpButtonClicked() {
        JavaFxApplication.showSignUpScene();
    }
}
