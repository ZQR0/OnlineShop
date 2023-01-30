package ru.os.OnlineShop.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;


@Component
@PropertySource("classpath:urls.properties")
public class URLAddressesContainer {

    @Value(value = "${url.api.default}")
    public String defaultAPI_URL;

    @Value(value = "${url.api.admin.default}")
    public String defaultAdminURL;

    @Value(value = "${url.api.advanced}")
    public String adminAdvanced_URL;

    @Value(value = "${url.api.advanced}")
    public String advanced_API_URl;

    @Value(value = "${url.api.auth.signIn}")
    public String signInAuthUrl;
}
