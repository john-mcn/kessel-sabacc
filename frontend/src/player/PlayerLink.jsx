import {Link} from "react-router-dom";

const PlayerLink = ({ user, username, name }) => {
    if (user.username === username) {
        return <Link to="/profile">{name}</Link>;
    }

    return ( <Link to={`/players/${username}`}>{name}</Link> )
};

export default PlayerLink;