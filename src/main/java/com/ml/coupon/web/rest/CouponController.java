package com.ml.coupon.web.rest;

import com.ml.coupon.web.rest.error.ApiError;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
@Slf4j
public class CouponController {

    /**
     * Given a maximum amount, calculate list of items that maximizes the total spent without exceeding it.
     * <p>
     * Considerations:
     * - It is only possible to buy one unit per item_id
     * - There is no preference in the total amount of items as long as they spend the maximum possible
     * - The price can contain up to 2 decimal places
     * <p>
     * Response with HTTP status code 200 if the list of items could be calculated
     * Response with HTTP status code 404, in case the amount given is not enough to minimally buy an item
     *
     * @param items  Favorite items list
     * @param amount Maximum amount to spend
     * @return List of items that maximize the amount to spend
     */
    @ApiOperation(value= "calculate", notes = "calculates the list of items that maximizes the amount to spend", response = String.class, responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK - Returns the list of calculated items", response = String.class, responseContainer = "List"),
            @ApiResponse(code = 400, message = "Bad Request", response = ApiError.class),
            @ApiResponse(code = 404, message = "The item list cannot be calculated")})
    @ResponseStatus(HttpStatus.OK)
    @PostMapping(value = "/coupon")
    public List<String> calculate(
            @RequestParam("items") @ApiParam(value="Favorite item list", required=true) List<String> items,
            @RequestParam("amount") @ApiParam(value="Maximun amount to spent", required=true) Float amount) {
        log.debug("Rest request to calculate: items {} amount {}", items, amount);
        return items;
    }
}