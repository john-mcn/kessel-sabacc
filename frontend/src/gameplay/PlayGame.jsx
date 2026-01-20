import {useEffect, useRef, useState} from "react";
import {Link, useNavigate, useParams} from "react-router-dom";
import BackButton from "../components/BackButton.jsx";
import {Alert, Form, FormControl, FormLabel, FormSelect, InputGroup, Table} from "react-bootstrap";
import Button from "react-bootstrap/Button";
import PlayerLink from "../player/PlayerLink.jsx";
import ActionButton from "./ActionButton.jsx";
import ChooseDice from "./ChooseDice.jsx";

const PlayGame = ({ client }) => {
    const [game, setGame] = useState(null);
    const [error, setError] = useState(null);
    const nav = useNavigate();

    const tokenIndexRef = useRef(null);
    const [tokenIndex, setTokenIndex] = useState(null);
    const tokenIndexGrabber = (e) => {
        setTokenIndex(e.target.value);
    };
    const tokenRequiresDice = (tokenName) => {
        // replace this with your real logic
        return tokenName === "Prime Sabacc";
    };
    const [pendingTokenAction, setPendingTokenAction] = useState(null);
    const [diceRolls, setDiceRolls] = useState([1,1]);

    useEffect(() => {
        client.getGameInProgress()
            .then(response => {
                setGame(response.data);
            }).catch(error => {
            setError(error)
        });
    }, [client]);

    const handleActionIntent = ({ player, action, tokenIndex }) => {
        // tokenIndex can be 0 — check for null/undefined
        if (action === "PLAY_TOKEN" && tokenIndex != null) {
            const tokenName = player.tokens[tokenIndex];
            if (tokenName && tokenRequiresDice(tokenName)) {
                // generate rolls (or ask server to generate if you want authoritative roll)
                const r1 = Math.floor(Math.random() * 6) + 1;
                const r2 = Math.floor(Math.random() * 6) + 1;
                setDiceRolls([r1, r2]);
                setPendingTokenAction({ player, action, tokenIndex });
                return;
            }
        }

        sendAction({ playerName: player.name, action });
    };

    const sendAction = (payload) => {
        client.performAction(payload)
            .then(response => {
                setGame(response.data); // update UI from server snapshot
                setPendingTokenAction(null);
            })
            .catch(e => {
                console.log(error.response && error.response.data.message? error.response.data.message : error.message)
            });
    };

    const handleDiceChosen = (selectedValue) => {
        if (!pendingTokenAction) return;
        const { player, action, tokenIndex } = pendingTokenAction;

        const payload = {
            playerName: player.name,
            action,
            tokenIndex,
            selectedValue
        };

        sendAction(payload);
    };

    const handleDiceCancel = () => {
        setPendingTokenAction(null);
    };

    if (error) {
        if (error.response && error.response.data.message === "No game in progress") {
            return (
                <>
                    <Alert>No game active</Alert>
                    <Link to={`/play/start-game`}><Button variant="primary">Play a Game</Button></Link>
                </>
            )
        }

        return (<div>{error}</div>);
    }

    // Show imposter rank (MAJOR_FRAUD)
    if (game) {
        const roundEnded = game.turnNumber > 3 || game.inStand.length == game.players.length;
        if (game.winner) { nav(`/play/summary`); }
        if (roundEnded) {
            if (!game.impostersResolved) {
                nav(`/play/reveal`);
            } else {
                nav(`/play/round-summary`)
            }
        }
        if (game.roundWinners) { nav(`/play/round-summary`); }

        const playerNames = game.players.map(p => p.name);
        const currentPlayer = game.players[game.currPlayerIndex];
        const drawDisabled = currentPlayer.stock < 1;
        return (
            <>
                <BackButton/>
                <h1>Play Game {game.id}</h1>
                <h5>Credit pot: {game.buyIn * game.players.length} <b>|</b> Chips/player: {game.chipsPerPlayer}</h5>
                <u>Players:</u>
                <Table bordered style={{maxWidth: 300}}>
                    <thead>
                    <tr>
                        <th>Name</th>
                        <th>Stock</th>
                        <th>Pot</th>
                        <th>Chip gain/loss</th>
                    </tr>
                    </thead>
                    <tbody>
                    {game.players.map((p) => <tr key={p.name} className={p.name == currentPlayer.name ? "table-row-lightblue" : ""}>
                        <td>{p.name}</td>
                        <td>{p.stock}</td>
                        <td>{p.pot}</td>
                        <td>{p.chipDifference >=0? `+${p.chipDifference}` : `${p.chipDifference}`}</td>
                    </tr>)}
                    </tbody>
                </Table>
                <u>Rewards:</u>
                <ul>
                    {/*{game.rewards.map((r) => <li key={r}>{r}</li>)}*/}
                    <li>{game.rewards}</li>
                </ul>
                <hr/>

                <h4><span style={{color: "gray"}}><b>Round {game.roundNumber}</b> | Turn {game.turnNumber}/3</span></h4>
                <h5>Players in stand: [{game.inStand.join(", ")}]</h5>
                <h5>Top of discard piles: [{game.bloodDiscardTop? `Blood ${game.bloodDiscardTop.rank}` : ""}], [{game.sandDiscardTop? `Sand ${game.sandDiscardTop.rank}` : ""}]</h5>
                <h5>Best Sabacc: {game.bestSabacc? game.bestSabacc : "Sylop"}</h5>
                <br/>
                <h4 style={{color: "gray"}}>Current player ({currentPlayer.name})</h4>
                <h5>Hand: Blood {(currentPlayer.hand[0])? currentPlayer.hand[0].rank : ""}, Sand {(currentPlayer.hand[1])? currentPlayer.hand[1].rank : ""}</h5>
                <h5>Stock = {currentPlayer.stock}, Pot = {currentPlayer.pot}</h5>
                <br/><br/>
                {/*TODO refactor? (length)*/}
                {currentPlayer.drawnCard
                    ?<>
                        <h5>Drawn card: {currentPlayer.drawnCard.family} {currentPlayer.drawnCard.rank}</h5>
                        <ActionButton client={client} text={"Swap"} player={currentPlayer} action={"REPLACE_WITH_DRAWN"} onUpdate={setGame} onAction={handleActionIntent}/>
                        <ActionButton client={client} text={"Discard"} player={currentPlayer} action={"DISCARD_DRAWN"} onUpdate={setGame} onAction={handleActionIntent}/>
                    </>
                    :<>
                        <ActionButton client={client} text={"Stand"} player={currentPlayer} action={"STAND"} onUpdate={setGame} onAction={handleActionIntent}/>
                        <br/><br/>
                        <p className={"p-before-btn"}>Draw blood card from:</p>
                        <ActionButton client={client} text={"Draw Pile"} player={currentPlayer} action={"DRAW_BLOOD_DRAW"} disabled={drawDisabled} onUpdate={setGame} onAction={handleActionIntent}/>
                        <ActionButton client={client} text={"Discard Pile"} player={currentPlayer} action={"DRAW_BLOOD_DISCARD"} disabled={drawDisabled || !game.bloodDiscardTop} onUpdate={setGame} onAction={handleActionIntent}/>
                        <br/>
                        <p className={"p-before-btn"}>Draw sand card from:</p>
                        <ActionButton client={client} text={"Draw Pile"} player={currentPlayer} action={"DRAW_SAND_DRAW"} disabled={drawDisabled} onUpdate={setGame} onAction={handleActionIntent}/>
                        <ActionButton client={client} text={"Discard Pile"} player={currentPlayer} action={"DRAW_SAND_DISCARD"} disabled={drawDisabled || !game.sandDiscardTop} onUpdate={setGame} onAction={handleActionIntent}/>
                        <br/><br/>
                        {currentPlayer.tokens
                            ? <>
                                <h5>Available tokens:</h5>
                                <ol start={0}>
                                    {currentPlayer.tokens.map(t => <li key={t}>{t}</li>)}
                                </ol>
                            </>
                            : <></>
                        }
                        <Form>
                            <FormLabel column={false} controlId="tokenIndex" label="Token index">
                                Token index: <FormControl ref={tokenIndexRef} type="number" placeholder={0} required min={0} max={currentPlayer.tokens.length - 1} onChange={tokenIndexGrabber}/>
                            </FormLabel>
                            <ActionButton client={client} text={"Play Token"} player={currentPlayer} action={"PLAY_TOKEN"} tokenIndex={tokenIndex} disabled={currentPlayer.tokens.length < 1} onUpdate={setGame} onAction={handleActionIntent}/>
                        </Form>

                        { pendingTokenAction &&
                                <ChooseDice rolls={diceRolls} onChoose={handleDiceChosen} onCancel={handleDiceCancel}/>
                        }
                    </>
                }
            </>
        )
    }
};

export default PlayGame;