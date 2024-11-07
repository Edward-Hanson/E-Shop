package org.hanson.eddyshop.dto.request.category;

import jakarta.validation.constraints.Pattern;

public record AddCategory(
        @Pattern(regexp = "^[A-Za-z]+$")
        String name
) {
}
