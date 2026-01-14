import {Link} from "react-router-dom";

const PlayerLink = ({ username, name }) => {
    return (
        <Link to={`/players/${username}`}>{name}</Link>
    )
};

export default PlayerLink;