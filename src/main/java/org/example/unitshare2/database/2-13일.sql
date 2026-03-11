
drop database if exists unishare;
create database unishare;
use unishare;

create table user(
                     uno int AUTO_INCREMENT,CONSTRAINT PRIMARY KEY(uno),
                     id varchar(15) not null UNIQUE,
                     pwd varchar(15) not null,
                     phone varchar(15) not null unique,
                     name varchar(10) not null unique,
                     loginState tinyint default 0);
select*from user;

CREATE TABLE product (
                         pno INT AUTO_INCREMENT PRIMARY KEY,          -- 물품번호 (PK)
                         pname VARCHAR(50) NOT NULL,                  -- 제품명
                         pprice INT NOT NULL,                         -- 가격
                         pcontent VARCHAR(30) NOT NULL,               -- 설명
                         pdate DATETIME DEFAULT NOW(),                -- 등록일
                         plink VARCHAR(100),                          -- 오픈채팅링크 (추가)
                         people INT(10) NOT NULL,                     -- 인원수
                         uno INT,                                     -- 회원번호 (FK)
                         CONSTRAINT fk_user_uno FOREIGN KEY (uno) REFERENCES user(uno)
);
select*from product;

CREATE TABLE participant (
                             tno INT AUTO_INCREMENT PRIMARY KEY,          -- 참여자번호 (PK)
                             status TINYINT DEFAULT 0,                    -- 참가 상태 (0: 대기, 1: 승인, 2: 거절 등)
                             pno INT,                                     -- 물품번호 (FK)
                             uno INT,                                     -- 회원번호 (FK)
                             CONSTRAINT fk_participant_pno FOREIGN KEY (pno) REFERENCES product(pno) on delete CASCADE,
                             CONSTRAINT fk_participant_uno FOREIGN KEY (uno) REFERENCES user(uno)
);
select*from participant;

create table loginStatement(
logNo int auto_increment primary key, -- 회원로그(PK)
loginState tinyint default 0,                  -- 로그인상태
loginTime datetime default now(),          -- 접속날짜/시간
uno int,                                                          -- 회원번호(FK)
constraint fk_loginStatement_uno Foreign key (uno) references user(uno)
);
select*from loginStatement;

INSERT INTO user (id, pwd, phone, name, loginState) VALUES
                                           ('admin', 'pw1234', '010-1111-0000', '관리자', 0),
                                           ('buyer01', 'pass01', '010-2222-1111', '김철수', 0),
                                           ('seller01', 'sell01', '010-3333-2222', '이영희', 0),
                                           ('user33', 'user33!', '010-4444-3333', '박지민', 0),
                                           ('nana', 'nana123', '010-5555-4444', '최유나', 0),
                                           ('lucky7', '7777', '010-6666-5555', '정대만', 1),
                                           ('coding', 'java88', '010-7777-6666', '강백호', 0),
                                           ('hany', 'hany99', '010-8888-7777', '송태섭', 1),
                                           ('star', 'star00', '010-9999-8888', '서태웅', 0),
                                           ('coffee', 'latte5', '010-1010-9999', '권준호', 0);

INSERT INTO product (pname, pprice, pcontent, pdate, plink, people, uno) VALUES
                                                                             ('청소기', 5000, '거의 새상품', '2026-02-09', 'https://open.kakao.com/o/s1', 1, 1),
                                                                             ('후드티', 5500, '거의 새상품', '2026-02-10', 'https://open.kakao.com/o/s2', 2, 2),
                                                                             ('샤넬 가방', 6000, '거의 새상품', '2026-02-11', 'https://open.kakao.com/o/s3', 3, 3),
                                                                             ('아이폰20', 6500, '거의 새상품', '2026-02-12', 'https://open.kakao.com/o/s4', 4, 4),
                                                                             ('갤럭시100', 7000, '거의 새상품', '2026-02-13', 'https://open.kakao.com/o/s5', 5, 5),
                                                                             ('하츄핑', 7500, '거의 새상품', '2026-02-14', 'https://open.kakao.com/o/s6', 6, 6),
                                                                             ('책', 8000, '거의 새상품', '2026-02-15', 'https://open.kakao.com/o/s7', 5, 7),
                                                                             ('에어포스', 8500, '거의 새상품', '2026-02-16', 'https://open.kakao.com/o/s8', 5, 8),
                                                                             ('광케이블 리퍼', 9000, '거의 새상품', '2026-02-17', 'https://open.kakao.com/o/s9', 5, 9),
                                                                             ('네트워크 허브', 9500, '거의 새상품', '2026-02-18', 'https://open.kakao.com/o/s10', 5, 10);


INSERT INTO participant (status, pno, uno) VALUES
                                               (1, 1, 2), -- 2번 회원이 1번 물품 참여 승인
                                               (0, 1, 3), -- 3번 회원이 1번 물품 참여 대기
                                               (1, 2, 4), -- 4번 회원이 2번 물품 참여 승인
                                               (2, 2, 5), -- 5번 회원이 2번 물품 참여 거절
                                               (0, 3, 1), -- 1번 회원이 3번 물품 참여 대기
                                               (1, 4, 6), -- 6번 회원이 4번 물품 참여 승인
                                               (1, 5, 7), -- 7번 회원이 5번 물품 참여 승인
                                               (0, 6, 8), -- 8번 회원이 6번 물품 참여 대기
                                               (1, 7, 9), -- 9번 회원이 7번 물품 참여 승인
                                               (0, 8, 10); -- 10번 회원이 8번 물품 참여 대기

insert into loginStatement (logNo, loginState, loginTime, uno) values
(1,        0,        '2026-02-23 17:00:07',        1),
(2,        0,        '2026-02-23 17:00:20',        2),
(3,        0,        '2026-02-23 17:00:23',        3),
(4,        0,        '2026-02-23 17:00:11',        4),
(5,        0,        '2026-02-23 17:00:12',        5),
(6,        1,        '2026-02-22 17:00:00',        6),
(7,        0,        '2026-02-23 17:00:23',        7),
(8,        0,        '2026-02-23 17:00:23',        8),
(9,        1,        '2026-02-22 16:23:01',    9),
(10, 0,        '2026-02-23 17:00:23',        10);
select*from user;
use unishare;
select*from product;
alter table product change plink openchat LONGTEXT;
alter table user ADD point INT DEFAULT 0;
select*from participant;
select*from user;
select*from loginStatement;


delete p from product p inner join user u on p.uno=u.uno where p.pno=1 and u.pwd='pw1234';