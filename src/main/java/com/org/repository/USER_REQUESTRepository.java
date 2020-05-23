package com.org.repository;

import com.org.domain.USER_REQUEST;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the USER_REQUEST entity.
 */
@SuppressWarnings("unused")
@Repository
public interface USER_REQUESTRepository extends JpaRepository<USER_REQUEST, Long> {
}
