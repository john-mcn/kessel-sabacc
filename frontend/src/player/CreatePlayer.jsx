import {useEffect, useRef, useState} from "react";
import {Link, useNavigate} from "react-router-dom";
import BackButton from "../components/BackButton.jsx";
import {Form, FormControl, FormLabel, FormSelect} from "react-bootstrap";
import Button from "react-bootstrap/Button";

const CreatePlayer = ({ client }) => {
    const [errors, setErrors] = useState([]);
    const [tokens, setTokens] = useState([]);

    const nav = useNavigate();

    const nameRef = useRef(null);
    const creditsRef = useRef(null);
    const tokensRef = useRef(null);

    useEffect(() => {
        client.getTokens()
            .then(response => {
                setTokens(response.data);
            }).catch(error => {
            // setError(error)
            console.log(error)
        });
    }, [client]);

    const submitHandler = (e) => {
        e.preventDefault();

        let currentErrors = [];

        const name = nameRef.current.value;
        const credits = Number(creditsRef.current.value);
        const tokens = Array.from(tokensRef.current.selectedOptions, option => option.value);

        const payload = {
            name: name,
            credits: credits,
            tokens: tokens
        };

        if (currentErrors.length === 0) {
            client.createPlayer(payload)
                .then(() => nav("/players"))
                .catch(() => nav("/players/create"));
        } else {
            setErrors(currentErrors)
        }
    };

    if (tokens) {
        console.log(tokens.map((t) => t.name));
        return (
            <>
                <BackButton/>

                <Form onSubmit={submitHandler}>
                    <h1>Create New Player</h1>
                    <FormLabel column={true} controlId="name" label="Player name">
                        Name: <FormControl ref={nameRef} type="text" placeholder="" required/>
                    </FormLabel>
                    <br/>

                    <FormLabel column={true} controlId="credits" label="Player name">
                        Credits: <FormControl ref={creditsRef} type="number" placeholder={0}/>
                    </FormLabel>
                    <br/>

                    <FormLabel column={true} controlId="tokens" label="Player name">
                        Tokens: <FormSelect ref={tokensRef} multiple>
                        {tokens.map(t => (
                            <option key={t.name}>{t.name}</option>
                        ))}
                    </FormSelect>
                    </FormLabel>
                    <br/>
                    <Link to={`/tokens`}>Shift Token list</Link>
                    <br/>

                    <br/><br/>
                    <Button type="submit" variant="primary">Create</Button>
                </Form>
            </>
        )
    }
};

export default CreatePlayer;