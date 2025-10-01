'use client';

import { useEffect, useState } from 'react';
import Clubdropdown from "@/component/Clubdropdown";
import {getContentType} from "next/dist/server/serve-static";

type Team = {
    id: number;
    name: string;
    crest: string;
};

export type Club = {
    id: number;
};

type Score = {
    fullTime: {
        home: number | null;
        away: number | null;
    };
};

type Match = {
    id: number;
    status: string;
    utcDate: string;
    homeTeam: Team;
    awayTeam: Team;
    score: Score;
};

export default function HomepageClient() {
    const [matches, setMatches] = useState<Match[]>([]);
    const [bookmarks,setBookMarks] = useState<Club[]>([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const fetchMatches = async () => {
            try {
                const res = await fetch('http://localhost:4000/team/bookmarks',{
                    method: 'GET',
                    credentials: 'include',
                });
                if(!res.ok){
                    console.log('No bookmarked Teams.')
                }
                else{
                    const datas = await res.json();
                    console.log(datas.bookmarks);
                    const response = await fetch('http://localhost:4000/team',{
                        credentials: 'include'
                    });
                    const contentType = response.headers.get("Content-Type");
                    // || !contentType?.includes("application/json")
                    if (response.status === 204) {
                        console.log('No content returned');
                    } else {
                        const data = await response.json();
                        console.log(data.matches);
                        setMatches(data.matches || []);
                    }
                }

            } catch (err) {
                console.error('Error fetching match data:', err);
            } finally {
                setLoading(false);
            }
        };

        fetchMatches();
    }, [bookmarks]);

    const scheduledMatches = matches.filter((m) => m.status === 'SCHEDULED');
    const finishedMatches = matches.filter((m) => m.status === 'FINISHED');
    const timedMatches = matches.filter((m) => m.status === 'TIMED');


    if (loading) return <div className="p-6">Loading matches...</div>;

    return (
        <div className="p-6">
            <Clubdropdown bookmarks={bookmarks} setBookMarks={setBookMarks}/>
            <h1 className="text-3xl font-bold mb-6">Team Matches</h1>

            <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
                {/*Timed Matched*/}
                <div className="bg-blue-50 rounded-lg shadow h-[500px] overflow-y-auto p-4">
                    <h2 className="text-2xl font-semibold text-blue-700 mb-4">Next Matches</h2>
                    {timedMatches.length === 0 ? (
                        <p>No Timed matches.</p>
                    ) : (
                        timedMatches.map((match) => (
                            <MatchCard key={match.id} match={match} showScore={false} />
                        ))
                    )}
                </div>
                {/* Scheduled Matches */}
                <div className="bg-blue-50 rounded-lg shadow h-[500px] overflow-y-auto p-4">
                    <h2 className="text-2xl font-semibold text-blue-700 mb-4">Scheduled</h2>
                    {scheduledMatches.length === 0 ? (
                        <p>No scheduled matches.</p>
                    ) : (
                        scheduledMatches.map((match) => (
                            <MatchCard key={match.id} match={match} showScore={false} />
                        ))
                    )}
                </div>

                {/* Finished Matches */}
                <div className="bg-green-50 rounded-lg shadow h-[500px] overflow-y-auto p-4">
                    <h2 className="text-2xl font-semibold text-green-700 mb-4">Finished</h2>
                    {finishedMatches.length === 0 ? (
                        <p>No finished matches.</p>
                    ) : (
                        finishedMatches.map((match) => (
                            <MatchCard key={match.id} match={match} showScore={true} />
                        ))
                    )}
                </div>
            </div>
        </div>
    );
}

function MatchCard({
                       match,
                       showScore,
                   }: {
    match: Match;
    showScore: boolean;
}) {
    return (
        <div className="flex items-center gap-4 border p-3 mb-3 rounded bg-white shadow-sm">
            <img
                src={match.homeTeam.crest}
                alt={match.homeTeam.name}
                className="w-8 h-8 object-contain"
            />
            <div className="flex-1">
                <p className="font-medium text-sm">
                    {match.homeTeam.name} vs {match.awayTeam.name}
                </p>
                <p className="text-xs text-gray-500">
                    {new Date(match.utcDate).toLocaleString()}
                </p>
                {showScore && match.score?.fullTime && (
                    <p className="text-sm font-semibold text-gray-800 mt-1">
                        {match.score.fullTime.home} - {match.score.fullTime.away}
                    </p>
                )}
            </div>
            <img
                src={match.awayTeam.crest}
                alt={match.awayTeam.name}
                className="w-8 h-8 object-contain"
            />
        </div>
    );
}
