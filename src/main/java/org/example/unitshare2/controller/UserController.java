package org.example.unitshare2.controller;

import org.example.unitshare2.dto.UserDto;
import org.example.unitshare2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import unitshare.model.dto.UserDto;
import unitshare.model.dao.UserDao;
import unitshare.view.ProductView;
import unitshare2.view.UserView;

import java.util.Scanner;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    // 01. 아이디 중복사용 여부 controller
    public boolean checkId(String id) {
        return ud.getInstance().checkId(id);
    }

    // 01-2. 전화번호 중복사용 여부 controller
    public boolean checkphone(String phone) {
        return ud.getInstance().checkPhone(phone);
    }


    // 아이디찾기
    @GetMapping("/hb")
    public String findId(@RequestBody UserDto userDto) {
        String result = userService.findId(userDto);
        return result;
    }

    // 02 end // 0213

    // 비밀번호찾기
    public String findPwd(@RequestBody UserDto userDto) {
        String result = userService.findPwd(userDto);
        return result;
    }
    // 03 end // 0213

    // 04. 회원가입 Controller
    public boolean signup(String id, String pwd, String name, String phone) {
        // [*] 유효성검사 (중복검사, 데이터 길이검사)
        boolean result = ud.signup(id, pwd, name, phone);
        return result;
    } // 04 end


    // 로그인 메소드
    private int loginSession = 0; // 세션:일정한 저장소 구역
    public TimerThread timerThread = new TimerThread();

    public boolean login(String id, String pwd) {
        int result = ud.login(id, pwd);
        if (result > 0) {
            loginSession = result;

            //* 휴면계정 // 0223 수정
            System.out.println("\n[안내]"+id+"님 환영합니다.");

           // 타이머 시작
            if( timerThread != null) timerThread.state= false;// 기존 타이머가 있다면 종료
            timerThread  = new TimerThread(id, loginSession, this);// 현재 유저 정보 전달
            timerThread.state = true; //(1)실행 상태로 변경
            timerThread.start(); // 새 스레드로 30초 시작
            return true;
        }
        return false;
    }

    // 로그아웃 메소드
    public boolean logout() {
        loginSession = 0; // 로그인 상태를 0(비로그인)으로 변경 // 세션(변수) 초기화
        if ( timerThread != null) timerThread.state = false; // 0223 수정
        return true;
    }

    // 현재 로그인된 유저 번호 반환해주는 메소드
    public int getLoginSession() {
        return loginSession;
    }

    public UserDao getUd() {
        return ud;
    }

    public void setUd(UserDao ud) {
        this.ud = ud;
    }

    // 비밀번호 변경 페이지
    public boolean newPwd(String currentPwd, String newPwd) {
        if (loginSession == 0) {
            System.out.println("[경고] 로그인이 필요한 서비스입니다.");
            return false;
        }
        // 현재 비밀번호, 새로운 비밀번호 같은지 체크
        if (currentPwd.equals(newPwd)) {
            System.out.println("[경고] 현재 비밀번호와 동일한 비밀번호로 변경할 수 없습니다.");
            return false;
        }
        // DAO 호출, 전달받은 데이터와 현재 로그인 세션번호를 넘김
        boolean result = userDao.newPwd(this.loginSession, currentPwd, newPwd);
        return result;
    } // m END

    public int pointAdd(int point , String pwd){
        int uno = getLoginSession();
        int result = ud.pointAdd(point , uno , pwd);
        return result;
    }
    // UserController.java

    public boolean isTimerOff() {
        if (this.timerThread == null) return false; // 1. 만약 타이머 스레드가 아예 없거나 아직 시작 안 했다면 정상 진행(false)
        this.timerThread.second = 0;// 2. [시간 리셋] 사용자가 입력을 했으므로 스레드의 시간을 0으로 되돌림
        if (this.timerThread.state == false) {      // 3. [상태 확인] 이미 7초가 지나서 스레드가 state를 false로 바꿨는지 확인
            return true; }// 7초 지남! (View에서 return 하도록 true 반환)
          return false;} // 아직 시간 남음! (View에서 계속 진행)
}


