import { DropdownButton, Dropdown, Navbar, Container, Nav } from 'react-bootstrap';
import {Link} from "react-router-dom";

function NavBar({ logout, user, token }) {
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
                    <Nav.Link href="/syndicates">Syndicates</Nav.Link>
                </Nav>

                {/* Right Side */}
                {token && token !== "" ? (
                    <DropdownButton title="Menu" variant="outline-light" align="end">
                        <Dropdown.Item href="/profile">Profile</Dropdown.Item>
                        <Dropdown.Divider />

                        <Dropdown.Item onClick={logout}>Logout</Dropdown.Item>
                    </DropdownButton>
                ) : (
                    <Nav.Link href="/">Login</Nav.Link>
                )}

            </Container>
        </Navbar>
    );
}

export default NavBar;
