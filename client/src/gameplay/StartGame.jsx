import {useEffect, useRef, useState} from "react";
import {useNavigate} from "react-router-dom";
import BackButton from "../components/BackButton.jsx";
import {Alert, Form, FormControl, FormLabel, FormSelect} from "react-bootstrap";
import Button from "react-bootstrap/Button";
import Credits from "../components/Credits.jsx";

const StartGame = ({ client }) => {
    const [game, setGame] = useState(null);
    const [errorMsg, setErrorMsg] = useState(null);
    const [presets, setPresets] = useState([]);
    const [loading, setLoading] = useState(false);
    const nav = useNavigate();

    const [playersLst, setPlayerNamesLst] = useState([]);
    const playersRef = useRef([]);
    const buyInRef = useRef(null);
    const chipsPerPlayerRef = useRef(null);
    // const rewardsRef = useRef([]);

    useEffect(() => {
        client.getPlayers()
            .then(response => {
                setPlayerNamesLst(response.data);
            }).catch(error => {
            // setError(error)
            console.log(error)
        });
        client.getGameInProgress()
            .then(response => {
                if (response) setGame(response.data);
            }).catch(error => {
            console.log(error.response && error.response.data.message? error.response.data.message : error.message);
        });
        client.getGamePresets()
            .then(response => {
                setPresets(response.data);
            }).catch(error => {
            console.log(error.response && error.response.data.message? error.response.data.message : error.message);
        });
        setLoading(false);
    }, [client]);

    const submitHandler = (e) => {
        e.preventDefault();

        let currentErrors = [];
        setErrorMsg(null);

        const players = Array.from(
            playersRef.current.selectedOptions,
            option => ({ name: option.value })
        );
        const buIn = Number(buyInRef.current.value);
        const chipsPerPlayer = Number(chipsPerPlayerRef.current.value);
        // const rewards = rewardsRef.current.value.split(",");

        const payload = {
            players: players,
            buyIn: buIn,
            chipsPerPlayer: chipsPerPlayer,
            // rewards: rewards
        };

        // if (!winnerNames.every(n => players.includes(n))) {
        //     currentErrors.push(winnersArePlayersMsg);
        // }
        console.log(`NO ERRORS? ${currentErrors.length === 0 && !errorMsg}`)
        client.startGame(payload)
            .then((response) => {
                console.log("NO ERRORS -> PLAY GAME")
                nav(`/play`)
            })
            .catch(error => {
                const errorMessage = error.response && error.response.data.message ? error.response.data.message : error.message;
                console.log(errorMessage)
                setErrorMsg(errorMessage)
            });
    };

    if (loading) { return null; }

    if (game && !game.winner) { nav(`/play`) }

    console.log(presets[0]);
    return (
        <>
            <BackButton/>

            <h1 className="englibesh">New Game</h1>
            <h2>Create a Custom Game</h2>
            <Form onSubmit={submitHandler}>
                <FormLabel column={true} controlId="players" label="Player names">
                    Players: <FormSelect ref={playersRef} multiple required>
                    {playersLst.map(p => (
                        <option key={`player:${p.name}`}>{p.name}</option>
                    ))}
                </FormSelect>
                </FormLabel>
                <br/>

                <FormLabel column={true} controlId="buyIn" label="Buy-in">
                    Buy-in: <FormControl ref={buyInRef} type="number" placeholder={0} required/>
                </FormLabel>
                <br/>

                <FormLabel column={true} controlId="chipsPerPlayer" label="Chips per player">
                    Chips per player: <FormControl ref={chipsPerPlayerRef} type="number" placeholder={1} required/>
                </FormLabel>
                <br/>

                {/*<FormLabel column={true} controlId="rewards" label="Rewards">*/}
                {/*    Rewards: <FormControl ref={rewardsRef} type="text" placeholder=""/>*/}
                {/*</FormLabel>*/}
                <br/>
                <br/>

                {errorMsg && <Alert variant="danger">{errorMsg}</Alert>}

                <Button type="submit" variant="primary">Create</Button>
            </Form>

            <hr/>

            <h2>Play a Standard Game</h2>
            {presets.map((preset) => <>
                <ul>
                    <li>ID: {preset.id}</li>
                    <li>Buy-in: <Credits amount={preset.buyIn}/></li>
                    <li>Chips/player: {preset.chipsPerPlayer}</li>
                    <li>Reputation requirements:&nbsp;
                        {preset.dawnRepReq === 0? `` : `Crimson Dawn: ${preset.dawnRepReq}%`}
                        {preset.huttRepReq === 0? `` : `Hutt Clan: ${preset.huttRepReq}%`}
                        {preset.pykeRepReq === 0? `` : `Pyke Syndicate: ${preset.pykeRepReq}%`}
                    </li>
                </ul>
            </>)}
        </>
    )
};

export default StartGame;