package org.example.unitshare2.controller;

import unitshare.model.dao.ProductDao;
import unitshare.model.dto.ProductDto;
import unitshare.view.UserView;

import java.util.ArrayList;

public class ProductController {
    // 싱글톤 생성
    private ProductController(){}
    private static final ProductController instance = new ProductController();
    public static ProductController getInstance(){return instance;}




    private ProductDao pd = ProductDao.getInstance();
    private UserController uc = UserController.getInstance();


    //20. 물품등록
    public int productAdd(String pname , int pprice , String pcontent , int people , String openchat){
        int uno = uc.getLoginSession();
        int result = pd.productAdd(pname, pprice, pcontent, people, openchat , uno);

        int result2 =pd.myGroupBuying(result , uno);
        return result2;
    }



    //21. 전체 공동구매 목록조회
    public ArrayList<ProductDto> findAll(int page, int pageSize) {
        return pd.findAll(page, pageSize);
    }

    public int getTotalPages(int pageSize) {
        int totalCount = pd.getTotalCount();

        int totalPages = totalCount / pageSize;
        if (totalCount % pageSize > 0) {
            totalPages++;
        }
        if (totalPages == 0) return 1;

        return totalPages;
    }


    //공동구매 취소
    public boolean  GroupCancel(int pno,String pwd) {
        boolean result = pd.GroupCancel(pno,pwd);
        if (result) {
            System.out.println("공동구매가 정상적으로 취소되었습니다.");
        } else {
            System.out.println("취소");
        }
        return result;
    }


    //자신이 등록한 물품 등록취소 함수
    public boolean BoardCancel(int pno, String pwd) {
        boolean result = pd.BoardCancel(pno, pwd);
        return result;
    }


    // 내 구매 신청 목록 조회(mylist)
    public ArrayList<ProductDto> mylist(){
        // 현재 로그인된 유저 번호를 가져온다.
        int loginNo = UserController.getInstance().getLoginSession();

        return pd.mylist(loginNo);
    }

    // 내가 등록한 물품 목록 조회
    public ArrayList<ProductDto> myUpList(){
        int loginNo = UserController.getInstance().getLoginSession();

        return pd.myuplist(loginNo);
    }

    //공동구매 신청
    public int groupBuying(int pno){
        int uno = uc.getLoginSession();
        return pd.groupBuying(pno , uno);
    }
    //거래시작
    public int tradeStart(int pno , int uno){
        return pd.tradeStart(pno , uno);
    }
    // 1. 포인트 입금(전송) 컨트롤러
    public int payPoint(int pno , int uno) {
        return pd.payPoint(pno , uno);
    }
    //거래완료 상태
    public int complete(int pno , int uno){
        return pd.complete(pno , uno);
    }


}
