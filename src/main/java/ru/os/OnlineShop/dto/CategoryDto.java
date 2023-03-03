package ru.os.OnlineShop.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Builder
@Data
@EqualsAndHashCode(callSuper = true)
public class CategoryDto extends AbstractDto {

    @JsonProperty("name")
    private String name;
}
