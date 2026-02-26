const API_BASE = '/api';

export const api = {
    getGame: async (code) => {
        try {
            const res = await fetch(`${API_BASE}/v1/monopoly/games/${code}`);
            const json = await res.json();
            return json.successful ? json.data : null;
        } catch (e) { return null; }
    },

    createGame: async () => {
        try {
            const res = await fetch(`${API_BASE}/v1/monopoly/games`, { method: 'POST' });
            const json = await res.json();
            return json.data;
        } catch (e) { return null; }
    },

    joinGame: async (code, name) => {
        try {
            const res = await fetch(`${API_BASE}/v1/monopoly/games/${code}/join?playerName=${encodeURIComponent(name)}`, { method: 'POST' });
            const json = await res.json();
            return json;
        } catch (e) { return { successful: false, message: 'Server error' }; }
    },

    startGame: async (code) => {
        try {
            const res = await fetch(`${API_BASE}/v1/monopoly/games/${code}/start`, { method: 'POST' });
            const json = await res.json();
            return json.successful;
        } catch (e) { return false; }
    },

    pickHousing: async (playerId, type) => {
        try {
            const res = await fetch(`${API_BASE}/v1/monopoly/games/players/${playerId}/housing?housingType=${type}`, { method: 'POST' });
            const json = await res.json();
            return json.successful ? json.data : null;
        } catch (e) { return null; }
    },

    playRound: async (playerId, loanPayment) => {
        try {
            const res = await fetch(`${API_BASE}/v1/monopoly/rounds/players/${playerId}/play?loanPayment=${loanPayment}`, { method: 'POST' });
            return await res.json();
        } catch (e) { return { successful: false, message: 'Server error' }; }
    },

    getPlayerHistory: async (playerId) => {
        try {
            const res = await fetch(`${API_BASE}/v1/monopoly/rounds/players/${playerId}/history`);
            const json = await res.json();
            return json.successful ? json.data : null;
        } catch (e) { return null; }
    },

    getLeaderboard: async (code, round) => {
        try {
            const res = await fetch(`${API_BASE}/v1/monopoly/games/${code}/leaderboard/${round}`);
            const json = await res.json();
            return json.successful ? json.data : null;
        } catch (e) { return null; }
    },

    formatNaira: (amount) => {
        return '₦' + new Intl.NumberFormat('en-NG').format(amount || 0);
    }
};
