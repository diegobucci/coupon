package com.ml.coupon.service;

import com.ml.coupon.domain.Coupon;

import java.util.List;

public interface CouponService {

    /**
     * Given a maximum amount and a item list, calculate a coupon that contains a list of items
     * that maximizes the total spent without exceeding it and the total amount spent
     *
     * @param items  list with the item's code
     * @param amount maximun amount
     * @return Coupon with the list of items that maximize the amount to spend
     */
    Coupon calculateCoupon(List<String> items, Float amount);
}
