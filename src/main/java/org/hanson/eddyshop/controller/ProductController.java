package org.hanson.eddyshop.controller;


import lombok.RequiredArgsConstructor;
import org.hanson.eddyshop.dto.request.product.AddProduct;
import org.hanson.eddyshop.dto.request.product.UpdateProduct;
import org.hanson.eddyshop.dto.response.product.UnitProductDto;
import org.hanson.eddyshop.model.Product;
import org.hanson.eddyshop.service.product.ProductService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.ACCEPTED;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;


@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/product")
public class ProductController {

    private final ProductService productService;

    @GetMapping("all")
    @ResponseStatus(OK)
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{productId}")
    @ResponseStatus(OK)
    public UnitProductDto getProduct(@PathVariable Long productId) {
        return productService.getProductById(productId);
    }

    @PostMapping("add")
    @ResponseStatus(CREATED)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String addProduct(@RequestBody AddProduct product) {
        return productService.addProduct(product);
    }

    @PatchMapping("product/{productId}/update")
    @ResponseStatus(ACCEPTED)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String updateProduct(@PathVariable Long productId, @RequestBody UpdateProduct product) {
        return productService.updateProduct(product, productId);
    }

    @DeleteMapping("product/{productId}/delete")
    @ResponseStatus(NO_CONTENT )
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteProduct(@PathVariable Long productId) {
        return productService.deleteProductById(productId);
    }

    @GetMapping("by/brand-and-name")
    @ResponseStatus(OK)
    public List<Product> getProductByBrandAndName(@RequestParam String brand, @RequestParam String name) {
        return productService.getProductsByBrandAndName(brand, name);
    }

    @GetMapping("by/category-and-brand")
    @ResponseStatus(OK)
    public List<Product> getProductByCategoryAndBrand(@RequestParam String category, @RequestParam String brand) {
        return productService.getProductByCategoryAndBrand(category, brand);
    }

    @GetMapping("by/name")
    @ResponseStatus(OK)
    public List<Product> getProductByName(@RequestParam String name) {
        return productService.getProductByName(name);
    }

    @GetMapping("by/brand")
    @ResponseStatus(OK)
    public List<Product> getProductByBrand(@RequestParam String brand) {
        return productService.getProductByBrand(brand);
    }

    @GetMapping("by/category")
    @ResponseStatus(OK)
    public List<Product> getProductByCategory(@RequestParam String category) {
        return productService.getProductByCategory(category);
    }
}
