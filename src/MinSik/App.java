package MinSik;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import MinSik.Util.Util;
import MinSik.dto.Article;
import MinSik.dto.Member;

public class App {
	List<Member> members = new ArrayList<>();
	List<Article> articles = new ArrayList<>();
	
	public void start() {
		Scanner sc = new Scanner(System.in);
		int lastArticleId = 3;
		int lastMemberId = 3;
		Member memberLogin = null;
		boolean isLogined = false;
		
		System.out.println("=== 프로그램 시작 ===");
		
		
		makeArticleTestDate();
		makeMemberTestDate();
		
		while (true) {
			
			System.out.print("명령어 ) ");
			String cmd = sc.nextLine().trim();
			
			if (cmd.equals("exit")) {
				break;
			}
			
			if (cmd.equals("article list")) {
				
				if (articles.size() == 0) {
					System.out.println("게시글이 없습니다.");
					return;
				}
				
				System.out.println("번호    /    제목");
				
				for (int i = articles.size() - 1; i >= 0; i--) {
					Article article = articles.get(i);
					System.out.printf("%4d    /    %s\n", article.id, article.title);
				}
			}
			
			if (cmd.equals("article write")) {
				
				if (isLogined == false) {
					System.out.println("로그인 후 이용해주세요");
					continue;
				}
				
				int id = lastArticleId + 1;
				String regDate = Util.getNowDateTime();
				System.out.println("=== 게시글 작성 ===");
				System.out.print("제목 ) ");
				String title = sc.nextLine();
				System.out.print("내용 ) ");
				String body = sc.nextLine();
				String writerName = memberLogin.name;
				int wrtierNo = memberLogin.id;
				
				Article article = new Article(id, regDate, regDate, writerName, title, body, wrtierNo);
				articles.add(article);
				
				System.out.printf("%d번 게시글이 작성되었습니다.\n", id);
				lastArticleId++;
				
			} else if (cmd.startsWith("article detail")) {
				
				String[] cmdDiv = cmd.split(" ");
				
				if (cmdDiv.length < 3) {
					System.out.println("명령어를 확인해주세요");
					return;
				}
				
				int id = Integer.parseInt(cmdDiv[2]);
				Article foundArticle = getArticleById(id);
				
				if (foundArticle == null) {
					System.out.printf("%d번 글은 존재하지 않습니다\n", id);
					return;
				}
				
				System.out.println("번호 ) " + foundArticle.id);
				System.out.println("작성날짜 ) " + foundArticle.regDate);
				System.out.println("수정날짜 ) " + foundArticle.updateDate);
				System.out.println("작성자 ) " + memberLogin.name);
				System.out.println("제목 ) " + foundArticle.title);
				System.out.println("내용 ) " + foundArticle.body);
				
			} else if (cmd.startsWith("article modify")) {
				
				if (isLogined == false) {
					System.out.println("로그인 후 이용해주세요");
					continue;
				}
				
				String[] cmdDiv = cmd.split(" ");
				
				if (cmdDiv.length < 3) {
					System.out.println("명령어를 확인해주세요");
					return;
				}
				
				int id = Integer.parseInt(cmdDiv[2]);
				Article foundArticle = getArticleById(id);
				
				if (foundArticle == null) {
					System.out.printf("%d번 글은 존재하지 않습니다\n", id);
					return;
				}
				
				String updateDate = Util.getNowDateTime();
				System.out.print("새 제목 ) ");
				String title = sc.nextLine();
				System.out.print("새 내용 ) ");
				String body = sc.nextLine();
				
				foundArticle.title = title;
				foundArticle.body = body;
				foundArticle.updateDate = updateDate;
				
				System.out.printf("%d번 게시글이 수정되었습니다.\n", id);
				
				
			} else if (cmd.startsWith("article delete")) {
				
				if (isLogined == false) {
					System.out.println("로그인 후 이용해주세요");
					continue;
				}
				
				String[] cmdDiv = cmd.split(" ");
				
				if (cmdDiv.length < 3) {
					System.out.println("명령어를 확인해주세요");
					return;
				}
				
				int id = Integer.parseInt(cmdDiv[2]);
				int foundIndex = getArticleByIndexId(id);
				
				if (foundIndex == -1) {
					System.out.printf("%d번 글은 존재하지 않습니다\n", id);
					return;
				}
				
				articles.remove(foundIndex);
				System.out.printf("%d번 게시글이 삭제되었습니다.\n", id);
				
			} else if (cmd.equals("member join")) {
				
				int id = lastMemberId + 1;
				String regDate = Util.getNowDateTime();
				String loginId;
				String loginPw;
				String name;
				
				if (isLogined == true) {
					System.out.println("로그아웃 후 이용해주세요");
					continue;
				}
				
				System.out.println("=== 회원가입 ===");
				
				while (true) {
					System.out.print("사용하실 아이디 ) ");
					loginId = sc.nextLine();
					
					if (isLoginedId(loginId) == false) {
						System.out.println("이미 사용중인 아이디입니다.");
						continue;
					}
					break;
				}
				
				while (true) {
					System.out.print("사용하실 비밀번호 ) ");
					loginPw = sc.nextLine();
					System.out.print("비밀번호 재확인 ) ");
					String loginPw2 = sc.nextLine();
					
					if (loginPw.equals(loginPw2) == false) {
						System.out.println("비밀번호가 일치하지 않습니다");
						continue;
					}
					
					while (true) {
						System.out.print("사용자 이름 ) ");
						name = sc.nextLine();
						
						if (name.length() < 1) {
							System.out.println("이름을 입력해주세요");
							continue;
						}
						break;
					}
					break;
				}
			
				
				Member member = new Member(id, regDate, regDate, loginId, loginPw, name);
				members.add(member);
				
				System.out.printf("%s님 회원가입이 완료 되었습니다.\n", name);
				lastMemberId++;
				
			} else if (cmd.equals("member login")) {
			
				System.out.print("로그인 아이디 ) ");
				String loginId = sc.nextLine();
				System.out.print("로그인 비밀번호 ) ");
				String loginPw = sc.nextLine();
				
				memberLogin = isLoginedMember(loginId);
				
				if (isLoginedId(loginId) == true) {
					System.out.println("존재하지 않는 아이디입니다.");
					continue;
				}
				
				if (memberLogin.loginPw.equals(loginPw) == false) {
					System.out.println("비밀번호가 일치하지 않습니다");
					continue;
				}
				
				System.out.printf("%s님 환영합니다.\n", memberLogin.name);
				isLogined = true;
				
			} else if (cmd.equals("member logout")) {
				
				System.out.printf("%s님 안녕히가세요.\n", memberLogin.name);
				isLogined = false;
			}
		}
		
		System.out.println("=== 프로그램 시작 ===");
		sc.close();
	}
	
