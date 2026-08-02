import { useState, useEffect } from 'react'
import { Badge, Nav, Dropdown } from 'react-bootstrap'
import { Link, useLocation } from 'react-router-dom'
import { apiFetch } from '../api/client.js'

const ONE_DAY_MS = 24 * 60 * 60 * 1000
const ONE_WEEK_MS = 7 * ONE_DAY_MS

function NotificationBell() {
    const [entries, setEntries] = useState([])
    const location = useLocation()

    useEffect(() => {
        apiFetch('/api/watchlist')
            .then((data) => setEntries(data))
            .catch(() => setEntries([]))
    }, [location])

    const now = Date.now()

    // Only saved launches with reminders enabled, that haven't launched yet
    const pending = entries.filter(
        (e) => e.reminderEnabled && e.launch?.net && new Date(e.launch.net).getTime() > now
    )

    const withinWeek = pending.filter(
        (e) => new Date(e.launch.net).getTime() - now <= ONE_WEEK_MS
    )
    const withinDay = withinWeek.filter(
        (e) => new Date(e.launch.net).getTime() - now <= ONE_DAY_MS
    )

    let bellColor = '#adb5bd'
    if (withinDay.length > 0) {
        bellColor = '#d00000'
    } else if (withinWeek.length > 0) {
        bellColor = '#00a000'
    }

    return (
        <Dropdown align="end">
            <Dropdown.Toggle
                variant="link"
                id="reminder-bell"
                className="nav-link p-0 border-0 shadow-none d-flex align-items-center"
            >
                <svg xmlns="http://www.w3.org/2000/svg" width="22" height="22"
                     viewBox="0 0 16 16" fill={bellColor}>
                    <path d="M8 16a2 2 0 0 0 2-2H6a2 2 0 0 0 2 2zM8 1.918l-.797.161A4.002 4.002 0 0 0 4 6c0 .628-.134 2.197-.459 3.742-.16.767-.376 1.566-.663 2.258h10.244c-.287-.692-.502-1.49-.663-2.258C12.134 8.197 12 6.628 12 6a4.002 4.002 0 0 0-3.203-3.92L8 1.918zM14.22 12c.223.447.481.801.78 1H1c.299-.199.557-.553.78-1C2.68 10.2 3 6.88 3 6c0-2.42 1.72-4.44 4.005-4.901a1 1 0 1 1 1.99 0A5.002 5.002 0 0 1 13 6c0 .88.32 4.2 1.22 6z"/>
                </svg>
                {withinWeek.length > 0 && (
                    <Badge bg="" style={{ backgroundColor: bellColor }} className="ms-1">
                        {withinWeek.length}
                    </Badge>
                )}
            </Dropdown.Toggle>

            <Dropdown.Menu style={{ minWidth: '340px' }}>
                <Dropdown.Header>Launch Reminders</Dropdown.Header>

                {withinWeek.length === 0 ? (
                    <Dropdown.ItemText className="text-muted small">
                        No reminders within the next week.
                    </Dropdown.ItemText>
                ) : (
                    withinWeek.map((entry) => {
                        const isUrgent = new Date(entry.launch.net).getTime() - now <= ONE_DAY_MS
                        return (
                            <Dropdown.ItemText key={entry.id} className="small">
              <span
                  style={{
                      display: 'inline-block', width: '8px', height: '8px',
                      borderRadius: '50%', marginRight: '8px',
                      backgroundColor: isUrgent ? '#d00000' : '#00a000',
                  }}
              />
                                <strong>{entry.launch.name}</strong>
                                <br />
                                <span className="text-muted" style={{ marginLeft: '16px' }}>
                {new Date(entry.launch.net).toLocaleString()}
              </span>
                            </Dropdown.ItemText>
                        )
                    })
                )}

                <Dropdown.Divider />
                <Dropdown.Item as={Link} to="/watchlist">View full watchlist</Dropdown.Item>
            </Dropdown.Menu>
        </Dropdown>
    )
}

export default NotificationBell