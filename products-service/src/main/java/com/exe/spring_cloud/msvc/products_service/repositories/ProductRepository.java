package com.exe.spring_cloud.msvc.products_service.repositories;

import com.exe.spring_cloud.msvc.libs_common_service.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
