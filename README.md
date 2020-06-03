카카오페이 사전과제
=====================

### 개발 환경

 * 기본 환경
    * IDE : IntelliJ IDEA Ultimate
    * OS : Windows
    * GIT
 * 서버
    * Java 14
    * Spring Boot 2.3.0
    * Gradle
    * JPA
    * PostgreSQL
    * Junit5
 
 
 
빌드 및 실행
-------------------------
 
   * 접속 URI 
   <pre><code>http://localhost:8080</code></pre>

 
문제 해결
-------------------------
1. 쿠폰번호 생성 방법
 > 1-1. 임의의 문자열 생성 (예시 : dmxcdyf8zo0t13t1ahya)
 
  * 쿠폰번호는 해쉬 값과 함께 저장하여 빠르게 탐색
    * 20자리 영문과 숫자를 혼합하여 랜덤코드 생성
    * java.util.Random class를 사용
    * nextBoolean()은 true(소문자),false(랜덤 숫자)를 리턴
    * nextInt() 메서드를 통해서 반환된 숫자를 62진수로 변환 ([0-9][a-z][A-Z])
    * 20번 반복하여 만들어진 문자를 모두 StringBuffer로 연결하여 20자리의 임의의 문자열 생성 
    
 > 1-2. HashCode 생성 (예시 : 1828977362)    
  * 생성된 랜덤 코드를 받아서 HashCode를 만들어 함께 저장 
    * java.util.zip.CRC32를 사용
    * 랜덤코드를 byte 배열로 변환
    * byte 배열 값을 update()함수를 이용하여 CRC32 Long형식의 코드를 생성
      
  
  * 현실적인 성능문제 
     * 적은 개수의 데이터에서는 랜덤코드를 조회하여 생성가능
     * 하지만 수억개의 데이터를 검색할 시 코드값을 검사하는데 비용이 많이 일어남
     * 쿠폰코드 발급시 만들어진 해시코드 값을 함께 저장하여 조회 (9991개를 한번에 저장할 시 40초에서 20초로 단축)
