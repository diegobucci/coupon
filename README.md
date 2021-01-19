# Coupon App

> **This application assists in the purchase of items** <br>
Calculate the list of items that maximize a maximum amount to spend

### Some considerations
- It is only possible to buy one unit per item_id
- There is no preference in the total amount of items as long as they spend the maximum possible
- The price can contain up to 2 decimal places

## Availability

This application is available on heroku cloud: http://coupon-assistant.herokuapp.com/

## Using the API

### Documentation 
For detailed API documentation, refer to Swagger API Documentation at https://coupon-assistant.herokuapp.com/v2/api-docs

### Swagger Endpoints Testing Utility
Use for testing the api: https://coupon-assistant.herokuapp.com/swagger-ui.html

### API

**POST** /api/coupon

> **Calculates the list of items that maximizes the amount to spend** <br>
>Given a maximum amount, calculate list of items that maximizes the total >spent without exceeding it <br>

#### Example 
```bash
curl -X POST "http://coupon-assistant.herokuapp.com/api/coupon" -H  "accept: application/json" -H  "Content-Type: application/json" -d "{\"item_ids\":[\"MLA810645375\",\"MLA805330648\",\"MLA844702264\"],\"amount\":2000}"
```

### Technical Details
- Used technology
    - SpringBoot
        - Cache
        - Async
        - Rest Template
    - Swagger
    - Lombok
    - Jacoco
    - Heroku
    - ~~OpenApi~~
- 85% Code Coverage 
    - ![](/code-coverage.png)


    

 
