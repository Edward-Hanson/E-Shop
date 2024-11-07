package org.hanson.eddyshop.service.cartItem;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hanson.eddyshop.constants.ErrorConstant;
import org.hanson.eddyshop.constants.SuccessConstant;
import org.hanson.eddyshop.exception.customizedExceptions.CartItemRelatedException;
import org.hanson.eddyshop.exception.customizedExceptions.ProductRelatedExceptions;
import org.hanson.eddyshop.model.Cart;
import org.hanson.eddyshop.model.CartItem;
import org.hanson.eddyshop.model.Product;
import org.hanson.eddyshop.repository.CartItemRepository;
import org.hanson.eddyshop.repository.ProductRepository;
import org.hanson.eddyshop.service.User.UserService;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CartItemServiceImp implements CartItemService{

    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final UserService userService;


    @Override
    @Transactional
    public String addItemToCart(Long productId, int quantity) {
        Cart cart = userService.getAuthenticatedUser().getCart();
        Product product = productRepository.findById(productId).orElseThrow(
                ()-> new ProductRelatedExceptions(ErrorConstant.PRODUCT_NOT_FOUND));
        CartItem cartItem = cart.getCartItems().stream().filter(item->item.getProduct().getId().equals(productId))
                .findFirst().orElse(new CartItem());
        if (cartItem.getId()==null){
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItem.setUnitPrice(product.getPrice());
        }
        else{
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        }
        cartItem.setTotalPrice();
        cartItem.getCart().setTotalAmount();
        cart.getCartItems().add(cartItem);
        cart.setTotalAmount();
        cartItemRepository.save(cartItem);

        return SuccessConstant.CREATED;
    }


    @Override
    @Transactional
    public void removeItemFromCart(Long productId) {
        Cart cart = userService.getAuthenticatedUser().getCart();
        CartItem cartItem = cart.getCartItems().stream().filter(item-> item.getProduct().getId().equals(productId))
                .findFirst().orElseThrow(()->new CartItemRelatedException(ErrorConstant.CART_ITEM_NOT_FOUND));
        cartItem.getCart().setTotalAmount();
        cartItemRepository.delete(cartItem);
    }

    @Override
    @Transactional
    public String updateItemQuantity(Long productId, int quantity) {
        Cart cart = userService.getAuthenticatedUser().getCart();
        CartItem cartItem = cart.getCartItems().stream().filter(item->item.getProduct().getId().equals(productId))
                .findFirst().orElseThrow(()->new CartItemRelatedException(ErrorConstant.CART_ITEM_NOT_FOUND));

        cartItem.setQuantity(quantity);
        cartItem.setUnitPrice(cartItem.getProduct().getPrice());
        cartItem.setTotalPrice();
        cartItem.getCart().setTotalAmount();
        cartItemRepository.save(cartItem);
        return SuccessConstant.UPDATED;
    }
}
