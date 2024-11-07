package org.hanson.eddyshop.mapper;

import org.hanson.eddyshop.dto.request.product.AddProduct;
import org.hanson.eddyshop.model.Product;

public class UniversalMapper {

    public static Product mapToProduct(AddProduct request) {
        return Product.builder()
                .brand(request.brand())
                .name(request.name())
                .price(request.price())
                .description(request.description())
                .inventory(request.inventory())
                .build();
    }
}
