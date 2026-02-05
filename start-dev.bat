@echo off
start cmd /k "cd server && .\mvnw spring-boot:run -Dmaven.test.skip=true"
start cmd /k "cd client && npm run dev"
timeout /t 8 > nul
start "" "http://localhost:5173/"