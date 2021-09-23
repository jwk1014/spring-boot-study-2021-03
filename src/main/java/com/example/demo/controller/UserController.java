package com.example.demo.controller;

import com.example.demo.model.req.RUserSign;
import com.example.demo.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import static org.springframework.security.web.context.HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/users/sign-in")
    public ResponseEntity<Void> signIn(
            @Valid @RequestBody final RUserSign.In req,
            final HttpSession session
    ) {
        final Authentication auth = userService.signIn(req);

        final SecurityContext sc = SecurityContextHolder.getContext();
        sc.setAuthentication(auth);
        session.setAttribute(SPRING_SECURITY_CONTEXT_KEY, sc);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/users/sign-up")
    public ResponseEntity<Void> signUp(
            @Valid @RequestBody final RUserSign.Up req
    ) {
        userService.signUp(req);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation("로그아웃")
    @PostMapping("/users/sign-out")
    public ResponseEntity<Void> signOut() {
        throw new IllegalStateException("This Method not working");
    }
}
