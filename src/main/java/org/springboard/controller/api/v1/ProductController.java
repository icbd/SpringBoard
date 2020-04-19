package org.springboard.controller.api.v1;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springboard.dto.ProductDto;
import org.springboard.entity.Product;
import org.springboard.entity.User;
import org.springboard.mapper.ProductMapper;
import org.springboard.security.CurrentUserContextHolder;
import org.springboard.service.AuthorizationService;
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
import java.util.UUID;

import static org.springboard.constant.PermissionEnum.ADMIN;
import static org.springboard.constant.PermissionEnum.EDIT;
import static org.springboard.constant.PermissionEnum.READ;

@Api(tags = "项目/产品")
@RequiredArgsConstructor
@RestController
@RequestMapping(BaseController.PREFIX + "/products")
public class ProductController extends BaseController {

    private final ProductService productService;
    private final ProductMapper productMapper;
    private final AuthorizationService authorizationService;

    @ApiOperation("展示项目")
    @GetMapping("/{uuid}")
    public ResponseEntity<ProductDto> show(@PathVariable UUID uuid) {
        Product product = productService.getProductByUuid(uuid);
        authorizationService.can(product, READ, getCurrentUser());

        ProductDto productDto = productMapper.toProductDto(product);
        return ResponseEntity.ok(productDto);
    }

    @ApiOperation("创建项目")
    @PostMapping
    public ResponseEntity<ProductDto> create(@Valid @RequestBody CreateProductVo vo) {
        User currentUser = CurrentUserContextHolder.getContext();
        Product product = productService.createProduct(vo, currentUser);
        ProductDto productDto = productMapper.toProductDto(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(productDto);
    }

    @ApiOperation("编辑项目")
    @PatchMapping("/{uuid}")
    public ResponseEntity<ProductDto> update(@PathVariable UUID uuid, @Valid @RequestBody UpdateProductVo vo) {
        Product product = productService.getProductByUuid(uuid);
        authorizationService.can(product, EDIT, getCurrentUser());

        productService.updateProduct(product, vo);
        ProductDto productDto = productMapper.toProductDto(product);
        return ResponseEntity.ok(productDto);
    }

    @ApiOperation("删除项目")
    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> destroy(@PathVariable UUID uuid) {
        Product product = productService.getProductByUuid(uuid);
        authorizationService.can(product, ADMIN, getCurrentUser());

        productService.destroyProduct(product.getId());
        return ResponseEntity.noContent().build();
    }
}
