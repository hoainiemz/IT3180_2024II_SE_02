package org.example.hellofx.controller;

public interface PasswordChangeController {
    /**
     * user request to change the password with the following information
     * @param username
     * @param password
     * @param xacNhanMatKhauMoiPasswordField
     * @return state of the request
     */
    public String passwordChangeRequest(String username, String password, String xacNhanMatKhauMoiPasswordField);
}
