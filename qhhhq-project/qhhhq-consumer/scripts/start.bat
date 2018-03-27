@echo off & setlocal enabledelayedexpansion

set LIB_JARS=""
cd ..\lib
for %%i in (*) do set LIB_JARS=!LIB_JARS!;..\lib\%%i

cd ..\bin

if ""%1"" == ""debug"" goto debug
if ""%1"" == ""jmx"" goto jmx

java -Ddubbo.properties.file=galaxy.properties -Xms64m -Xmx1024m -DPT_HOME=../conf/ -classpath ..\conf;%LIB_JARS% net.qhhhq.core.boot.PlatformBootstart
goto end

:debug
java -Xms64m -Xmx1024m  -Xdebug -Xnoagent -DPT_HOME=../conf/ -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,address=8000,server=y,suspend=n -classpath ..\conf;%LIB_JARS% net.qhhhq.core.boot.PlatformBootstart
goto end

:jmx
java -Xms64m -Xmx1024m  -Dcom.sun.management.jmxremote.port=1099 -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.auth
:end
pause
