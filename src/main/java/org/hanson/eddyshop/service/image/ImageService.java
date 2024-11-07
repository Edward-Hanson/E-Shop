package org.hanson.eddyshop.service.image;

import org.hanson.eddyshop.dto.response.image.ImageDownloadDto;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

public interface ImageService {
    ImageDownloadDto getImageById(Long id);
    String deleteImageById(Long id);
    String saveImages(Long productId, List<MultipartFile> files);
    String updateImage(MultipartFile file,  Long imageId);
}
