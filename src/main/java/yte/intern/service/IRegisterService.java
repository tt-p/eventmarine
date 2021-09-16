package yte.intern.service;

import yte.intern.common.MessageResponse;
import yte.intern.common.enums.Authorities;
import yte.intern.model.UserLogin;
import yte.intern.model.UserProfile;

import java.time.LocalDate;

public interface IRegisterService {
    MessageResponse registerUser(Authorities authorities, UserLogin userLogin, UserProfile userProfile);

    MessageResponse registerUser(Long tcNo, String email, String name, String surname, LocalDate birthDate, String username, String password, Authorities authorities);
}
