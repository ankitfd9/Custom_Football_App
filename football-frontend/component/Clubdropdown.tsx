'use client'

import React, {useState} from "react";
import {Club} from '@/app/Homepage/page'

interface ClubDropdownProps {
    bookmarks: Club[]; // or a more specific type depending on what a bookmark is
    setBookMarks: React.Dispatch<React.SetStateAction<Club[]>>;
}

export default function Clubdropdown({bookmarks, setBookMarks}:ClubDropdownProps) {
    const [selectedClubId, setSelectedClubId] = useState('');
    const [message, setMessage] = useState('');

    const clubIds = [81, 88, 78, 524, 5, 108, 109, 113, 57, 98, 67, 65, 64, 61, 66, 73];


    function handleChange(e: React.ChangeEvent<HTMLSelectElement>) {
        setSelectedClubId(e.target.value);
        console.log(selectedClubId);

        setMessage('');
    }

    const handleAddClub = async () => {
        if (!selectedClubId) {
            setMessage('Please select a club.');
            return;
        }

        try {
            const res = await fetch('http://localhost:4000/team/addBookmark', {
                method: 'POST',
                credentials: 'include',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({clubId: parseInt(selectedClubId)}),
            });

            if (res.ok) {
                const data = await res.json();
                const club = (data.club || []);
                setBookMarks([...bookmarks,club]);
                setMessage(`Club #${selectedClubId} bookmarked successfully!`);
            } else {
                const error = await res.text();
                setMessage(`Error: ${error}`);
            }
        } catch (err) {
            console.error('Error bookmarking club:', err);
            setMessage('Something went wrong.');
        }
    };


    return (
        <div>
            <select id="club-select" value={selectedClubId} onChange={handleChange}>
                <option key="1000" value="">--Select Club Id--</option>
                {clubIds.map((id) => (
                    <option key={id} value={id.toString()}>
                        Club #{id}
                    </option>
                ))}
            </select>
            <button onClick={handleAddClub}>Add</button>
            {message && <p style={{marginTop: '1rem'}}>{message}</p>}
        </div>
    );
}