import styled from "styled-components";
import {useEffect, useState} from "react";
import BackButton from "../components/BackButton.jsx";
import {Alert} from "react-bootstrap";
import {Link} from "react-router-dom";

const Syndicates = ({ client, token, user }) => {
    const [syndicates, setSyndicates] = useState([]);
    const [error, setError] = useState(null)

    useEffect(() => {
        client.getSyndicates()
            .then(response => {
                setSyndicates(response.data);
            }).catch(error => {
            setError(error)
        });
    }, [client]);

    if (error) {
        return <div>{error}</div>;
    }

    if (syndicates) {
        return (
            <>
                <BackButton/><br/><br/>
                <h1 className="englibesh">Syndicates</h1>
                <p>A higher <b>reputation</b> with a crime syndicate will grant access to elite games with better rewards.</p>
                {syndicates.map((syndicate) =>
                    <>
                        <hr/>
                        <h2><span className="englibesh">{syndicate.displayName}</span> ({syndicate.displayName})</h2>
                        <p><Link to={`/syndicates/${syndicate.name}`}>Decrypt {syndicate.displayName} data entry</Link></p>
                        <p className="aurebesh" style={{fontSize: "12px"}}>{syndicate.description}</p>
                    </>
                )}
            </>
        );
    }
}

export default Syndicates;