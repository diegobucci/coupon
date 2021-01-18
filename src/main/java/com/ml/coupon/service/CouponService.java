package com.ml.coupon.service;

import java.util.List;
import java.util.Map;

public interface CouponService {

    /**
     * Given a maximum amount and a item list, calculate list of items that maximizes the total spent without exceeding it
     *
     * @param items  map with the item's code as the key and the item's price as the value
     * @param amount maximun amount
     * @return item list of items that maximize the amount to spend
     */
    List<String> calculate(Map<String, Float> items, Float amount);
}
