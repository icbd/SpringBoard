package org.springboard.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springboard.vo.CreateProductVo;
import org.springboard.dto.ProductDto;
import org.springboard.vo.UpdateProductVo;
import org.springboard.entity.Product;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductDto toProductDto(Product product);

    Product createProduct(CreateProductVo vo);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Product mergeProduct(@MappingTarget Product product, UpdateProductVo vo);
}
