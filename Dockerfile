# 1단계: 베이스 이미지 지정
FROM openjdk:17-jdk-slim

# 2단계: 작업 디렉토리 생성
WORKDIR /app

# 3단계: JAR 파일 복사
COPY build/libs/*.jar app.jar

# 4단계: JAR 파일이 매번 바뀌어도 캐시 문제 없게 하기 위해 COPY만 하단에 둠
#        (이렇게 하면 소스 변경 후 빌드시 캐시를 새로 씀)

# 5단계: 앱 실행
ENTRYPOINT ["java", "-jar", "app.jar"]
