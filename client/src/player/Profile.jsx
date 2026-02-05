import styled from "styled-components";
import {useEffect, useState} from "react";
import {Form, ListGroup, ListGroupItem, Table} from "react-bootstrap";
import {Link, useNavigate, useParams} from "react-router-dom";
import BackButton from "../components/BackButton.jsx";
import Button from "react-bootstrap/Button";
import Credits from "../components/Credits.jsx";

const Profile = ({ client, token, user }) => {
    const [player, setPlayer] = useState(null);
    const [error, setError] = useState(null);

    const nav  = useNavigate();

    const handleDeletePlayer = (e) => {
        e.preventDefault();
        if (window.confirm("Are you sure you want to delete this player? This action cannot be undone.")) {
            client.deletePlayer(user.username)
                .then(() => nav("/players"));
        }
    };

    useEffect(() => {
        client.getPlayer(user.username)
            .then(response => {
                setPlayer(response.data);
            }).catch(error => {
            setError(error)
            console.log(error)
        });
    }, [client]);

    if (error) {
        return <div>{error}</div>
    }

    if (player) {
        return (
            <>
                <BackButton/>
                <h1><span className="englibesh">Your profile</span> ({player.name})</h1>
                <p>Credits: <Credits amount={player.credits}/></p>
                <p>Shift Tokens: [{player.tokens.join(", ")}]</p>
                <u>Reputation:</u>
                <ul>
                    <li><Link to="/syndicates/crimson_dawn">Crimson Dawn</Link>: {player.dawnRep}%</li>
                    <li><Link to="/syndicates/hutt">Hutt Cartel</Link>: {player.huttRep}%</li>
                    <li><Link to="/syndicates/pyke">Pyke Syndicate</Link>: {player.pykeRep}%</li>
                </ul>
                <br/>
                <Form onSubmit={handleDeletePlayer}>
                    <Button type="submit" variant="danger">Delete</Button>
                </Form>
            </>
        )
    }
};

export default Profile;