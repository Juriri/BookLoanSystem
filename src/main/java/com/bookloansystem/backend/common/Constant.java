package com.bookloansystem.backend.common;

public class Constant {

    //유저 권한
    public enum RoleType{
        USER, ADMIN
    }


    //Default State:: ON_LOAN
    public enum State {
        ON_LOAN, OVERDUE, RETURNED
    }

}