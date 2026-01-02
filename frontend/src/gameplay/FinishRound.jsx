import {useEffect, useRef, useState} from "react";
import {Link, useNavigate, useParams} from "react-router-dom";
import BackButton from "../components/BackButton.jsx";
import {Alert, Form, FormControl, FormLabel, FormSelect, Table} from "react-bootstrap";
import Button from "react-bootstrap/Button";
import PlayerLink from "../player/PlayerLink.jsx";
import ActionButton from "./ActionButton.jsx";
import GameLink from "../game/GameLink.jsx";

const FinishRound = ({ client }) => {
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

    const submitHandler = (e) => {
        e.preventDefault();
        client.startRound().then(() => {
            nav("/play")
        }).catch((e) => console.log(e.message));
    };

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
        if (game.winner) { nav("/play/summary"); }
        const finishedOrder = game.roundFinishedOrder;

        return (
            <>
                <BackButton/>
                {/* -1 from roundNumber bc frontend increments upon completion*/}
                <h1>Round {game.roundNumber - 1} Summary</h1>
                <h5>Credit pot: {game.buyIn * game.players.length} <b>|</b> Chips/player: {game.chipsPerPlayer}</h5>
                <u>Players:</u>
                <ol>
                    {finishedOrder.map((p) => {
                        // Need to map to names otherwise = false
                        const isWinner = game.roundWinners.map(w => w.name).includes(p.name);
                        return (<li key={p.name} className={isWinner ? "text-gold" : ""}>
                            {p.name} {isWinner && "(Round Winner)"}
                        </li>)
                    })}
                </ol>
                <u>Rewards:</u>
                <ul>
                    {/*{game.rewards.map((r) => <li key={r}>{r}</li>)}*/}
                    <li>{game.rewards}</li>
                </ul>
                <hr/>

                <h4><span style={{color: "gray"}}>End of Round Results</span></h4>
                <Table bordered>
                    <thead>
                    <tr>
                        <th>Name</th>
                        <th>Hand</th>
                        <th>Stock</th>
                    </tr>
                    </thead>
                    <tbody>
                    {game.roundFinishedOrder.map((p) => <tr key={p.id} className={game.roundWinners.map(w => w.name).includes(p.name) ? "table-row-gold" : ""}>
                        <td>{p.name}</td>
                        <td>{p.hand[0].family} {p.hand[0].rank}, {p.hand[1].family} {p.hand[1].rank}</td>
                        <td>{p.stock}</td>
                    </tr>)}
                    </tbody>
                </Table>

                <br/>

                <Button onClick={submitHandler}>Start New Round</Button>
            </>
        )
    }
};

export default FinishRound;