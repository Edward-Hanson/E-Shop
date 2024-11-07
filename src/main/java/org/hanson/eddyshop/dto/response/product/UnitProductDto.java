package org.hanson.eddyshop.dto.response.product;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hanson.eddyshop.dto.response.image.ProductImageDto;
import org.hanson.eddyshop.model.Category;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UnitProductDto {
    private Long id;
    private String name;
    private String brand;
    private String description;
    private BigDecimal price;
    private int inventory;
    private LocalDateTime createdAt;
    private Category category;
    List<ProductImageDto> images;
}
