package yte.intern.service.impl;

import org.springframework.stereotype.Service;
import yte.intern.common.MessageResponse;
import yte.intern.common.enums.Message;
import yte.intern.model.Authority;
import yte.intern.repository.AuthorityRepository;
import yte.intern.service.IAuthorityService;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class AuthorityService implements IAuthorityService {

    private final AuthorityRepository authorityRepository;

    public AuthorityService(AuthorityRepository authorityRepository) {
        this.authorityRepository = authorityRepository;
    }

    @Override
    public List<Authority> getAllAuthorities() {
        return authorityRepository.findAll();
    }

    @Override
    public Authority getAuthorityById(Long id) {
        return authorityRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Authority Not Found.")
        );
    }

    @Override
    public Authority getAuthorityByAuthority(String authority) {
        return authorityRepository.findByAuthority(authority).orElseThrow(
                () -> new EntityNotFoundException("Authority Not Found.")
        );
    }

    @Override
    public Boolean existsByAuthority(String authority) {
        return authorityRepository.existsByAuthority(authority);
    }

    @Override
    @Transactional
    public Authority createAuthority(Authority authority) {
        return authorityRepository.save(authority);
    }

    @Transactional
    public MessageResponse<Authority> createAuthorityChecked(String authority) {

        if (authorityRepository.existsByAuthority(authority))
            return new MessageResponse<>(Message.INUSE_AUTHORITY);

        Authority savedAuthority = authorityRepository.save(new Authority(authority));

        if (savedAuthority.getId() > 0)
            return new MessageResponse<>(Message.CREATED_AUTHORITY, savedAuthority);
        else
            return new MessageResponse<>(Message.ERROR_CREATION);
    }


}