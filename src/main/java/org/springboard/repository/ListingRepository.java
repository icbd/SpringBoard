package org.springboard.repository;

import org.springboard.entity.Listing;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ListingRepository extends JpaRepository<Listing, Long> {

    Listing getByUuid(String uuid);
}
