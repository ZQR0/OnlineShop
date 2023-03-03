package ru.os.OnlineShop.models.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CategoryCreateRequestModel {

    @JsonProperty("name")
    private String name;
}
