package org.hanson.eddyshop.dto.response.image;

public record ProductImageDto (
        Long id,
        String fileName,
        String downloadUrl){
}
