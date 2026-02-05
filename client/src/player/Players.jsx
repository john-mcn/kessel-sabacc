import styled from "styled-components";
import {useEffect, useState} from "react";
import {Stack, Table} from "react-bootstrap";
import {Link} from "react-router-dom";
import BackButton from "../components/BackButton.jsx";
import Button from "react-bootstrap/Button";
import CreatePlayer from "./CreatePlayer.jsx";
import PlayerLink from "./PlayerLink.jsx";
import Credits from "../components/Credits.jsx";

const Players = ({ client, token, user }) => {
    const [players, setPlayers] = useState([]);
    const [error, setError] = useState(null)

    const Grid = styled.div`
        display: flex;
        flex-wrap: wrap;
        justify-content: flex-start;
        gap: 24px;
        max-width: 1200px;
        margin: 0 auto;
    `;

    useEffect(() => {
        client.getPlayers()
            .then(response => {
                setPlayers(response.data);
            }).catch(error => {
                setError(error)
        });
    }, [client]);

    if (error) {
        return <div>{error}</div>;
    }

    if (players) {
        return (
            <>
                <BackButton/><br/><br/>
                <Link to={`/players/create`}><Button>Create Player</Button></Link>
                <h1 className="englibesh">Players</h1>
                <Table striped bordered>
                    <thead>
                    <tr>
                        <th>Name</th>
                        <th>Credits</th>
                        <th>Tokens</th>
                        <th>Avg. Rep</th>
                    </tr>
                    </thead>
                    <tbody>
                    {players.map((p) => <tr key={p.username}>
                        <td><PlayerLink user={user} username={p.username} name={p.name}/></td>
                        <td><Credits amount={p.credits}/></td>
                        <td>[{p.tokens.join(", ")}]</td>
                        <td>{(p.dawnRep + p.huttRep + p.pykeRep) / 3}%</td>
                    </tr>)}
                    </tbody>
                </Table>
            </>
        );
    }
}

export default Players;