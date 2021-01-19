package com.ml.coupon;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.ml.coupon.domain.Coupon;
import com.ml.coupon.exception.CouponNotFoundException;
import com.ml.coupon.exception.InvalidItemCodeException;
import com.ml.coupon.service.CouponService;
import com.ml.coupon.web.rest.CouponController;
import com.ml.coupon.web.rest.dto.CalculateCouponRequest;
import com.ml.coupon.web.rest.dto.CalculateCouponResponse;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test of CouponController class
 * All dependencies have been mocked
 */
@RunWith(SpringRunner.class)
@WebMvcTest(value = CouponController.class)
public class CouponControllerTests {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CouponService couponService;

    private static final String MLA1 = "MLA811601010";
    private static final String MLA2 = "MLA816019440";
    private static final String MLA3 = "MLA805330648";
    private static final String MLA4 = "MLA844702264";
    private static final String INVALID_ITEM = "INVALID_ITEM_CODE";

    private static final Float PRICE_MLA1 = Float.valueOf("17499");
    private static final Float PRICE_MLA2 = Float.valueOf("116352.03");
    private static final Float PRICE_MLA3 = Float.valueOf("14899");
    private static final Float PRICE_MLA4 = Float.valueOf("1000");

    private static final String COUPON_API_URI = "/api/coupon";

    private static final ObjectMapper mapper = createObjectMapper();

    private static final Float ZERO = Float.valueOf(0);

    private static final List<String> NO_ITEMS = new ArrayList<>();

    private static ObjectMapper createObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        mapper.registerModule(new JavaTimeModule());
        return mapper;
    }

    private static byte[] convertObjectToJsonBytes(Object object)
            throws IOException {
        return mapper.writeValueAsBytes(object);
    }

    @Test
    @SneakyThrows
    public void amountIsNotEnough() {
        doThrow(new CouponNotFoundException()).when(couponService).calculateCoupon(any(),any());

        CalculateCouponRequest calculateCouponRequest = CalculateCouponRequest.builder()
                .item_ids(Arrays.asList(MLA1))
                .amount(ZERO)
                .build();

        RequestBuilder request = MockMvcRequestBuilders.post(COUPON_API_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(calculateCouponRequest));

        MvcResult result = mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(request().asyncStarted())
                .andReturn();

        assertEquals(ResponseStatusException.class, result.getAsyncResult().getClass());
        ResponseStatusException rse = (ResponseStatusException)result.getAsyncResult();
        assertEquals(HttpStatus.NOT_FOUND, rse.getStatus());
    }


    @Test
    @SneakyThrows
    public void couponFound() {

        Coupon coupon = Coupon.builder().itemsIds(Arrays.asList(MLA1)).total(PRICE_MLA1).build();
        doReturn(coupon).when(couponService).calculateCoupon(any(),any());

        CalculateCouponRequest calculateCouponRequest = CalculateCouponRequest.builder()
                .item_ids(Arrays.asList(MLA1))
                .amount(PRICE_MLA1)
                .build();

        RequestBuilder request = MockMvcRequestBuilders.post(COUPON_API_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(calculateCouponRequest));

        MvcResult result = mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(request().asyncStarted())
                .andReturn();

        CalculateCouponResponse calculateCouponResponse = CalculateCouponResponse.builder()
                .item_ids(calculateCouponRequest.getItem_ids())
                .total(calculateCouponRequest.getAmount())
                .build();
        assertEquals(calculateCouponResponse.getItem_ids(), ((CalculateCouponResponse)result.getAsyncResult()).getItem_ids());
        assertEquals(calculateCouponResponse.getTotal(), ((CalculateCouponResponse)result.getAsyncResult()).getTotal());
    }

    @Test
    @SneakyThrows
    public void amountIsMissing() {

        CalculateCouponRequest calculateCouponRequest = CalculateCouponRequest.builder()
                .item_ids(Arrays.asList(MLA1))
                .amount(null)
                .build();

        RequestBuilder request = MockMvcRequestBuilders.post(COUPON_API_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(calculateCouponRequest));

        MvcResult result = mvc.perform(request)
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    @SneakyThrows
    public void itemIdsIsMissing() {

        CalculateCouponRequest calculateCouponRequest = CalculateCouponRequest.builder()
                .item_ids(null)
                .amount(PRICE_MLA1)
                .build();

        RequestBuilder request = MockMvcRequestBuilders.post(COUPON_API_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(calculateCouponRequest));

        MvcResult result = mvc.perform(request)
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    @SneakyThrows
    public void itemIsInvalid() {

        doThrow(new InvalidItemCodeException(INVALID_ITEM)).when(couponService).calculateCoupon(any(),any());

        CalculateCouponRequest calculateCouponRequest = CalculateCouponRequest.builder()
                .item_ids(Arrays.asList(INVALID_ITEM))
                .amount(PRICE_MLA1)
                .build();

        RequestBuilder request = MockMvcRequestBuilders.post(COUPON_API_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(calculateCouponRequest));

        MvcResult result = mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(request().asyncStarted())
                .andReturn();

        assertEquals(ResponseStatusException.class, result.getAsyncResult().getClass());
        ResponseStatusException rse = (ResponseStatusException)result.getAsyncResult();
        assertEquals(HttpStatus.BAD_REQUEST, rse.getStatus());
    }
}
