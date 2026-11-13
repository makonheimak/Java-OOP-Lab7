@echo off
chcp 65001 > nul
cd /d "%~dp0"
title Lab 7 (Variant 5) - Yurash Maksim

if exist "Lab7_Main.class" goto run
echo [INFO] Compiling Java source files...
javac -encoding UTF-8 *.java
if errorlevel 1 goto error

:run
echo ========================================================
echo  Лабораторная работа № 7: Веб-приложение (Вариант 5)
echo  Выполнил студент 4 курса ИВТ-А Юраш Максим
echo.
echo  Сервер запущен на: http://localhost:8080/
echo  Для остановки сервера закройте это окно или нажмите Ctrl+C
echo ========================================================
java -Dfile.encoding=UTF-8 -cp . Lab7_Main
goto end

:error
echo [ERROR] Compilation failed. Ensure JDK is installed.
pause

:end
