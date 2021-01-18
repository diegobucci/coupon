package com.ml.coupon.service.impl;

import com.ml.coupon.exception.InvalidItemCodeException;
import com.ml.coupon.repository.ItemRepository;
import com.ml.coupon.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Override
    public Float getItemPrice(String itemCode) throws InvalidItemCodeException {
        return itemRepository
                .findByCode(itemCode)
                .orElseThrow(() -> new InvalidItemCodeException(itemCode))
                .getPrice();
    }
}
