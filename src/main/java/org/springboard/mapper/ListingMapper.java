package org.springboard.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springboard.dto.ListingDto;
import org.springboard.entity.Listing;
import org.springboard.vo.CreateListingVo;
import org.springboard.vo.UpdateListingVo;

@Mapper(componentModel = "spring")
public interface ListingMapper {

    ListingDto toListingDto(Listing listing);

    Listing createListing(CreateListingVo vo);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Listing mergeListing(@MappingTarget Listing listing, UpdateListingVo vo);
}
