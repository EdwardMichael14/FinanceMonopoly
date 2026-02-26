import React, { useState, useEffect } from 'react';
import { Routes, Route, useNavigate, Navigate } from 'react-router-dom';
import Lobby from './components/Lobby';
import GameBoard from './components/GameBoard';
import ResultsPage from './components/ResultsPage';
import { api } from './utils/api';
import { Wallet, User } from 'lucide-react';

export default function App() {
    const [game, setGame] = useState(null);
    const [player, setPlayer] = useState(null);
    const [leaderboard, setLeaderboard] = useState([]);
    const [history, setHistory] = useState([]);
    const navigate = useNavigate();

    const fetchGameState = async () => {
        if (!game?.gameCode) return;
        const data = await api.getGame(game.gameCode);
        if (data) {
            setGame(data);

            // Refresh leaderboard
            const lb = await api.getLeaderboard(game.gameCode, data.currentRound || 1);
            if (lb) setLeaderboard(lb);

            // If player exists, sync their specific state
            if (player) {
                const currentPlayerData = data.players.find(p => p.id === player.id);
                if (currentPlayerData) {
                    setPlayer(prev => ({ ...prev, ...currentPlayerData }));
                }
                const h = await api.getPlayerHistory(player.id);
                if (h) setHistory(h);
            }

            // Handle game end routing
            if (data.status === 'ENDED') {
                navigate('/results');
            } else if (data.status === 'IN_PROGRESS' && player && window.location.pathname === '/') {
                navigate('/game');
            }
        }
    };

    // Polling for game state updates
    useEffect(() => {
        fetchGameState(); // Initial fetch
        const interval = setInterval(fetchGameState, 1500); // 1.5s interval
        return () => clearInterval(interval);
    }, [game?.gameCode, player?.id, navigate]);

    const handleGameCreated = (newGame) => {
        setGame(newGame);
    };

    const handlePlayerJoined = (newPlayer, gameCode) => {
        setPlayer(newPlayer);
        setGame({ gameCode }); // Initial placeholder until polling kicks in
    };

    return (
        <div className="min-h-screen p-4 md:p-8 max-w-7xl mx-auto space-y-12">
            <header className="flex justify-between items-center">
                <h1 className="text-4xl font-black gradient-text tracking-tighter uppercase italic">Finance Monopoly</h1>

                {player && (
                    <div className="flex gap-4 items-center">
                        <div className="glass px-4 py-2 rounded-2xl flex items-center gap-2 border-sky-500/30">
                            <span className="text-[10px] font-black text-sky-500/50 uppercase tracking-widest absolute -top-2 left-4 bg-slate-950 px-2">Role</span>
                            <User className="w-4 h-4 text-sky-400" />
                            <span className="font-bold text-sm tracking-tight">{player.name}</span>
                            <span className="text-[10px] font-black text-slate-500 uppercase tracking-widest ml-2 border-l border-white/10 pl-2">Software Engineer</span>
                        </div>
                        <div className="glass px-4 py-2 rounded-2xl flex items-center gap-2 border-emerald-500/30">
                            <Wallet className="w-4 h-4 text-emerald-400" />
                            <span className="font-mono font-bold text-emerald-400 text-sm">
                                {api.formatNaira(player.cashBalance)}
                            </span>
                        </div>
                    </div>
                )}
            </header>

            <main>
                <Routes>
                    <Route path="/" element={
                        <Lobby
                            onGameCreated={handleGameCreated}
                            onPlayerJoined={handlePlayerJoined}
                            game={game}
                            currentPlayer={player}
                        />
                    } />
                    <Route path="/game" element={
                        player ? (
                            <GameBoard
                                game={game}
                                player={player}
                                leaderboard={leaderboard}
                                history={history}
                                refresh={fetchGameState}
                            />
                        ) : <Navigate to="/" />
                    } />
                    <Route path="/results" element={
                        <ResultsPage
                            game={game}
                            player={player}
                            leaderboard={leaderboard}
                            refresh={fetchGameState}
                        />
                    } />
                </Routes>
            </main>
        </div>
    );
}
