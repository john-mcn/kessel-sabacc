import { DropdownButton, Dropdown, Navbar, Container, Nav } from 'react-bootstrap';
import {Link} from "react-router-dom";

function NavBar({ logout }) {
    return (
        <Navbar bg="dark" data-bs-theme="dark" className="navbar-gold">
            <Container>

                {/* Brand */}
                <Navbar.Brand href="/">Kessel Sabacc</Navbar.Brand>

                {/* Middle Nav */}
                <Nav className="me-auto">
                    <Nav.Link href="/rules">Rules</Nav.Link>
                    <Nav.Link href="/games">Games</Nav.Link>
                    <Nav.Link href="/players">Players</Nav.Link>
                    <Nav.Link href="/tokens">Shift Tokens</Nav.Link>
                </Nav>

                {/* Right Side */}
                <Nav className="align-items-center">
                    <Nav.Link onClick={logout}>Log out</Nav.Link>
                </Nav>

            </Container>
        </Navbar>
    );
}

export default NavBar;
