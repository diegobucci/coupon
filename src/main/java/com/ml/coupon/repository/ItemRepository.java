package com.ml.coupon.repository;

import com.ml.coupon.domain.Item;

import java.util.Optional;

public interface ItemRepository {

    Optional<Item> findByCode(String code);

}
