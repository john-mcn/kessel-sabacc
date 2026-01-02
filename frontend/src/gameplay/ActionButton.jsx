import {useNavigate} from "react-router-dom";
import Button from "react-bootstrap/Button";
import {useEffect, useState} from "react";

const ActionButton = ({ client, text, player, action, tokenIndex, disabled, onUpdate }) => {
    const nav = useNavigate();

    const submitHandler = (e) => {
        e.preventDefault();

        if (tokenIndex && player.tokens[tokenIndex]) {
            nav("/play/choose-dice");
        }

        const payload = {
            playerName: player.name,
            action: action,
            tokenIndex: tokenIndex
        };

        //NOTE assume on /play already
        client.performAction(payload)
            .then((response) => {
                onUpdate(response.data);
            })
            .catch((e) => console.log(e.message));
    };

    return (
        <Button type="button" variant="primary" onClick={submitHandler} disabled={disabled}>
            {text}
        </Button>
    );
};

export default ActionButton;