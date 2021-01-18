package com.ml.coupon.service;

import com.ml.coupon.exception.InvalidItemCodeException;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public interface ItemService {

    /**
     * Get the price of a given item code.
     *
     * @param itemCode the itemcode needed to get the price
     * @return a item price
     * @throws InvalidItemCodeException
     */

    Float getItemPrice(String itemCode) throws InvalidItemCodeException;

    /**
     * Get the price of a given item code list.
     *
     * @param itemCodes item code list
     * @return a Map (ItemCode,Price) with the items price
     * @throws InvalidItemCodeException
     */
    default Map<String, Float> getItemPrice(List<String> itemCodes) throws InvalidItemCodeException {
        return itemCodes
                .stream()
                .parallel()
                .collect(Collectors.toMap(Function.identity(), itemCode -> this.getItemPrice(itemCode)));
    }
}
