package com.ml.coupon.repository.impl;

import com.ml.coupon.domain.Item;
import com.ml.coupon.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Repository
public class ItemRepositoryImpl implements ItemRepository {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${ml.apps.item.api}")
    private String itemApi;

    @Cacheable(value = "itemCache")
    public Optional<Item> findByCode(String code) {
        try {
            return Optional.of(restTemplate.getForObject(itemApi + code, Item.class));
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
