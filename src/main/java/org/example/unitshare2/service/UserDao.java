package org.example.unitshare2.service;

import org.example.unitshare2.dto.UserDto;

import java.sql.*;
import java.util.ArrayList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDao {
    //싱글톤 생성
    public UserDao() {connect();} // <--- 아!!!!!!!!!! ??? 객체가 생성될 때 DB 연동을 시작합니다.

    public static final UserDao instance = new UserDao();

    public static UserDao getInstance() {
        return instance;
    }
    private ArrayList<UserDto> UserDtos = new ArrayList<>();

    //db연동
    private String url = "jdbc:mysql://localhost:3306/unishare";
    private String user = "root";
    private String pw = "1234";
    private Connection conn;

    private void connect() { // conn 변수에 DB 연결 정보가 담김.
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, user, pw);
            System.out.println("연동성공");
        } catch (Exception e) {
            System.out.println("연동실패"+e);
        }
    }




    // 01 end

    // 02. 아이디찾기 Dao
    public UserDto findId( String name, String phone ){
        String sql ="select * from user where name = ? and phone = ?";
        try(
            PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setString(2, phone);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    UserDto dto = new UserDto();
                    dto.setUno(rs.getInt("uno"));
                    dto.setId(rs.getString("id"));
                    dto.setPwd(rs.getString("pwd"));
                    dto.setPhone(rs.getString("phone"));
                    dto.setName(rs.getString("name"));
                    return dto;
                }
            }
        }catch (Exception e){e.printStackTrace();}
        return null;
        }
    // 02 end // 0213 수정

    // 03. 비밀번호 찾기 Dao
    public UserDto findPwd( String id, String phone ){
        String sql ="select * from user where id = ? and phone = ?";
        try(
            PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            ps.setString(2, phone);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    UserDto dto = new UserDto();
                    dto.setUno(rs.getInt("uno"));
                    dto.setId(rs.getString("id"));
                    dto.setPwd(rs.getString("pwd"));
                    dto.setPhone(rs.getString("phone"));
                    dto.setName(rs.getString("name"));
                    return dto;
                }
            }
        }catch (Exception e){
            System.out.println("[경고]비밀번호 찾기 오류" + e);}
        return null;
    }
    // 03 end // 0213 수정

    // 04. 회원가입 Dao
    private int currentUno = 1; // 0211 수정
    public boolean signup(String id, String pwd, String name, String phone ) {
       try{ String sql = "insert into user(id, pwd, name, phone) values(?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            ps.setString(2, pwd);
            ps.setString(3, name);
            ps.setString(4, phone);
            int count = ps.executeUpdate();
           if( count == 1 ){ return true; }
           else{ return false; }
        } catch (SQLException e) {System.out.println("[시스템오류] 회원가입 SQL 실행 중 실패 : " + e);}
            return false;
        }
    // 04 end
    // 01-1. 아이디 중복 사용 여부 dao             //  0219 수정
    public boolean checkId(String id){
        String sql = "select * from user where id = ?";
        try{
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return true;
        } catch (Exception e) { System.out.println("[경고] 해당 아이디는 사용이 불가합니다" + e);}
        return false;
    }// 01-1 end

    // 01-2. 전화번호 중복 사용 여부 dao
    public boolean checkPhone(String phone) {
        String sql = "select * from user where phone = ?";
        try{
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, phone);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return true;
        } catch (Exception e) { System.out.println("[경고] 해당 번호는 사용 불가합니다." + e);}
        return false;
    }    // 01-2 end

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

    // 비밀번호 변경
    public boolean newPwd(int uno, String currentPwd,String newPwd){
        try {
            String sql = "UPDATE user SET pwd = ? WHERE uno = ? AND pwd =?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1,newPwd);
            ps.setInt(2,uno);
            ps.setString(3,currentPwd);

            int result = ps.executeUpdate();

            if(result > 0){
                System.out.println("[안내] 비밀번호 변경 성공!");
                return true;
            } else{
                System.out.println("[경고] 현재 비밀번호가 틀렸거나 변경에 실패하였습니다.");
                return false;
            }

        }catch (Exception e){
            System.out.println("[경고] 비밀번호 변경 도중 에러 발생 : " + e);
        }
        return false;
    }

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
