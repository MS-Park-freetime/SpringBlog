package com.cos.blog.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity//User 클래스가 MySQL에 테이블이 생성이 된다.
//@DynamicInsert //insert할때 null인 필드 제외
public class User {
	
	@Id //primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY)//프로젝트에서 연결된 DB의 넘버링 전략을 따라간다.
	private int id;//oracle:시퀀스, mysql:auto_increment
	
	@Column(nullable = false, length=100, unique = true)
	private String username;
	
	@Column(nullable = false, length=100)//해쉬(비밀번호 암호화 할것)
	private String password;
	
	@Column(nullable = false, length = 50)
	private String email;
	
	//DB는 RoleType이라는게 없다.
	//RoleType에 입력된것만 입력될 수 있다.
	//@ColumnDefault("'user'")
	@Enumerated(EnumType.STRING)
	private RoleType role;//Enum을 쓰는게 좋다. //admin,user,manager
	
	private String oauth;//kakao 등 로그인 방식
	
	@CreationTimestamp // 시간이 자동입력
	private Timestamp createDate;

}
