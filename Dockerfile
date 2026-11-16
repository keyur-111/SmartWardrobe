FROM tomcat:9.0.124-jdk21-temurin AS build

WORKDIR /workspace
COPY src/java/ src/java/
COPY web/ web/

RUN mkdir -p web/WEB-INF/classes \
    && javac --release 8 -encoding UTF-8 \
        -classpath /usr/local/tomcat/lib/servlet-api.jar:web/WEB-INF/lib/mysql-connector-j-9.7.0.jar \
        -d web/WEB-INF/classes \
        $(find src/java -type f -name '*.java')

FROM tomcat:9.0.124-jre21-temurin

COPY --from=build /workspace/web/ /usr/local/tomcat/webapps/SmartWardrobe/
COPY docker-entrypoint.sh /usr/local/bin/smartwardrobe-entrypoint
RUN chmod +x /usr/local/bin/smartwardrobe-entrypoint

EXPOSE 8080
ENTRYPOINT ["/usr/local/bin/smartwardrobe-entrypoint"]
CMD ["catalina.sh", "run"]
