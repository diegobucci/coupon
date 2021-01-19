package com.ml.coupon;

import com.ml.coupon.config.CacheEvictor;
import com.ml.coupon.domain.Coupon;
import com.ml.coupon.repository.impl.ItemRepositoryImpl;
import com.ml.coupon.service.CouponService;
import com.ml.coupon.service.impl.ItemServiceImpl;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.cache.CacheManager;

import java.util.Arrays;

import static org.mockito.Mockito.when;

@SpringBootTest
public class CacheTests {

    @Autowired
    private CouponService couponService;

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private CacheEvictor cacheEvictor;

    @SpyBean
    private ItemServiceImpl itemService;

    private static final String MLA1 = "MLA811601010";
    private static final Float PRICE_MLA1 = Float.valueOf("17499");

    @Before
    public void setup() {
        when(itemService.getItemPrice(MLA1)).thenReturn(PRICE_MLA1);
    }

    @Test
    public void cacheIsUsed() {
        Coupon coupon = couponService.calculateCoupon(Arrays.asList(MLA1),PRICE_MLA1);
        assert(cacheManager.getCache("itemCache").get(MLA1)!=null);

        cacheEvictor.evictsAllCache();
        assert(cacheManager.getCache("itemCache").get(MLA1)==null);
    }
}
