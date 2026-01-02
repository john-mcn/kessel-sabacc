import {useNavigate} from "react-router-dom";
import {useEffect, useState} from "react";
import Button from "react-bootstrap/Button";

const ChooseDice = ({ client }) => {
    const nav = useNavigate();
    const [rolls, setRolls] = useState([]);
    const value = useState(null);

    const submitHandler = (e, val) => {
        e.preventDefault();

        console.log(val);
    }

    useEffect(() => {
        setRolls([Math.trunc(Math.random() * 6 + 1), Math.trunc(Math.random() * 6 + 1)])
    }, []);

    return (
        <>
            <p>Dice values rolled, select one:</p>
            <div style={{display: "flex", gap: 2}}>
                <Button variant="secondary" onClick={(e) => submitHandler(e, rolls[0])}>{rolls[0]}</Button>
                <br/><br/>
                <Button variant="secondary" onClick={(e) => submitHandler(e, rolls[1])}>{rolls[1]}</Button>
            </div>
        </>
    );
};


export default ChooseDice;