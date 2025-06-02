package com.eazybytes.accounts.controller;

import com.eazybytes.accounts.entity.MyUser;
import com.eazybytes.accounts.repository.MyUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class MyUserController {
    private final MyUserRepository myUserRepository;

    @PostMapping
    public ResponseEntity<MyUser> createUser() {
         MyUser myUser = new MyUser();
         myUser.setName("Emil");
         myUser.setActive(false);
         myUserRepository.save(myUser);
         return ResponseEntity.ok(myUser);

    }
}
