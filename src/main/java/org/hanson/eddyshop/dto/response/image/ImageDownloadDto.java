package org.hanson.eddyshop.dto.response.image;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.core.io.ByteArrayResource;

@Data
@AllArgsConstructor
@Builder
public class ImageDownloadDto {
    private ByteArrayResource resource;
    private String fileType;
    private String fileName;
}
