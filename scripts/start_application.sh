#!/bin/bash
export JAVA_HOME=/usr/lib/jvm/jre-11-openjdk
export PATH=$PATH:$JAVA_HOME/bin
echo $JAVA_HOME
java -version
java -jar build/libs/demo-0.0.1-SNAPSHOT.jar