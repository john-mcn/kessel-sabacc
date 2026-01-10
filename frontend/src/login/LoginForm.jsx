import { useRef, useState } from "react";

import { Button, Form, FloatingLabel} from "react-bootstrap";

import { styled } from 'styled-components'

const StyledForm = styled.form`
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 1rem;
  max-width: 700px;
  margin: 0 auto;
  margin-top: 40px;
  width: 50%;
  padding: 16px;
  border: 2px solid #e0e0e0;
  border-radius: 12px;
  text-align: center;
  background-color: #fafafa;
  font-family: system-ui;
  `;

const StyledLabel = styled(FloatingLabel)`
  width: 80%;
`;
const StyledButton = styled(Button)`
  width:80%;
`;

const LoginForm = ({ client }) => {

    const [errors, setErrors] = useState([]);

    const usernameRef = useRef(null);
    const passwordRef = useRef(null);

    const wrongCredsMsg = "Incorrect username or password"

    const submitHandler = (e) => {
        e.preventDefault();

        let currentErrors = []

        const username = usernameRef.current.value;
        const password = passwordRef.current.value;

        if (currentErrors.length === 0) {
            client.login({username, password})
                .catch(error => console.log(error));
        } else {
            setErrors(currentErrors)
        }
    }


    return (
        <>
            <StyledForm onSubmit={submitHandler}>
                <h1>Login</h1>

                <StyledLabel className="mb-3" controlId="username" label="Email address">
                    <Form.Control ref={usernameRef} type="username" placeholder="" required/>
                </StyledLabel>

                <StyledLabel className="mb-3" controlId="password" label="Password">
                    <Form.Control ref={passwordRef} type="password" placeholder="" required/>
                </StyledLabel>

                { (errors?.includes(wrongCredsMsg) && <p className="text-danger">{wrongCredsMsg}</p>) }

                <StyledButton type="submit" variant="primary" size="lg">
                    Submit
                </StyledButton>
            </StyledForm>
        </>
    );
};


export default LoginForm;