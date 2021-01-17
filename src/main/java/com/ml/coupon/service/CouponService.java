package com.ml.coupon.service;

import com.ml.coupon.service.util.ListUtil;
import com.ml.coupon.web.api.CouponApiDelegate;
import com.ml.coupon.web.api.model.CalculateRequest;
import com.ml.coupon.web.api.model.CalculateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CouponService implements CouponApiDelegate {

    @Autowired
    private ItemService itemService;

    @Autowired
    private ListUtil<String> listUtil;

    @Override
    public ResponseEntity<CalculateResponse> calculate(CalculateRequest calculateRequest) {
        List<String> itemIds = listUtil.distinct(calculateRequest.getItemIds());
        Map<String,Float> itemsPrice = itemService.getItemPrice(itemIds);
        return ResponseEntity.ok(new CalculateResponse());
    }
}
