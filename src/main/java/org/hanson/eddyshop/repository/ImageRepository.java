package org.hanson.eddyshop.repository;

import org.hanson.eddyshop.dto.response.image.ProductImageDto;
import org.hanson.eddyshop.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {
   List<ProductImageDto> findByProductId(Long productId);
}