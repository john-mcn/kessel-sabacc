import { Navbar, Container, Nav } from 'react-bootstrap';
import "../style/Footer.css";

function Footer() {
    return (
        <>
            <Navbar fixed="bottom" bg="dark" data-bs-theme="dark" className="footer-fix">
                <Container className="flex-column">

                    {/* Links row */}
                    <Nav className="m-auto">
                        <Nav.Link href="/about" className="footer-link">About</Nav.Link>
                        <Nav.Link href="/contact" className="footer-link">Contact</Nav.Link>
                        <Nav.Link href="/help" className="footer-link">Help</Nav.Link>
                    </Nav>

                    {/* Copyright */}
                    <div className="footer-copy">
                        © {new Date().getFullYear()} KesselSabacc - John McNally &nbsp;|&nbsp;
                        'Sabacc' owned by Lucasfilm, used for educational purposes
                    </div>

                </Container>
            </Navbar>
        </>
    );
}

export default Footer;
