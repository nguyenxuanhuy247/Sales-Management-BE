package com.project.salesmanagement.responses.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.salesmanagement.models.Comment;
import com.project.salesmanagement.models.Favorite;
import com.project.salesmanagement.models.Product;
import com.project.salesmanagement.models.ProductImage;
import com.project.salesmanagement.responses.BaseResponse;
import com.project.salesmanagement.responses.comment.CommentResponse;
import com.project.salesmanagement.responses.favorite.FavoriteResponse;
import lombok.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponse extends BaseResponse {
    private Long id;
    private String name;
    private Float price;
    private String thumbnail;
    private String description;
    private int totalPages;

    @JsonProperty("product_images")
    private List<ProductImage> productImages = new ArrayList<>();

    @JsonProperty("comments")
    private List<CommentResponse> comments = new ArrayList<>();

    @JsonProperty("favorites")
    private List<FavoriteResponse> favorites = new ArrayList<>();

    @JsonProperty("category_id")
    private Long categoryId;

    /**
     * @usageNotes Chuyển đổi dữu liệu từ đối tượng Product sang ProductResponse
     * @params product - đối tượng Product cần chuyển đổi.
     * @returns ProductResponse - đối tượng phản hồi đã chuyển đổi.
     */
    public static ProductResponse fromProduct(Product product) {
        List<Comment> comments = product.getComments()
                /** stream() và collect(Collectors.toList()) được sử dụng để thực hiện các thao tác như sắp xếp, lọc, biến đổi,...*/
                .stream()
                .sorted(Comparator.comparing(Comment::getCreatedAt).reversed()) // Sort comments by createdAt in descending order
                .collect(Collectors.toList());

        List<Favorite> favorites = product.getFavorites();

        /** builder giúp khởi tạo đối tượng bằng cách chỉ định từng trường mong muốn mà không cần
         * constructor dài hoặc nhiều setter. */
        ProductResponse productResponse = ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .thumbnail(product.getThumbnail())
                .comments(comments.stream().map(CommentResponse::fromComment).toList()) // Collect sorted comments into a list
                .favorites(favorites.stream().map(FavoriteResponse::fromFavorite).toList())
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
