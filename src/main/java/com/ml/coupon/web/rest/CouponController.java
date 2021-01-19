package com.ml.coupon.web.rest;

import com.ml.coupon.domain.Coupon;
import com.ml.coupon.exception.CouponNotFoundException;
import com.ml.coupon.exception.InvalidItemCodeException;
import com.ml.coupon.service.CouponService;
import com.ml.coupon.web.rest.dto.CalculateCouponRequest;
import com.ml.coupon.web.rest.dto.CalculateCouponResponse;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.concurrent.CompletableFuture;

@Controller
@RequestMapping("/api")
@Api(value = "coupon")
@Slf4j
public class CouponController {

    @Autowired
    private CouponService couponService;

    @ApiOperation(
            value = "Calculates the list of items that maximizes the amount to spend",
            nickname = "calculate",
            notes = "Given a maximum amount, calculate list of items that maximizes the total spent without exceeding it <br> " +
                    "Considerations: <br>   " +
                    "- It is only possible to buy one unit per item_id <br>   " +
                    "- There is no preference in the total amount of items as long as they spend the maximum possible <br>   " +
                    "- The price can contain up to 2 decimal places",
            response = CalculateCouponResponse.class, tags = {})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK - Returns the list of calculated items", response = CalculateCouponResponse.class),
            @ApiResponse(code = 404, message = "The item list cannot be calculated")})
    @PostMapping(value = "/coupon",
            produces = {"application/json"},
            consumes = {"application/json"})
    public @ResponseBody
    CompletableFuture<CalculateCouponResponse> calculateCoupon(
            @ApiParam(value = "", required = true) @Valid @RequestBody CalculateCouponRequest calculateCouponRequest) {
        log.info("Calculate coupon request: {}",calculateCouponRequest);
        return CompletableFuture.supplyAsync(() -> {
            try {
                Coupon coupon = couponService.calculateCoupon(
                        calculateCouponRequest.getItem_ids(),
                        calculateCouponRequest.getAmount());
                CalculateCouponResponse calculateCouponResponse = CalculateCouponResponse.builder()
                        .item_ids(coupon.itemsIds())
                        .total(coupon.total())
                        .build();
                log.info("Calculate coupon response: {}",calculateCouponResponse);
                return calculateCouponResponse;
            } catch (CouponNotFoundException cnfe) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, cnfe.getMessage(), cnfe);
            } catch (InvalidItemCodeException iice) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, iice.getMessage(), iice);
            }
        });
    }
}
