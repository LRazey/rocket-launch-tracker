import { useState } from 'react'
import { useNavigate, Link } from 'react-router-dom'
import { Container, Card, Form, Button, Alert } from 'react-bootstrap'
import { useAuth } from '../context/AuthContext.jsx'

function RegisterPage() {
    const [username, setUsername] = useState('')
    const [email, setEmail] = useState('')
    const [password, setPassword] = useState('')
    const [error, setError] = useState('')

    const { register } = useAuth()
    const navigate = useNavigate()

    async function handleSubmit(event) {
        event.preventDefault()
        setError('')
        try {
            await register(username, email, password)
            navigate('/login')
        } catch (err) {
            setError(err.message)
        }
    }

    return (
        <Container className="mt-4" style={{ maxWidth: '420px' }}>
            <Card>
                <Card.Body>
                    <Card.Title>Create an Account</Card.Title>

                    {error && <Alert variant="danger">{error}</Alert>}

                    <Form onSubmit={handleSubmit}>
                        <Form.Group className="mb-3">
                            <Form.Label>Username</Form.Label>
                            <Form.Control type="text" value={username}
                                          onChange={(e) => setUsername(e.target.value)} required />
                        </Form.Group>

                        <Form.Group className="mb-3">
                            <Form.Label>Email</Form.Label>
                            <Form.Control type="email" value={email}
                                          onChange={(e) => setEmail(e.target.value)} required />
                        </Form.Group>

                        <Form.Group className="mb-3">
                            <Form.Label>Password</Form.Label>
                            <Form.Control type="password" value={password}
                                          onChange={(e) => setPassword(e.target.value)} required minLength={8} />
                        </Form.Group>

                        <Button type="submit" variant="primary">Register</Button>
                    </Form>

                    <div className="mt-3">
                        Already have an account? <Link to="/login">Log in</Link>
                    </div>
                </Card.Body>
            </Card>
        </Container>
    )
}

export default RegisterPage