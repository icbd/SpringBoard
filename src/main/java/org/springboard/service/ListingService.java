package org.springboard.service;

import lombok.RequiredArgsConstructor;
import org.springboard.entity.Listing;
import org.springboard.entity.Product;
import org.springboard.entity.User;
import org.springboard.mapper.ListingMapper;
import org.springboard.repository.ListingRepository;
import org.springboard.vo.CreateListingVo;
import org.springboard.vo.UpdateListingVo;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ListingService {

    private final ProductService productService;
    private final ListingRepository listingRepository;
    private final ListingMapper listingMapper;

    public Listing getListingById(Long id) {
        return listingRepository.getOne(id);
    }

    public Listing getListingByUuid(UUID uuid) {
        Listing listing = listingRepository.getByUuid(uuid);
        if (listing == null) {
            throw new EntityNotFoundException();
        }
        return listing;
    }

    public Listing createListing(CreateListingVo vo, User creator) {
        Product product = productService.getProductByUuid(vo.getProductUuid());
        Listing listing = listingMapper.createListing(vo);
        listing.setCreator(creator);
        listing.setProject(product);
        return listingRepository.save(listing);
    }

    public void updateListing(Listing listing, UpdateListingVo vo) {
        listingMapper.mergeListing(listing, vo);
        listingRepository.save(listing);
    }

    public void destroyListing(Long id) {
        listingRepository.deleteById(id);
    }
}
