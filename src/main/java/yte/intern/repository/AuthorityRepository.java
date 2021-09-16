package yte.intern.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yte.intern.model.Authority;

import java.util.Optional;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {

    Optional<Authority> findByAuthority(String authority);

    Boolean existsByAuthority(String authority);

}