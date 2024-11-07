package org.hanson.eddyshop.dto.response.cart;

import lombok.Data;
import org.hanson.eddyshop.dto.response.product.UnitProductDto;

import java.math.BigDecimal;

@Data
public class CartItemDto {
    private Long id;
    private Integer quantity;
    private BigDecimal unitPrice;
    private UnitProductDto product;
}
