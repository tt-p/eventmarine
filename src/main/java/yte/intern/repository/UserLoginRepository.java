package yte.intern.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yte.intern.model.UserLogin;
import yte.intern.model.UserProfile;

import java.util.List;
import java.util.Optional;

public interface UserLoginRepository extends JpaRepository<UserLogin, Long> {

    Optional<UserLogin> findByUsername(String username);

    List<UserLogin> findAllByUserProfile(UserProfile userProfile);

    Boolean existsByUsername(String username);

}