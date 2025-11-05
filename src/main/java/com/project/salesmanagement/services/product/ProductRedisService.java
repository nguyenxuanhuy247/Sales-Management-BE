package com.project.salesmanagement.services.product;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.salesmanagement.responses.product.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductRedisService implements IProductRedisService {
    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper redisObjectMapper;
    @Value("${spring.data.redis.use-redis-cache}")
    private boolean useRedisCache;

    /**
     * @usageNotes hàm này dùng để tạo ra một chuỗi key duy nhất dựa trên các tham số truy vấn nhằm lưu và truy xuất dữ liệu từ Redis cache.
     * @params keyword : Chuôi tìm kiếm sản phẩm.
     * @params categoryId : ID của danh mục sản phẩm.
     * @params pageRequest : Thông tin phân trang bao gồm số trang, kích thước trang và hướng sắp xếp.
     * @return Trả về một chuỗi key
     */
    private String getKeyFrom(String keyword, Long categoryId, PageRequest pageRequest) {
        int pageNumber = pageRequest.getPageNumber();
        int pageSize = pageRequest.getPageSize();
        Sort sort = pageRequest.getSort();
        String sortDirection = sort.getOrderFor("updatedAt").getDirection() == Sort.Direction.ASC ? "asc" : "desc";
        String key = String.format("all_products:%s:%d:%d:%d:%s", keyword, categoryId, pageNumber, pageSize, sortDirection);
        return key;
    }

    @Override
    public List<ProductResponse> getAllProducts(String keyword,
                                                Long categoryId,
                                                PageRequest pageRequest
    /** JsonProcessingException được sử dụng vì phương thức readValue và writeValueAsString của ObjectMapper
     * có thể phát sinh lỗi khi chuyển đổi giữa JSON và Java object. Exception này giúp bắt và xử lý
     * các lỗi liên quan đến việc parse hoặc serialize dữ liệu JSON. */
    ) throws JsonProcessingException {
        if (!useRedisCache) {
            return null;
        }
        String key = this.getKeyFrom(keyword, categoryId, pageRequest);
        String json = (String) redisTemplate.opsForValue().get(key);
        List<ProductResponse> productResponses = json != null ? redisObjectMapper.readValue(json, new TypeReference<List<ProductResponse>>() {
        }) : null;
        return productResponses;
    }

    @Override
    public void clear() {
        redisTemplate.getConnectionFactory().getConnection().flushAll();
    }

    @Override
    //save to Redis
    public void saveAllProducts(List<ProductResponse> productResponses, String keyword, Long categoryId, PageRequest pageRequest) throws JsonProcessingException {
        String key = this.getKeyFrom(keyword, categoryId, pageRequest);
        String json = redisObjectMapper.writeValueAsString(productResponses);
        redisTemplate.opsForValue().set(key, json);
    }
}
