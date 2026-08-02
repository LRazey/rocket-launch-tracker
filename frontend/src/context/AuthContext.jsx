import { createContext, useContext, useState } from 'react'
import { apiFetch, getToken, setToken, clearToken } from '../api/client.js'

const AuthContext = createContext(null)

export function AuthProvider({ children }) {
    const [token, setTokenState] = useState(getToken())

    const isLoggedIn = Boolean(token)

    async function login(username, password) {
        const data = await apiFetch('/api/auth/login', {
            method: 'POST',
            body: JSON.stringify({ username, password }),
        })
        setToken(data.token)
        setTokenState(data.token)
    }

    async function register(username, email, password) {
        await apiFetch('/api/auth/register', {
            method: 'POST',
            body: JSON.stringify({ username, email, password }),
        })
    }

    function logout() {
        clearToken()
        setTokenState(null)
    }

    return (
        <AuthContext.Provider value={{ token, isLoggedIn, login, register, logout }}>
            {children}
        </AuthContext.Provider>
    )
}

export function useAuth() {
    return useContext(AuthContext)
}