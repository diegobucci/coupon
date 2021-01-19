package com.ml.coupon.web.rest.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CalculateCouponRequest {

    @ApiModelProperty(position = 0, required = true, notes = "Favorite Item List", example = "[\"MLA811601010\", \"MLA816019440\", \"MLA810645375\", \"MLA805330648\", \"MLA844702264\"]")
    @NotNull
    private List<String> item_ids;
    @ApiModelProperty(position = 1, required = true, notes = "Maximum amount to spent", example = "2000")
    @NotNull
    private Float amount;
}
