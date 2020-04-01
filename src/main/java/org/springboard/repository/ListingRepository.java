package org.springboard.repository;

import org.springboard.entity.Listing;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ListingRepository extends JpaRepository<Listing, Long> {

    Listing getByUuid(UUID uuid);
}
