package com.de4bi.study.security.corespringsecurityboard.security;

public enum UserRoles {
    USER, MANAGER, ADMIN;

    public String getRoleType() {
        return "ROLE_" + this.name();
    }
}
