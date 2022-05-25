package com.javaex.phone2;

import java.util.List;
import java.util.Scanner;

public class PhoneView {
	
	Scanner sc = new Scanner(System.in);

	//타이틀 출력 메소드
	public void showTitle() {
		System.out.println("**********************************************");
		System.out.println("*           전화번호 관리 프로그램           *");
		System.out.println("**********************************************");
	}
	
	
	//메뉴 출력 메소드
	public int showMenu() {

		System.out.println();
		System.out.println("1.리스트   2.등록   3.수정   4.삭제   5.검색   6.종료");
		System.out.println("-------------------------------------------------------");
		System.out.print(">메뉴번호: ");
		int num = sc.nextInt();
		
		return num;
	}
	
	
	//리스트 출력 메소드
	public void showList(List<PersonVo> personList) {
		for(PersonVo p : personList) {
			System.out.println(p.getPersonId() + ".\t"
							+ p.getName() + "\t"
							+ p.getHp() + "\t"
							+ p.getCompany());
		}
	}
	
	
	//프로그램 종료 메소드
	public boolean showEnd() {
		System.out.println("**********************************************");
		System.out.println("*                 감사합니다                 *");
		System.out.println("**********************************************");
		sc.close();
		
		return false;
	}
}
