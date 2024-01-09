@echo off
setlocal EnableDelayedExpansion

echo %*

start java -cp "C:\Users\%USER_NAME%\.maven-lite\" MavenLite %*

endlocal
