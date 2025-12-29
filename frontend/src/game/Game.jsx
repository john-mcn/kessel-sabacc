import styled from "styled-components";
import {useEffect, useState} from "react";
import {ListGroup, ListGroupItem, Table} from "react-bootstrap";
import {Link, useParams} from "react-router-dom";
import BackButton from "../components/BackButton.jsx";

const Game = ({ client }) => {
    const { id } = useParams();
    const [game, setGame] = useState(null);
    const [error, setError] = useState(null)

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
        return (
            <>
                <BackButton/>
                <h1>Game {game.id}</h1>
                <h4><b>Winner/s:</b> {game.winnerNames.length === 1
                    ? game.winnerNames
                    : `[${game.winnerNames.join(", ")}]`}
                </h4>
                <h5>Winner pot: {game.buyIn * game.playerNames.length}</h5>
                <hr/>
                <u>Players:</u>
                <ul>
                    {game.playerNames.map((n) => <Link to={`/players/${n}`} key={n}><li>{n}</li></Link>)}
                </ul>
                <br/>
                <u>Rewards:</u>
                <ul>
                    {/*{game.rewards.map((r) => <li key={r}>{r}</li>)}*/}
                    <li>{game.rewards}</li>
                </ul>
            </>
        )
    }
};

export default Game;