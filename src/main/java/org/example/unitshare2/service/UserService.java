package org.example.unitshare2.service;

import jakarta.transaction.Transactional;
import org.example.unitshare2.dto.UserDto;
import org.example.unitshare2.repository.UserRepository;

public class UserService {
    private UserRepository userRepository;

    // 회원가입
    public boolean 회원가입(UserDto userDto){
        // 1] dto --> entity 변환
        UserEntity userEntity = userDto.toEntity();
        // 2] JPA save 이용하여 insert 하기
        UserEntity saved = userRepository.save( userEntity );
        // 3] save 결과에 pk 여부 성공판단
        if( saved.getUserId() >= 1 ) {
            return true;
        }
        return false;
    }

    // 아이디 중복 체크
    @Transactional
    public boolean 아이디중복체크(String id){
        return userRepository.existsByLoginId(id);
    }

    // 전화번호 중복 체크
    public boolean 전화번호중복체크(String phone){
        return userRepository.existsByPhone(phone);
    }
}
