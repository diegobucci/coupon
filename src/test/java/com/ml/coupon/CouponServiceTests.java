package com.ml.coupon;

import com.ml.coupon.domain.Coupon;
import com.ml.coupon.exception.CouponNotFoundException;
import com.ml.coupon.exception.InvalidItemCodeException;
import com.ml.coupon.service.CouponService;
import com.ml.coupon.service.impl.ItemServiceImpl;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

/**
 * Unit tests for CouponApplication
 *
 */
@SpringBootTest
class CouponServiceTests {

    @Autowired
    private CacheManager cacheManager;

    @SpyBean
    private ItemServiceImpl itemService;

    @Autowired
    private CouponService couponService;

    private static final String MLA1 = "MLA811601010";
    private static final String MLA2 = "MLA816019440";
    private static final String MLA3 = "MLA805330648";
    private static final String MLA4 = "MLA844702264";
    private static final String INVALID_ITEM = "INVALID";

    private static final Float PRICE_MLA1 = Float.valueOf("17499");
    private static final Float PRICE_MLA2 = Float.valueOf("116352.03");
    private static final Float PRICE_MLA3 = Float.valueOf("14899");
    private static final Float PRICE_MLA4 = Float.valueOf("1000");

    @Before
    public void setup() {
        when(itemService.getItemPrice(MLA1)).thenReturn(PRICE_MLA1);
        when(itemService.getItemPrice(MLA2)).thenReturn(PRICE_MLA2);
        when(itemService.getItemPrice(MLA3)).thenReturn(PRICE_MLA3);
        when(itemService.getItemPrice(MLA4)).thenReturn(PRICE_MLA4);
    }

    /**
     * Test case: The amount to spent is not enough
     */
    @Test
    public void amountIsNotEnough() {
        assertThrows(CouponNotFoundException.class,
                () -> couponService.calculateCoupon(Arrays.asList(MLA1),Float.valueOf(0)));
    }

    /**
     * Test case: There are not items given
     */
    @Test
    public void couponNotFound() {
        assertThrows(CouponNotFoundException.class,
                () -> couponService.calculateCoupon(new ArrayList<>(),PRICE_MLA2));
    }

    /**
     * Test case: The given items contains a invalid item code
     */
    @Test
    public void invalidItemCode() {
        assertThrows(InvalidItemCodeException.class,
                () -> couponService.calculateCoupon(Arrays.asList(INVALID_ITEM),PRICE_MLA2));
    }

    /**
     * Test case: The amount to spent is equal to items price
     */
    @Test
    public void amountEqualsItemPrice() {
        Coupon coupon = couponService.calculateCoupon(Arrays.asList(MLA1),PRICE_MLA1);
        assertEquals(1, coupon.itemsIds().size());
        assertEquals(MLA1, coupon.itemsIds().get(0));
        assertEquals(PRICE_MLA1, coupon.total());
    }

    /**
     * Test case: The amount to spent is greater than items price
     */
    @Test
    public void amountGreatherThanItemsPrice() {
        Coupon coupon = couponService.calculateCoupon(Arrays.asList(MLA1),PRICE_MLA1+PRICE_MLA2);
        assertEquals(1, coupon.itemsIds().size());
        assertEquals(MLA1, coupon.itemsIds().get(0));
        assertEquals(PRICE_MLA1, coupon.total());
    }
}
