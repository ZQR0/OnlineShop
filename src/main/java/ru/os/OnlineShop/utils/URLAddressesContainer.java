package ru.os.OnlineShop.utils;

import org.springframework.beans.factory.annotation.Value;

public class URLAddressesContainer {

    @Value(value = "${url.api.default}")
    public static String defaultAPI_URL;

    @Value(value = "${url.api.admin.default}")
    public static String defaultAdminURL;

    @Value(value = "${url.api.advanced}")
    public static String adminAdvanced_URL;

    @Value(value = "${url.api.advanced}")
    public static String advanced_API_URl;
}
