package com.ml.coupon.web.rest;

import com.ml.coupon.service.CouponService;
import com.ml.coupon.service.ItemService;
import com.ml.coupon.service.util.CouponUtil;
import com.ml.coupon.web.api.CouponApiDelegate;
import com.ml.coupon.web.api.model.CalculateRequest;
import com.ml.coupon.web.api.model.CalculateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CouponApiDelegateImpl implements CouponApiDelegate {

    @Autowired
    private ItemService itemService;

    @Autowired
    private CouponService couponService;

    @Autowired
    private CouponUtil couponUtil;

    @Override
    public ResponseEntity<CalculateResponse> calculate(CalculateRequest calculateRequest) {
        List<String> favoriteItemIds = couponUtil.distinct(calculateRequest.getItemIds());
        Map<String, Float> itemsPrice = itemService.getItemPrice(favoriteItemIds);
        List<String> calculatedItemIds = couponService.calculate(itemsPrice,calculateRequest.getAmount());
        return ResponseEntity.ok(
                new CalculateResponse()
                        .itemIds(calculatedItemIds)
                        .total(couponUtil.calculateItemsPrice(itemsPrice,calculatedItemIds))
        );
    }
}
