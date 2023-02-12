package ru.os.OnlineShop.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;

/*
* @author ZQR0
* @since 1.01.2023
* @version 0.1
* User database entity main class
*/
@Entity
@Table(name = "user_table")
@Data
public class UserEntity implements UserDetails {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "first_name", unique = true, nullable = false)
    private String firstName;

    @Column(name = "password", nullable = false, unique = false)
    private String password;

    @Column(name = "is_enabled", nullable = false)
    private Boolean isEnabled = true;


    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "register_time", nullable = false)
    private Date registerTime;


    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private RoleEntity role = RoleEntity.USER;

    // Constructor
    public UserEntity(String email, String firstName, String password, Boolean isEnabled, Date registerTime, RoleEntity role) {
        this.email = email;
        this.firstName = firstName;
        this.password = password;
        this.isEnabled = isEnabled;
        this.registerTime = registerTime;
        this.role = role;
    }

    public UserEntity() {}

    // Override methods
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return this.email;
    }


    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.isEnabled;
    }

    public static UserBuilder builder() {
        return new UserBuilder();
    }

    // Builder for user
    /*
    * Builder pattern using for this class
    * We have a lot of params in constructor, which is not good
    * So, we can just create a builder and use it like Java Streams API
    */
    public static class UserBuilder {
        private String _email;
        private String _firstName;
        private String _password;
        private Date _registerTime;
        private RoleEntity _role;
        private Boolean _isEnabled;

        public UserBuilder setEmail(String email) {
            this._email = email;
            return this;
        }

        public UserBuilder setFirstName(String firstName) {
            this._firstName = firstName;
            return this;
        }

        public UserBuilder setPassword(String password) {
            this._password = password;
            return this;
        }

        public UserBuilder date(Date registerTime) {
            this._registerTime = registerTime;
            return this;
        }

        public UserBuilder role(RoleEntity role) {
            this._role = role;
            return this;
        }

        public UserBuilder isEnabled(Boolean isEnabled) {
            this._isEnabled = isEnabled;
            return this;
        }

        public UserEntity build() {
            return new UserEntity(_email, _firstName, _password, _isEnabled, _registerTime, _role);
        }

    }
}
