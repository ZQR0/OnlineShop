package ru.os.OnlineShop.models.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CategoryUpdateRequestModel {

    @JsonProperty("name")
    private String name;
}
