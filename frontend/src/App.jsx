import { useEffect, useState } from "react";

import './style/App.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import { Alert, Container } from "react-bootstrap";
import { useLocation, useNavigate } from "react-router-dom";
import axios from "axios";
import AuthedRoutes from "./AuthedRoutes.jsx";

const App = () => {
    const BASE_URL = "http://localhost:8080"

    const authorisedRequest = (url, method, data = {}) => axios({
        method,
        url: `${BASE_URL}${url}`,
        data,
        // headers: {
        //     "Authorization": `Bearer ${token}`
        // }
    });

    const client = {
        getPlayers: () => axios.get(`${BASE_URL}/players`),
        getPlayer: (name) => axios.get(`${BASE_URL}/players/${name}`),
        getGames: () => axios.get(`${BASE_URL}/games`),
        getGame: (gameId) => axios.get(`${BASE_URL}/games/${gameId}`)
    };

    return (
        <Container>
            {
                <AuthedRoutes client={client}/>
            }
        </Container>
    );
};

export default App;
