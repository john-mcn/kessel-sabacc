# Kessel Sabacc
## Summary
This is a full-stack application to facilitate and manage the playing of Sabacc games digitally; the system is built with a React frontend and Spring Boot backend.

## Features
- Users can **play Sabacc games** by betting virtual credits and compete to earn rewards
- One such reward/penalty is a gain/loss

## Setup Instructions
**1. Cloning the repository**

Clone the repo by copying either the HTTPS or SSH link from the "Code" button, then enter the root directory.

For example:
```shell
git clone https://github.com/johnm-portfolio/kessel-sabacc.git
cd .\sabacc\ # This is the root directory
```

**2. Setting up your public and private key**

Either run the script labelled `make_certs.sh` in the resource folder, or enter the following commands manually in a Linux/WSL/Git Bash terminal
- The backend test folder also requires RSA keys for integration tests
```shell
#From the root directory
cd .\server\backend\src\main\resources
mkdir certs
cd certs
openssl genrsa -out keypair.pem 2048
openssl rsa -in keypair.pem -pubout -out public.pem
openssl pkcs8 -topk8 -inform PEM -outform PEM -nocrypt -in keypair.pem -out private.pem
rm keypair.pem
```

**3. Setting up the server/s**

The file `start.bat` *builds* the client then runs the backend server.
This can be done manually by the following commands:

Build the frontend (will start on http://localhost:5173)
```shell
cd .\client\
npm install # Installs dependencies
npm run build # Runs local (development) frontend server
cd ..
# Remove backend\src\main\resources\static if exists
mkdir backend\src\main\resources\static
# Copy contents of client\dist to backend\src\main\resources\static
```

```shell
# From the root folder
cd \server\backend\
.\mvnw -U clean verify # Installs dependencies
.\mvnw spring-boot:run # Runs local backend server
```

Visit the web app in the browser (http://localhost:8080)

\
The file `start-dev.bat` runs *both* the client development server *and* the backend server.
This can be done manually by the following commands:

Run the backend (will start on http://localhost:8080)
```shell
cd .\server\backend\
.\mvnw -U clean verify # Installs dependencies
.\mvnw spring-boot:run # Runs local backend server
```
Run the frontend server (will start on http://localhost:5173)
```shell
# In a separate terminal window
cd .\client\
npm install # Installs dependencies
npm run dev # Runs local (development) frontend server
```
Visit the web app in the browser (http://localhost:5173)

## Using the System
**Logging in / Signing up**: Individuals are required to create a user account in order to be registered for play in the system.

**Playing a game**: Registered users can participate in Sabacc games (if logged in) and have their data persisted. This is as simple as one user starting a game and selecting other players. Each player completes their turns sequentially until the game is won.

## Project details
- **Author**: John McNally
- **Version**: v1.2

Technical Details
- **JDK**: 17
- **Client location**: `./client/`
- **Server location**: `./server/`

## Legal
"Kessel Sabacc" is a concept within the "Star Wars" universe, both of which are owned by Lucasfilm and not myself.

This is an educational project and not for commercial use.

Gambling should not be promoted or condoned, this game is purely recreational. The in-game currency, "credits" cannot be purchased with real money.