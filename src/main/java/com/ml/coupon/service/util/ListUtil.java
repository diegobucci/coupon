package com.ml.coupon.service.util;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ListUtil<T> {

    public List<T> distinct(List<T> list) {
        if (list==null) {
            return null;
        }
        return list.stream().distinct().collect(Collectors.toList());
    }
}
