package org.example.hellofx.controller.defaultcontroller;

import org.example.hellofx.controller.PasswordChangeController;
import org.example.hellofx.controller.ProfileController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DefaultPasswordChangeController implements PasswordChangeController {
    @Autowired
    private ProfileController profileController;

    @Override
    public String passwordChangeRequest(String username, String password, String xacNhanMatKhauMoiPasswordField) {
        return profileController.passwordChangeRequest(username, password, xacNhanMatKhauMoiPasswordField);
    }
}
