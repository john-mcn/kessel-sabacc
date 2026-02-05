import React, { useState } from "react";
import Button from "react-bootstrap/Button";
import {Form} from "react-bootstrap";

const Feedback = ({ client }) => {
    const [text, setText] = useState("");
    const [sent, setSent] = useState(false);

    const FORM_ID = "1FAIpQLScxTNMhlxtuXtaiFB2Ut6c_HP4efm8rmLfHICg-pIZtywq3Lw";
    const USERNAME_ENTRY_ID = "entry.485346115";
    const FEEDBACK_ENTRY_ID = "entry.689660902";

    const submit = async (e) => {
        e.preventDefault();

        const formData = new FormData();
        const username = "test"
        formData.append(USERNAME_ENTRY_ID, username);
        formData.append(FEEDBACK_ENTRY_ID, text);

        await fetch(
            `https://docs.google.com/forms/d/e/${FORM_ID}/formResponse`,
            {
                method: "POST",
                mode: "no-cors", // IMPORTANT
                body: formData,
            }
        );

        setText("");
        setSent(true);
    };

    return (
        <Form onSubmit={submit} style={{ maxWidth: 600 }}>
            <label>
                Enter feedback or suggestions
                <textarea
                    required
                    value={text}
                    onChange={(e) => setText(e.target.value)}
                    rows={5}
                    style={{ width: "100%", marginTop: 8 }}
                />
            </label>
            <br/>

            <Button type="submit" style={{ marginTop: 10 }}>
                Send
            </Button>

            {sent && <p>Thanks! Feedback sent.</p>}
        </Form>
    );
};

export default Feedback;
