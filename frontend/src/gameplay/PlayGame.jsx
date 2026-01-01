import {useEffect, useRef, useState} from "react";
import {useNavigate, useParams} from "react-router-dom";
import BackButton from "../components/BackButton.jsx";
import {Form, FormControl, FormLabel, FormSelect} from "react-bootstrap";
import Button from "react-bootstrap/Button";
import PlayerLink from "../player/PlayerLink.jsx";

const PlayGame = ({ client }) => {
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
            console.log(error)
        });
    }, [client]);

    if (error) {
        return <div>{error}</div>
    }

    if (game) {
        const playerNames = game.players.map(p => p.name);
        const currentPlayer = game.players[game.currPlayerIndex];

        return (
            <>
                <BackButton/>
                <h1>Play Game {game.id}</h1>
                <h5>Credit pot: {game.buyIn * game.players.length} <b>|</b> Chips/player: {game.chipsPerPlayer}</h5>
                <u>Players:</u>
                <ul>
                    {playerNames.map((n) => <li key={n}>{n}</li>)}
                </ul>
                <u>Rewards:</u>
                <ul>
                    {/*{game.rewards.map((r) => <li key={r}>{r}</li>)}*/}
                    <li>{game.rewards}</li>
                </ul>
                <hr/>

                <h4><span style={{color: "gray"}}>Turn {game.turnNumber}/3</span></h4>
                <h5>Players in stand: [{game.inStand.join(", ")}]</h5>
                <h5>Top of discard piles: Blood = [{(game.bloodTopDiscard)? `${game.bloodTopDiscard.rank}]]]` : "]"}, Sand = [{(game.sandTopDiscard)? `${game.sandTopDiscard.rank}]]]` : "]"}</h5>
                <br/>
                <h4 style={{color: "gray"}}>Current player ({currentPlayer.name})</h4>
                <h5>Hand: Blood = [{(currentPlayer.hand[0])? currentPlayer.hand[0].rank : ""}], Sand = [{(currentPlayer.hand[1])? currentPlayer.hand[1].rank : ""}]</h5>
                <h5>Stock = {currentPlayer.stock}, Pot = {currentPlayer.pot}</h5>
                {currentPlayer.drawnCard
                    ? <h5>Drawn card: {currentPlayer.drawnCard.family} {currentPlayer.drawnCard.rank}</h5>
                    : <></>
                }
                <br/>
                {currentPlayer.tokens
                    ? <><p style={{color: "red"}}>TOEKNS DOES NOT WORK</p>
                        <h5>Available tokens:</h5>
                        <ol start={0}>
                            {currentPlayer.tokens.map(t => <li key={t}>{t}</li>)}
                        </ol>
                    </>
                    : <></>
                }
            </>
        )
    }
};

export default PlayGame;