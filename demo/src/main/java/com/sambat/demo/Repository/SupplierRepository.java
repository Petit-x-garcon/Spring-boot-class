package com.sambat.demo.Repository;

import com.sambat.demo.Entity.SupplierEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierRepository extends JpaRepository<SupplierEntity, Long> {
    Boolean existsByName(String name);
}
