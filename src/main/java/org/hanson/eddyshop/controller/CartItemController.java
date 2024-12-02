package org.hanson.eddyshop.controller;

import lombok.RequiredArgsConstructor;
import org.hanson.eddyshop.service.cartItem.CartItemService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("cartItems")
public class CartItemController {

    private final CartItemService cartItemService;


    @PostMapping("add")
    @ResponseStatus(CREATED)
    public String addItemToCart(@RequestParam Long productId, @RequestParam int quantity) {
        return cartItemService.addItemToCart(productId, quantity);
    }

    @DeleteMapping("{productId}/remove")
    @ResponseStatus(NO_CONTENT)
    public void removeItemFromCart(@PathVariable Long productId) {
        cartItemService.removeItemFromCart(productId);
    }

    @PatchMapping("{productId}/update-quantity")
    @ResponseStatus(OK)
    public String updateItemQuantity(@PathVariable Long productId, @RequestParam int quantity) {
        return cartItemService.updateItemQuantity(productId, quantity);
    }
}
