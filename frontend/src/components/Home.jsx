import {Link} from "react-router-dom";
import Button from "react-bootstrap/Button";
import {Alert} from "react-bootstrap";
import Englibesh from "./Englibesh.jsx";
import Aurebesh from "./Aurebesh.jsx";

const Home = ({ client, token, user }) => {
    if (!user) { return null; }

    return (
        <>
            <h1 className="englibesh">Home</h1>
            <p>Hello, {user.name}</p>
            <Link to={`/play/start-game`}><Button variant="primary">Play a Game</Button></Link>
            <br/>
            <hr/>
        </>
    );
}

export default Home;