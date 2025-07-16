package com.sambat.demo.Repository;

import com.sambat.demo.Entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    @Query("SELECT p FROM ProductEntity p WHERE :name IS NULL OR LOWER(p.name) LIKE %LOWER(:name)%")
    ProductEntity findByProductName(String name);
}
