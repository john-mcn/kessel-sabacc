import { useEffect, useState } from "react";

import 'bootstrap/dist/css/bootstrap.min.css';
import './style/App.css';
import {Alert, Container } from "react-bootstrap";
import { useLocation, useNavigate } from "react-router-dom";
import axios from "axios";
import AuthedRoutes from "./AuthedRoutes.jsx";
import NavBar from "./components/NavBar.jsx";
import Footer from "./components/Footer.jsx";

const App = () => {
    const BASE_URL = "http://localhost:8080/api";
    const PLAYER_URL = `${BASE_URL}/players`;
    const GAME_URL = `${BASE_URL}/games`;
    const GAMEPLAY_URL = `${BASE_URL}/play`;

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
        getTokens: () => axios.get(`${BASE_URL}/tokens`)
    };

    return (
        <>
            <NavBar/>
            <Container style={{padding: 15}}>
                {
                    <AuthedRoutes client={client}/>
                }
            </Container>
            <Footer/>
        </>
    );
};

export default App;
