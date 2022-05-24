package com.javaex.phone;

import java.util.List;
import java.util.Scanner;


public class PhoneApp {

	public static void main(String[] args) {
		
		boolean flag = true;
		Scanner sc = new Scanner(System.in);
		PhoneDao phoneDao = new PhoneDao();
		String name = null, hp = null, company = null;
		int idNum;
		
		System.out.println("**********************************************");
		System.out.println("*           전화번호 관리 프로그램           *");
		System.out.println("**********************************************");
		
		while(flag) {

			System.out.println();
			System.out.println("1.리스트   2.등록   3.수정   4.삭제   5.검색   6.종료");
			System.out.println("-------------------------------------------------------");
			System.out.print(">메뉴번호: ");
			int num = sc.nextInt();
			
			switch(num) {
				case 1: //전체 리스트 출력
					System.out.println("<1.리스트>");
					List<PersonVo> personList = phoneDao.phoneSelect();
					
					for(PersonVo p : personList) {
						System.out.println(p.getPersonId() + ".\t"
										+ p.getName() + "\t"
										+ p.getHp() + "\t"
										+ p.getCompany());
					}
					break;
					
					
				case 2: //리스트에 등록
					System.out.println("<2.등록>");
					sc.nextLine();
					System.out.print("이름 > ");
					name = sc.nextLine();
					System.out.print("휴대전화 > ");
					hp = sc.nextLine();
					System.out.print("회사전화 > ");
					company = sc.nextLine();
					
					phoneDao.phoneInsert(new PersonVo(name, hp, company));
					
					break;
					
					
				case 3: //리스트에서 수정
					System.out.println("<3.수정>");
					System.out.print("번호 > ");
					idNum = sc.nextInt();
					sc.nextLine();
					System.out.print("이름 > ");
					name = sc.nextLine();
					System.out.print("휴대전화 > ");
					hp = sc.nextLine();
					System.out.print("회사전화 > ");
					company = sc.nextLine();
					
					phoneDao.phoneUpdate(new PersonVo(idNum, name, hp, company));
					
					break;
					
					
				case 4: //리스트에서 삭제
					System.out.println("<4.삭제>");
					System.out.print("번호 > ");
					idNum = sc.nextInt();
					
					phoneDao.phoneDelete(idNum);
									
					break;
							
					
				case 5: //리스트에서 검색
					System.out.println("<5.검색>");
					sc.nextLine();
					System.out.print("검색어 > ");
					String keyword = sc.nextLine();
					
					List<PersonVo> psearchList = phoneDao.phoneSelect(keyword);
					
					for(PersonVo p : psearchList) {
						System.out.println(p.getPersonId() + ".\t"
								+ p.getName() + "\t"
								+ p.getHp() + "\t"
								+ p.getCompany());
					}
					
					break;
					
					
				case 6: //종료
					System.out.println("**********************************************");
					System.out.println("*                 감사합니다                 *");
					System.out.println("**********************************************");
					flag = false;
					sc.close();
					break;
					
					
				default: //없는 메뉴 입력했을때
					System.out.println("[다시 입력해 주세요.]");
					break;
			}
		}
	}
}
