package org.hanson.eddyshop.service.cartItem;


public interface CartItemService {
    String addItemToCart(Long productId, int quantity);
    void removeItemFromCart(Long productId);
    String updateItemQuantity(Long productId, int quantity);
}
