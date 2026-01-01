import {Link} from "react-router-dom";

const PlayerLink = ({ id }) => {
    return (
        <Link to={`/games/${id}`}>{id}</Link>
    )
};

export default PlayerLink;