	private Member isLoginedMember(String loginId) {
		
		for (Member member : members) {
			if (member.loginId.equals(loginId)) {
				return member;
			}
		}
		return null;
	}
	
	public void makeArticleTestDate() {
		System.out.println("게시글 테스트 데이터를 생성합니다.");
		articles.add(new Article(1, Util.getNowDateTime(), Util.getNowDateTime(), "제목1", "내용1", "홍길동", 1));
		articles.add(new Article(2, Util.getNowDateTime(), Util.getNowDateTime(), "제목2", "내용2", "홍길동", 1));
		articles.add(new Article(3, Util.getNowDateTime(), Util.getNowDateTime(), "제목3", "내용3", "김철수", 3));
	}
	
	public void makeMemberTestDate() {
		System.out.println("회원 테스트 데이터를 생성합니다.");
		members.add(new Member(1, Util.getNowDateTime(), Util.getNowDateTime(), "test1", "test1", "홍길동"));
		members.add(new Member(2, Util.getNowDateTime(), Util.getNowDateTime(), "test2", "test2", "신짱구"));
		members.add(new Member(3, Util.getNowDateTime(), Util.getNowDateTime(), "test3", "test3", "김철수"));
	}
	
	private Article getArticleById(int id) {
		
		for (Article article : articles) {
			if (article.id == id) {
				return article;
			}
		}
		return null;
		
	}
	
	private int getArticleByIndexId(int id) {
		int i = 0;
		
		for (Article article : articles) {
			if (article.id == id) {
				return i;
			}
			i++;
		}
		return -1;
	}
	
	private boolean isLoginedId(String loginId) {
		int index = getMemberByIndexId(loginId);
		
			if (index == -1) {
				return true;
			}
		return false;	
	}
	
	private int getMemberByIndexId(String loginId) {
		int i = 0;
		
		for (Member member : members) {
			if (member.loginId.equals(loginId)) {
				return i;
			}
			i++;
		}
		return -1;
	}
	
}
