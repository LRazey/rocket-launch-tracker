import { useState, useEffect } from 'react'
import {Alert, Button, Card, Container, Form, InputGroup} from 'react-bootstrap'
import {apiFetch} from "../api/client.js";
import {useAuth} from "../context/AuthContext.jsx";

function LaunchesPage() {
    const [launches, setLaunches] = useState([])
    const [message, setMessage] = useState('')
    const { isLoggedIn } = useAuth()
    const [query,setQuery] = useState('')

    async function loadUpcoming() {
        const res = await fetch('/api/launches/upcoming')
        setLaunches(await res.json())
    }

    useEffect(() => {
        loadUpcoming()
    }, [])

    async function handleSearch(event) {
        event.preventDefault()
        if (!query.trim()) {
            loadUpcoming()
            return
        }
        const res = await fetch(`/api/launches/search?q=${encodeURIComponent(query)}`)
        setLaunches(await res.json())
    }

    async function handleAdd(launch) {
        setMessage('')
        try {
            await apiFetch('/api/watchlist', {
                method: 'POST',
                body: JSON.stringify({ launchId: launch.id }),
            })
            setMessage(`Added "${launch.name}" to your watchlist.`)
        } catch (err) {
            setMessage(err.message)
        }
    }

    return (
        <Container className="mt-4">
            <h1>Upcoming Launches</h1>

            <Form onSubmit={handleSearch} className="mb-4">
                <InputGroup>
                    <Form.Control
                        placeholder="Search launches (e.g. Starlink, SpaceX)"
                        value={query}
                        onChange={(e) => setQuery(e.target.value)}
                    />
                    <Button type="submit" variant="primary">Search</Button>
                    <Button variant="outline-secondary" onClick={() => { setQuery(''); loadUpcoming() }}>
                        Clear
                    </Button>
                </InputGroup>
            </Form>

            {message && (
                <Alert
                    variant="info"
                    dismissible
                    onClose={() => setMessage('')}
                    style={{ position: 'fixed', top: '70px', right: '20px', zIndex: 1050, maxWidth: '360px' }}
                >
                    {message}
                </Alert>
            )}

            {launches.map((launch) => (
                <Card key={launch.id} className="mb-3">
                    <Card.Body>
                        <Card.Title>{launch.name}</Card.Title>
                        <Card.Subtitle className="mb-2 text-muted">{launch.agency}</Card.Subtitle>
                        <Card.Text>{launch.summary}</Card.Text>
                        <Card.Text><strong>Status:</strong> {launch.status}</Card.Text>

                        {isLoggedIn && (
                            <Button variant="outline-primary" size="sm" onClick={() => handleAdd(launch)}>
                                Add to Watchlist
                            </Button>
                        )}
                    </Card.Body>
                </Card>
            ))}
        </Container>
    )
}

export default LaunchesPage