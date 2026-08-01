import { useState, useEffect } from 'react'
import { Card, Container } from 'react-bootstrap'

function App() {
  const [launches, setLaunches] = useState([])

    useEffect(() => {
        fetch('/api/launches/upcoming')
            .then((res) => res.json())
            .then((data) => setLaunches(data))
    }, [])

  return (
      <Container className="mt-4">
          <h1>Upcoming Launches</h1>
          {launches.map((launch) => (
              <Card key={launch.name} className="mb-3">
                  <Card.Body>
                      <Card.Title>{launch.name}</Card.Title>
                      <Card.Subtitle className="mb-2 text-muted">{launch.agency}</Card.Subtitle>
                      <Card.Text>{launch.summary}</Card.Text>
                      <Card.Text><strong>Status:</strong> {launch.status}</Card.Text>
                  </Card.Body>
              </Card>
          ))}
      </Container>
  )
}

export default App