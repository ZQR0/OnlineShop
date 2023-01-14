package ru.wm.WorkManager.entities;

public enum RoleEntity {
    ADMIN("admin"),
    USER("user"),
    BANNED("banned");

    private String _roleName;

    RoleEntity(String roleName) {
        this._roleName = roleName;
    }

    public String getRoleName() {
        return this._roleName;
    }
}
