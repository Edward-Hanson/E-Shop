package org.hanson.eddyshop.service.order;


import lombok.RequiredArgsConstructor;
import org.hanson.eddyshop.constants.ErrorConstant;
import org.hanson.eddyshop.dto.response.order.OrderDto;
import org.hanson.eddyshop.enums.OrderStatus;
import org.hanson.eddyshop.exception.customizedExceptions.OrderRelatedException;
import org.hanson.eddyshop.model.Cart;
import org.hanson.eddyshop.model.Order;
import org.hanson.eddyshop.model.OrderItem;
import org.hanson.eddyshop.model.Product;
import org.hanson.eddyshop.model.User;
import org.hanson.eddyshop.repository.OrderRepository;
import org.hanson.eddyshop.repository.ProductRepository;
import org.hanson.eddyshop.repository.UserRepository;
import org.hanson.eddyshop.service.cart.CartService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImp implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final CartService cartService;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;


    @Override
    public OrderDto placeOrder(Long userId) {
        User user = getUser(userId);
        Cart cart = user.getCart();

        Order order = createOrder(cart);
        order.setTotalAmount(cart.getTotalAmount());

        Set<OrderItem> orderItems = createOrderItems(order, cart);
        order.setOrderItems(orderItems);

        Order savedOrder = orderRepository.save(order);
        cartService.clearCart();
        return convertToOrderDTO(savedOrder);
    }

    private User getUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new OrderRelatedException(ErrorConstant.USER_NOT_FOUND));
    }

    @Override
    public OrderDto getOrder(Long orderId) {
        return orderRepository.findById(orderId)
                .map(this::convertToOrderDTO)
                .orElseThrow(()-> new OrderRelatedException(ErrorConstant.ORDER_NOT_FOUND));
    }

    private Order createOrder(Cart cart){
        Order order = new Order();
        order.setUser(cart.getUser());
        order.setOrderStatus(OrderStatus.PENDING);
        order.setOrderDate(LocalDate.now());
        return order;
    }

    private Set<OrderItem> createOrderItems(Order order, Cart cart){
        return cart.getCartItems().stream().map(cartItem -> {
            Product product = cartItem.getProduct();
            product.setInventory(product.getInventory()-cartItem.getQuantity());
            productRepository.save(product);
            return new OrderItem(
                    order,
                    product,
                    cartItem.getQuantity(),
                    cartItem.getUnitPrice());
        }).collect(Collectors.toSet());
    }

    private BigDecimal calculateTotalAmount(List<OrderItem> orderItems) {
        return orderItems.stream().map(item-> item.getPrice().multiply(new BigDecimal(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

    }

    @Override
    public List<OrderDto> getUserOrders(Long userId) {
        getUser(userId);
        List<Order> orders = orderRepository.findByUserId(userId);
        return orders.stream().map(this::convertToOrderDTO).toList();
    }

    private OrderDto convertToOrderDTO(Order order){
        return modelMapper.map(order, OrderDto.class);
    }
}
