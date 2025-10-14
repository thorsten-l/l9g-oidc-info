FROM bellsoft/liberica-openjdk-alpine:21
WORKDIR /workspace
COPY l9g-oidc-info.jar /workspace/l9g-oidc-info.jar
ENTRYPOINT ["java", "-jar", "/workspace/l9g-oidc-info.jar"]
EXPOSE 40443
