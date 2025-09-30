CREATE TABLE MEMBER(
   m_id varchar2(20 char) PRIMARY KEY,
   m_name varchar2(20 char) NOT NULL,
   m_birth DATE NOT NULL,
   m_loc varchar2(100 char),
   m_pw varchar2(20 char) NOT NULL,
   m_photo varchar2(200 char),
   m_admin number(1) NOT null,
   m_addr varchar2(50 char) NOT null
);
INSERT INTO "MEMBER" values('test','홍길동',sysdate,'서울특별시 종로구','1111','ddd.png',0,'서울특별시!종로구!관철동!종로코아')

DROP TABLE PRODUCT CASCADE constraint 
CREATE TABLE product(
   p_id number(5) PRIMARY KEY,
   p_m_id varchar2(20 char) NOT NULL,
   p_name varchar2(50 char) NOT NULL,
   p_price number(10) NOT NULL,
   p_loc varchar2(50),
   p_desc varchar2(500 char) NOT NULL,
   p_category varchar2(20 char) NOT NULL,
   p_view number(5) NOT NULL,
   p_uploadDate DATE NOT NULL,
   p_updateDate DATE NOT null,
   p_photo varchar2(200 char),
   p_state varchar2(20 char) NOT null,
   CONSTRAINT product_member_id
      FOREIGN key(p_m_id) REFERENCES member(m_id)
      ON DELETE cascade
);

CREATE TABLE sold(
   s_id number(5) PRIMARY key,
   s_m_id varchar2(20 char) NOT NULL,
   s_p_id number(5) NOT NULL,
   s_date DATE NOT NULL,
   s_price number(10) NOT NULL,
   CONSTRAINT sold_member_id
      FOREIGN key(s_m_id) REFERENCES member(m_id)
      ON DELETE CASCADE,
   CONSTRAINT sold_product_id
      FOREIGN key(s_p_id) REFERENCES product(p_id)
      ON DELETE cascade
);

CREATE TABLE bought(
   b_id number(5) PRIMARY key,
   b_m_id varchar2(20 char) NOT NULL,
   b_p_id number(5) NOT NULL,
   b_date DATE NOT NULL,
   b_price number(10) NOT NULL,
   CONSTRAINT bought_member_id
      FOREIGN key(b_m_id) REFERENCES member(m_id)
      ON DELETE CASCADE,
   CONSTRAINT bought_product_id
      FOREIGN key(b_p_id) REFERENCES product(p_id)
      ON DELETE cascade
);
   
CREATE TABLE wishlist(
   w_id number(5) PRIMARY KEY,
   w_m_id varchar2(20) NOT NULL,
   w_p_id number(5) NOT NULL,
   CONSTRAINT wishlist_member_id
      FOREIGN key(w_m_id) REFERENCES member(m_id)
      ON DELETE CASCADE,
   CONSTRAINT wishlist_product_id
      FOREIGN key(w_p_id) REFERENCES product(p_id)
      ON DELETE cascade
);

CREATE TABLE chatroom(
   cr_id number(5) PRIMARY KEY,
   cr_p_id number(5) NOT NULL,
   cr_m_id varchar2(20 char) NOT NULL,
   cr_time timestamp NOT NULL,
   CONSTRAINT chatroom_member_id
      FOREIGN key(cr_m_id) REFERENCES member(m_id)
      ON DELETE CASCADE,
   CONSTRAINT chatroom_product_id
      FOREIGN key(cr_p_id) REFERENCES product(p_id)
      ON DELETE cascade
);

CREATE TABLE chatmessage(
   cm_id number(5) PRIMARY KEY,
   cm_cr_id number(5) NOT NULL,
   cm_m_id varchar2(20 char) NOT NULL,
   cm_text varchar2(100 char) NOT NULL,
   cm_time timestamp NOT NULL,
   CONSTRAINT chatmessage_cr_id
      FOREIGN key(cm_cr_id) REFERENCES chatroom(cr_id)
      ON DELETE CASCADE,
   CONSTRAINT chatmessage_m_id
      FOREIGN key(cm_m_id) REFERENCES member(m_id)
      ON DELETE cascade
);

CREATE TABLE townNews(
   t_id number(5) PRIMARY KEY,
   t_m_id varchar2(20 char) NOT NULL,
   t_title varchar2(50 char) NOT NULL,
   t_text varchar2(500 char) NOT NULL,
   t_photo varchar2(200 char),
   t_date DATE NOT NULL,
   CONSTRAINT townNews_m_id
      FOREIGN key(t_m_id) REFERENCES member(m_id)
      ON DELETE cascade
);


CREATE TABLE townNews_reply(
   tr_id number(5) PRIMARY KEY,
   tr_t_id number(5) NOT NULL,
   tr_m_id varchar2(20 char) NOT NULL,
   tr_text varchar2(100 char) NOT NULL,
   tr_date DATE NOT NULL,
   CONSTRAINT townNews_r_t_id
      FOREIGN key(tr_t_id) REFERENCES townNews(t_id)
      ON DELETE CASCADE,
   CONSTRAINT townNews_r_m_id
      FOREIGN key(tr_m_id) REFERENCES member(m_id)
      ON DELETE CASCADE
);

CREATE TABLE report(
   r_id number(5) PRIMARY KEY,
   r_m_writerId varchar2(20 char) NOT NULL,
   r_m_reportedId varchar2(20 char) NOT NULL,
   r_category varchar2(20 char) NOT NULL,
   r_text varchar2(500 char) NOT NULL,
   r_photo varchar2(200 char),
   r_date DATE NOT NULL,
   CONSTRAINT report_m_writerId
      FOREIGN key(r_m_writerId) REFERENCES member(m_id),
   CONSTRAINT report_m_reportedId
      FOREIGN key(r_m_reportedId) REFERENCES member(m_id)
);