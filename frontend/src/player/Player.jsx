import styled from "styled-components";
import {useEffect, useState} from "react";
import {Form, ListGroup, ListGroupItem, Table} from "react-bootstrap";
import {Link, useNavigate, useParams} from "react-router-dom";
import BackButton from "../components/BackButton.jsx";
import Button from "react-bootstrap/Button";

const Player = ({ client }) => {
    const { name } = useParams();
    const [person, setPlayer] = useState(null);
    const [error, setError] = useState(null);

    const { nav } = useNavigate();

    const handleDeletePlayer = () => {
        if (window.confirm("Are you sure you want to delete this player? This action cannot be undone.")) {
            client.deletePlayer(name)
                .then(() => nav("/users"));
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

    if (person) {
        return (
            <>
                <BackButton/>
                <h1>{person.name}</h1>
                <p>Credits: {person.credits}</p>
                <p>Shift Tokens: {person.tokens}</p>
                <br/>
                <Form onSubmit={handleDeletePlayer}>
                    <Button type="submit" variant="danger">Delete</Button>
                </Form>
            </>
        )
    }
};

export default Player;