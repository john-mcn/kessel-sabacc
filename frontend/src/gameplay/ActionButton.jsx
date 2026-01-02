import {useNavigate} from "react-router-dom";
import Button from "react-bootstrap/Button";
import {useEffect, useState} from "react";

const ActionButton = ({ client, text, player, action, tokenIndex, disabled, onUpdate, onAction }) => {
    const nav = useNavigate();

    const submitHandler = (e) => {
        e.preventDefault();

        onAction({ player, action, tokenIndex})
    };

    return (
        <Button type="button" variant="primary" onClick={submitHandler} disabled={disabled}>
            {text}
        </Button>
    );
};

export default ActionButton;