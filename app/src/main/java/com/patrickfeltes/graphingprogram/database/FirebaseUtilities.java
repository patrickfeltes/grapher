package com.patrickfeltes.graphingprogram.database;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseUtilities {

    public static void addUserToDatabase(String UID, String username) {
        DatabaseReference usersTree = FirebaseDatabase.getInstance().getReference("users");
        usersTree.child(UID).child("username").setValue(username);
    }

}
