import React, { useState } from 'react';
import { api } from '../utils/api';
import { Users, Plus, ChevronRight, Award } from 'lucide-react';

export default function Lobby({ onGameCreated, onPlayerJoined, game, currentPlayer }) {
    const [userName, setUserName] = useState('');
    const [joinCode, setJoinCode] = useState('');

    const handleCreate = async () => {
        const data = await api.createGame();
        if (data) {
            onGameCreated(data);
            setJoinCode(data.gameCode);
        }
    };

    const handleJoin = async () => {
        if (!userName || !joinCode) return alert('Name and Code required');
        const res = await api.joinGame(joinCode, userName);
        if (res.successful) {
            onPlayerJoined(res.data, joinCode);
        } else {
            alert(res.message);
        }
    };

    const handleStart = async () => {
        const success = await api.startGame(game.gameCode);
        if (!success) alert('Failed to start');
    };

    return (
        <div className="grid lg:grid-cols-2 gap-12 items-center py-12">
            <div className="space-y-8">
                <h2 className="text-6xl font-black leading-[1.1] tracking-tight italic uppercase">
                    Build Wealth. <span className="text-sky-500">Master</span> The Dice.
                </h2>
                <p className="text-xl text-slate-400 font-light max-w-lg">
                    Compete with friends in a high-stakes financial simulation. Manage loans, select housing, and grow your net worth.
                </p>

                <div className="space-y-4 max-w-md">
                    {!currentPlayer ? (
                        <>
                            <input
                                type="text"
                                placeholder="What should we call you?"
                                className="w-full bg-slate-900 border border-white/10 rounded-2xl px-6 py-4 focus:ring-2 focus:ring-sky-500 outline-none transition-all"
                                value={userName}
                                onChange={(e) => setUserName(e.target.value)}
                            />

                            <div className="flex gap-4">
                                <button onClick={handleCreate} className="btn-primary flex-1 flex items-center justify-center gap-2">
                                    <Plus className="w-5 h-5" />
                                    New Game
                                </button>
                                <div className="flex flex-[1.5]">
                                    <input
                                        type="text"
                                        placeholder="CODE"
                                        className="w-full bg-slate-900 border border-white/10 rounded-l-2xl px-4 py-4 outline-none focus:border-sky-500"
                                        value={joinCode}
                                        onChange={(e) => setJoinCode(e.target.value.toUpperCase())}
                                    />
                                    <button onClick={handleJoin} className="bg-slate-800 hover:bg-slate-700 px-6 rounded-r-2xl font-bold border-y border-r border-white/10 transition-colors">
                                        Join
                                    </button>
                                </div>
                            </div>

                            <div className="p-6 rounded-3xl bg-sky-500/5 border border-sky-500/10 space-y-4 animate-in fade-in slide-in-from-top-4 duration-700">
                                <div className="flex items-center gap-2 text-xs font-black text-sky-400 uppercase tracking-widest italic">
                                    <Award className="w-4 h-4" />
                                    Initial Profile: Software Engineer
                                </div>
                                <div className="grid grid-cols-2 gap-4">
                                    <div className="space-y-1">
                                        <div className="text-[10px] text-slate-500 font-bold uppercase tracking-tight">Starting Cash</div>
                                        <div className="text-emerald-400 font-mono font-bold">₦2,400,000</div>
                                    </div>
                                    <div className="space-y-1">
                                        <div className="text-[10px] text-slate-500 font-bold uppercase tracking-tight">Student Loan</div>
                                        <div className="text-rose-500 font-mono font-bold">₦1,500,000</div>
                                    </div>
                                </div>
                            </div>
                        </>
                    ) : (
                        <div className="glass p-8 rounded-3xl border-sky-500/20 text-center space-y-4">
                            <div className="flex justify-center">
                                <div className="w-3 h-3 rounded-full bg-sky-500 animate-ping"></div>
                            </div>
                            <h4 className="text-xl font-bold italic uppercase tracking-wider">Waiting for more players...</h4>
                            <p className="text-slate-500 text-sm">The game will start automatically when 3 players have joined.</p>
                            <div className="text-sky-400 font-mono font-bold text-2xl tracking-widest">{game?.gameCode}</div>
                        </div>
                    )}
                </div>
            </div>

            <div className="relative group">
                <div className="absolute -inset-8 bg-sky-500/10 blur-3xl rounded-full group-hover:bg-sky-500/20 transition-all duration-1000"></div>
                <div className="glass p-10 rounded-[2.5rem] relative space-y-8">
                    <div className="flex items-center justify-between">
                        <h3 className="text-2xl font-bold flex items-center gap-3 italic uppercase">
                            <Users className="text-sky-400" />
                            Game Lobby
                        </h3>
                        {game?.gameCode && (
                            <span className="bg-sky-500/20 text-sky-400 px-3 py-1 rounded-full text-xs font-black tracking-widest uppercase">
                                {game.gameCode}
                            </span>
                        )}
                    </div>

                    <div className="space-y-3 min-h-[200px]">
                        {game?.players?.length > 0 ? (
                            game.players.map((p, i) => (
                                <div key={i} className="flex justify-between items-center p-4 bg-white/5 rounded-2xl border border-white/5 card-hover">
                                    <span className="font-bold flex items-center gap-3">
                                        <div className="w-2 h-2 rounded-full bg-sky-400 shadow-[0_0_10px_rgba(56,189,248,0.5)]"></div>
                                        {p.name}
                                    </span>
                                    <span className="text-[10px] bg-sky-400/10 text-sky-400 px-2 py-0.5 rounded font-black italic">READY</span>
                                </div>
                            ))
                        ) : (
                            <div className="text-slate-500 text-center py-12 flex flex-col items-center gap-4">
                                <Users className="w-12 h-12 opacity-20" />
                                <p>Waiting for players to join...</p>
                            </div>
                        )}
                    </div>

                    {game?.players?.length > 0 && (
                        <button
                            onClick={handleStart}
                            className="w-full bg-emerald-500 hover:bg-emerald-400 text-slate-950 font-black py-4 rounded-2xl flex items-center justify-center gap-2 group/btn uppercase tracking-widest italic"
                        >
                            Start Operation
                            <ChevronRight className="w-5 h-5 group-hover/btn:translate-x-1 transition-transform" />
                        </button>
                    )}
                </div>
            </div>
        </div>
    );
}
