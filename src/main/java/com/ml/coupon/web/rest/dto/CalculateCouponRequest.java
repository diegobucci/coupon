package com.ml.coupon.web.rest.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class CalculateCouponRequest {

    @ApiModelProperty(position = 0, required = true, notes = "Favorite Item List", example = "[\"MLA811601010\", \"MLA816019440\", \"MLA810645375\", \"MLA805330648\", \"MLA844702264\"]")
    private List<String> itemIds;
    @ApiModelProperty(position = 1, required = true, notes = "Maximum amount to spent", example = "2000")
    private Float amount;
}
