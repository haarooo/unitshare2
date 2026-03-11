package org.example.unitshare2.view;
import unitshare.controller.ProductController;
import unitshare.controller.UserController;
import unitshare.model.dto.UserDto;

import java.util.Scanner;
import java.util.InputMismatchException;

public class UserView {
    private UserView() {
    }
    UserController userController = UserController.getInstance();

    private static final UserView instance = new UserView();
    public static UserView getInstance() {return instance;}

    private UserController uc = UserController.getInstance();
    Scanner scan = new Scanner(System.in); // 스캐너 멤버변수로 빼면 더 편리함.

    // [*] 메인페이지
    public void index() {
        for (; ; ) {
            try {
                System.out.println("\n┌────────────────────────────────────────────────────┐");
                System.out.println("│             📢 UNIT SHARE FOR SOLO                 │");
                System.out.println("├────────────────────────────────────────────────────┤");
                if (uc.getLoginSession() == 0) {
                    System.out.println("│  👤 1. 회원가입          🔑 2. 로그인                │");
                    System.out.println("│  🔍 3. 아이디 찾기       🔒 4. 비밀번호 찾기          │");
                }
                System.out.println("└────────────────────────────────────────────────────┘");
                System.out.print("✨ 선택 > ");
                int ch = scan.nextInt();

                if (uc.getLoginSession() == 0) { // [로그인 전 메뉴 처리]
                    if (ch == 1) {
                        signup();
                    } else if (ch == 2) {
                        login();
                    } else if (ch == 3) {
                        findIdView();
                    } // 0213 수정
                    else if (ch == 4) {
                        findPwdView();
                    }
                }
            } catch (InputMismatchException e) {
                System.out.println("[경고] 잘못된 입력 방식입니다.");
                scan = new Scanner(System.in); // 입력객체 초기화
            } catch (Exception e) {
                System.out.println("[시스템 오류] 관리자에게 문의하십시오.");
            }
        }
    }

    // 02. 아이디찾기 View
    public void findIdView() {
        System.out.println("----- 아이디 찾기 -----");
        System.out.print("이름 입력: ");
        String name = scan.next();

        System.out.print("전화번호 입력: ");
        String phone = scan.next();

        String result = uc.findId(name, phone);

        if (result != null) {
            System.out.println("찾은 아이디 : " + result);
        } else {
            System.out.println("일치하는 회원이 없습니다.");
        }
    }
    // 02 end // 0213 수정

    // 03. 비밀번호찾기 View

        public void findPwdView() {
        System.out.println("----- 비밀번호 찾기 -----");
        System.out.println("아이디 입력: ");
        String id = scan.next();

        System.out.print("전화번호 입력: ");
        String phone = scan.next();

        String result = uc.findPwd(id, phone);

        if (result != null) {
            System.out.println("찾은 비밀번호 : " + result);
        } else {
            System.out.println("일치하는 회원이 없습니다.");
        }
    }
    // 03 end // 0213 수정

    // 04. 회원가입 View
    public void signup() {
        // 아이디 중복확인
        String id = "";
        while (true) {
            System.out.println("----- 회원가입 -----"); // 0219 수정
            System.out.print("아이디 : ");
            id= scan.next();
            if (uc.checkId(id)) {
                System.out.println("[오류] 이미 존재하는 아이디입니다. 다시 입력해주세요.");
            } else {
                System.out.println("[안내] 사용 가능한 아이디입니다.");
                break;
            }
        } // 01-1 end
        System.out.print("비밀번호 : ");
        String pwd = scan.next();

        System.out.print("성함 : ");
        String name = scan.next();

         // 01-2. 전화번호 중복 확인
        String phone = ""; // 0219 수정
        while (true) {
            System.out.print("연락처 : ");
            phone = scan.next();
            if (uc.checkphone(phone)) {
                System.out.println("[오류] 이미 등록된 전화번호 입니다. 다시 입력해주세요.");
            } else {
                System.out.println("[안내] 등록 가능한 전화번호 입니다.");
                break;
            }// 01-2 end
        } // while end

        // 모든 입력이 정상일 때 회원가입 실행
        boolean result = uc.signup(id, pwd, name, phone);
        if (result) {
            System.out.println("[안내] 회원가입이 완료되었습니다.");
        } else {
            System.out.println("[안내] 회원가입에 실패하였습니다. 다시 시도해주십시오.");
        }
    } //04 end

    // 로그인 페이지 view
    public void login() {
        System.out.println("----- 로그인 -----");
        Scanner scan = new Scanner(System.in);
        System.out.print("아이디 : ");
        String id = scan.next();
        System.out.print("비밀번호 : ");
        String pwd = scan.next();
        boolean result = uc.login(id, pwd);
        if (result) {
            System.out.println("[안내] 로그인에 성공하였습니다.");
            ProductView.getInstance().index2();
        } else {
            System.out.println("[경고] 로그인에 실패하였습니다.");
        }

    } // m END

    // 로그아웃 페이지 view
    public void logout() {
        boolean result = uc.logout();
        if (result) {
            System.out.println("[안내] 로그아웃되었습니다.");
        }
        UserView.getInstance().index();
    }

    // 비밀번호 변경 메소드
    public boolean newPwd(){
        // 입력 받기
        System.out.println("==============비밀번호 변경===============");

        System.out.print("현재 비밀번호를 입력해주세요 : ");
        String currentPwd = scan.next();

        //새로운 비밀번호 입력받기
        System.out.println("새로운 비밀번호를 입력해주세요. : ");
        String newPwd = scan.next();

        boolean result = userController.newPwd(currentPwd,newPwd);

            if (result) {
                System.out.println("[안내] 비밀번호 변경이 완료되었습니다.");
                ProductView.getInstance().index2();
                return true;
            } else {
                System.out.println("[경고] 비밀번호 변경에 실패하였습니다.");
                return false;
            }
    }

    public int pointAdd(){

        System.out.println("================포인트 충전===============");
        System.out.print("충전할 포인트를 입력하세요 : ");
        int newPoint = scan.nextInt();
        scan.nextLine();
        System.out.print("현재 비밀번호를 입력해주세요 : ");
        String cpwd = scan.nextLine();
        int result = uc.pointAdd(newPoint , cpwd);
        if(result == 1){
            System.out.println("[안내]포인트 충전이 완료되었습니다");
        }else{
            System.out.println("[경고]비밀번호가 일치하지 않습니다");
        }return 0;
    }






    } // class END



