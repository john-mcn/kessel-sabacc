import styled from "styled-components";
import {useEffect, useState} from "react";
import {Form, ListGroup, ListGroupItem, Table} from "react-bootstrap";
import {Link, useNavigate, useParams} from "react-router-dom";
import BackButton from "../components/BackButton.jsx";
import PlayerLink from "../player/PlayerLink.jsx";
import Button from "react-bootstrap/Button";
import Credits from "../components/Credits.jsx";

const Game = ({ client, token, user }) => {
    const { id } = useParams();
    const [game, setGame] = useState(null);
    const [error, setError] = useState(null);
    const nav = useNavigate();

    const handleDeleteGame = (e) => {
        e.preventDefault();
        if (window.confirm("Are you sure you want to delete this game? This action cannot be undone.")) {
            client.deleteGame(id)
                .then(() => nav("/games"));
        }
    };

    useEffect(() => {
        client.getGame(id)
            .then(response => {
                setGame(response.data);
            }).catch(error => {
            setError(error)
            console.log(error)
        });
    }, [client]);

    if (error) {
        return <div>{error}</div>
    }

    if (game) {
        const winner = game.winner;
        return (
            <>
                <BackButton/>
                <h1>Game {game.id}</h1>
                <h4><b>Winner:</b> {<PlayerLink user={user} username={winner.username} name={winner.name} />}
                </h4>
                <h5>Winner pot: <Credits amount={game.buyIn * game.playerNames.length}/> (<Credits amount={game.buyIn}/> buy-in)</h5>
                <hr/>
                <u>Players:</u>
                <ul>
                    {game.playerNames.map((n) => <li key={n}><PlayerLink user={user} name={n}/></li>)}
                </ul>
                <br/>
                <u>Rewards:</u>
                <ul>
                    {/*{game.rewards.map((r) => <li key={r}>{r}</li>)}*/}
                    <li>{game.rewards}</li>
                </ul>
                <br/>
                <Form onSubmit={handleDeleteGame}>
                    <Button type="submit" variant="danger">Delete</Button>
                </Form>
            </>
        )
    }
};

export default Game;