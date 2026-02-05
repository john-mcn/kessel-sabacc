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
    const [authReady, setAuthReady] = useState(false);

    const authorisedRequest = (url, method, data = {}) =>
        axios({
            method,
            url: `${url}`,
            data,
            headers: {
                Authorization: `Bearer ${localStorage.getItem("token") ?? ""}`,
                ...(data instanceof FormData? {} : {"Content-Type": "application/json"}),
            },
        });

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

    useEffect(() => {
        setToken(localStorage.getItem("token") ?? "");
        setUser(JSON.parse(localStorage.getItem("user") ?? "{}"));
        setAuthReady(true);
    }, []);

    //TODO START ROUND when starting game
    const client = {
        getCurrPlayerURL: () => { `${PLAYER_URL}/${user.username}`; },
        // Player routes
        getPlayers: () => authorisedRequest(`${PLAYER_URL}`, "GET"),
        getPlayer: (username) => authorisedRequest(`${PLAYER_URL}/${username}`, "GET"),
        createPlayer: (data) => authorisedRequest(`${PLAYER_URL}`, "POST", data),
        deletePlayer: (username) => authorisedRequest(`${PLAYER_URL}/${username}`, "DELETE"),
        // Game routes
        getGames: () => authorisedRequest(`${GAME_URL}`, "GET"),
        getGame: (gameId) => authorisedRequest(`${GAME_URL}/${gameId}`, "GET"),
        // createGame: (data) => axios.post(`${GAME_URL}`, data),
        deleteGame: (gameId) => authorisedRequest(`${GAME_URL}/${gameId}`, "DELETE"),
        // Gameplay routes
        startGame: (data) => authorisedRequest(`${GAMEPLAY_URL}/start-game`, "POST",data),
        startRound: () => authorisedRequest(`${GAMEPLAY_URL}/start-round`, "POST"),
        getGameInProgress: () => authorisedRequest(`${GAMEPLAY_URL}`, "GET"),
        performAction: (data) => authorisedRequest(`${GAMEPLAY_URL}/action`, "POST", data),
        resolveImposters: (data) => authorisedRequest(`${GAMEPLAY_URL}/resolve-imposters`, "POST", data),
        showSummary: () => authorisedRequest(`${GAMEPLAY_URL}/summary`, "GET"),
        // ShiftToken routes
        getTokens: () => authorisedRequest(`${BASE_URL}/tokens`, "GET"),

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

    if (!authReady) { return <p>Loading...</p>; }

    return (
        <>
            <NavBar logout={logout} user={user} token={token} />
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
