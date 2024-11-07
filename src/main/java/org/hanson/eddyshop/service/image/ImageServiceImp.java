package org.hanson.eddyshop.service.image;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hanson.eddyshop.constants.ErrorConstant;
import org.hanson.eddyshop.constants.SuccessConstant;
import org.hanson.eddyshop.dto.response.image.ImageDownloadDto;
import org.hanson.eddyshop.exception.customizedExceptions.ImageRelatedException;
import org.hanson.eddyshop.exception.customizedExceptions.ProductRelatedExceptions;
import org.hanson.eddyshop.model.Image;
import org.hanson.eddyshop.model.Product;
import org.hanson.eddyshop.repository.ImageRepository;
import org.hanson.eddyshop.repository.ProductRepository;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


@RequiredArgsConstructor
@Service
public class ImageServiceImp implements ImageService{

    private final ImageRepository imageRepository;
    private final ProductRepository productRepository;

    @Override
    @Transactional
    public ImageDownloadDto getImageById(Long id) {
        Image image = getImage(id);
        try {
            return ImageDownloadDto.builder()
                    .fileName(image.getFileName())
                    .fileType(image.getFileType())
                    .resource(new ByteArrayResource(image.getData().getBytes(1, (int) image.getData().length())))
                .build();
        }
        catch (SQLException e) {
            throw new ImageRelatedException(e.getMessage());
        }
    }

    private Image getImage(Long id) {
        return imageRepository.findById(id).orElseThrow(
                ()-> new ImageRelatedException(ErrorConstant.IMAGE_NOT_FOUND));
    }

    @Override
    public String deleteImageById(Long id) {
      imageRepository.deleteById(id);
      return SuccessConstant.DELETED;
    }

    @Override
    public String saveImages(Long productId, List<MultipartFile> files) {
        Product product = productRepository.findById(productId).orElseThrow(
                ()-> new ProductRelatedExceptions(ErrorConstant.PRODUCT_NOT_FOUND));
        List<Image> images = new ArrayList<>();

        files.parallelStream().forEach(file -> {
            try {
                Image image = Image.builder()
                        .fileName(file.getOriginalFilename())
                        .fileType(file.getContentType())
                        .data(new SerialBlob(file.getBytes()))
                        .product(product)
                        .build();
                imageRepository.save(image);
                images.add(image);
            }
            catch (SQLException | IOException e) {
                throw new ImageRelatedException(e.getMessage());
            }
        });
        images.parallelStream().forEach(image -> image.setDownloadUrl("api/v1/images/download/"+ image.getId()));
        imageRepository.saveAll(images);
        return SuccessConstant.CREATED;
    }

    @Override
    public String updateImage(MultipartFile file, Long imageId) {
        try {
            Image image = getImage(imageId);
            image.setFileType(file.getContentType());
            image.setData(new SerialBlob(file.getBytes()));
            image.setFileName(file.getOriginalFilename());
            imageRepository.save(image);

            return SuccessConstant.UPDATED;
        }
        catch (SQLException | IOException e){
            throw new ImageRelatedException(e.getMessage());
        }

    }
}
