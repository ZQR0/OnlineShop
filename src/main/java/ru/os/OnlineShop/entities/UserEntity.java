package ru.os.OnlineShop.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
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
@AllArgsConstructor
@Entity
@Table(name = "user_table")
@Data
public class UserEntity implements UserDetails {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Long id;

    @Column(name = "email", unique = true, nullable = false)
    @Getter
    @Setter
    private String email;

    @Column(name = "username", unique = true, nullable = false)
    @Getter
    @Setter
    private String username;

    @Column(name = "password", nullable = false, unique = false)
    @Getter
    @Setter
    private String password;

    @Column(name = "is_enabled", nullable = false)
    @Getter
    @Setter
    private Boolean isEnabled = true;

    @Getter
    @Setter
    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "register_time", nullable = false, unique = false)
    private Date registerTime;

    @Getter
    @Setter
    @Column(name = "role", nullable = false)
    private String role = RoleEntity.USER.getRoleName();

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_authority",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id", referencedColumnName = "id")
    )
    private List<Authorities> authorities;


    // Constructor
    public UserEntity() {}

    public UserEntity(String email, String username, String password, Date registerTime, String role, Boolean isEnabled) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.registerTime = registerTime;
        this.role = role;
        this.isEnabled = isEnabled;
    }


    // Override methods
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    // We are going to use email instead of username for authentication
    @Override
    public String getUsername() {
        return this.getEmail();
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
        private String _username;
        private String _password;
        private Date _registerTime;
        private String _role;
        private Boolean _isEnabled;

        public UserBuilder setEmail(String email) {
            this._email = email;
            return this;
        }

        public UserBuilder setUsername(String username) {
            this._username = username;
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

        public UserBuilder role(String role) {
            this._role = role;
            return this;
        }

        public UserBuilder isEnabled(Boolean isEnabled) {
            this._isEnabled = isEnabled;
            return this;
        }

        public UserEntity build() {
            return new UserEntity(_email, _username, _password, _registerTime, _role, _isEnabled);
        }

    }
}
