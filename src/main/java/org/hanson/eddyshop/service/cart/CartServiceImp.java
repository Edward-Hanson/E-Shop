package org.hanson.eddyshop.service.cart;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hanson.eddyshop.constants.SuccessConstant;
import org.hanson.eddyshop.model.Cart;
import org.hanson.eddyshop.repository.CartItemRepository;
import org.hanson.eddyshop.service.User.UserService;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CartServiceImp implements CartService {

    private final CartItemRepository cartItemRepository;
    private final UserService userService;

    @Override
    public Cart getCart() {
        Cart cart = userService.getAuthenticatedUser().getCart();
        cart.setTotalAmount();
        return cart;
    }

    @Override
    @Transactional
    public String clearCart() {
        Cart cart = userService.getAuthenticatedUser().getCart();
        cartItemRepository.deleteAllByCartId(cart.getId());
        cart.getCartItems().clear();
        cart.setTotalAmount();
        return SuccessConstant.DELETED;
    }

    @Override
    public BigDecimal getTotalAmount() {
        Cart cart = userService.getAuthenticatedUser().getCart();
        cart.setTotalAmount();
        return cart.getTotalAmount();
    }
}
