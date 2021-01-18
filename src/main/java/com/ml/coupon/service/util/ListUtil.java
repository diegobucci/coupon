package com.ml.coupon.service.util;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ListUtil<T> {

    public List<T> distinct(List<T> list) {
        return list.stream().collect(Collectors.toList());
    }
}
