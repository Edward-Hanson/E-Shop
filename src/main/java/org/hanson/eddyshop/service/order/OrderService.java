package org.hanson.eddyshop.service.order;

import org.hanson.eddyshop.dto.response.order.OrderDto;
import org.hanson.eddyshop.model.Order;

import java.util.List;

public interface OrderService {
    OrderDto placeOrder(Long userId);
    OrderDto getOrder(Long orderId);
    List<OrderDto> getUserOrders(Long userId);
}
