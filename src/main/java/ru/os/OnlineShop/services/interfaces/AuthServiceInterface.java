package ru.os.OnlineShop.services.interfaces;

import ru.os.OnlineShop.controllers.models.AuthenticationResponseModel;
import ru.os.OnlineShop.dto.AuthDTO;

public interface AuthServiceInterface {
    AuthenticationResponseModel signIn(AuthDTO dto);
}
