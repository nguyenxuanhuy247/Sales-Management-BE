package com.project.salesmanagement.services.product.image;

import com.project.salesmanagement.models.ProductImage;

public interface IProductImageService {
    ProductImage deleteProductImage(Long id) throws Exception;
}
