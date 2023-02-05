package ru.os.OnlineShop.services.interfaces;

import org.springframework.security.core.userdetails.UserDetails;
import ru.os.OnlineShop.dto.AuthDTO;
import ru.os.OnlineShop.exceptions.AuthenticationFailedException;

public interface AuthServiceInterface {
    UserDetails signIn(AuthDTO dto) throws AuthenticationFailedException;
    void signOut();
}
