package org.hanson.eddyshop.service.product;

import org.hanson.eddyshop.dto.request.product.AddProduct;
import org.hanson.eddyshop.dto.request.product.UpdateProduct;
import org.hanson.eddyshop.dto.response.product.UnitProductDto;
import org.hanson.eddyshop.model.Product;

import java.util.List;


public interface ProductService {
    String addProduct(AddProduct product);
    List<Product> getAllProducts();
    UnitProductDto getProductById(Long id);
    String deleteProductById(Long id);
    String updateProduct(UpdateProduct request, Long productId);
    List<Product> getProductByCategory(String category);
    List<Product> getProductByBrand(String brand);
    List<Product> getProductByCategoryAndBrand(String category, String brand);
    List<Product> getProductByName(String name);
    List<Product> getProductsByBrandAndName(String brand, String name);
    Long countProductByBrandAndName(String brand, String name);
}
