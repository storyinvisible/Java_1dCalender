package com.example.sutdcalendar_login;

public class SignupInfo {
    private String signup_name;
    private String signup_email;
    private String signup_code;

    public SignupInfo() {
        signup_name="nobody";
        signup_email="empty";
        signup_code="null";
    }
    public SignupInfo(String name,String email, String password) {
        signup_name=name;
        signup_email=email;
        signup_code=password;
    }
    public boolean checkvalid() {
        if (this.signup_name.equals("Zhang Shaozuo")|this.signup_email.equals("shaozuo_zhang@mymail.sutd.edu,sg")|this.signup_code.equals("123")) {
            return true;
        }
        return false;
    }
    public boolean checkvalidname() {
        if (this.signup_name.isEmpty()) {
            return false;
        }
        return true;
    }
    public boolean checkvalidemail() {
        if (this.signup_email.isEmpty()) {
            return false;
        }
        return true;
    }
    public boolean checkvalidpassword() {
        if (this.signup_code.isEmpty()) {
            return false;
        }
        return true;
    }


}
