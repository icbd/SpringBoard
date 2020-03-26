package org.springboard.repository;

import org.springboard.entity.Product;
import org.springboard.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Product getByUuid(String uuid);

    Page<Product> findByCreator(User creator, Pageable pageable);
}
