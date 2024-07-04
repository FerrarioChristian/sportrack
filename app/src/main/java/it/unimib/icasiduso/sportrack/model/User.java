package it.unimib.icasiduso.sportrack.model;

import com.google.firebase.database.Exclude;

public class User {
    private String name;
    private String email;
    private String idToken;

    public User(String name, String email, String idToken) {
        this.name = name;
        this.email = email;
        this.idToken = idToken;
    }

    @Exclude
    public String getIdToken() {
        return idToken;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
