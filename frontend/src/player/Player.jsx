import styled from "styled-components";
import {useEffect, useState} from "react";
import {Form, ListGroup, ListGroupItem, Table} from "react-bootstrap";
import {Link, useNavigate, useParams} from "react-router-dom";
import BackButton from "../components/BackButton.jsx";
import Button from "react-bootstrap/Button";
import Credits from "../components/Credits.jsx";

const Player = ({ client, token, user }) => {
    //TODO change name -> username, and person -> player; causes error??
    const { name } = useParams();
    const [player, setPlayer] = useState(null);
    const [error, setError] = useState(null);

    const nav  = useNavigate();

    const handleDeletePlayer = (e) => {
        e.preventDefault();
        if (window.confirm("Are you sure you want to delete this player? This action cannot be undone.")) {
            client.deletePlayer(name)
                .then(() => nav("/players"));
        }
    };

    useEffect(() => {
        client.getPlayer(name)
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
                <h1><span className="englibesh">{player.name}</span> ({player.name})</h1>
                <p>Credits: <Credits amount={player.credits}/></p>
                <p>Shift Tokens: [{player.tokens.join(", ")}]</p>
                <br/>
                <Form onSubmit={handleDeletePlayer}>
                    <Button type="submit" variant="danger">Delete</Button>
                </Form>
            </>
        )
    }
};

export default Player;