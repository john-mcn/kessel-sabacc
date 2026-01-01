import { Route, Routes } from "react-router-dom";
import Players from "./player/Players.jsx";
import Player from "./player/Player.jsx"
import CreatePlayer from "./player/CreatePlayer.jsx";
import Games from "./game/Games.jsx"
import Game from "./game/Game.jsx"
import CreateGame from "./game/CreateGame.jsx";
import ShiftTokens from "./game/ShiftTokens.jsx";
import Home from "./components/Home.jsx";
import Rules from "./docs/Rules.jsx";

const AuthedRoutes = ({ client }) => {
    return (
        <div className="mt2">
            <Routes>
                <Route path="/" element={<Home client={ client }/>}></Route>
                <Route path="/rules" element={<Rules client={ client }/>}></Route>
                {/* Player routes */}
                <Route path="/players" element={<Players client={ client }/>}></Route>
                <Route path="/players/:name" element={<Player client={client}/>} />
                <Route path="/players/create" element={<CreatePlayer client={client}/>} />
                {/* Game routes */}
                <Route path="/games" element={<Games client={ client }/>}></Route>
                <Route path="/games/:id" element={<Game client={client}/>} />
                <Route path="/games/create" element={<CreateGame client={client}/>} />
                <Route path="/games/play/{id}" element={<CreateGame client={client}/>} />
                {/* Shift Token routes */}
                <Route path="/tokens" element={<ShiftTokens client={client}/>}/>
            </Routes>
        </div>
    )
}

export default AuthedRoutes;