package yte.intern.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yte.intern.common.MessageResponse;
import yte.intern.common.enums.Authorities;
import yte.intern.common.enums.Message;
import yte.intern.dto.RegisterRequest;
import yte.intern.model.UserLogin;
import yte.intern.model.UserProfile;
import yte.intern.service.IRegisterService;
import yte.intern.service.impl.RegisterService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/register")
public class RegisterController {

    private final IRegisterService registerService;

    public RegisterController(RegisterService registerService) {
        this.registerService = registerService;
    }

    @PostMapping("/external")
    public ResponseEntity<List<String>> register(@Valid @RequestBody RegisterRequest registerRequest) {

        UserLogin userLogin = registerRequest.getUserLogin();
        UserProfile userProfile = registerRequest.getUserProfile();

        MessageResponse messageResponse = registerService.registerUser(Authorities.EXTERNAL, userLogin, userProfile);

        if (messageResponse.getMessage() == Message.SUCCESS_REGISTRY) {
            return ResponseEntity
                    .ok()
                    .body(List.of(messageResponse.getMessage().getValue()));
        } else {
            return ResponseEntity
                    .badRequest()
                    .body(List.of(messageResponse.getMessage().getValue()));
        }

    }

}
