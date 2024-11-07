package org.hanson.eddyshop.service.product;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hanson.eddyshop.constants.ErrorConstant;
import org.hanson.eddyshop.constants.SuccessConstant;
import org.hanson.eddyshop.dto.request.product.AddProduct;
import org.hanson.eddyshop.dto.request.product.UpdateProduct;
import org.hanson.eddyshop.dto.response.image.ProductImageDto;
import org.hanson.eddyshop.dto.response.product.UnitProductDto;
import org.hanson.eddyshop.exception.customizedExceptions.CategoryRelatedException;
import org.hanson.eddyshop.exception.customizedExceptions.ProductRelatedExceptions;
import org.hanson.eddyshop.mapper.UniversalMapper;
import org.hanson.eddyshop.model.Category;
import org.hanson.eddyshop.model.Product;
import org.hanson.eddyshop.repository.CategoryRepository;
import org.hanson.eddyshop.repository.ImageRepository;
import org.hanson.eddyshop.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImp implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ImageRepository imageRepository;
    private final ModelMapper mapper;

    @Override
    public String addProduct(AddProduct request) {

        if (productExists(request.name(),request.brand())){
            throw new ProductRelatedExceptions(ErrorConstant.PRODUCT_ALREADY_EXISTS);
        }
        Category category = findCategoryById(request.categoryId());
        Product product = UniversalMapper.mapToProduct(request);

        product.setCategory(category);
        productRepository.save(product);
        return SuccessConstant.CREATED;
    }

    private Category findCategoryById(Long id) {
        return categoryRepository.findById(id).orElseThrow(
                () -> new CategoryRelatedException("Category not found")
        );
    }


    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    @Transactional
    public UnitProductDto getProductById(Long id) {
        Product product = findProductById(id);
        UnitProductDto unitProductDto = mapper.map(product, UnitProductDto.class);
        List<ProductImageDto> productImages = getProductImages(id);
        unitProductDto.setImages(productImages);
        return unitProductDto;
    }

    public Product findProductById(Long id) {
        return productRepository.findById(id).orElseThrow(
                () -> new ProductRelatedExceptions(ErrorConstant.PRODUCT_NOT_FOUND));
    }

    @Override
    @Transactional
    public String deleteProductById(Long id) {
        Product product = findProductById(id);
        productRepository.delete(product);
        return SuccessConstant.DELETED;
    }

    @Override
    public String updateProduct(UpdateProduct request, Long productId) {
        Category category = findCategoryById(productId);
        Product product = findProductById(productId);
        editProduct(request, product, category);
        return SuccessConstant.UPDATED;
    }

    private void editProduct(UpdateProduct request, Product product, Category category) {
        try {
            product.setName(request.name());
            product.setDescription(request.description());
            product.setPrice(request.price());
            product.setCategory(category);
            product.setBrand(request.brand());
            productRepository.save(product);
        }
        catch(Exception e) {
            throw new ProductRelatedExceptions(e.getMessage());
        }
    }

    @Override
    public List<Product> getProductByCategory(String category) {
        return productRepository.findByCategoryName(category);
    }

    @Override
    public List<Product> getProductByBrand(String brand) {
        return productRepository.findByBrand(brand);
    }

    @Override
    public List<Product> getProductByCategoryAndBrand(String category, String brand) {
        return productRepository.findByCategoryNameAndBrand(category,brand);
    }

    @Override
    public List<Product> getProductByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public List<Product> getProductsByBrandAndName(String brand, String name) {
        return productRepository.findByBrandAndName(brand, name);
    }

    @Override
    public Long countProductByBrandAndName(String brand, String name) {
        return productRepository.countByBrandAndName(brand,name);
    }

    private List<ProductImageDto> getProductImages(Long productId){
        return imageRepository.findByProductId(productId);
    }

    private boolean productExists(String name, String brand){
        return productRepository.existsByNameAndBrand(name,brand);
    }
}
