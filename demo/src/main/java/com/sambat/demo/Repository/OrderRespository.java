package com.sambat.demo.Repository;

import com.sambat.demo.Entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRespository extends JpaRepository<OrderEntity, Long> {
}
