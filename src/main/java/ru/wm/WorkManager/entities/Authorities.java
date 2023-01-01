package ru.wm.WorkManager.entities;

import jakarta.persistence.*;
import net.minidev.json.annotate.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;

/*
* @author ZQR0
* @since 1.01.2023
* Authority class, which implements GrantedAuthority
*/
public class Authorities implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "name")
    private RoleEntity name;

    @Override
    public String getAuthority() {
        return this.name.name();
    }

    // Getters & Setters
    @JsonIgnore
    public RoleEntity getName() {
        return this.name;
    }

    @JsonIgnore
    public Long getId() {
        return this.id;
    }

    public void setName(RoleEntity name) {
        this.name = name;
    }
}
