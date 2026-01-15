import {useEffect, useRef, useState} from "react";
import {useNavigate} from "react-router-dom";
import BackButton from "../components/BackButton.jsx";
import {Form, FormControl, FormLabel, FormSelect} from "react-bootstrap";
import Button from "react-bootstrap/Button";

const CreateGame = ({ client }) => {
    const [errors, setErrors] = useState([]);
    const nav = useNavigate();

    const [playerNamesLst, setPlayerNamesLst] = useState([]);
    const playerNamesRef = useRef([]);
    // const winnerNamesRef = useRef([]);
    const buyInRef = useRef(null);
    const chipsPerPlayerRef = useRef(null);
    const rewardsRef = useRef([]);

    useEffect(() => {
        client.getPlayers()
            .then(response => {
                setPlayerNamesLst(response.data);
            }).catch(error => {
            // setError(error)
            console.log(error)
        });
    }, [client]);

    const submitHandler = (e) => {
        e.preventDefault();

        let currentErrors = [];

        const playerNames = Array.from(playerNamesRef.current.selectedOptions, option => option.value);
        // const winnerNames = Array.from(winnerNamesRef.current.selectedOptions, option => option.value);
        const buIn = Number(buyInRef.current.value);
        const chipsPerPlayer = Number(chipsPerPlayerRef.current.value);
        const rewards = rewardsRef.current.value.split(",");

        const payload = {
            playerNames: playerNames,
            // winnerNames: winnerNames,
            buyIn: buIn,
            chipsPerPlayer: chipsPerPlayer,
            rewards: rewards
        };

        // if (!winnerNames.every(n => playerNames.includes(n))) {
        //     currentErrors.push(winnersArePlayersMsg);
        // }

        if (currentErrors.length === 0) {
            client.createGame(payload)
                .then((response) => {
                    nav(`/games/play/${response.data.id}`)
                })
                .catch((e) => console.log(e));
        } else {
            setErrors(currentErrors)
        }
    };

    return (
        <>
            <BackButton/>

            <Form onSubmit={submitHandler}>
                <h1>Create New Game</h1>
                <hr/>
                <FormLabel column={true} controlId="playerNames" label="Player names">
                    Players: <FormSelect ref={playerNamesRef} multiple required>
                    {playerNamesLst.map(p => (
                        <option key={`player:${p.name}`}>{p.name}</option>
                    ))}
                </FormSelect>
                </FormLabel>
                <br/>

                {/*/!*TODO validation for 'winner subset players'*!/*/}
                {/*<FormLabel column={true} controlId="winnerNames" label="Winner names">*/}
                {/*    Winners: <FormSelect ref={winnerNamesRef} multiple required>*/}
                {/*    {playerNamesLst.map(n => (*/}
                {/*        <option key={`winner:${n.name}`}>{n.name}</option>*/}
                {/*    ))}*/}
                {/*</FormSelect>*/}
                {/*    { (errors?.includes(winnersArePlayersMsg) && (<p className="text-danger">{winnersArePlayersMsg}</p>)) }*/}
                {/*</FormLabel>*/}
                {/*<br/>*/}

                <FormLabel column={true} controlId="buyIn" label="Buy-in">
                    Buy-in: <FormControl ref={buyInRef} type="number" placeholder={0} required/>
                </FormLabel>
                <br/>

                <FormLabel column={true} controlId="chipsPerPlayer" label="Chips per player">
                    Chips per player: <FormControl ref={chipsPerPlayerRef} type="number" placeholder={1} required/>
                </FormLabel>
                <br/>

                <FormLabel column={true} controlId="rewards" label="Rewards">
                    Rewards: <FormControl ref={rewardsRef} type="text" placeholder=""/>
                </FormLabel>
                <br/>
                <br/>

                <Button type="submit" variant="primary">Create</Button>
            </Form>
        </>
    )
};

export default CreateGame;