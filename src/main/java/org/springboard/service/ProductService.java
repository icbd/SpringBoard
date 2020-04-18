package org.springboard.service;

import lombok.RequiredArgsConstructor;
import org.springboard.entity.Product;
import org.springboard.entity.Role;
import org.springboard.entity.User;
import org.springboard.mapper.ProductMapper;
import org.springboard.repository.ProductRepository;
import org.springboard.vo.CreateProductVo;
import org.springboard.vo.UpdateProductVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.UUID;

import static org.springboard.constant.RolePackageEnum.ADMINISTRATOR;

@RequiredArgsConstructor
@Service
@Transactional
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final RolePackageService rolePackageService;
    private final RoleAndUserService roleAndUserService;

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
        productRepository.save(product);
        initRolePackageByADMINISTRATOR(product, creator);
        return product;
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

    private void initRolePackageByADMINISTRATOR(Product product, User user) {
        Role role = rolePackageService
                .createRolePackage(product.getClass().getSimpleName(), product.getId(), ADMINISTRATOR, user);
        roleAndUserService.bindRoleWithUser(role, user);
    }
}
