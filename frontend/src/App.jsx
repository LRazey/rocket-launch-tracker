import { Routes, Route, Navigate, Link } from 'react-router-dom'
import {Navbar, Nav, Container, Button} from 'react-bootstrap'
import LaunchesPage from './pages/LaunchesPage.jsx'
import {useAuth} from "./context/AuthContext.jsx";
import LoginPage from "./pages/LoginPage.jsx";
import RegisterPage from "./pages/RegisterPage.jsx";

function App() {
    const { isLoggedIn, logout} = useAuth()

    return (
        <>
            <Navbar bg="dark" variant="dark" expand="lg">
                <Container>
                    <Navbar.Brand as={Link} to="/launches">Rocket Launch Tracker</Navbar.Brand>
                    <Nav className="me-auto">
                        <Nav.Link as={Link} to="/launches">Launches</Nav.Link>
                        {isLoggedIn && <Nav.Link as={Link} to="/watchlist">My Watchlist</Nav.Link>}
                    </Nav>
                    <Nav>
                        {isLoggedIn ? (
                            <Button variant="outline-light" size="sm" onClick={logout}>Log Out</Button>
                        ) : (
                            <>
                                <Nav.Link as={Link} to="/login">Log in</Nav.Link>
                                <Nav.Link as={Link} to="/register">Register</Nav.Link>
                            </>
                        )}
                    </Nav>
                </Container>
            </Navbar>

            <Routes>
                <Route path="/launches" element={<LaunchesPage />} />
                <Route path="/login" element={<LoginPage />} />
                <Route path="/register" element={<RegisterPage />} />
                <Route path="*" element={<Navigate to="/launches" replace />} />
            </Routes>
        </>
    )
}

export default App