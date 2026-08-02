import { useState, useEffect } from 'react'
import { Container, Table, Button, Form, Alert } from 'react-bootstrap'
import { apiFetch } from '../api/client.js'

function WatchlistPage() {
    const [entries, setEntries] = useState([])
    const [error, setError] = useState('')

    async function loadWatchlist() {
        try {
            const data = await apiFetch('/api/watchlist')
            setEntries(data)
        } catch (err) {
            setError(err.message)
        }
    }

    useEffect(() => {
        loadWatchlist()
    }, [])

    async function updateEntry(entry, changes) {
        setError('')
        try {
            await apiFetch(`/api/watchlist/${entry.id}`, {
                method: 'PATCH',
                body: JSON.stringify(changes),
            })
            loadWatchlist()
        } catch (err) {
            setError(err.message)
        }
    }

    async function removeEntry(id) {
        setError('')
        try {
            await apiFetch(`/api/watchlist/${id}`, { method: 'DELETE' })
            loadWatchlist()
        } catch (err) {
            setError(err.message)
        }
    }

    return (
        <Container className="mt-4">
            <h1>My Watchlist</h1>

            {error && <Alert variant="danger" dismissible onClose={() => setError('')}>{error}</Alert>}

            {entries.length === 0 ? (
                <p className="text-muted">Nothing saved yet. Add launches from the Launches page.</p>
            ) : (
                <Table striped bordered hover responsive>
                    <thead>
                    <tr>
                        <th>Launch</th>
                        <th>Agency</th>
                        <th>Date (NET)</th>
                        <th>Status</th>
                        <th>Reminder</th>
                        <th>Watched</th>
                        <th>Actions</th>
                    </tr>
                    </thead>
                    <tbody>
                    {entries.map((entry) => (
                        <tr key={entry.id}>
                            <td>{entry.launch?.name ?? 'Details unavailable'}</td>
                            <td>{entry.launch?.agency ?? '—'}</td>
                            <td>{entry.launch?.net ? new Date(entry.launch.net).toLocaleString() : '—'}</td>
                            <td>{entry.launch?.status ?? '—'}</td>
                            <td>
                                <Form.Check
                                    type="switch"
                                    checked={entry.reminderEnabled}
                                    onChange={() => updateEntry(entry, { reminderEnabled: !entry.reminderEnabled })}
                                />
                            </td>
                            <td>
                                <Form.Check
                                    type="switch"
                                    checked={entry.watched}
                                    onChange={() => updateEntry(entry, { watched: !entry.watched })}
                                />
                            </td>
                            <td>
                                <Button variant="outline-danger" size="sm" onClick={() => removeEntry(entry.id)}>
                                    Remove
                                </Button>
                            </td>
                        </tr>
                    ))}
                    </tbody>
                </Table>
            )}
        </Container>
    )
}

export default WatchlistPage