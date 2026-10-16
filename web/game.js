/* Browser port of the Java console game by Gordon Schartner and Samuel Rees.
   Keeps the original ASCII map, prompts, and numbered turn menu. */
(function (root) {
  'use strict';
  const COLORS = ['red', 'blue', 'green'];
  const CITIES = ['Gary', 'Toledo', 'Indianapolis', 'Dayton', 'Columbus', 'Cleveland', 'Cincinnati', 'Charleston', 'Erie', 'Pittsburgh'];
  const WORDS = ['one', 'two', 'three', 'four', 'five', 'six', 'seven', 'eight', 'nine', 'ten'];
  const TRACKS = [
    [1, 2, '&', 2], [1, 3, 'blue', 4], [2, 4, 'green', 3],
    [3, 4, 'red', 1], [4, 5, '&', 5], [3, 7, 'green', 2],
    [6, 5, 'blue', 3], [5, 8, 'green', 1], [7, 8, 'red', 6],
    [5, 9, 'red', 4], [9, 10, '&', 1], [5, 10, 'green', 1], [8, 10, 'blue', 2]
  ];
  const ROUTES = [[1, 7, 9], [1, 4, 7], [6, 7, 17], [3, 8, 12], [2, 9, 20], [3, 10, 12], [8, 6, 4], [2, 5, 14]];
  const POINTS = [0, 1, 2, 4, 7, 10, 15];
  const MENU = '\nwould you like to:\n1 - Pick up 2 random cards\n2 - Place trains\n3 - Check your points, trains, and routs \n4 - Take a rout card\n5 - Pick up 1 card from the card row';
  function shuffle(a) {
    for (let i = a.length - 1; i > 0; i--) {
      const j = Math.floor(Math.random() * (i + 1));
      [a[i], a[j]] = [a[j], a[i]];
    }
    return a;
  }
  function newDeck() { return shuffle(COLORS.flatMap(c => Array(30).fill(c))); }
  function location(value) {
    const v = value.trim().toLowerCase();
    if (/^(?:[1-9]|10)$/.test(v)) return Number(v);
    const index = CITIES.findIndex(c => c.toLowerCase() === v);
    return index >= 0 ? index + 1 : WORDS.indexOf(v) + 1;
  }
  class TerminalGame {
    constructor(saved) {
      if (saved && saved.version === 2 && typeof saved.transcript === 'string' &&
          ['players', 'menu', 'from', 'to', 'market', 'flex', 'restart', 'over'].includes(saved.phase)) {
        this.state = saved;
      } else this.reset();
    }
    reset() {
      this.state = { version: 2, phase: 'players', players: [], turn: 0, claims: Array(13).fill(null), deck: [], routes: [], market: [], transcript: '', from: null, track: null, payment: [] };
      this.print("Welcome rail tycoon to Ticket To Ride!\nThe Ohio valley is fit to be the industrial heartland of the country, it's up to you to bring the steam and steel to this land\n\nHow many capitalists will be playing? 1-3 players allowed");
    }
    print(s = '') {
      this.state.transcript += String(s) + '\n';
      if (this.state.transcript.length > 100000) {
        const cut = this.state.transcript.indexOf('\n', this.state.transcript.length - 80000);
        this.state.transcript = this.state.transcript.slice(cut + 1);
      }
    }
    player() { return this.state.players[this.state.turn]; }
    hand(p = this.player()) { return COLORS.map(c => 'You have ' + p.hand[c] + ' ' + c).join(', '); }
    routs(p = this.player()) {
      return p.tickets.map(i => 'You have rout from ' + ROUTES[i][0] + ' to ' + ROUTES[i][1]).join('\n');
    }
    draw() {
      if (!this.state.deck.length) this.state.deck = newDeck();
      return this.state.deck.pop();
    }
    setup(n) {
      const s = this.state;
      s.deck = newDeck();
      s.routes = shuffle(ROUTES.map((_, i) => i));
      s.market = Array.from({ length: 5 }, () => this.draw());
      s.players = Array.from({ length: n }, () => {
        const p = { hand: { red: 0, blue: 0, green: 0 }, trains: 15, points: 0, tickets: [] };
        p.hand[this.draw()]++; p.hand[this.draw()]++;
        return p;
      });
      this.beginTurn();
    }
    board() {
      const a = TRACKS.map((t, i) => this.state.claims[i] === null ? t[3] + t[2][0] : 'P' + (this.state.claims[i] + 1));
      return '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~' +
        '\n{  (1)--' + a[0] + '--(2)          (6)      (9)} 1-Gary         10-Pittsburgh' +
        '\n{   |        |            |     _/ | } 2-Toledo       Train lines are in the form [number, letter]' +
        '\n{  ' + a[1] + '       ' + a[2] + '           ' + a[6] + '   ' + a[9] + '  ' + a[10] + ' } 3-Indianapolis b is blue, g is green, r is red' +
        '\n{   |        |            | _/     | } 4-Dayton       & means you can use any cards' +
        '\n{  (3)--' + a[3] + '--(4)----' + a[4] + '----(5)--' + a[11] + '-(10)} 5-Columbus' +
        '\n{   |                     |      _/  } 6-Cleveland' +
        '\n{  ' + a[5] + '                     ' + a[7] + '   ' + a[12] + '    } 7-Cincinnati' +
        '\n{   |                     |  _/      } 8-Charleston' +
        '\n{  (7)-------' + a[8] + '----------(8)/        } 9-Erie' +
        '\n~~~~~~~~~~~~~~~~~~~~~~~~~~~';
    }
    menu() { this.state.phase = 'menu'; this.print(MENU); }
    beginTurn() {
      const p = this.player();
      this.print('___________________________________________________________________');
      this.print(this.board());
      if (p.trains === 0) { this.end('The game has ended because ' + (this.state.turn + 1) + ' has no more trains'); return; }
      this.print('Your hand:\n' + this.hand() + '\n\nThe card row has\n' + this.state.market.join(', '));
      this.print('\nPlayer ' + (this.state.turn + 1) + 's turn\n' + this.hand());
      this.menu();
    }
    connected(a, b) {
      const seen = new Set([a]), pending = [a];
      while (pending.length) {
        const x = pending.pop();
        TRACKS.forEach((t, i) => {
          if (this.state.claims[i] !== this.state.turn) return;
          const y = t[0] === x ? t[1] : t[1] === x ? t[0] : null;
          if (y !== null && !seen.has(y)) { seen.add(y); pending.push(y); }
        });
      }
      return seen.has(b);
    }
    finishTurn() {
      const p = this.player(), s = this.state;
      p.tickets = p.tickets.filter(i => {
        const r = ROUTES[i];
        if (!this.connected(r[0], r[1])) return true;
        p.points += r[2];
        this.print('You have completed your rout form ' + r[0] + ' to ' + r[1] + ' gaining ' + r[2] + '  points');
        return false;
      });
      if (s.deck.length < 2) s.deck = newDeck();
      s.track = null; s.payment = [];
      if (!s.players.some(p => TRACKS.some((t, i) => s.claims[i] === null && p.trains >= t[3]))) {
        this.end('The game has ended because no player has enough trains for an unclaimed line.'); return;
      }
      s.turn = (s.turn + 1) % s.players.length;
      this.beginTurn();
    }
    end(reason) {
      this.print(reason);
      const high = Math.max(...this.state.players.map(p => p.points));
      const winners = this.state.players.flatMap((p, i) => p.points === high ? [i + 1] : []);
      this.state.players.forEach((p, i) => this.print('Player ' + (i + 1) + ': ' + p.points + ' points'));
      this.print(winners.length === 1 ? 'player ' + winners[0] + ' WINS!!!!' : 'Players ' + winners.join(' and ') + ' tie!!!!');
      this.print('\nType /restart to play again.');
      this.state.phase = 'over';
    }
    chooseTrack(end) {
      const s = this.state, p = this.player();
      const index = TRACKS.findIndex(t => (t[0] === s.from && t[1] === end) || (t[1] === s.from && t[0] === end));
      if (index < 0) { this.print('There is no direct line between those cities.'); this.menu(); return; }
      const t = TRACKS[index];
      if (s.claims[index] !== null) { this.print('Line already built'); this.menu(); return; }
      if (p.trains < t[3]) { this.print("You don't have enough trains for that"); this.menu(); return; }
      if (t[2] === '&') {
        if (COLORS.reduce((n, c) => n + p.hand[c], 0) < t[3]) { this.print("You don't have the required cards"); this.menu(); return; }
        s.track = index; s.payment = []; s.phase = 'flex';
        this.print('choose what colors you will use for the & track you need to choose ' + t[3] + ' cards');
        this.flexPrompt(); return;
      }
      if (p.hand[t[2]] < t[3]) { this.print("You don't have the required cards"); this.menu(); return; }
      p.hand[t[2]] -= t[3];
      this.claim(index);
    }
    flexPrompt() {
      const s = this.state, p = this.player();
      this.print('what card do you want to use: [' + (s.payment.length + 1) + '-' + TRACKS[s.track][3] + ']');
      this.print(COLORS.map(c => 'You have ' + (p.hand[c] - s.payment.filter(v => v === c).length) + ' ' + c).join(', '));
    }
    claim(i) {
      const p = this.player(), t = TRACKS[i];
      p.trains -= t[3]; p.points += POINTS[t[3]]; this.state.claims[i] = this.state.turn;
      this.print("That's " + POINTS[t[3]] + ' points');
      this.finishTurn();
    }
    submit(input) {
      const value = String(input).trim(), s = this.state;
      this.print('> ' + value);
      if (value.toLowerCase() === '/restart') {
        if (s.phase !== 'restart') s.previousPhase = s.phase;
        s.phase = 'restart'; this.print('Start a new game and erase this game? Type yes or no.'); return;
      }
      if (s.phase === 'restart') {
        if (/^(yes|y)$/i.test(value)) this.reset();
        else if (/^(no|n)$/i.test(value)) { s.phase = s.previousPhase; delete s.previousPhase; this.print('Continuing your game. Enter your answer to the previous prompt.'); }
        else this.print('Type yes or no.');
        return;
      }
      if (s.phase === 'players') {
        if (!/^[1-3]$/.test(value)) { this.print('Not an option\nHow many capitalists will be playing? 1-3 players allowed'); return; }
        this.setup(Number(value)); return;
      }
      if (s.phase === 'over') { this.print('Type /restart to play again.'); return; }
      if (s.phase === 'from' || s.phase === 'to') {
        const place = location(value);
        if (!place) { this.print('Enter a city number (1-10) or a city name shown on the board.'); return; }
        if (s.phase === 'from') { s.from = place; s.phase = 'to'; this.print('Where would you like your train to end?'); }
        else this.chooseTrack(place);
        return;
      }
      if (s.phase === 'market') {
        const c = value.toLowerCase(), i = s.market.indexOf(c);
        if (i < 0) { this.print('There is no card with that color, what would you like to do'); this.menu(); return; }
        this.player().hand[c]++; s.market.splice(i, 1); s.market.push(this.draw());
        this.finishTurn(); return;
      }
      if (s.phase === 'flex') {
        const c = value.toLowerCase();
        if (!COLORS.includes(c)) { this.print('that is not a color try again'); return; }
        if (this.player().hand[c] <= s.payment.filter(v => v === c).length) { this.print("You don't have that color card"); return; }
        s.payment.push(c);
        if (s.payment.length === TRACKS[s.track][3]) {
          s.payment.forEach(c => this.player().hand[c]--); this.claim(s.track);
        } else this.flexPrompt();
        return;
      }
      const p = this.player();
      switch (value) {
        case '1': p.hand[this.draw()]++; p.hand[this.draw()]++; this.print(this.hand()); this.finishTurn(); break;
        case '2': s.phase = 'from'; this.print('Where would you like your train to start?'); break;
        case '3': this.print(p.points + ' points\n' + p.trains + ' trains remaining'); if (p.tickets.length) this.print(this.routs()); this.print('What else?'); break;
        case '4':
          if (p.tickets.length === 3) { this.print('You can only have 3 rout cards at a time'); this.menu(); }
          else if (!s.routes.length) { this.print('There are no more rout cards'); this.menu(); }
          else { p.tickets.push(s.routes.pop()); this.print('Your new hand ' + this.routs()); this.finishTurn(); }
          break;
        case '5': s.phase = 'market'; this.print('What color card do you want'); break;
        default: this.print('Not an option');
      }
    }
  }
  if (typeof module !== 'undefined' && module.exports) module.exports = { TerminalGame, TRACKS, ROUTES };
  else root.TerminalGame = TerminalGame;
})(typeof globalThis === 'undefined' ? this : globalThis);
