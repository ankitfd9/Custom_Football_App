'use client';

import { useRouter } from 'next/navigation';
import { useState } from "react";

export default function Login() {
    const router = useRouter();
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState("");

    const handleLogin = async () => {
        setError("");
        try {
            const response = await


            /*fetch('http://localhost:4000/sign-in', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                credentials: 'include', // ✅ CRITICAL
                body: JSON.stringify({
                    username: username,
                    password: password
                })
            })
                .then(response => {
                    console.log('Headers:', response.headers);
                    return response.json();
                })
                .then(data => {
                    console.log('Login success:', data);

                    // Now make authenticated request
                    return fetch('http://localhost:4000/team', {
                        credentials: 'include' // ✅ CRITICAL
                    });
                })
                .then(response => response.json())
                .then(data => console.log('Team data:', data))
                .catch(error => console.error('Error:', error));*/

                fetch('http://localhost:4000/sign-in', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                credentials: 'include',
                body: JSON.stringify({ username, password }),
            });

            if (response.ok) {
                // Optionally handle token or user data here
                router.push('/Homepage');
            } else {
                const data = await response.json();
                setError(data.message || "Login failed");
            }
        } catch (err) {
            setError("Network error");
        }
    };

    return (
        <div>
            <div className="flex gap-4 items-center flex-col sm:flex-row">
                <input
                    type="text"
                    id="username"
                    name="username"
                    placeholder="Enter your username"
                    value={username}
                    onChange={e => setUsername(e.target.value)}
                    className="border border-gray-300 rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
                />
                <input
                    type="password"
                    id="password"
                    name="password"
                    placeholder="Enter your password"
                    value={password}
                    onChange={e => setPassword(e.target.value)}
                    className="border border-gray-300 rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
                />
            </div>
            <button
                onClick={handleLogin}
                className="px-4 py-2 bg-blue-600 text-white rounded mt-4"
            >
                Login
            </button>
            {error && (
                <div className="text-red-500 mt-2">{error}</div>
            )}
        </div>
    );
}