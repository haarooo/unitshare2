package org.example.unitshare2.service;

import jakarta.transaction.Transactional;
import org.example.unitshare2.dto.UserDto;
import org.example.unitshare2.entity.ParticipantEntity;
import org.example.unitshare2.entity.UserEntity;
import org.example.unitshare2.repository.UserRepository;
import org.hibernate.sql.results.jdbc.internal.JdbcValuesResultSetImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service @Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // 로그인 Service 스프링
    public boolean login(String id, String pwd){
        Optional<UserEntity> optionalUserEntity = userRepository.findByIdAndPwd(id,pwd);
        if(optionalUserEntity.isPresent()){
            return true;
        }
        return false;
    }

    // 로그인 일정 시간 지나면 자동 로그아웃(휴면계정 전환) Service 스프링
    public boolean loginStatement(int uno){
        Optional<UserEntity> optionalUserEntity = userRepository.findById(uno);

    }
}

//    public String findId( UserDto userDto ){
//            Optional<UserEntity> optionalName = userRepository.findById(userDto.getName());
//            Optional<UserEntity> optionalPhone = userRepository.findById(userDto.getPhone());
//            if(optionalName.isPresent() && optionalPhone.isPresent()){
//                Optional<UserEntity> findId = userRepository.findById(userDto.getId());
//                return findId.get().toDto();
//            }
//        }
//    // 02 end // 0213 수정
//
//    // 03. 비밀번호 찾기 Dao
//    public String findPwd(UserDto userDto){
//        userRepository.findById(userDto.get);
//    }
//
//    // 비밀번호 변경
//    public boolean changePwd(UserDto userDto){
//
//
//    }
//    // 04. 회원가입 Dao
//    private int currentUno = 1; // 0211 수정
//    public boolean signup(String id, String pwd, String name, String phone ) {
//       try{ String sql = "insert into user(id, pwd, name, phone) values(?, ?, ?, ?)";
//            PreparedStatement ps = conn.prepareStatement(sql);
//            ps.setString(1, id);
//            ps.setString(2, pwd);
//            ps.setString(3, name);
//            ps.setString(4, phone);
//            int count = ps.executeUpdate();
//           if( count == 1 ){ return true; }
//           else{ return false; }
//        } catch (SQLException e) {System.out.println("[시스템오류] 회원가입 SQL 실행 중 실패 : " + e);}
//            return false;
//        }
//    // 04 end
//    // 01-1. 아이디 중복 사용 여부 dao             //  0219 수정
//    public boolean checkId(String id){
//        String sql = "select * from user where id = ?";
//        try{
//            PreparedStatement ps = conn.prepareStatement(sql);
//            ps.setString(1, id);
//            ResultSet rs = ps.executeQuery();
//            if (rs.next()) return true;
//        } catch (Exception e) { System.out.println("[경고] 해당 아이디는 사용이 불가합니다" + e);}
//        return false;
//    }// 01-1 end
//
//    // 01-2. 전화번호 중복 사용 여부 dao
//    public boolean checkPhone(String phone) {
//        String sql = "select * from user where phone = ?";
//        try{
//            PreparedStatement ps = conn.prepareStatement(sql);
//            ps.setString(1, phone);
//            ResultSet rs = ps.executeQuery();
//            if (rs.next()) return true;
//        } catch (Exception e) { System.out.println("[경고] 해당 번호는 사용 불가합니다." + e);}
//        return false;
//    }    // 01-2 end
//
//    // 로그인(현재 정보와 기존 정보를 비교)
//
//    // [2] 로그인(현재 정보와 기존 정보를 비교)
//    /*public int login(String id, String pwd) {
//        try { // SQL 작성 : 입력받은 id와 pwd가 일치하는 레코드가 있는지 확인
//            String sql = "select * from user where id = ? AND pwd = ?";
//            PreparedStatement ps = conn.prepareStatement(sql);
//            ps.setString(1, id);
//            ps.setString(2, pwd);
//
//            ResultSet rs = ps.executeQuery(); // 쿼리 실행 결과 받기
//
//            if (rs.next()) {return rs.getInt("uno");}
//        }catch (Exception e){            System.out.println("[경고] 로그인 처리 중 에러 : " + e);
//        }
//        return 0;
//
//    } // m END
//*/
//
//    // 휴면계정 전환 // 0223 추가
//    public boolean loginStatement(int uno){
//        try {
//            String sql1 = "update user set loginState = 1 where uno = ?";
//            PreparedStatement ps = conn.prepareStatement(sql1);
//            ps.setInt(1, uno);
//            if (ps.executeUpdate() == 1) {
//                String sql2 = "insert into loginStatement(loginState, uno) values (1, ?)";
//                PreparedStatement ps2 = conn.prepareStatement(sql2);
//                ps2.setInt(1, uno);
//                ps2.executeUpdate();
//                return true;
//            }
//        }catch (SQLException e){System.out.print("[경고] 휴면계정 처리 중 에러" + e);}
//        return false;
//    }
//
//
//    public int pointAdd(int point , int uno , String pwd){
//        try{            String sql1 = "update user set point = point + ? where uno = ? and pwd = ?";
//            PreparedStatement ps1 = conn.prepareStatement(sql1);
//            ps1.setInt(1 , point);
//            ps1.setInt(2, uno);
//            ps1.setString(3, pwd);
//            int count = ps1.executeUpdate();
//            if(count == 1){return 1;
//            }else{return 2;}
//
//        }catch (SQLException e){System.out.println(" 포인트 sql오류 ");}
//        return 0;
//    }
//} // class END
