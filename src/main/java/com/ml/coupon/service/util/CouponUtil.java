package com.ml.coupon.service.util;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class CouponUtil {

    public List<String> distinct(List<String> list) {
        return list.stream().distinct().collect(Collectors.toList());
    }

    public Float calculateItemsPrice(Map<String,Float> itemPrices, List<String> selectedItems) {
        Double total = selectedItems.stream()
                .mapToDouble(item->itemPrices.get(item))
                .sum();
        return total.floatValue();
    }
}
