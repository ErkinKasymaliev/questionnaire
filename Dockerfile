FROM openjdk:11-jre

#COPY target/questionnaire-api.jar /opt/questionnaire/app.jar
#
#ENV JAVA_OPTS="-Duser.timezone=Asia/Bishkek"
#ENV JAVA_OPTS="-Dfile.encoding=UTF-8"
#
#ENTRYPOINT java $JAVA_OPTS -jar /opt/questionnaire/app.jar

ARG JAR_FILE=*.jar
COPY target/${JAR_FILE} application.jar
ENTRYPOINT ["java", "-jar", "application.jar"]