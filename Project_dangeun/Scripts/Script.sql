SELECT * FROM PRODUCT
DELETE FROM product WHERE p_name='스티크'
INSERT  INTO PRODUCT values(product_seq.nextval,'test','스티커',1000,'좋아요','electronic',0,sysdate,sysdate,'keyboard.jpg','판매중');
INSERT  INTO PRODUCT values(product_seq.nextval,'test','dddd',2000,'좋아요','fashion',30,sysdate,sysdate,'keyboard.jpg','판매완료')

SELECT * FROM "MEMBER"
DROP TABLE address

CREATE TABLE address(
a_id varchar2(5) PRIMARY KEY,
a_name varchar2(50) NOT null
);

SELECT * FROM address
UPDATE member SET m_loc ='종로구' where m_id='test' 
SELECT * FROM "MEMBER" 
SELECT * FROM "MEMBER" 
DROP TABLE PRODUCT 
SELECT * FROM PRODUCT

CREATE TABLE product(
   p_id number(5) PRIMARY KEY,
   p_m_id varchar2(20) NOT NULL,
   p_name varchar2(50) NOT NULL,
   p_price number(10) NOT NULL,
   p_loc varchar2(50)NOT NULL,
   p_desc varchar2(500) NOT NULL,
   p_category varchar2(20) NOT NULL,
   p_view number(5) NOT NULL,
   p_uploadDate DATE NOT NULL,
   p_updateDate DATE NOT null,
   p_photo varchar2(50),
   p_state varchar2(20) NOT null,
   CONSTRAINT product_member_id
      FOREIGN key(p_m_id) REFERENCES member(m_id)
      ON DELETE cascade
   CONSTRAINT product_member_loc
      FOREIGN key(p_loc) REFERENCES member(m_loc)
      ON DELETE cascade
);
