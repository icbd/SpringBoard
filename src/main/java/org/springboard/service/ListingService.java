package org.springboard.service;

import lombok.RequiredArgsConstructor;
import org.springboard.entity.Listing;
import org.springboard.entity.User;
import org.springboard.mapper.ListingMapper;
import org.springboard.repository.ListingRepository;
import org.springboard.vo.CreateListingVo;
import org.springboard.vo.UpdateListingVo;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@RequiredArgsConstructor
@Service
public class ListingService {

    private final ListingRepository listingRepository;
    private final ListingMapper listingMapper;

    public Listing getListingById(Long id) {
        return listingRepository.getOne(id);
    }

    public Listing getListingByUuid(String uuid) {
        Listing listing = listingRepository.getByUuid(uuid);
        if (listing == null) {
            throw new EntityNotFoundException();
        }
        return listing;
    }

    public Listing createListing(CreateListingVo vo, User creator) {
        Listing listing = listingMapper.createListing(vo);
        listing.setCreator(creator);
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
