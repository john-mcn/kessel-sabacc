// import {useEffect, useRef, useState} from "react";
// import {Link, useNavigate, useParams} from "react-router-dom";
// import BackButton from "../components/BackButton.jsx";
// import {Alert, Form, FormControl, FormLabel, FormSelect, Table} from "react-bootstrap";
// import Button from "react-bootstrap/Button";
// import PlayerLink from "../player/PlayerLink.jsx";
// import ActionButton from "./ActionButton.jsx";
// import GameLink from "../game/GameLink.jsx";
// import ChooseDice from "../game/ChooseDice.jsx";
//
// const RevealCards = ({ client }) => {
//     const { id } = useParams();
//     const [game, setGame] = useState(null);
//     const [error, setError] = useState(null);
//     const [rolls, setRolls] = useState([])
//     const nav = useNavigate();
//
//     const withImposter = game.players.filter(p => p.hand[0].rank == "Imposter" || p.hand[1].rank == "Imposter");
//     const firstWithImposter = withImposter[0];
//
//
//     useEffect(() => {
//         setRolls([Math.floor(Math.random() * 6 + 1), Math.floor(Math.random() * 6 + 1)]);
//         client.getGameInProgress()
//             .then(response => {
//                 setGame(response.data);
//             }).catch(error => {
//             setError(error)
//         });
//     }, [client]);
//
//     const submitHandler = (e) => {
//         e.preventDefault();
//
//         const payload = {};
//
//         client.resolveImposter(payload).then(() => {
//             nav("/play")
//         }).catch((e) => console.log(e.message));
//     };
//
//     const handleDiceChosen = (selectedValue) => {
//         // if (!pendingTokenAction) return;
//
//         const payload = {
//             playerName: firstWithImposter.name,
//             "Blood",
//             selectedValue,
//             "Sand",
//             //TODO ???
//         };
//
//         sendAction(payload);
//     };
//
//     if (error) {
//         return (<div>{error}</div>);
//     }
//
//     if (game) {
//         if (game.winner) { nav("/play/summary"); }
//
//
//         return (
//             <>
//                 <BackButton/>
//                 {/* -1 from roundNumber bc frontend increments upon completion*/}
//                 <h1>Round {game.roundNumber - 1} - Resolve Imposters</h1>
//                 <h5>Resolve Imposter cards before the reveal phase</h5>
//                 <br/><br/>
//                 {firstWithImposter.hand[0].rank == "Imposter"
//                     ? (
//                         <>
//                             <p>Roll for Blood Imposter:</p>
//                             <ChooseDice rolls={rolls} onChoose={handleDiceChosen} onCancel={() => {console.log("cancel")}}/>
//                         </>
//                     )
//                     : <></>}
//                 <br/>
//                 {firstWithImposter.hand[1].rank == "Imposter"
//                     ? <p>Roll for Sand Imposter:</p>
//                     : <></>}
//                 <br/>
//
//                 {/*<Button onClick={submitHandler}>Start New Round</Button>*/}
//             </>
//         )
//     }
// };
//
// export default RevealCards;

// RevealCards.jsx (replacement)
import {useEffect, useState} from "react";
import {useNavigate} from "react-router-dom";
import BackButton from "../components/BackButton.jsx";
import Button from "react-bootstrap/Button";
import ChooseDice from "./ChooseDice.jsx";

const roll2d6 = () => [Math.floor(Math.random()*6)+1, Math.floor(Math.random()*6)+1];

const RevealCards = ({ client }) => {
    const [game, setGame] = useState(null);
    const [error, setError] = useState(null);
    const [imposterList, setImposterList] = useState([]); // items: { playerName, family, dice:[a,b], chosen: null }
    const nav = useNavigate();

    useEffect(() => {
        client.getGameInProgress()
            .then(res => {
                setGame(res.data);
                buildImposterList(res.data);
            })
            .catch(err => setError(err));
    }, [client]);

    const buildImposterList = (gameData) => {
        const list = [];
        gameData.players.forEach(p => {
            if (p.hand[0] && p.hand[0].rank === "Imposter") {
                list.push({ playerName: p.name, family: "Blood", dice: roll2d6(), chosen: null });
            }
            if (p.hand[1] && p.hand[1].rank === "Imposter") {
                list.push({ playerName: p.name, family: "Sand", dice: roll2d6(), chosen: null });
            }
        });
        setImposterList(list);
    };

    const chooseForIndex = (idx, value) => {
        const copy = [...imposterList];
        copy[idx] = { ...copy[idx], chosen: value };
        setImposterList(copy);
    };

    const submitHandler = async (e) => {
        e.preventDefault();
        // group choices by player into ResolveImposterDTO shape:
        const grouped = {};
        for (const item of imposterList) {
            if (item.chosen == null) {
                alert("Please choose a value for all imposter cards.");
                return;
            }
            grouped[item.playerName] = grouped[item.playerName] || [];
            grouped[item.playerName].push({ family: item.family, rank: String(item.chosen) }); // CardDTO-like shape
        }

        const payload = Object.entries(grouped).map(([playerName, cards]) => ({
            playerName,
            cards
        }));

        try {
            await client.resolveImposters(payload); // implement client call to POST /play/resolve-imposters
            // refresh and navigate back to play — play page will forward to summary if round finished
            nav("/play");
        } catch (err) {
            console.error(err);
            setError(err);
        }
    };

    if (error) return (<div>{String(error)}</div>);
    if (!game) return (<div>Loading...</div>);

    if (imposterList.length === 0) {
        // nothing to resolve, go back to play
        nav("/play");
        return null;
    }

    if (game) {
        return (
            <>
                <BackButton/>
                <h1>Resolve Imposters — Round {game.roundNumber - 1}</h1>
                <p>For each imposter card, pick which dice value to use.</p>

                <form onSubmit={submitHandler}>
                    <div>
                        {imposterList.map((it, idx) => (
                            <div key={`${it.playerName}-${it.family}`}
                                 style={{marginBottom: '12px', padding: '8px', border: '1px solid #ddd'}}>
                                {/*<p>{it.playerName} hand: Blood {game.players.filter(p => p.name == it.playerName)[0].hand[0].rank}, Sand {game.players.filter(p => p.name == it.playerName)[0].hand[1].rank}</p>*/}
                                <p>{it.playerName} hand: Blood {game.players.find(p => p.name === it.playerName)?.hand[0].rank}, Sand {game.players.find(p => p.name === it.playerName)?.hand[1].rank}</p>
                                <strong>{it.playerName}</strong> — {it.family} imposter
                                <div style={{marginTop: '6px'}}>
                                    <span>Dice rolled: </span>
                                    <Button variant={it.chosen === it.dice[0] ? "primary" : "secondary"} size="sm"
                                            onClick={() => chooseForIndex(idx, it.dice[0])}>{it.dice[0]}</Button>
                                    <Button variant={it.chosen === it.dice[1] ? "primary" : "secondary"} size="sm"
                                            onClick={() => chooseForIndex(idx, it.dice[1])}>{it.dice[1]}</Button>
                                    <span> &nbsp;Chosen: {it.chosen ?? ""}</span>
                                    <input type="number" min="1" max="12" placeholder="Manual" value={it.chosen ?? ""}
                                           onChange={(e) => chooseForIndex(idx, Number(e.target.value) || null)}
                                           style={{width: 80, marginLeft: 8}} hidden/>
                                </div>
                            </div>
                        ))}
                    </div>

                    <div style={{marginTop: '18px'}}>
                        <Button type="submit">Submit Imposter Resolutions</Button>
                    </div>
                </form>
            </>
        );
    }
};

export default RevealCards;
