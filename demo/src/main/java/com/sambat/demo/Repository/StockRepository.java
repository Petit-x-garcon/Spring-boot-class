package com.sambat.demo.Repository;

import com.sambat.demo.Entity.StockEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockRepository extends JpaRepository<StockEntity, Long> {
    List<StockEntity> findByProductIdIn(List<Long> productId, Sort createdAt);
}
