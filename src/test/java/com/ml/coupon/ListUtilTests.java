package com.ml.coupon;

import com.ml.coupon.service.util.ListUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class ListUtilTests {

    @Autowired
    private ListUtil<String> listUtil;

    @Test
    public void nullList() {
        assertEquals(null,listUtil.distinct(null));
    }

    @Test
    public void distinct() {
        List<String> result = listUtil.distinct(Arrays.asList("a","a","a"));
        assertTrue(result.size()==1);
        assertTrue(result.get(0)=="a");
    }
}
