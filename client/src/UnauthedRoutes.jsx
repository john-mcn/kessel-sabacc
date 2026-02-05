import {Navigate, Route, Routes, useNavigate} from "react-router-dom";

import LoginForm from "./login/LoginForm.jsx";
// import RegisterForm from "../users/CreateUser.jsx";
import NotFound from "./components/NotFound.jsx";
import Contact from "./docs/Contact.jsx";
import Players from "./player/Players.jsx";

const UnauthedRoutes = ({ client, token, user }) => {
    return (
        <Routes>
            <Route path="/" element={<LoginForm client={client}/>} />
            {/*<Route path="signup" element={<RegisterForm client={client}/>}/>*/}
            {/*<Route path="*" element={<NotFound message={"Invalid access"}/>} />*/}

            {/*<Route path="/players" element={<Players client={ client }/>}></Route>*/}

            {token === ""? <Route path="*" element={<Navigate to="/" replace/>}/> : <></>}

            {/* Footer pages */}
            {/*<Route path="/about" element={<About/>} />*/}
            {/*<Route path="/help" element={<Help/>} />*/}
            <Route path="/contact" element={<Contact/>} />
        </Routes>
    );
};

export default UnauthedRoutes;