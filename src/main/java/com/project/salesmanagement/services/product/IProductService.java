package com.project.salesmanagement.services.product;

import com.project.salesmanagement.dtos.ProductDTO;
import com.project.salesmanagement.dtos.ProductImageDTO;
import com.project.salesmanagement.models.Product;
import com.project.salesmanagement.models.ProductImage;
import com.project.salesmanagement.responses.product.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface IProductService {
    Product createProduct(ProductDTO productDTO) throws Exception;

    Product getProductById(long id) throws Exception;

    Page<ProductResponse> getAllProducts(String keyword, Long categoryId, PageRequest pageRequest);

    Product updateProduct(long id, ProductDTO productDTO) throws Exception;

    void deleteProduct(long id) throws com.project.salesmanagement.exceptions.DataNotFoundException, com.project.salesmanagement.exceptions.InvalidParamException;

    ProductImage createProductImage(
            Long productId,
            ProductImageDTO productImageDTO) throws Exception;

    List<Product> findProductsByIds(List<Long> productIds);
}
