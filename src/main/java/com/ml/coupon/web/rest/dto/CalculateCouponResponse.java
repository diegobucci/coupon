package com.ml.coupon.web.rest.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;

@Getter
@ToString
@Builder
public class CalculateCouponResponse {

    @ApiModelProperty(position = 0, notes = "Items that maximize the amount to spend", example = "[\"MLA844702264\"]")
    private List<String> item_ids;
    @ApiModelProperty(position = 1, notes = "Total price of calculated items", example = "1000")
    private Float total;
}
