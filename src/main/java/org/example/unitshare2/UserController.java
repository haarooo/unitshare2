package org.example.unitshare2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserController {
    @Autowired private UserRepository userRepository;

    // 로그인

    // 휴면계정 전환
}
