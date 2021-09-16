package yte.intern.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yte.intern.model.UserProfile;

import java.util.Optional;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {

    Optional<UserProfile> findByTcNo(Long tcNo);

    Boolean existsByTcNoAndEmail(Long tcNo, String email);

    Boolean existsByTcNo(Long tcNo);

    Boolean existsByEmail(String email);

}