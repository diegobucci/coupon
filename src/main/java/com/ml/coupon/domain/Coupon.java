package com.ml.coupon.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.List;

@Getter
@Setter
@ToString
@Accessors(chain = true, fluent = true)
@Builder
public class Coupon {

    private List<String> itemsIds;
    private Float total;
}
