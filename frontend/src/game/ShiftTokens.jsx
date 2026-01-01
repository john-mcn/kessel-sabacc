import styled from "styled-components";
import {useEffect, useState} from "react";
import {Stack, Table} from "react-bootstrap";
import {Link} from "react-router-dom";
import BackButton from "../components/BackButton.jsx";
import Button from "react-bootstrap/Button";

const ShiftTokens = ({ client }) => {
    const [tokens, setTokens] = useState([]);
    const [error, setError] = useState(null)

    useEffect(() => {
        client.getTokens()
            .then(response => {
                setTokens(response.data);
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
            <h1>Shift Tokens</h1>
            <Table striped bordered>
                <thead>
                <tr>
                    <th>Name</th>
                    <th>Effect</th>
                </tr>
                </thead>
                <tbody>
                {tokens.map((t) => <tr key={t.name}>
                    <td>{t.name}</td>
                    <td>{t.description}</td>
                </tr>)}
                </tbody>
            </Table>
        </>
    );
}

export default ShiftTokens;