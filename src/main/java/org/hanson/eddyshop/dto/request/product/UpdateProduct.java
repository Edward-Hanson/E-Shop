package org.hanson.eddyshop.dto.request.product;

import java.math.BigDecimal;

public record UpdateProduct(
        String name,
        String brand,
        String description,
        BigDecimal price,
        int inventory,
        Long categoryId

) {
}
