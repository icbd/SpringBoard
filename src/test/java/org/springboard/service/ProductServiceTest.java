package org.springboard.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springboard.entity.Product;
import org.springboard.entity.User;
import org.springboard.repository.ProductRepository;
import org.springboard.repository.UserRepository;
import org.springboard.vo.CreateProductVo;
import org.springboard.vo.UpdateProductVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springboard.util.ListHelper.sample;

@DataJpaTest
@Nested
class ProductServiceTest extends ServiceTestBase {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductService productService;

    private User creator;
    private List<Product> cases = new ArrayList<>();
    private Product aCase;

    @BeforeEach
    void setUp() {
        creator = userRepository.save(buildUser());
        IntStream.range(0, CASE_COUNT).forEach(i -> cases.add(productRepository.save(buildProduct(creator))));
        aCase = sample(cases);
    }

    @Test
    void getProductByIdTest() {
        Product product = productService.getProductById(aCase.getId());
        assertEquals(aCase.getTitle(), product.getTitle());

        assertThrows(EntityNotFoundException.class, () -> {
            productService.getProductById(Long.MAX_VALUE).getTitle();
        });
    }

    @Test
    void getProductByUuidTest() {
        Product product = productService.getProductByUuid(aCase.getUuid());
        assertEquals(aCase.getTitle(), product.getTitle());

        assertThrows(EntityNotFoundException.class, () -> {
            productService.getProductByUuid(UUID.randomUUID());
        });
    }

    @Test
    void findAllProductsTest() {
        int page = 0;
        int size = 1;
        // fetch last one by id
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("id")));
        Page<Product> productPage = productService.findAllProducts(pageable);
        assertEquals(size, productPage.getSize());
        assertEquals(cases.get(cases.size() - 1).getUuid(), productPage.getContent().get(0).getUuid());
    }

    @Test
    void createProductTest() {
        long originCount = productRepository.count();
        CreateProductVo vo = buildCreateProductVo();
        Product product = productService.createProduct(vo, creator);
        assertEquals(vo.getTitle(), product.getTitle());
        assertEquals(originCount + 1, productRepository.count());
    }

    @Test
    void updateProductTest() {
        UpdateProductVo vo = buildUpdateProductVo();
        productService.updateProduct(aCase, vo);
        assertEquals(vo.getTitle(), aCase.getTitle());
    }

    @Test
    void destroyProductTest() {
        long originCount = productRepository.count();
        productService.destroyProduct(aCase.getId());
        assertEquals(originCount - 1, productRepository.count());
    }

    @Test
    void findProductPageByCreatorTest() {
        int page = 0;
        int size = 1;
        // fetch last one by id
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("id")));
        Page<Product> productPage = productService.findProductPageByCreator(creator, pageable);
        assertEquals(size, productPage.getSize());
        assertEquals(Long.valueOf(CASE_COUNT), productPage.getTotalElements());
        assertEquals(cases.get(cases.size() - 1).getUuid(), productPage.getContent().get(0).getUuid());
    }
}
