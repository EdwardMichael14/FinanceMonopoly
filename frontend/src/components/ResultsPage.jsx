import React from 'react';
import { Trophy, RefreshCw, Home } from 'lucide-react';
import { api } from '../utils/api';

export default function ResultsPage({ game, player, leaderboard, refresh }) {
    if (!game || game.status !== 'ENDED') {
        return (
            <div className="flex flex-col items-center justify-center min-h-[60vh] space-y-6">
                <div className="w-16 h-16 border-4 border-sky-500/30 border-t-sky-500 rounded-full animate-spin"></div>
                <p className="text-slate-400 font-bold uppercase tracking-widest italic">Waiting for final results...</p>
                <button
                    onClick={() => refresh()}
                    className="btn-primary px-8 py-2 text-sm"
                >
                    Refresh Now
                </button>
            </div>
        );
    }

    const isWinner = leaderboard[0]?.player?.id === player?.id;

    return (
        <div className="max-w-4xl mx-auto space-y-12 animate-in fade-in zoom-in duration-700">
            <div className="text-center space-y-4">
                <div className="inline-block p-6 rounded-[2.5rem] bg-emerald-500/10 border border-emerald-500/20 mb-4">
                    <Trophy className={`w-20 h-20 ${isWinner ? 'text-amber-400 animate-bounce' : 'text-slate-400'}`} />
                </div>
                <h2 className="text-6xl font-black italic uppercase tracking-tighter gradient-text">
                    Game Over
                </h2>
                <p className="text-slate-400 text-xl font-medium">
                    {isWinner ? "Incredible! You've mastered the financial markets." : "A tough market, but a great learning experience."}
                </p>
            </div>

            <div className="glass p-10 rounded-[3rem] border-white/10 relative overflow-hidden">
                <div className="absolute top-0 right-0 p-8 opacity-5">
                    <Trophy className="w-64 h-64 text-white" />
                </div>

                <h3 className="text-2xl font-black mb-8 italic uppercase flex items-center gap-3">
                    <span className="w-8 h-8 rounded-lg bg-sky-500 flex items-center justify-center text-slate-950 not-italic">#</span>
                    Final Standings
                </h3>

                <div className="space-y-4 relative z-10">
                    {leaderboard.map((item, i) => (
                        <div
                            key={i}
                            className={`flex items-center gap-6 p-6 rounded-[2rem] border transition-all duration-300 ${i === 0
                                ? 'bg-amber-400/10 border-amber-400/30 scale-105 shadow-[0_0_30px_rgba(251,191,36,0.1)]'
                                : 'bg-white/5 border-white/5 hover:bg-white/10'
                                }`}
                        >
                            <div className={`w-12 h-12 rounded-2xl flex items-center justify-center font-black text-xl ${i === 0 ? 'bg-amber-400 text-slate-950' : 'bg-slate-800 text-slate-400'
                                }`}>
                                {i + 1}
                            </div>
                            <div className="flex-1">
                                <div className="font-black text-xl tracking-tight uppercase italic">{item.player.name}</div>
                                <div className="text-sm text-slate-500 font-bold uppercase tracking-widest italic">
                                    {i === 0 ? 'Market Leader' : 'Participant'}
                                </div>
                            </div>
                            <div className="text-right">
                                <div className="text-2xl font-black font-mono text-emerald-400">
                                    {api.formatNaira(item.netWorth)}
                                </div>
                                <div className="text-[10px] text-slate-600 font-black uppercase tracking-widest">Final Net Worth</div>
                            </div>
                        </div>
                    ))}
                </div>
            </div>

            <div className="flex gap-4">
                <button
                    onClick={() => window.location.href = '/'}
                    className="flex-1 p-6 rounded-[2rem] bg-white text-slate-950 font-black italic uppercase tracking-widest hover:bg-sky-400 transition-all flex items-center justify-center gap-3 group"
                >
                    <RefreshCw className="w-6 h-6 group-hover:rotate-180 transition-transform duration-500" />
                    New Game
                </button>
            </div>
        </div>
    );
}
