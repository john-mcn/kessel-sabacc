import { useEffect, useState } from "react";

import 'bootstrap/dist/css/bootstrap.min.css';
import './style/App.css';
import {Alert, Container } from "react-bootstrap";
import { useLocation, useNavigate } from "react-router-dom";
import axios from "axios";
import AuthedRoutes from "./AuthedRoutes.jsx";
import NavBar from "./components/NavBar.jsx";
import Footer from "./components/Footer.jsx";
import UnauthedRoutes from "./UnauthedRoutes.jsx";

const App = () => {
    const BASE_URL = "http://localhost:8080/api";
    const PLAYER_URL = `${BASE_URL}/players`;
    const GAME_URL = `${BASE_URL}/games`;
    const GAMEPLAY_URL = `${BASE_URL}/play`;

    const nav = useNavigate();

    const [token, setToken] = useState("");
    const [user, setUser] = useState({});

    const loginHandler = (data) => {
        setToken(data.token);
        setUser(data.user);

        localStorage.setItem("token", data.token);
        localStorage.setItem("user", JSON.stringify(data.user));
        localStorage.setItem("EOView", JSON.stringify(true));

        nav("/");
    };

    const logout = () => {
        setToken("");
        setUser({});
        localStorage.removeItem("token");
        localStorage.removeItem("user");
        nav("/");
    };

    //TODO START ROUND when starting game
    const client = {
        // Player routes
        getPlayers: () => axios.get(`${PLAYER_URL}`),
        getPlayer: (name) => axios.get(`${PLAYER_URL}/${name}`),
        createPlayer: (data) => axios.post(`${PLAYER_URL}`, data),
        deletePlayer: (name) => axios.delete(`${PLAYER_URL}/${name}`),
        // Game routes
        getGames: () => axios.get(`${GAME_URL}`),
        getGame: (gameId) => axios.get(`${GAME_URL}/${gameId}`),
        // createGame: (data) => axios.post(`${GAME_URL}`, data),
        deleteGame: (gameId) => axios.delete(`${GAME_URL}/${gameId}`),
        // Gameplay routes
        startGame: (data) => axios.post(`${GAMEPLAY_URL}/start-game`, data),
        startRound: () => axios.post(`${GAMEPLAY_URL}/start-round`),
        getGameInProgress: () => axios.get(`${GAMEPLAY_URL}`),
        performAction: (data) => axios.post(`${GAMEPLAY_URL}/action`, data),
        resolveImposters: (data) => axios.post(`${GAMEPLAY_URL}/resolve-imposters`, data),
        showSummary: () => axios.get(`${GAMEPLAY_URL}/summary`),
        // ShiftToken routes
        getTokens: () => axios.get(`${BASE_URL}/tokens`),

        // Login & signup
        login: (data) => axios({
            method: "POST",
            url: `${BASE_URL}/auth/login`,
            headers: {
                "Content-Type": "application/json",
            },
            data: JSON.stringify(data),
        }).then((response) => {
                loginHandler(response.data);
        }).catch((error) => console.log(error)),
    };

    return (
        <>
            <NavBar logout = {logout} />
            <Container  style={{padding: 15}}>
                {/*{errorMessage !== "" && (*/}
                {/*    <Alert className="mt-2" variant="danger">*/}
                {/*        {errorMessage}*/}
                {/*    </Alert>*/}
                {/*)}*/}
                {token === ""
                    ? <UnauthedRoutes client={client} token={token} user={user}/>
                    : <AuthedRoutes client={client} token={token} user={user}/>
                }
            </Container>
            <Footer/>
        </>
    );
};

export default App;
