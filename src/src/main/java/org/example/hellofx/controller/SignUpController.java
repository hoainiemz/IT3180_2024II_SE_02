package org.example.hellofx.controller;

import java.util.List;

public interface SignUpController {
    /**
     * user clicked back to sign in
     */
    void backToSignInClicked();

    /**
     * user clicked to sign up
     * @param username
     * @param password
     * @param email
     * @param phone
     * @return status list
     */
    List<String> signUpClicked(String username, String password, String email, String phone);
}
