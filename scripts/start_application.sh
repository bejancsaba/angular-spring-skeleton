#!/bin/bash
export JAVA_HOME=/usr/lib/jvm/java-11-amazon-corretto.x86_64
export PATH=$PATH:$JAVA_HOME/bin
echo $JAVA_HOME
java -version
java -jar /tmp/demo-0.0.1-SNAPSHOT.jar