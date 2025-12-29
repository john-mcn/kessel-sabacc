import { Route, Routes } from "react-router-dom";
import Players from "./player/Players.jsx";
import Player from "./player/Player.jsx"
import Games from "./game/Games.jsx"
import Game from "./game/Game.jsx"

const AuthedRoutes = ({ client }) => {
    return (
        <div className="mt2">
            <Routes>
                <Route path="/players" element={<Players client={ client }/>}></Route>
                <Route path="/players/:name" element={<Player client={client}/>} />
                <Route path="/games" element={<Games client={ client }/>}></Route>
                <Route path="/games/:id" element={<Game client={client}/>} />
            </Routes>
        </div>
    )
}

export default AuthedRoutes;