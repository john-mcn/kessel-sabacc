import { Link } from "react-router-dom";

const NotFound = ({ message }) => {
    return (
        <div>
            <h1>{message}</h1>
            <p>Click <Link to={"/"}>here</Link> to go home</p>
        </div>
    );
};

export default NotFound;