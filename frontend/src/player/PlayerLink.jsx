import {Link} from "react-router-dom";

const PlayerLink = ({ name }) => {
    return (
        <Link to={`/players/${name}`}>{name}</Link>
    )
};

export default PlayerLink;