FROM tomcat:10.1.57-jdk21-temurin-noble

COPY dist/EntreEntrelinhasWeb.war /usr/local/tomcat/webapps/

EXPOSE 8080

