package org.example.unitshare2.service;

import unitshare.controller.ProductController;
import org.example.unitshare2.dto.ProductDto;

import java.sql.*;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class ProductDao {
    //싱글톤 생성
    private ProductDao() {
        connect();
    }

    private static final ProductDao instance = new ProductDao();

    public static ProductDao getInstance() {
        return instance;
    }

    //db 연동
    private String url = "jdbc:mysql://localhost:3306/unishare";
    private String user = "root";
    private String pw = "1234";
    private Connection conn;

    private void connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, user, pw);
            System.out.println("연동성공");
        } catch (Exception e) {
            System.out.println("연동실패");
        }
    }

    //21. 물품등록
    public int productAdd(String pname, int pprice, String pcontent, int people, String openchat, int uno) {
        try {

            String sql = "insert into product(pname , pprice , pcontent , people , openchat , uno)values(?,?,?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, pname);
            ps.setInt(2, pprice);
            ps.setString(3, pcontent);
            ps.setInt(4, people);
            ps.setString(5, openchat);
            ps.setInt(6, uno);
            int count = ps.executeUpdate();
            if (count == 1) {

                String sql2 = "select * from product order By pno desc limit 1";
                PreparedStatement ps1 = conn.prepareStatement(sql2);
                ResultSet rs = ps1.executeQuery();
                if (rs.next()) {
                    return rs.getInt("pno");
                }
            } else {
                return 0;
            }
        } catch (SQLException e) {
            System.out.println("[시스템오류] SQL 문법 문제발행" + e);
        }
        return 0;
    }


    //공동구매 참여취소:
    public boolean GroupCancel(int pno, String pwd) {
        try {
            String sql = "delete p from participant p inner join user u on p.uno=u.uno where p.pno=? and u.pwd=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, pno);
            ps.setString(2, pwd);
            int count = ps.executeUpdate();
            if (count == 1) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            System.out.println("sql문법 오류발생" + e);
        }
        return false;
    }


    //내가 올린 물품 등록 취소
    public boolean BoardCancel(int pno, String pwd) {
        try {
            String sql = "delete p from product p inner join user u on p.uno=u.uno where p.pno=? and u.pwd=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps = conn.prepareStatement(sql);
            ps.setInt(1, pno);
            ps.setString(2, pwd);
            int count = ps.executeUpdate();
            if (count == 1) {
                System.out.println("삭제 성공");
            } else {
                System.out.println("삭제실패");
            }
        } catch (SQLException e) {
            System.out.println("sql오류" + e);
            return false;
        }
        return false;
    }



    //공동구매 신청 조회
    public ArrayList<ProductDto> findAll(int page, int pageSize) {
        ArrayList<ProductDto> products = new ArrayList<>();
        int Start = (page - 1) * pageSize; //시작번호(pno)=페이지번호-1*5 :
        try {
            String sql = "SELECT *, (SELECT COUNT(*) FROM participant WHERE participant.pno = product.pno) AS cpeople FROM product ORDER BY pno DESC LIMIT ?, ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, Start);
            ps.setInt(2, pageSize);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int pno = rs.getInt("pno");
                String pname = rs.getString("pname");
                int pprice = rs.getInt("pprice");
                String pcontent = rs.getString("pcontent");
                String pdate = rs.getString("pdate");
                String openchat = rs.getString("openchat");
                int people = rs.getInt("people");
                int cpeople = rs.getInt("cpeople");
                ProductDto productDto = new ProductDto(pno, pname, pprice, pcontent, pdate, openchat, people, cpeople);
                products.add(productDto);
            }
        } catch (SQLException e) {
            System.out.println("sql 문법문제 2" + e);
        }
        return products;
    }

    //전체 상품갯수가 몇인지 카운트로 개수
    public int getTotalCount() {
        try {
            String sql = "SELECT COUNT(*) FROM product";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return 0;
    }


    // 내 구매 신청 목록 조회
    public ArrayList<ProductDto> mylist(int uno) {
        ArrayList<ProductDto> products = new ArrayList<>();

        String sql = "SELECT p.* FROM product p INNER JOIN participant t ON p.pno = t.pno WHERE t.uno = ?";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, uno);


            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ProductDto productDto = new ProductDto(
                        rs.getInt("pno"),
                        rs.getString("pname"),
                        rs.getInt("pprice"),
                        rs.getString("pdate"),
                        rs.getString("openchat")
                );

                products.add(productDto);
            } // whi END
        } catch (SQLException e) {
            System.out.println("[시스템 오류] sql 문법문제 발생" + e);
        }
        return products;
    } // m END

    // 내가 등록한 물품 목록 조회
    public ArrayList<ProductDto> myuplist(int uno) {
        ArrayList<ProductDto> products = new ArrayList<>();
        String sql = "SELECT * FROM product WHERE uno = ? ";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, uno);


            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ProductDto productDto = new ProductDto(
                        rs.getInt("pno"),
                        rs.getString("pname"),
                        rs.getInt("pprice"),
                        rs.getString("pdate"),
                        rs.getString("openchat")
                );
                products.add(productDto);
            } // whi END
        } catch (SQLException e) {
            System.out.println("[시스템 오류] sql 문법문제 발생" + e);
        }
        return products;
    } // m END

    //등록자 참여자 신청
    public int myGroupBuying(int pno, int uno) {
        try {
            String sql4 = "insert into participant(pno , uno , status)values (?,?,0)";
            PreparedStatement ps4 = conn.prepareStatement(sql4);
            ps4.setInt(1, pno);
            ps4.setInt(2, uno);
            if (ps4.executeUpdate() == 1) return 1;
        } catch (Exception e) {
            System.out.println("sql오류");
        }
        return 0;
    }

    //공동구매 신청
    public int groupBuying(int pno, int uno) {
        try {

            //등록자와 신청자가 같을시 2반환
            String sql = "select uno,people from product where pno = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, pno);
            ResultSet rs = ps.executeQuery();
            int addUno = -1;
            int maxPeople = 0;
            if (rs.next()) {
                addUno = rs.getInt("uno");
                maxPeople = rs.getInt("people");
            }
            if (addUno == uno) return 2;

            //현재 신청 인원이 최대인원보다 크거나 같으면 4반환
            String sql2 = "select count(*) from participant where pno = ?";
            PreparedStatement ps2 = conn.prepareStatement(sql2);
            ps2.setInt(1, pno);
            ResultSet rs2 = ps2.executeQuery();
            if (rs2.next()) {
                int npeople = rs2.getInt(1);
                if (npeople == maxPeople) return 4;
            }
            //내가 이미 신청한 게시물이면 3반환
            String sql3 = "select count(*) from  participant where pno = ? and uno = ?";
            PreparedStatement ps3 = conn.prepareStatement(sql3);
            ps3.setInt(1, pno);
            ps3.setInt(2, uno);
            ResultSet rs3 = ps3.executeQuery();
            if (rs3.next()) {
                int myApply = rs3.getInt(1);
                if (myApply > 0) return 3;
            }

            String sql4 = "insert into participant(pno , uno , status)values (?,?,0)";
            PreparedStatement ps4 = conn.prepareStatement(sql4);
            ps4.setInt(1, pno);
            ps4.setInt(2, uno);
            if (ps4.executeUpdate() == 1) return 1;

        } catch (Exception e) {
            System.out.println("sql오류");
        }
        return 0;
    }

    //거래 준비
    public int tradeStart(int pno, int uno) {
        try {
            // 내 현재 상태 확인
            String sql1 = "select status from participant where pno = ? and uno = ?";
            PreparedStatement ps1 = conn.prepareStatement(sql1);
            ps1.setInt(1, pno);
            ps1.setInt(2, uno);
            ResultSet rs1 = ps1.executeQuery();

            if (rs1.next()) {
                int currentStatus = rs1.getInt("status");

                // 중복준비 방지
                if (currentStatus >= 1) {
                    System.out.println("[안내] 이미 준비완료 되었거나 진행 중인 거래입니다.");
                    return 3; // 이미 처리됨 코드
                }
            } else {
                // 참여 명단에 없는 사람이 접근한 경우
                return 2;
            }

            // 준비완료로 변경
            String sql2 = "update participant set status = 1 where pno = ? and uno = ?";
            PreparedStatement ps2 = conn.prepareStatement(sql2);
            ps2.setInt(1, pno);
            ps2.setInt(2, uno);

            int result = ps2.executeUpdate();

            if (result == 1) {
                return 1; // 준비완료
            } else {
                return 2; // 업데이트 실패
            }
        } catch (Exception e) {
            System.out.println("거래준비오류: " + e);
        }
        return 0; // 에러
    }

    //입금함수
    public int payPoint(int pno, int uno) {
        try {
            // 현재 상태 확인 (중복 입금 및 완료된 거래 방지)
            String sql0 = "select status from participant where pno = ? and uno = ?";
            PreparedStatement ps0 = conn.prepareStatement(sql0);
            ps0.setInt(1, pno);
            ps0.setInt(2, uno);
            ResultSet rs0 = ps0.executeQuery();

            if (rs0.next()) {
                int currentStatus = rs0.getInt("status");
                if (currentStatus == 3) return 8; // 이미 거래 완료됨
                if (currentStatus == 2) return 7; // 이미 입금 완료됨
                if (currentStatus == 0) return 9; // 아직 준비완료를 안 누름
            } else {
                return 2; // 신청안한사람
            }

            // 등록자인지 확인
            String sql1 = "select uno, people, pprice from product where pno = ?";
            PreparedStatement ps1 = conn.prepareStatement(sql1);
            ps1.setInt(1, pno);
            ResultSet rs1 = ps1.executeQuery();

            int ownerUno = -1;
            int totalPeople = 0;
            int pprice = 0;
            if (rs1.next()) {
                ownerUno = rs1.getInt("uno");
                totalPeople = rs1.getInt("people");
                pprice = rs1.getInt("pprice");
            }
            if (ownerUno == uno) return 6; // 주최자는 입금 대상이 아님

            // 인원수가 다 찼는지 확인
            String sql3 = "select count(*) from participant where pno = ?";
            PreparedStatement ps3 = conn.prepareStatement(sql3);
            ps3.setInt(1, pno);
            ResultSet rs3 = ps3.executeQuery();
            if (rs3.next()) {
                if (rs3.getInt(1) < totalPeople) return 4; // 인원 미달
            }

            // 모든 인원이 준비완료인지 확인
            String sql4 = "select count(*) from participant where pno = ? and status >= 1";
            PreparedStatement ps4 = conn.prepareStatement(sql4);
            ps4.setInt(1, pno);
            ResultSet rs4 = ps4.executeQuery();
            if (rs4.next()) {
                if (rs4.getInt(1) < totalPeople) return 5; // 준비 안 된 인원 있음
            }

            // 포인트 차감
            int perPrice = pprice / totalPeople; // 1인당 가격 계산
            String sql5 = "update user set point = point - ? where uno = ? and point >= ?";
            PreparedStatement ps5 = conn.prepareStatement(sql5);
            ps5.setInt(1, perPrice);
            ps5.setInt(2, uno);
            ps5.setInt(3, perPrice);

            if (ps5.executeUpdate() == 1) {
                // 참여 상태를 변경
                String sql6 = "update participant SET status = 2 WHERE pno = ? AND uno = ?";
                PreparedStatement ps6 = conn.prepareStatement(sql6);
                ps6.setInt(1, pno);
                ps6.setInt(2, uno);
                ps6.executeUpdate();
                return 1; // 입금 및 상태 변경 성공
            } else {
                return 2; // 잔액 부족 (또는 유저 정보 없음)
            }

        } catch (Exception e) {
            System.out.println("입금 로직 오류: " + e.getMessage());
        }
        return 0; // 시스템 에러
    }

    //거래완료
    public int complete(int pno, int uno) {
        try {
            // 등록자 ,총 인원과 가격은 얼마인지 가져오기
            String sql1 = "SELECT uno, pprice, people from product WHERE pno = ?";
            PreparedStatement ps1 = conn.prepareStatement(sql1);
            ps1.setInt(1, pno);
            ResultSet rs1 = ps1.executeQuery();
            int hostUno = -1;
            int pprice = 0;
            int totalPeople = 0;
            if (rs1.next()) {
                hostUno = rs1.getInt("uno");
                pprice = rs1.getInt("pprice");
                totalPeople = rs1.getInt("people");
            }
            boolean isOwner = (hostUno == uno);


            //전원 입금 완료 상태 여부 확인
            String sql6 = "select count(*) from participant where pno = ? and status < 2";
            PreparedStatement ps6 = conn.prepareStatement(sql6);
            ps6.setInt(1, pno);
            ResultSet rs6 = ps6.executeQuery();

            if (rs6.next()) {
                int notPaidCount = rs6.getInt(1);
                if (notPaidCount > 1) {
                    return 5; // 전원 입금 미완료
                }
            }


            // 내 참여 상태 확인
            String sql2 = "SELECT status from participant WHERE pno = ? AND uno = ?";
            PreparedStatement ps2 = conn.prepareStatement(sql2);
            ps2.setInt(1, pno);
            ps2.setInt(2, uno);
            ResultSet rs2 = ps2.executeQuery();

            if (rs2.next()) {
                int currentStatus = rs2.getInt("status");
                if (currentStatus == 3) return 4; // 이미 완료된 상태
                // 등록자가 아닌데 입금(2)도 안 한 상태에서 완료를 누르면 거부
                if (!isOwner && currentStatus < 2) return 3;
            } else {
                return 2; // 명단에 없음
            }


            // 상태를 거래완료로 변경
            String sql3 = "UPDATE participant SET status = 3 WHERE pno = ? AND uno = ?";
            PreparedStatement ps3 = conn.prepareStatement(sql3);
            ps3.setInt(1, pno);
            ps3.setInt(2, uno);

            int result = ps3.executeUpdate();

            if (result == 1) {
                //모든 사람이 완료 상태인지 카운트
                String sql4 = "SELECT count(*) from participant WHERE pno = ? AND status = 3";
                PreparedStatement ps4 = conn.prepareStatement(sql4);
                ps4.setInt(1, pno);
                ResultSet rs4 = ps4.executeQuery();

                if (rs4.next()) {
                    int completeCount = rs4.getInt(1);

                    // 모든 인원이 완료를 눌렀다면 최종 정산 실행
                    if (completeCount == totalPeople) {
                        int perPrice = pprice / totalPeople;
                        int finalAmount = pprice - perPrice;
                        // 주최자에게 보관 중이던 제품 총액(pprice) 입금
                        String sql5 = "update user SET point = point + ? WHERE uno = ?";
                        PreparedStatement ps5 = conn.prepareStatement(sql5);
                        ps5.setInt(1, finalAmount);
                        ps5.setInt(2, hostUno);

                        if (ps5.executeUpdate() == 1) {
                            System.out.println("전원 거래 완료 주최자에게 정산되었습니다.");
                        }
                    }
                }
                return 1; // 내 상태 변경 성공 정산 여부와 관계없이 본인 처리는 끝
            } else {
                return 2; // 업데이트 실패 쿼리는 돌았으나 영향받은 행 없음
            }

        } catch (Exception e) {
            System.out.println("거래완료 및 정산 오류: " + e.getMessage());
        }
        return 0; // 시스템/SQL 오류
    }

}

