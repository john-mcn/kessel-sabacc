import styled from "styled-components";
import {useEffect, useState} from "react";
import {Table} from "react-bootstrap";
import {Link} from "react-router-dom";
import GameLink from "./GameLink.jsx";
import BackButton from "../components/BackButton.jsx";
import Button from "react-bootstrap/Button";

const Games = ({ client }) => {
    const [games, setGames] = useState([]);
    const [error, setError] = useState(null)

    useEffect(() => {
        client.getGames()
            .then(response => {
                setGames(response.data);
            }).catch(error => {
            setError(error)
        });
    }, [client]);

    if (error) {
        return <div>{error}</div>
    }

    return (
        <>
            <BackButton/><br/><br/>
            <h1>Games</h1>
            <Table striped bordered>
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Winner</th>
                    <th>Players</th>
                    <th>Pot</th>
                    <th>Rewards</th>
                </tr>
                </thead>
                <tbody>
                {games.map((g) => <tr key={g.id}>
                    <td><GameLink id={g.id}/></td>
                    <td>{g.winner.name}</td>
                    <td>[{g.playerNames.join(", ")}]</td>
                    <td>{g.buyIn * g.playerNames.length}</td>
                    <td>[{g.rewards}]</td>
                </tr>)}
                </tbody>
            </Table>
        </>
    )
}

export default Games;