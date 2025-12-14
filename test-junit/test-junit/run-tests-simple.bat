@echo off
REM Simple test runner script for Windows
REM Double-click to run all tests

echo ========================================
echo Smart Home System JUnit Test Runner
echo ========================================
echo.

REM Check if Maven is installed
where mvn >nul 2>&1
if %ERRORLEVEL% NEQ 0 (
    echo [ERROR] Maven not found. Please ensure Maven is installed and added to PATH
    echo.
    pause
    exit /b 1
)

echo [INFO] Running tests...
echo.

REM Run tests
call mvn -f test-junit\pom.xml test

echo.
echo ========================================
echo Tests completed!
echo ========================================
echo.
echo Test reports location: test-junit\target\surefire-reports\
echo.

pause


