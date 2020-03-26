package org.springboard.controller.api.v1;

import lombok.RequiredArgsConstructor;
import org.springboard.dto.ProductDto;
import org.springboard.entity.Product;
import org.springboard.mapper.ProductMapper;
import org.springboard.service.ProductService;
import org.springboard.vo.CreateProductVo;
import org.springboard.vo.UpdateProductVo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping(BaseController.PREFIX + "/products")
public class ProductController extends BaseController {

    private final ProductService productService;
    private final ProductMapper productMapper;

    @GetMapping("/{uuid}")
    public ResponseEntity<ProductDto> show(@PathVariable String uuid) {
        Product product = productService.getProductByUuid(uuid);
        ProductDto productDto = productMapper.toProductDto(product);
        return ResponseEntity.ok(productDto);
    }

    @PostMapping
    public ResponseEntity<ProductDto> create(@Valid @RequestBody CreateProductVo vo) {
        Product product = productService.createProduct(vo, getCurrentUser());
        ProductDto productDto = productMapper.toProductDto(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(productDto);
    }

    @PatchMapping("/{uuid}")
    public ResponseEntity<ProductDto> update(@PathVariable String uuid, @RequestBody UpdateProductVo vo) {
        Product product = productService.getProductByUuid(uuid);
        productService.updateProduct(product, vo);
        ProductDto productDto = productMapper.toProductDto(product);
        return ResponseEntity.ok(productDto);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> destroy(@PathVariable String uuid) {
        Product product = productService.getProductByUuid(uuid);
        productService.destroyProduct(product.getId());
        return ResponseEntity.noContent().build();
    }
}
