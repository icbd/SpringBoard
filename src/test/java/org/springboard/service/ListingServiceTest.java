package org.springboard.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springboard.entity.Listing;
import org.springboard.entity.Product;
import org.springboard.entity.User;
import org.springboard.repository.ListingRepository;
import org.springboard.repository.ProductRepository;
import org.springboard.repository.UserRepository;
import org.springboard.vo.CreateListingVo;
import org.springboard.vo.UpdateListingVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springboard.util.ListHelper.sample;

@DataJpaTest
@Nested
class ListingServiceTest extends ServiceTestBase {

    @Autowired
    private ListingRepository listingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ListingService listingService;

    private User creator;
    private Product product;
    private List<Listing> cases = new ArrayList<>();
    private Listing aCase;

    @BeforeEach
    void setUp() {
        creator = userRepository.save(buildUser());
        product = productRepository.save(buildProduct(creator));
        IntStream.range(0, CASE_COUNT).forEach(i -> cases.add(listingRepository.save(buildListing(product, creator))));
        aCase = sample(cases);
    }

    @Test
    void getListingByIdTest() {
        Listing listing = listingService.getListingById(aCase.getId());
        assertEquals(aCase.getTitle(), listing.getTitle());

        assertThrows(EntityNotFoundException.class, () -> {
            listingService.getListingById(Long.MAX_VALUE).getTitle();
        });
    }

    @Test
    void getListingByUuidTest() {
        Listing listing = listingService.getListingByUuid(aCase.getUuid());
        assertEquals(aCase.getTitle(), listing.getTitle());

        assertThrows(EntityNotFoundException.class, () -> {
            listingService.getListingByUuid("INVALID UUID").getTitle();
        });
    }

    @Test
    void createListingTest() {
        long originCount = listingRepository.count();
        CreateListingVo vo = buildCreateListingVo(product.getUuid());
        Listing listing = listingService.createListing(vo, creator);
        assertEquals(vo.getTitle(), listing.getTitle());
        assertEquals(originCount + 1, listingRepository.count());
    }

    @Test
    void updateListingTest() {
        UpdateListingVo vo = buildUpdateListingVo();
        listingService.updateListing(aCase, vo);
        assertEquals(vo.getTitle(), aCase.getTitle());
    }

    @Test
    void destroyListingTest() {
        long originCount = listingRepository.count();
        listingService.destroyListing(aCase.getId());
        assertEquals(originCount - 1, listingRepository.count());
    }
}
