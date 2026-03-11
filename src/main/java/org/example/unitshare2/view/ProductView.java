package org.example.unitshare2.view;

import unitshare.controller.ProductController;
import unitshare.controller.UserController;
import unitshare.model.dto.ProductDto;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ProductView {
    private ProductView(){}
    private static final ProductView instance = new ProductView();
    public static ProductView getInstance(){return instance;}
    //호출
    private ProductController pc = ProductController.getInstance();
    private UserController uc = UserController.getInstance();

    //로그인 이후 페이지
    public void index2() {
        for (; ; ) {
            try { if(uc.isTimerOff()) return;
                System.out.println("\n--------------------------------------------------");
                System.out.println("[안내] 아무런 활동이 없으시다면, 7초 뒤에 휴면계정으로 전환 및 자동 로그아웃됩니다.");
                System.out.println("--------------------------------------------------\n");
                System.out.println("\n[ UNIT SHARE FOR SOLO ]");
                System.out.println("--------------------------------------------------");
                System.out.print(" 1.로그아웃 🏠   ");
                System.out.print(" 2.물품등록 📦   ");
                System.out.println(" 3.구매신청 🛒");

                System.out.print(" 4.공구신청목록 📜 ");
                System.out.print("5.내가등록한물품 📋 ");
                System.out.println("6.내가올린글삭제 ❌");
                System.out.print(" 7.구매취소 🚫");
                System.out.print("  8. 거래현황 ");
                System.out.print(" 9. 비밀번호 변경");
                System.out.println(" 10. 포인트 충전");
                System.out.println("--------------------------------------------------");
                System.out.print("선택 > ");
                int ch = scan.nextInt();
                scan.nextLine();
                if(uc.isTimerOff()) return;
                if (ch == 1) {UserView.getInstance().logout();}
                else if (ch == 2) {productAdd();}
                else if (ch == 3) {findAll();}
                else if (ch == 4) {mylist();}
                else if (ch == 5) {myUpList();}
                else if(ch==6){BoardCancel();}
                else if(ch==7){GroupCancel();}
                else if(ch==8){productDetail();}
                else if(ch==9){UserView.getInstance().newPwd();}
                else if(ch==10){UserView.getInstance().pointAdd();}
                else {System.out.println("[경고] 없는 기능 번호입니다.");}
            } catch (InputMismatchException e) {
                System.out.println("[경고] 잘못된 입력 방식입니다." + e);
                scan = new Scanner(System.in); // 입력객체 초기화
            } catch (Exception e) {
                System.out.println("[시스템 오류] 관리자에게 문의하십시오.");
            }
        }
    }


    //20. 물품등록
    public void productAdd(){
        Scanner scan = new Scanner(System.in);
        System.out.print("제품명 : "); String pname = scan.nextLine();
        if(uc.isTimerOff()) return;
        System.out.print("가격 : "); int pprice = scan.nextInt();
        scan.nextLine();
        if(uc.isTimerOff()) return;
        System.out.print("설명 : "); String pcontent = scan.nextLine();
        if(uc.isTimerOff()) return;
        System.out.print("인원수 : "); int people = scan.nextInt();
        if(uc.isTimerOff()) return;
        if(people > 5){System.out.println("최대 4명까지 가능합니다");return;}
        scan.nextLine();
        if(uc.isTimerOff()) return;
        System.out.print("오픈채팅링크 : "); String openchat = scan.nextLine();
        if(uc.isTimerOff()) return;
        int result = pc.productAdd(pname, pprice ,pcontent ,people ,openchat);
        if(result == 1){
            System.out.println("[안내] 물품등록 완료");
        }else{
            System.out.println("[경고] 물품등록 실패");
        }
    }//product Add e


    Scanner scan = new Scanner(System.in);

    //21 전체 공동구매 목록조회
    public void findAll() {
        int page = 1;
        int totalPage = pc.getTotalPages(5);

        while (true) {
            System.out.println("\n========= [ 공동구매 목록 " + page + " / " + totalPage + " ] =========");
            ArrayList<ProductDto> products = pc.findAll(page, 5);

            for (ProductDto product : products) {
                int cpeople = product.getPeople() - product.getCpeople();
                if (cpeople < 0) { cpeople = 0; }
                System.out.printf("번호 : %d , 제품명 : %s , 가격 : %d , 설명 : %s , 인원수 : %d(남은자리)/%d(총인원) , 오픈채팅링크 : %s , 등록일 : %s \n",
                        product.getPno(), product.getPname(), product.getPprice(), product.getPcontent(), cpeople, product.getPeople(), product.getOpenchat(), product.getPdate());
            }

            System.out.print("\n신청할 번호 선택 (이전: b / 다음: p / 뒤로가기: 0): ");
            String next = scan.next();
            if(uc.isTimerOff()) return;
            //페이지 로직
            if (next.equalsIgnoreCase("p")) {
                if (page < totalPage) page++;
                else System.out.println("[알림] 마지막 페이지입니다.");
                continue;
            }

            if (next.equalsIgnoreCase("b")) {
                if (page > 1) page--;
                continue;
            }
            try {
                int apply = Integer.parseInt(next);

                if (apply == 0) {
                    return;
                }

                // 신청 로직 수행
                int result = pc.groupBuying(apply);
                if (result == 1) {
                    System.out.println("공동구매 신청 성공");
                    return;
                } else if (result == 2) {
                    System.out.println("[경고] 본인이 등록한 물품은 신청할 수 없습니다.");
                } else if (result == 3) {
                    System.out.println("[경고] 이미 신청한 물품입니다.");
                } else if (result == 4) {
                    System.out.println("[경고] 신청 실패: 모집 인원이 이미 가득 찼습니다.");
                } else {
                    System.out.println("[오류] 신청에 실패했습니다.");
                }

            } catch (NumberFormatException e) {
                System.out.println("[알림] 올바른 번호(숫자)나 'p', 'b'를 입력해주세요.");
            }
        } //while end
    } //findAll end


    //공동구매 참여취소
    public void GroupCancel() {
        ArrayList<ProductDto> products = pc.mylist();

        System.out.println("========================== 내 구매 신청 목록 ==========================");
        for(ProductDto product : products){
            System.out.printf(" 번호 : %d , 제품명 : %s , 가격 : %d , 등록일 : %s , 오픈채팅방링크 : %s \n",
                    product.getPno() , product.getPname() , product.getPprice() , product.getPdate() , product.getOpenchat());
        }
        System.out.println("====================================================================");
        System.out.print("취소하고싶은 게시물 번호를 입력하세요.");
        int pno = scan.nextInt();
        if(uc.isTimerOff()) return;
        System.out.print("비밀번호 입력:");
        String pwd = scan.next();
        if(uc.isTimerOff()) return;
        boolean result = pc.GroupCancel(pno,pwd);
    }


    //내가 올린 게시물 삭제
    public void BoardCancel() {
        ArrayList<ProductDto> products = pc.myUpList();

        System.out.println("========================== 내가 등록한 물품 목록 ==========================");
        for(ProductDto product : products){
            System.out.printf(" 번호 : %d , 제품명 : %s , 가격 : %d , 등록일 : %s , 오픈채팅방링크 : %s \n",
                    product.getPno() , product.getPname() , product.getPprice() , product.getPdate() , product.getOpenchat());
        }
        System.out.println("====================================================================");

        System.out.println("삭제할 게시물 번호를 입력해주세요.");
        int pno = scan.nextInt();
        if(uc.isTimerOff()) return;

        System.out.print("비밀번호 입력:");
        String pwd = scan.next();
        if(uc.isTimerOff()) return;
        boolean result = pc.BoardCancel(pno,pwd);
    }

    // 내 구매 신청 목록 조회
    public void mylist(){
        ArrayList<ProductDto> products = pc.mylist();

        System.out.println("========================== 내 구매 신청 목록 ==========================");
        for(ProductDto product : products){
            System.out.printf(" 번호 : %d , 제품명 : %s , 가격 : %d , 등록일 : %s , 오픈채팅방링크 : %s \n",
                    product.getPno() , product.getPname() , product.getPprice() , product.getPdate() , product.getOpenchat());
        }
        System.out.println("====================================================================");
    }

    // 내가 등록한 물품 목록 조회
    public void myUpList(){
        ArrayList<ProductDto> products = pc.myUpList();

        System.out.println("========================== 내가 등록한 물품 목록 ==========================");
        for(ProductDto product : products){
            System.out.printf(" 번호 : %d , 제품명 : %s , 가격 : %d , 등록일 : %s , 오픈채팅방링크 : %s \n",
                    product.getPno() , product.getPname() , product.getPprice() , product.getPdate() , product.getOpenchat());
        }
        System.out.println("====================================================================");
    }



    public void productDetail() {
        mylist();
        System.out.print("상태 변경할 물품 번호 입력 : ");
        int pno = scan.nextInt();
        System.out.println("----------------------- [ 거래 현황 ] -----------------------");
        if(uc.isTimerOff()) return;
        System.out.println("----------------------- [ 거래 관리 ] -----------------------");
        System.out.println(" 1. 거래준비(동의) ✅ | 2. 포인트 입금 💰 | 3. 거래완료 🏁 | 0. 뒤로가기");
        System.out.print(" 선택 > ");
        int ch = scan.nextInt();
        if(uc.isTimerOff()) return;
        if (ch == 0) return;
        int uno = uc.getLoginSession();

        // 1. 거래준비
        if (ch == 1) {
            int result = pc.tradeStart(pno, uno);
            if (result == 1) {System.out.println("[안내] 준비완료! 모든 참여자가 준비되면 입금이 가능합니다.");
            } else if (result == 3) {System.out.println("[경고] 이미 준비완료 되었습니다.");
            } else if (result == 2) {System.out.println("[오류] 신청하지 않은 글이거나 해당 물품 정보가 없습니다.");
            } else {System.out.println("[오류] 시스템 장애가 발생했습니다.");}
        }
        //입금
        else if (ch == 2) {
            int result = pc.payPoint(pno, uno);
            switch (result) {
                case 1: System.out.println("[안내] 입금 성공"); break;
                case 2: System.out.println("[경고] 잔액이 부족합니다. 포인트를 충전해주세요."); break;
                case 4: System.out.println("[경고] 아직 모집 인원이 다 차지 않았습니다."); break;
                case 5: System.out.println("[경고] 모든 인원이 준비완료 상태가 아닙니다"); break;
                case 6: System.out.println("[안내] 등록자는 본인 글에 입금할 수 없습니다."); break;
                case 7: System.out.println("[경고] 이미 입금이 완료된 상태입니다."); break;
                case 8: System.out.println("[경고] 이미 종료된 거래입니다. "); break;
                case 9: System.out.println("[경고] 먼저 거래준비 버튼을 눌러주세요"); break;
                default: System.out.println("[오류] 입금 처리 중 알 수 없는 에러 발생."); break;
            }
        }
        //거래완료
        else if (ch == 3) {
            int result = pc.complete(pno, uno);
            switch (result) {
                case 1:
                    System.out.println("[안내] 거래 완료");
                    break;
                case 2: System.out.println("[경고] 신청한 글에만 상태 변경이 가능합니다"); break;
                case 3: System.out.println("[경고] 입금을 먼저 완료해야 거래완료가 가능합니다."); break;
                case 4: System.out.println("[안내] 이미 최종 거래 완료 처리가 된 상품입니다."); break;
                case 5: System.out.println("[안내] 아직 모든 인원이 입금완료 상태가 아닙니다");
                default: System.out.println("[오류] 완료 처리 중 장애가 발생했습니다."); break;
            }
        }
        }
    }// class END
