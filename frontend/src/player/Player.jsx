import styled from "styled-components";
import {useEffect, useState} from "react";
import {ListGroup, ListGroupItem, Table} from "react-bootstrap";
import {Link, useParams} from "react-router-dom";
import BackButton from "../components/BackButton.jsx";

const Player = ({ client }) => {
    const { name } = useParams();
    const [person, setPlayer] = useState(null);
    const [error, setError] = useState(null)

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
            </>
        )
    }
};

export default Player;