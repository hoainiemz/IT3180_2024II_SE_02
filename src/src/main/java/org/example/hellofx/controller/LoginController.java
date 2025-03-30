package org.example.hellofx.controller;

public interface LoginController {
    /**
     * User clicked the login button
     * @param username
     * @param password
     * @return the string for the profile
     */
    public String loginButtonClicked(String username, String password);

    /**
     * user clicked the signup button
     */
    public void signUpButtonClicked();
}
