package org.hanson.eddyshop.dto.request.product;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.math.BigDecimal;

public record AddProduct(
        @NotNull
        @Pattern(regexp = "^[A-Za-z0-9 ,_-]{3,50}$\n")
        String name,
        @NotNull
        String brand,
        String description,
        @NotNull
        BigDecimal price,
        @NotNull
        int inventory,
        @NotNull
        Long categoryId
                         ) {
}
