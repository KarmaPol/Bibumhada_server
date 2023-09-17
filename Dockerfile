# Build stage
FROM gradle:8.2.1-jdk17 AS builder
WORKDIR /workspace

# Copy all necessary files
COPY . .

# Install H2 Database
RUN wget https://repo1.maven.org/maven2/com/h2database/h2/1.4.200/h2-1.4.200.jar

# Start H2 Database in the background
RUN java -cp h2-1.4.200.jar org.h2.tools.Server -web -webAllowOthers -tcp -tcpAllowOthers &

# Build the project
RUN gradle build

# Runtime stage
FROM openjdk:17-jdk-slim
WORKDIR /app

ARG JAR_PATH=/workspace/build/libs
ARG JAR_FILE_NAME=bibum_server-0.0.1-SNAPSHOT.jar

COPY --from=builder ${JAR_PATH}/${JAR_FILE_NAME} app.jar
ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar /app/app.jar"]
