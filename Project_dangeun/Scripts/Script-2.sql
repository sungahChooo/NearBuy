SELECT * FROM product
SELECT * FROM MEMBER;
UPDATE "MEMBER" SET m_loc='서울특별시 중구' WHERE m_loc='서울특별시중구'
CREATE TABLE WISHLIST (
 w_id number(5) PRIMARY KEY,
   w_m_id varchar2(20) NOT NULL,
   w_p_id number(5) NOT NULL,
   CONSTRAINT wishlist_member_id
      FOREIGN key(w_m_id) REFERENCES member(m_id)
      ON DELETE CASCADE,
   CONSTRAINT wishlist_product_id
      FOREIGN key(w_p_id) REFERENCES pro(p_id)
      ON DELETE cascade
);
SELECT * FROM WISHLIST 
CREATE SEQUENCE wishlist_seq START WITH 1 INCREMENT BY 1;
DROP SEQUENCE wishlist_seq;
DROP TABLE WISHLIST 
DELETE FROM WISHLIST WHERE w_m_id='test' 
INSERT INTO WISHLIST values(wishlist_seq.nextval,'test',31)
SELECT * FROM MEMBER
INSERT INTO MEMBER values('test','홍길동',sysdate,NULL,'1111','logo.png',0,'서울 종로구 관철동!솔데스크10층!903호')
DELETE FROM PRODUCT WHERE p_m_id='test'
UPDATE member SET m_loc=NULL WHERE m_id='test'

INSERT INTO PRODUCT values(product_seq.nextval,'test','키보드',3000,'dd','전자',0,sysdate,sysdate,'logo.png','판매중','서울특별시종로구')