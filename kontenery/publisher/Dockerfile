FROM openjdk:11 as builder 
WORKDIR /app
COPY . /app
RUN ./gradlew clean build 

FROM openjdk:11 as runtime 
COPY --from=builder /app//build/libs/publisher-0.0.1-SNAPSHOT.jar /app/app.jar 
EXPOSE 9292
RUN addgroup --system secureusers && useradd --system --group secureusers puser
USER puser 
ENTRYPOINT [ "java", "-jar","/app/app.jar" ]


