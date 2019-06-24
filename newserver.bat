@echo off
mkdir server
cd server/
curl https://repo.spongepowered.org/maven/org/spongepowered/spongevanilla/1.12.2-7.1.6/spongevanilla-1.12.2-7.1.6.jar --output server.jar
java -jar server.jar
