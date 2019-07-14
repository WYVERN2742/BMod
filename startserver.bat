@echo off
del .\server\mods\BMod-0.1.jar
call .\gradlew.bat build
move ".\build\libs\BMod-0.1.jar" ".\server\mods\BMod-0.1.jar"
cd .\server\
java -jar -Xmx1024M server.jar
cd ..
