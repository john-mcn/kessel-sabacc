import styled from "styled-components";
import {useEffect, useState} from "react";
import {Stack, Table} from "react-bootstrap";
import {Link} from "react-router-dom";
import BackButton from "../components/BackButton.jsx";
import Button from "react-bootstrap/Button";
import CreatePlayer from "./CreatePlayer.jsx";

const Players = ({ client }) => {
    const [people, setPlayers] = useState([]);
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

    return (
        <>
            <BackButton/><br/><br/>
            <Link to={`/players/create`}><Button>Create Player</Button></Link>
            <h1>Players</h1>
            <Table striped bordered>
                <thead>
                <tr>
                    <th>Name</th>
                    <th>Credits</th>
                    <th>Tokens</th>
                </tr>
                </thead>
                <tbody>
                {people.map((p) => <tr key={p.name}>
                    <td><Link to={`/players/${p.name}`}>{p.name}</Link></td>
                    <td>{p.credits}</td>
                    <td>[{p.tokens.join(", ")}]</td>
                </tr>)}
                </tbody>
            </Table>
        </>
    );
}

export default Players;