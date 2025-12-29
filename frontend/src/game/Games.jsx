import styled from "styled-components";
import {useEffect, useState} from "react";
import {Table} from "react-bootstrap";
import {Link} from "react-router-dom";

const Games = ({ client }) => {
    const [games, setGames] = useState([]);
    const [error, setError] = useState(null)

    useEffect(() => {
        client.getGames()
            .then(response => {
                setGames(response.data);
            }).catch(error => {
            setError(error)
        });
    }, [client]);

    if (error) {
        return <div>{error}</div>
    }

    return (
        <>
            <h1>Games</h1>
            <Table striped bordered>
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Winner/s</th>
                    <th>Players</th>
                    <th>Buy-In</th>
                    <th>Rewards</th>
                </tr>
                </thead>
                <tbody>
                {games.map((g) => <tr key={g.id}>
                    <td><Link to={`/games/${g.id}`}>{g.id}</Link></td>
                    <td>
                        {g.winnerNames.length === 1
                            ? g.winnerNames
                            : `[${g.winnerNames.join(", ")}]`}
                    </td>
                    <td>[{g.playerNames.join(", ")}]</td>
                    <td>{g.buyIn}</td>
                    <td>[{g.rewards}]</td>
                </tr>)}
                </tbody>
            </Table>
        </>
    )
}

export default Games;