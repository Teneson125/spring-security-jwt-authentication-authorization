package com.example.springsecurityjwtauthenticationauthorization.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class User{
    private String name;
    @Id
    private String userName;
    private String email;
    private String password;
    private boolean accountNotLocked;
    private boolean accountNotExpired;
    private boolean credentialNotExpired;
    private boolean enabled;

    public User() {
    }

    public User(String name, String userName, String email, String password, boolean accountNotLocked, boolean accountNotExpired, boolean credentialNotExpired, boolean enabled) {
        this.name = name;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.accountNotLocked = accountNotLocked;
        this.accountNotExpired = accountNotExpired;
        this.credentialNotExpired = credentialNotExpired;
        this.enabled = enabled;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAccountNotLocked() {
        return accountNotLocked;
    }

    public void setAccountNotLocked(boolean accountNotLocked) {
        this.accountNotLocked = accountNotLocked;
    }

    public boolean isAccountNotExpired() {
        return accountNotExpired;
    }

    public void setAccountNotExpired(boolean accountNotExpired) {
        this.accountNotExpired = accountNotExpired;
    }

    public boolean isCredentialNotExpired() {
        return credentialNotExpired;
    }

    public void setCredentialNotExpired(boolean credentialNotExpired) {
        this.credentialNotExpired = credentialNotExpired;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}

