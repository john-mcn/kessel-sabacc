import {useEffect, useRef, useState} from "react";
import {Link, useNavigate, useParams} from "react-router-dom";
import BackButton from "../components/BackButton.jsx";
import {Alert, Form, FormControl, FormLabel, FormSelect, Table} from "react-bootstrap";
import Button from "react-bootstrap/Button";
import PlayerLink from "../player/PlayerLink.jsx";
import ActionButton from "./ActionButton.jsx";

const FinishGame = ({ client }) => {
    const { id } = useParams();
    const [game, setGame] = useState(null);
    const [error, setError] = useState(null);
    const nav = useNavigate();


    useEffect(() => {
        client.getGameInProgress()
            .then(response => {
                setGame(response.data);
            }).catch(error => {
            setError(error)
        });
    }, [client]);

    if (error) {
        if (error.response.data.message == "No game in progress") {
            return (
                <>
                    <Alert>No game active</Alert>
                    <Link to={`/play/start-game`}><Button variant="primary">Play a Game</Button></Link>
                </>
            )
        }

        return (<div>{error}</div>);
    }

    if (game) {
        const playerNames = game.players.map(p => p.name);

        return (
            <>
                <BackButton/>
                <h1>Game {game.id} Summary</h1>
                <h5>Credit pot: {game.buyIn * game.players.length} <b>|</b> Chips/player: {game.chipsPerPlayer}</h5>
                <u>Players:</u>
                <ul>
                    {playerNames.map((n) => {
                        const isWinner = game.winner.name == n;
                        return (<li key={n} className={isWinner ? "text-gold" : ""}>
                            {n} {isWinner && "(Winner)"}
                        </li>)
                    })}
                </ul>
                <u>Rewards:</u>
                <ul>
                    {game.rewards && game.rewards.map((r) => <li key={r}>{r}</li>)}
                </ul>
                <hr/>

                <h4><span style={{color: "gray"}}>End of Game Results</span></h4>
                <Table bordered>
                    <thead>
                    <tr>
                        <th>Name</th>
                        <th>Final Hand</th>
                        <th>Final Stock</th>
                        <th>Credit gain/loss</th>
                    </tr>
                    </thead>
                    <tbody>
                    {game.players.map((p) => <tr key={p.id} className={game.winner.name == p.name ? "table-row-gold" : ""}>
                        <td>{p.name}</td>
                        <td>{p.hand[0].family} {p.hand[0].rank}, {p.hand[1].family} {p.hand[1].rank}</td>
                        <td>{p.stock}</td>
                        <td>{game.winner.name == p.name ? `+${game.buyIn - game.buyIn * game.players.length}` : `-${game.buyIn}`}</td>
                    </tr>)}
                    </tbody>
                </Table>
            </>
        )
    }
};

export default FinishGame;