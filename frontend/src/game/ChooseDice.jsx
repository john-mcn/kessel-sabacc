import {useNavigate} from "react-router-dom";
import {useEffect, useState} from "react";
import Button from "react-bootstrap/Button";

const ChooseDice = ({ rolls, onChoose, onCancel }) => {
    if (rolls) {
        return (
            <>
                <p>Dice values rolled, select one:</p>
                <div style={{display: "flex", gap: 2}}>
                    {/*<Button variant="secondary" onClick={(e) => submitHandler(e, rolls[0])}>{rolls[0]}</Button>*/}
                    <br/><br/>
                    <Button variant="secondary" onClick={(e) => onChoose(rolls[0])}>{rolls[0]}</Button>
                    <Button variant="secondary" onClick={(e) => onChoose(rolls[1])}>{rolls[1]}</Button>
                    <Button variant="link" onClick={onCancel}>Cancel</Button>
                </div>
            </>
        );
    }
};


export default ChooseDice;