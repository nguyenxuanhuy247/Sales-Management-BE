package com.project.salesmanagement.responses.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.salesmanagement.models.Product;
import com.project.salesmanagement.models.ProductImage;
import com.project.salesmanagement.responses.BaseResponse;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@com.fasterxml.jackson.annotation.JsonIgnoreProperties(ignoreUnknown = true)
public class ProductResponse extends BaseResponse {
    private Long id;
    private String name;
    private Float price;
    private String thumbnail;
    private String description;
    private int totalPages;

    @JsonProperty("product_images")
    private List<ProductImage> productImages = new ArrayList<>();

    @JsonProperty("category_id")
    private Long categoryId;

    /**
     * @usageNotes Chuyển đổi dữu liệu từ đối tượng Product sang ProductResponse
     * @params product - đối tượng Product cần chuyển đổi.
     * @returns ProductResponse - đối tượng phản hồi đã chuyển đổi.
     */
    public static ProductResponse fromProduct(Product product) {
        /** builder giúp khởi tạo đối tượng bằng cách chỉ định từng trường mong muốn mà không cần
         * constructor dài hoặc nhiều setter. */
        ProductResponse productResponse = ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .thumbnail(product.getThumbnail())
                .description(product.getDescription())
                .categoryId(product.getCategory().getId())
                .productImages(product.getProductImages())
                .totalPages(0)
                .build();

        productResponse.setCreatedAt(product.getCreatedAt());
        productResponse.setUpdatedAt(product.getUpdatedAt());
        return productResponse;
    }
}
