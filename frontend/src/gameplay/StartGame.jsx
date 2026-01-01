import {useEffect, useRef, useState} from "react";
import {useNavigate} from "react-router-dom";
import BackButton from "../components/BackButton.jsx";
import {Form, FormControl, FormLabel, FormSelect} from "react-bootstrap";
import Button from "react-bootstrap/Button";

const StartGame = ({ client }) => {
    const [errors, setErrors] = useState([]);
    const nav = useNavigate();

    const [playersLst, setPlayerNamesLst] = useState([]);
    const playersRef = useRef([]);
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

        const players = Array.from(
            playersRef.current.selectedOptions,
            option => ({ name: option.value })
        );
        // const winnerNames = Array.from(winnerNamesRef.current.selectedOptions, option => option.value);
        const buIn = Number(buyInRef.current.value);
        const chipsPerPlayer = Number(chipsPerPlayerRef.current.value);
        const rewards = rewardsRef.current.value.split(",");

        const payload = {
            players: players,
            // winnerNames: winnerNames,
            buyIn: buIn,
            chipsPerPlayer: chipsPerPlayer,
            rewards: rewards
        };

        // if (!winnerNames.every(n => players.includes(n))) {
        //     currentErrors.push(winnersArePlayersMsg);
        // }

        if (currentErrors.length === 0) {
            client.startGame(payload)
                .then((response) => {
                    nav(`/play`)
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
                <FormLabel column={true} controlId="players" label="Player names">
                    Players: <FormSelect ref={playersRef} multiple required>
                    {playersLst.map(p => (
                        <option key={`player:${p.name}`}>{p.name}</option>
                    ))}
                </FormSelect>
                </FormLabel>
                <br/>

                {/*/!*TODO validation for 'winner subset players'*!/*/}
                {/*<FormLabel column={true} controlId="winnerNames" label="Winner names">*/}
                {/*    Winners: <FormSelect ref={winnerNamesRef} multiple required>*/}
                {/*    {playersLst.map(n => (*/}
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

export default StartGame;