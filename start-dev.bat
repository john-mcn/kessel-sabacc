@echo off
start cmd /k "cd backend && .\mvnw spring-boot:run -Dmaven.test.skip=true"
start cmd /k "cd frontend && npm run dev"
timeout /t 8 > nul
start "" "http://localhost:5173/"