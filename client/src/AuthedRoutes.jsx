import {Navigate, Route, Routes} from "react-router-dom";
import Players from "./player/Players.jsx";
import Player from "./player/Player.jsx"
import CreatePlayer from "./player/CreatePlayer.jsx";
import Games from "./game/Games.jsx"
import Game from "./game/Game.jsx"
import CreateGame from "./game/CreateGame.jsx";
import ShiftTokens from "./game/ShiftTokens.jsx";
import Home from "./components/Home.jsx";
import Rules from "./docs/Rules.jsx";
import PlayGame from "./gameplay/PlayGame.jsx";
import StartGame from "./gameplay/StartGame.jsx";
import FinishGame from "./gameplay/FinishGame.jsx";
import FinishRound from "./gameplay/FinishRound.jsx";
import RevealCards from "./gameplay/RevealCards.jsx";
import Contact from "./docs/Contact.jsx";
import Profile from "./player/Profile.jsx";
import Syndicate from "./syndicates/Syndicate.jsx";
import Syndicates from "./syndicates/Syndicates.jsx";

const AuthedRoutes = ({ client, token, user }) => {
    return (
        <div className="mt2">
            <Routes>
                <Route path="/" element={<Home client={client} token={token} user={user}/>}></Route>
                <Route path="/profile" element={<Profile client={client} token={token} user={user}/>}></Route>
                <Route path="/rules" element={<Rules client={ client }/>}></Route>
                <Route path="/contact" element={<Contact client={ client }/>}></Route>
                {/* Player routes */}
                <Route path="/players" element={<Players client={client} token={token} user={user}/>}></Route>
                <Route path="/players/:name" element={<Player client={client} token={token} user={user}/>} />
                <Route path="/players/create" element={<CreatePlayer client={client}/>} />
                {/* Game routes */}
                <Route path="/games" element={<Games client={ client }/>}></Route>
                <Route path="/games/:id" element={<Game client={client} token={token} user={user}/>} />
                <Route path="/games/create" element={<CreateGame client={client}/>} />
                {/* Gameplay routes */}
                <Route path="/play/start-game" element={<StartGame client={client}/>} />
                <Route path="/play" element={<PlayGame client={client}/>} />
                <Route path="/play/reveal" element={<RevealCards client={client}/>} />
                <Route path="/play/round-summary" element={<FinishRound client={client}/>} />
                <Route path="/play/summary" element={<FinishGame client={client}/>} />
                {/* Shift Token routes */}
                <Route path="/tokens" element={<ShiftTokens client={client}/>}/>
                {/* Syndicate routes */}
                <Route path="/syndicates" element={<Syndicates client={client} token={token} user={user}/>}/>
                <Route path="/syndicates/crimson_dawn" element={<Syndicate client={client} token={token} user={user} syndName="crimson_dawn"/>}/>
                <Route path="/syndicates/hutt" element={<Syndicate client={client} token={token} user={user} syndName="hutt"/>}/>
                <Route path="/syndicates/pyke" element={<Syndicate client={client} token={token} user={user} syndName="pyke"/>}/>
            </Routes>
        </div>
    )
}

export default AuthedRoutes;