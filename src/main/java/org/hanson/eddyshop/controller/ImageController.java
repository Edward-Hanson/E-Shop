package org.hanson.eddyshop.controller;


import lombok.RequiredArgsConstructor;
import org.hanson.eddyshop.dto.response.image.ImageDownloadDto;
import org.hanson.eddyshop.service.image.ImageService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.sql.SQLException;
import java.util.List;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

@RequiredArgsConstructor
@RestController
@RequestMapping("images")
public class ImageController {

    private final ImageService imageService;

    @ResponseStatus(CREATED)
    @PostMapping("/upload")
    public String saveImages(@RequestParam List<MultipartFile> files, @RequestParam Long productId) {
       return imageService.saveImages(productId, files);
    }


    @ResponseStatus(OK)
    @GetMapping("download/{imageId}")
    public ResponseEntity<Resource> downloadImage(@PathVariable Long imageId) throws SQLException {
        ImageDownloadDto image = imageService.getImageById(imageId);
        return  ResponseEntity.ok().contentType(MediaType.parseMediaType(image.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" +image.getFileName() + "\"")
                .body(image.getResource());
    }


    @ResponseStatus(OK)
    @PutMapping("/image/{imageId}/update")
    public String updateImage(@PathVariable Long imageId, @RequestBody MultipartFile file) {
       return imageService.updateImage(file, imageId);
    }


    @ResponseStatus(NO_CONTENT)
    @DeleteMapping("/image/{imageId}/delete")
    public String deleteImage(@PathVariable Long imageId) {
       return imageService.deleteImageById(imageId);
    }
}
