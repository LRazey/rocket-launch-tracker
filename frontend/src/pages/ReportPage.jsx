import { useState, useEffect } from 'react'
import { Container, Table, Alert, Badge } from 'react-bootstrap'
import { apiFetch } from '../api/client.js'

function ReportPage() {
    const [entries, setEntries] = useState([])
    const [error, setError] = useState('')
    const [generatedAt, setGeneratedAt] = useState(null)

    useEffect(() => {
        apiFetch('/api/watchlist')
            .then((data) => {
                setEntries(data)
                setGeneratedAt(new Date())
            })
            .catch((err) => setError(err.message))
    }, [])

    const sorted = [...entries].sort((a, b) => {
        const dateA = a.launch?.net ?? ''
        const dateB = b.launch?.net ?? ''
        return dateA.localeCompare(dateB)
    })

    const watchedCount = entries.filter((e) => e.watched).length
    const reminderCount = entries.filter((e) => e.reminderEnabled).length

    return (
        <Container className="mt-4">
            <h1>Watchlist Report</h1>
            <p className="text-muted">
                Generated: {generatedAt ? generatedAt.toLocaleString() : '—'}
            </p>

            {error && <Alert variant="danger">{error}</Alert>}

            <p>
                <strong>{entries.length}</strong> launches tracked &nbsp;|&nbsp;
                <strong>{watchedCount}</strong> marked watched &nbsp;|&nbsp;
                <strong>{reminderCount}</strong> with reminders enabled
            </p>

            <Table striped bordered hover responsive>
                <thead>
                <tr>
                    <th>#</th>
                    <th>Launch</th>
                    <th>Agency</th>
                    <th>Date (NET)</th>
                    <th>Status</th>
                    <th>Type</th>
                    <th>Reminder</th>
                    <th>Watched</th>
                    <th>Date Added</th>
                </tr>
                </thead>
                <tbody>
                {sorted.map((entry, index) => (
                    <tr key={entry.id}>
                        <td>{index + 1}</td>
                        <td>{entry.launch?.name ?? 'Details unavailable'}</td>
                        <td>{entry.launch?.agency ?? '—'}</td>
                        <td>{entry.launch?.net ? new Date(entry.launch.net).toLocaleString() : '—'}</td>
                        <td>{entry.launch?.status ?? '—'}</td>
                        <td>
                            {entry.launch?.crewRoster
                                ? <Badge bg="info">Crewed</Badge>
                                : <Badge bg="secondary">Uncrewed</Badge>}
                        </td>
                        <td>{entry.reminderEnabled ? 'Yes' : 'No'}</td>
                        <td>{entry.watched ? 'Yes' : 'No'}</td>
                        <td>{entry.savedDate ? new Date(entry.savedDate).toLocaleString() : '—'}</td>
                    </tr>
                ))}
                </tbody>
            </Table>
        </Container>
    )
}

export default ReportPage