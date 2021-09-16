package yte.intern.service;

import yte.intern.common.MessageResponse;
import yte.intern.model.Authority;

import javax.transaction.Transactional;
import java.util.List;

public interface IAuthorityService {
    List<Authority> getAllAuthorities();

    Authority getAuthorityById(Long id) throws Exception;

    Authority getAuthorityByAuthority(String authority) throws Exception;

    Boolean existsByAuthority(String authority);

    @Transactional
    Authority createAuthority(Authority authority);

    @Transactional
    MessageResponse<Authority> createAuthorityChecked(String authority);

}
