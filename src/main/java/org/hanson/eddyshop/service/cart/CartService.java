package org.hanson.eddyshop.service.cart;

import org.hanson.eddyshop.model.Cart;
import java.math.BigDecimal;

public interface CartService {
    Cart getCart();
    String clearCart();
    BigDecimal getTotalAmount();
}
