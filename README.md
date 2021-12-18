# jpashop


1. jpashop 
2. 인프런 jpa 강좌


핵심 라이브러리

- 스프링 MVC
- 스프링 ORM
- JPA, 하이버네이트
- 스프링 데이터 JPA

기타 라이브러리

- H2 데이터베이스 클라이언트
- 커넥션 풀 : 부트 기본은 HikariCP
- WEB(thymeleaf)
- 로깅 SLF4J & LogBack
- 테스트


참고 : 스프링 데이터 JPA 는 스프링과 JPA 를 먼저 이해하고 사용해야 하는 응용기술이다.


h2 database 연결시 
h2 깔고 ./h2.sh후
연결확인 ( 처음에는 기본 으로 연결 확인, 파일 생성된거 확인되면 db)
jdbc url 를 jdbc:h2:tcp://localhost/~/jpashop 으로 변경 (네트워크 모드로 전환)

터미널 창 끄면 db 연결도 끊김.
다시 연결하려면 h2/bin 에서 ./h2.sh 실행
