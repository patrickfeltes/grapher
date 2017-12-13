package com.patrickfeltes.graphingprogram.database.objects;

/**
 * User is used by Firebase to retrieve and send data
 */
public class User {

    public String username;
    public String uid;

    public User() {}

    public User(String username, String uid) {
        this.username = username;
        this.uid = uid;
    }

}
