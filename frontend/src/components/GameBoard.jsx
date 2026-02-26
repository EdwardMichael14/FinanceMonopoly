import React, { useState } from 'react';
import { api } from '../utils/api';
import { TrendingUp, Home, Dice5, History, Award } from 'lucide-react';

export default function GameBoard({ game, player, leaderboard, history, refresh }) {
    const [loanPayment, setLoanPayment] = useState(0);
    const [diceResult, setDiceResult] = useState(null);
    const [loading, setLoading] = useState(false);

    const handlePickHousing = async (type) => {
        setLoading(true);
        await api.pickHousing(player.id, type);
        await refresh();
        setLoading(false);
    };

    const handleRoll = async () => {
        setLoading(true);
        const res = await api.playRound(player.id, loanPayment);
        if (res.successful) {
            setDiceResult(res.data);
            await refresh();
            setTimeout(() => setDiceResult(null), 8000);
        } else {
            alert(res.message);
        }
        setLoading(false);
    };

    const hasPlayed = history.some(h => h.roundNumber === game.currentRound);

    return (
        <div className="grid lg:grid-cols-3 gap-8">
            <div className="lg:col-span-2 space-y-8">
                {/* Main Controls */}
                <div className="glass p-10 rounded-[2.5rem] relative overflow-hidden">
                    <div className="flex justify-between items-center mb-10">
                        <div>
                            <h3 className="text-sm font-black text-sky-400 uppercase tracking-[0.2em] mb-1 italic">Active Round</h3>
                            <div className="text-5xl font-black italic uppercase tracking-tighter">Round {game.currentRound}<span className="text-sky-500">.</span></div>
                        </div>
                        <div className="text-right">
                            <div className="text-[10px] font-black text-slate-500 uppercase tracking-widest mb-1 italic">Profile</div>
                            <div className="px-4 py-1.5 rounded-xl bg-white/5 border border-white/10 text-xs font-black tracking-widest uppercase italic">
                                Software Engineer
                            </div>
                        </div>
                    </div>

                    {/* Step 1: Case Study */}
                    <div className="grid sm:grid-cols-3 gap-4 mb-10 p-6 rounded-3xl bg-white/5 border border-white/5">
                        <div className="space-y-1">
                            <div className="text-[10px] text-slate-500 font-black uppercase tracking-widest italic">Salary (6m)</div>
                            <div className="text-xl font-black text-emerald-400">₦2,400,000</div>
                        </div>
                        <div className="space-y-1">
                            <div className="text-[10px] text-slate-500 font-black uppercase tracking-widest italic">Survival Cost</div>
                            <div className="text-xl font-black text-rose-400">-₦700,000</div>
                        </div>
                        <div className="space-y-1">
                            <div className="text-[10px] text-slate-500 font-black uppercase tracking-widest italic">Loan Interest</div>
                            <div className="text-xl font-black text-amber-500">10% Final</div>
                        </div>
                    </div>

                    {!player.housingType ? (
                        <div className="space-y-8 animate-in slide-in-from-bottom-8 duration-500">
                            <h4 className="text-xl font-bold flex items-center gap-3 italic uppercase text-sky-400">
                                <Home className="w-5 h-5" />
                                Step 2: Select Housing Strategy
                            </h4>
                            <div className="grid sm:grid-cols-2 gap-4">
                                {[
                                    { id: 'PARENT_HOUSE', name: 'Parent\'s House', base: 150000, interest: 0.0, infl: 0.02, desc: 'Subject to inflation' },
                                    { id: 'SHARED_APARTMENT', name: 'Shared Apartment', base: 300000, interest: 0.20, infl: 0, desc: '20% Fixed interest' },
                                    { id: 'SINGLE_APARTMENT', name: 'Single Apartment', base: 900000, interest: 0.15, infl: 0, desc: '15% Fixed interest' },
                                    { id: 'LUXURY_APARTMENT', name: 'Luxury Apartment', base: 1500000, interest: 0.05, infl: 0, desc: '5% Fixed interest' }
                                ].map(h => {
                                    const roundsPassed = game.currentRound - 1;
                                    let currentCost;

                                    if (h.infl > 0) {
                                        let totalMultiplier = 1.0;
                                        // Use history for previous rounds
                                        history.forEach(h_record => {
                                            totalMultiplier *= (1 + (h.infl * h_record.diceRoll));
                                        });
                                        currentCost = Math.round(h.base * totalMultiplier);
                                    } else {
                                        currentCost = Math.round(h.base * Math.pow(1 + h.interest, roundsPassed));
                                    }

                                    return (
                                        <button
                                            key={h.id}
                                            onClick={() => handlePickHousing(h.id)}
                                            className="p-6 rounded-3xl bg-white/5 border border-white/5 text-left card-hover group relative overflow-hidden"
                                        >
                                            <div className="relative z-10">
                                                <div className="flex justify-between items-start mb-2">
                                                    <div className="font-black text-lg group-hover:text-sky-400 transition-colors uppercase italic tracking-tight">{h.name}</div>
                                                    <div className="text-amber-400 font-mono font-bold text-sm">{api.formatNaira(currentCost)}</div>
                                                </div>
                                                <div className="text-xs text-slate-500 font-medium uppercase tracking-tight">{h.desc}</div>
                                            </div>
                                        </button>
                                    );
                                })}
                            </div>
                        </div>
                    ) : (
                        <div className="space-y-10">
                            <div className="space-y-6">
                                <div className="flex items-center gap-3">
                                    <TrendingUp className="text-emerald-400 w-5 h-5" />
                                    <h4 className="text-xl font-bold italic uppercase">Step 3: Loan Repayment</h4>
                                </div>
                                <div className="flex gap-4">
                                    <div className="flex-1 relative">
                                        <input
                                            type="number"
                                            value={loanPayment}
                                            onChange={(e) => setLoanPayment(e.target.value)}
                                            className="w-full bg-slate-950/50 border border-white/10 rounded-[1.25rem] px-6 py-4 outline-none focus:ring-2 focus:ring-emerald-500 font-mono"
                                            placeholder="Adjustment amount..."
                                        />
                                        <span className="absolute right-4 top-1/2 -translate-y-1/2 text-[10px] font-black text-slate-600 uppercase tracking-widest">Naira</span>
                                    </div>
                                    <button
                                        onClick={handleRoll}
                                        disabled={hasPlayed || loading}
                                        className="btn-primary flex items-center gap-3 px-10 italic uppercase tracking-widest bg-indigo-600 hover:bg-indigo-500"
                                    >
                                        <Dice5 className="w-6 h-6" />
                                        {hasPlayed ? 'Step Complete' : 'Step 4: Roll Dice'}
                                    </button>
                                </div>
                                <p className="text-[10px] text-slate-500 uppercase font-black tracking-widest italic flex items-center gap-2">
                                    <div className="w-1.5 h-1.5 rounded-full bg-amber-500 animate-pulse"></div>
                                    Step 5: 10% interest applied to final balance after payment.
                                </p>
                            </div>

                            {diceResult && (
                                <div className="space-y-4 animate-in zoom-in duration-500">
                                    {diceResult.inflationDiceRoll && (
                                        <div className="p-6 rounded-[2rem] bg-amber-500/10 border border-amber-500/20 flex gap-6 items-center">
                                            <div className="text-5xl font-black text-amber-400 italic">
                                                {diceResult.inflationDiceRoll}
                                            </div>
                                            <div className="space-y-1">
                                                <div className="text-lg font-black italic uppercase tracking-tight text-white">Inflation Roll</div>
                                                <p className="text-amber-200/60 text-sm leading-relaxed font-medium">This roll determines the cost increase for Parent Housing.</p>
                                            </div>
                                        </div>
                                    )}
                                    <div className="p-8 rounded-[2rem] bg-indigo-500/10 border border-indigo-500/20 flex gap-8 items-center">
                                        <div className="text-7xl font-black text-indigo-400 drop-shadow-[0_0_15px_rgba(129,140,248,0.5)] italic">
                                            {diceResult.diceRoll}
                                        </div>
                                        <div className="space-y-1">
                                            <div className="text-2xl font-black italic uppercase tracking-tight text-white">{diceResult.diceOutcome?.title || 'Event'}</div>
                                            <p className="text-indigo-200/60 leading-relaxed font-medium">{diceResult.diceOutcome?.description || 'Reviewing outcome...'}</p>
                                        </div>
                                    </div>
                                </div>
                            )}
                        </div>
                    )}
                </div>

                {/* History Area */}
                <div className="glass p-10 rounded-[2.5rem]">
                    <h3 className="text-2xl font-black mb-8 flex items-center gap-3 italic uppercase">
                        <History className="text-indigo-400" />
                        Activity Log
                    </h3>
                    <div className="space-y-4">
                        {history.slice().reverse().map((h, i) => (
                            <div key={i} className="p-6 rounded-3xl bg-white/5 border border-white/5 flex flex-wrap justify-between items-center gap-4">
                                <div className="space-y-1">
                                    <div className="text-xs font-black text-indigo-400 uppercase tracking-widest italic">Round {h.roundNumber}</div>
                                    <div className="font-bold text-slate-300">{h.diceOutcome?.title || 'Initial Setup'}</div>
                                </div>
                                <div className="flex gap-8 text-xs font-mono">
                                    <div className="flex flex-col">
                                        <span className="text-slate-600 uppercase text-[10px] font-black tracking-widest">Salary</span>
                                        <span className="text-emerald-400">+{api.formatNaira(h.salaryReceived)}</span>
                                    </div>
                                    <div className="flex flex-col">
                                        <span className="text-slate-600 uppercase text-[10px] font-black tracking-widest">Housing</span>
                                        <span className="text-rose-400">-{api.formatNaira(h.housingCost)}</span>
                                    </div>
                                    <div className="flex flex-col">
                                        <span className="text-slate-600 uppercase text-[10px] font-black tracking-widest">Loan Pay</span>
                                        <span className="text-sky-400">-{api.formatNaira(h.loanPayment)}</span>
                                    </div>
                                    <div className="flex flex-col">
                                        <span className="text-slate-600 uppercase text-[10px] font-black tracking-widest">Events</span>
                                        <span className={h.diceEventAmount >= 0 ? 'text-emerald-400' : 'text-rose-400'}>
                                            {h.diceEventAmount >= 0 ? '+' : ''}{api.formatNaira(h.diceEventAmount)}
                                        </span>
                                    </div>
                                </div>
                            </div>
                        ))}
                    </div>
                </div>
            </div>

            <div className="space-y-8">
                {/* Stats Card */}
                <div className="glass p-8 rounded-[2.5rem] bg-gradient-to-br from-slate-900 to-slate-950 border-sky-500/20 shadow-2xl">
                    <h4 className="text-xs font-black text-slate-500 uppercase tracking-[0.2em] mb-1 italic">Real-Time Assets</h4>
                    <div className="text-5xl font-black italic uppercase tracking-tighter mb-8 break-all">
                        {api.formatNaira(player.cashBalance - player.loanBalance)}
                    </div>

                    <div className="space-y-4">
                        <div className="flex justify-between items-center p-4 bg-slate-950/50 rounded-2xl border border-white/5">
                            <span className="text-xs font-bold text-slate-500 uppercase italic">Liquid Cash</span>
                            <span className="text-emerald-400 font-mono font-bold tracking-tight">{api.formatNaira(player.cashBalance)}</span>
                        </div>
                        <div className="flex justify-between items-center p-4 bg-slate-950/50 rounded-2xl border border-white/5">
                            <span className="text-xs font-bold text-slate-500 uppercase italic">Outstanding Debt</span>
                            <span className="text-rose-500 font-mono font-bold tracking-tight">{api.formatNaira(player.loanBalance)}</span>
                        </div>
                    </div>
                </div>

                {/* Leaderboard Card */}
                <div className="glass p-8 rounded-[2.5rem] h-fit">
                    <h3 className="text-xl font-black mb-8 flex items-center gap-3 italic uppercase">
                        <Award className="text-amber-400" />
                        Standings
                    </h3>
                    <div className="space-y-4">
                        {leaderboard.map((item, i) => (
                            <div key={i} className={`flex items-center gap-4 p-4 rounded-2xl border ${i === 0 ? 'bg-amber-400/10 border-amber-400/20' : 'bg-white/5 border-white/5'}`}>
                                <div className={`w-8 h-8 rounded-full flex items-center justify-center font-black text-xs ${i === 0 ? 'bg-amber-400 text-slate-950' : 'bg-slate-800 text-slate-500'}`}>
                                    {i + 1}
                                </div>
                                <div className="flex-1">
                                    <div className="font-bold text-sm tracking-tight capitalize">{item.player.name}</div>
                                    <div className="text-[10px] text-slate-500 font-black uppercase tracking-widest">{api.formatNaira(item.netWorth)}</div>
                                </div>
                            </div>
                        ))}
                    </div>
                </div>
            </div>
        </div>
    );
}
