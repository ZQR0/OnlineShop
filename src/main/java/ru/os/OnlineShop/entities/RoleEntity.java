package ru.os.OnlineShop.entities;

public enum RoleEntity {
    ADMIN("ADMIN"),
    USER("USER"),
    BANNED("BANNED");

    private String _roleName;

    RoleEntity(String roleName) {
        this._roleName = roleName;
    }

    public String getRoleName() {
        return this._roleName;
    }
}
