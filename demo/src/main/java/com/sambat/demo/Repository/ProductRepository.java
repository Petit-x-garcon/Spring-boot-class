package com.sambat.demo.Repository;

import com.sambat.demo.Entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    @Query("SELECT p FROM ProductEntity p WHERE (:name IS NULL OR LOWER(p.productName) LIKE %:name%)" +
    " AND (:min IS NULL OR p.price >= :min) AND (:max IS NULL OR p.price <= :max)")
    List<ProductEntity> findByProductName(@Param("name") String name,
                                          @Param("min") Double min,
                                          @Param("max") Double max);
    Boolean existsByProductName(String name);
}
