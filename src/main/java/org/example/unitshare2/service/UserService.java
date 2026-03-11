package org.example.unitshare2.service;

import org.example.unitshare2.dto.UserDto;
import org.example.unitshare2.entity.UserEntity;
import org.example.unitshare2.repository.UserRepository;
import org.hibernate.sql.results.jdbc.internal.JdbcValuesResultSetImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;



    public String findId( UserDto userDto ){
            Optional<UserEntity> optionalName = userRepository.findById(userDto.getName());
            Optional<UserEntity> optionalPhone = userRepository.findById(userDto.getPhone());
            if(optionalName.isPresent() && optionalPhone.isPresent()){
                Optional<UserEntity> findId = userRepository.findById(userDto.getId());
                return findId.get().toDto();
            }
        }
ㅋ

    // ******************************************* 소영  *****************************************
    // 회원가입
    public boolean join( UserDto userDto ){
        // 1] dto --> entity 변환
        UserEntity userEntity = userDto.toEntity();
        // 2] JPA save 이용하여 insert 하기
        UserEntity saved = userRepository.save( userEntity );
        // 3] 저장 성공 여부 반환 (PK인 uno가 0보다 크면 성공)
        return saved.getUno() > 0;
    }

    // 아이디 중복 체크
    public boolean checkId(String id){
        List<UserEntity> userEntityList = userRepository.findAll();
        // 검사만 하니까 DTO XX
        for( int index = 0 ; index <= userEntityList.size()-1 ; index++ ){
            if(userEntityList.get(index).equals(id)){
                return true;
            }
        }
//        userEntityList.forEach(entity -> {
//            if (entity.getId().equals(id)){
//                return true;
//            }
//        });
        return false;
    }

    // 전화번호 중복 체크
    public boolean checkPhone(String phone){
        List<UserEntity> userEntityList = userRepository.findAll();
        for(int index = 0 ; index <= userEntityList.size()-1 ; index++ ){
            if(userEntityList.get(index).equals(phone)){
                return true;
            }
        }
        //        userEntityList.forEach(entity -> {
//            if (entity.getPhone().equals(phone)){
//                return true;
//            }
//        });
        return false;
     }
    }
    // ***************************************************************************************



    // 02 end // 0213 수정

    // 03. 비밀번호 찾기 Dao
    public String findPwd(UserDto userDto){
        userRepository.findById(userDto.get);
    }

    // 비밀번호 변경
    public boolean changePwd(UserDto userDto){


    }






    // 로그인(현재 정보와 기존 정보를 비교)

    // [2] 로그인(현재 정보와 기존 정보를 비교)
    public int login(String id, String pwd) {
        try { // SQL 작성 : 입력받은 id와 pwd가 일치하는 레코드가 있는지 확인
            String sql = "select * from user where id = ? AND pwd = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            ps.setString(2, pwd);

            ResultSet rs = ps.executeQuery(); // 쿼리 실행 결과 받기

            if (rs.next()) {return rs.getInt("uno");}
        }catch (Exception e){            System.out.println("[경고] 로그인 처리 중 에러 : " + e);
        }
        return 0;

    } // m END



    // 휴면계정 전환 // 0223 추가
    public boolean loginStatement(int uno){
        try {
            String sql1 = "update user set loginState = 1 where uno = ?";
            PreparedStatement ps = conn.prepareStatement(sql1);
            ps.setInt(1, uno);
            if (ps.executeUpdate() == 1) {
                String sql2 = "insert into loginStatement(loginState, uno) values (1, ?)";
                PreparedStatement ps2 = conn.prepareStatement(sql2);
                ps2.setInt(1, uno);
                ps2.executeUpdate();
                return true;
            }
        }catch (SQLException e){System.out.print("[경고] 휴면계정 처리 중 에러" + e);}
        return false;
    }


    public int pointAdd(int point , int uno , String pwd){
        try{            String sql1 = "update user set point = point + ? where uno = ? and pwd = ?";
            PreparedStatement ps1 = conn.prepareStatement(sql1);
            ps1.setInt(1 , point);
            ps1.setInt(2, uno);
            ps1.setString(3, pwd);
            int count = ps1.executeUpdate();
            if(count == 1){return 1;
            }else{return 2;}

        }catch (SQLException e){System.out.println(" 포인트 sql오류 ");}
        return 0;
    }
} // class END
