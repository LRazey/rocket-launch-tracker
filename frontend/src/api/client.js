const TOKEN_KEY = 'jwt'

export function getToken() {
    return localStorage.getItem(TOKEN_KEY)
}

export function setToken(token) {
    localStorage.setItem(TOKEN_KEY, token)
}

export function clearToken() {
    localStorage.removeItem(TOKEN_KEY)
}

export async function apiFetch(path, options = {}) {
    const token = getToken()

    const headers = {
        'Content-Type': 'application/json',
        ...options.headers,
    }
    if (token) {
        headers.Authorization = `Bearer ${token}`
    }

    const response = await fetch(path, { ...options, headers })

    if (!response.ok) {
        const body = await response.json().catch(() => ({}))
        throw new Error(body.error || `Request failed (${response.status})`)
    }

    if (response.status === 204) {
        return null
    }
    return response.json()
}