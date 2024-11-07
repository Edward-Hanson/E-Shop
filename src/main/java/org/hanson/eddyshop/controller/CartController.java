package org.hanson.eddyshop.controller;


import lombok.RequiredArgsConstructor;
import org.hanson.eddyshop.model.Cart;
import org.hanson.eddyshop.service.cart.CartService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import java.math.BigDecimal;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/cart")
public class CartController {
    private final CartService cartService;

    @ResponseStatus(OK)
    @GetMapping("my-cart")
    public Cart getCart(){
        return cartService.getCart();
    }

    @ResponseStatus(NO_CONTENT)
    @DeleteMapping("clear")
    public String clearCart(){
        return cartService.clearCart();
    }

    @GetMapping("total-amount")
    @ResponseStatus(OK)
    public BigDecimal getTotalAmount() {
        return cartService.getTotalAmount();
    }
}
