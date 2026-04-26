#!/bin/sh
# Maven Wrapper script for Mac/Linux
# Usage: ./mvnw clean package

MAVEN_WRAPPER_JAR=".mvn/wrapper/maven-wrapper.jar"
MAVEN_WRAPPER_PROPERTIES=".mvn/wrapper/maven-wrapper.properties"

exec java \
  -classpath "$MAVEN_WRAPPER_JAR" \
  "-Dmaven.multiModuleProjectDirectory=$PWD" \
  org.apache.maven.wrapper.MavenWrapperMain "$@"
