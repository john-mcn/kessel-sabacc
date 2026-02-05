import styled from "styled-components";
import {useEffect, useState} from "react";
import {Form, ListGroup, ListGroupItem, Table} from "react-bootstrap";
import {Link, useNavigate, useParams} from "react-router-dom";
import BackButton from "../components/BackButton.jsx";
import Button from "react-bootstrap/Button";
import Credits from "../components/Credits.jsx";

const Syndicate = ({ client, token, user, syndName }) => {
    const [syndicate, setSyndicate] = useState(null);

    useEffect(() => {
        client.getSyndicate(syndName)
            .then(response => {
                setSyndicate(response.data);
            }).catch(error => {
            console.log(error)
        });
    }, [client]);

    if (syndicate) {
        return (
            <>
                <BackButton/>
                <h1><span className="englibesh">{syndicate.displayName}</span> ({syndicate.displayName})</h1>
                <p>{syndicate.description}</p>
            </>
        )
    }
};

export default Syndicate;