package com.ml.coupon.web.rest;

import com.ml.coupon.domain.Coupon;
import com.ml.coupon.service.CouponService;
import com.ml.coupon.service.util.ListUtil;
import com.ml.coupon.web.rest.dto.CalculateCouponRequest;
import com.ml.coupon.web.rest.dto.CalculateCouponResponse;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api")
@Api(value = "coupon")
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
            response = CalculateCouponResponse.class, tags={  })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK - Returns the list of calculated items", response = CalculateCouponResponse.class),
            @ApiResponse(code = 404, message = "The item list cannot be calculated") })
    @PostMapping(value = "/coupon",
                   produces = { "application/json" },
            consumes = { "application/json" })
    public CompletableFuture<ResponseEntity<CalculateCouponResponse>> calculateCoupon(
            @ApiParam(value = "", required=true) @Valid @RequestBody CalculateCouponRequest calculateCouponRequest) {
        return CompletableFuture.supplyAsync(() -> {
            Coupon coupon  = couponService.calculateCoupon(
                    calculateCouponRequest.getItemIds(),
                    calculateCouponRequest.getAmount());

            return ResponseEntity.ok(CalculateCouponResponse.builder()
                    .itemIds(coupon.itemsIds())
                    .total(coupon.total())
                    .build());
        });
    }
}
