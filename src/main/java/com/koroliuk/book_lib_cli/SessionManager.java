package com.koroliuk.book_lib_cli;

public class SessionManager {
    static String currentUser;

    public static String getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(String currentUser) {
        SessionManager.currentUser = currentUser;
    }
}
