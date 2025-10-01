'use client';

import { useEffect, useState } from 'react';
import {Clubdropdown as ClubDropdown} from "@/component/Clubdropdown";
import {getContentType} from "next/dist/server/serve-static";



export default function CheckAuth() {
    const [cookies,setCookies]= useState("");

    useEffect(() => {
        const fetchMatches = async () => {
            try {
                const response = await fetch('http://localhost:4000/check-auth', {
                    credentials: 'include' // ✅ This sends cookies
                });
                // || !contentType?.includes("application/json")
                if (response.status === 204) {
                    console.log('No content returned');
                } else {
                    //const data = await response.json();
                    console.log(response.text());
                    setCookies(response.text.toString());
                }
            } catch (err) {
                console.error('Error fetching match data:', err);
            }
        };

        fetchMatches();
    }, []);



    return (
        <div className="p-6">
            {cookies}
        </div>
    );
}
