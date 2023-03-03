package ru.os.OnlineShop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
@Builder
public class CategoryUpdateDto extends AbstractDto {

    @JsonProperty("name")
    private String name;

}
