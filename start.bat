@echo on
REM ================================
REM Deployment start script (production)
REM ================================

REM Ensure npm and mvnw exist
where npm > nul 2>&1
if errorlevel 1 (
    echo npm not found in PATH
    pause
    exit /b 1
)

if not exist backend\mvnw (
    echo mvnw not found in backend folder
    pause
    exit /b 1
)

REM Build React frontend
echo Building React frontend...
cd client
call npm install --no-progress
if errorlevel 1 (
    echo npm install failed
    pause
    exit /b 1
)
call npm run build --no-progress
if errorlevel 1 (
    echo React build failed
    pause
    exit /b 1
)
cd ..

REM Copy React build to Spring Boot static folder
echo Copying React build to Spring Boot static folder...
if exist backend\src\main\resources\static rmdir /s /q backend\src\main\resources\static
mkdir backend\src\main\resources\static

if not exist client\dist (
    echo React build folder not found
    pause
    exit /b 1
)

xcopy client\dist\* backend\src\main\resources\static\ /E /I /Y
if errorlevel 1 (
    echo Failed to copy React build to static folder
    pause
    exit /b 1
)

REM Start backend in new terminal
echo Starting Spring Boot server...
start "" cmd /k "cd backend && call .\mvnw spring-boot:run -Dmaven.test.skip=true"

REM Wait for server to start then open in browser
echo Waiting 8 seconds for Spring Boot to start...
timeout /t 8 > nul
start "" "http://localhost:8080/"

echo Deployment script finished.
