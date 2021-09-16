package yte.intern.service;

import yte.intern.common.MessageResponse;
import yte.intern.dto.LoginRequest;
import yte.intern.dto.LoginResponse;

public interface ILoginService {
    MessageResponse<LoginResponse> login(LoginRequest loginRequest);
}
