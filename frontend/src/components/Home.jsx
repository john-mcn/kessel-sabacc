import {Link} from "react-router-dom";
import Button from "react-bootstrap/Button";

const Home = ({ client, token, user }) => {
    if (!user) { return null; }

    return (
        <>
            <h1>Home</h1>
            <p>Hello, {user.name}</p>
            <Link to={`/play/start-game`}><Button variant="primary">Play a Game</Button></Link>
            <br/>
            <hr/>
            <Link to={`/games`}>Games</Link><br/>
            <Link to={`/players`}>Players</Link><br/>
            <Link to={`/tokens`}>Shift Tokens</Link><br/>
        </>
    );
}

export default Home;