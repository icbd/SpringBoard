package org.springboard.service;

import lombok.RequiredArgsConstructor;
import org.springboard.entity.Product;
import org.springboard.entity.User;
import org.springboard.mapper.ProductMapper;
import org.springboard.repository.ProductRepository;
import org.springboard.vo.CreateProductVo;
import org.springboard.vo.UpdateProductVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public Product getProductById(Long id) {
        return productRepository.getOne(id);
    }

    public Product getProductByUuid(UUID uuid) {
        Product product = productRepository.getByUuid(uuid);
        if (product == null) {
            throw new EntityNotFoundException();
        }
        return product;
    }

    public Page<Product> findAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    public Product createProduct(CreateProductVo vo, User creator) {
        Product product = productMapper.createProduct(vo);
        product.setCreator(creator);
        return productRepository.save(product);
    }

    public void updateProduct(Product product, UpdateProductVo vo) {
        productMapper.mergeProduct(product, vo);
        productRepository.save(product);
    }

    public void destroyProduct(Long id) {
        productRepository.deleteById(id);
    }

    public Page<Product> findProductPageByCreator(User creator, Pageable pageable) {
        return productRepository.findByCreator(creator, pageable);
    }
}
