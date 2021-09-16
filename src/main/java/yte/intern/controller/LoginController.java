package yte.intern.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yte.intern.common.MessageResponse;
import yte.intern.common.enums.Message;
import yte.intern.dto.LoginRequest;
import yte.intern.dto.LoginResponse;
import yte.intern.service.ILoginService;
import yte.intern.service.impl.LoginService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/login")
public class LoginController {

    private final ILoginService loginService;

    public LoginController(final LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody final LoginRequest loginRequest) {
        MessageResponse<LoginResponse> messageResponse = loginService.login(loginRequest);

        if (messageResponse.getMessage() == Message.SUCCESS_LOGIN) {
            return ResponseEntity.ok().body(messageResponse.getT());
        } else
            return ResponseEntity.badRequest().body(null);
    }
}
