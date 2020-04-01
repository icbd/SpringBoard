package org.springboard.controller.api.v1;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springboard.dto.ListingDto;
import org.springboard.entity.Listing;
import org.springboard.mapper.ListingMapper;
import org.springboard.service.ListingService;
import org.springboard.vo.CreateListingVo;
import org.springboard.vo.UpdateListingVo;
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

@Api(tags = "列表/清单")
@RequiredArgsConstructor
@RestController
@RequestMapping(BaseController.PREFIX + "/listings")
public class ListingController extends BaseController {

    private final ListingService listingService;
    private final ListingMapper listingMapper;

    @ApiOperation("展示清单")
    @GetMapping("/{uuid}")
    public ResponseEntity<ListingDto> show(@PathVariable UUID uuid) {
        Listing listing = listingService.getListingByUuid(uuid);
        ListingDto listingDto = listingMapper.toListingDto(listing);
        return ResponseEntity.ok(listingDto);
    }

    @ApiOperation("创建清单")
    @PostMapping
    public ResponseEntity<ListingDto> create(@Valid @RequestBody CreateListingVo vo) {
        Listing listing = listingService.createListing(vo, getCurrentUser());
        ListingDto listingDto = listingMapper.toListingDto(listing);
        return ResponseEntity.status(HttpStatus.CREATED).body(listingDto);
    }

    @ApiOperation("编辑清单")
    @PatchMapping("/{uuid}")
    public ResponseEntity<ListingDto> update(@PathVariable UUID uuid, @Valid @RequestBody UpdateListingVo vo) {
        Listing listing = listingService.getListingByUuid(uuid);
        listingService.updateListing(listing, vo);
        ListingDto listingDto = listingMapper.toListingDto(listing);
        return ResponseEntity.ok(listingDto);
    }

    @ApiOperation("删除清单")
    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> destroy(@PathVariable UUID uuid) {
        Listing listing = listingService.getListingByUuid(uuid);
        listingService.destroyListing(listing.getId());
        return ResponseEntity.noContent().build();
    }
}
