#!/bin/sh


APP_BASE_NAME=${0##*/}



if [ -n "$JAVA_HOME" ] ; then
        JAVACMD=$JAVA_HOME/bin/java
    if [ ! -x "$JAVACMD" ] ; then
    fi
else
    JAVACMD=java
    fi
fi

