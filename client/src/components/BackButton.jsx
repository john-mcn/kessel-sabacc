import Button from "react-bootstrap/Button";
import { useNavigate } from "react-router-dom";

const BackButton = () => {
    const navigate = useNavigate();
    return (
        <Button variant="secondary" onClick={() => navigate(-1)}>
            ← Back
        </Button>
    );
};

export default BackButton;
