package org.example.unitshare2.controller;
/*
public class TimerThread extends Thread {
        public boolean state = false;
        String id;
        int uno;
        UserController uc;
        public int second = 0; // 타이머 초 초기화
        // 생성자
        public TimerThread(){}

        public TimerThread(String id, int uno, UserController userController){
            this.id = id;
            this.uno = uno;
            this.uc = userController;
        }
        @Override
        public void run(){
            while(state){ // state가 true인 동안 반복
                try{ Thread.sleep(1000); // 1초 대기
                    second++;
                    // 타이머가 10초 지났을 때
                    if( second > 60){
                        System.out.println("[안내] 장기간 활동이 없으므로 메인페이지로 돌아갑니다.");
                        uc.getUd().loginStatement(uno); // DB를 '휴면계정'으로 상태 변경
                        uc.logout(); // 자동 로그아웃
                        // unitshare.view.UserView.getInstance().index(); // 메인으로 강제 이동
                        this.state = false; // 타이머 중지
                        break;
                    }
                }catch (Exception e){System.out.println("[경고] 휴면계정으로 전환 중에 문제가 발생했습니다." + e);}

            }// while end
        } // method(run) end
} // Thread class end

 */