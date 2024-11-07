package org.hanson.eddyshop.dto.response.user;

import lombok.Data;
import org.hanson.eddyshop.dto.response.cart.CartDto;
import org.hanson.eddyshop.dto.response.order.OrderDto;
import java.util.List;

@Data
public class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private List<OrderDto> orders;
    private CartDto cart;
}
