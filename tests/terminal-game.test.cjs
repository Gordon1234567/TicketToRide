const assert = require('node:assert/strict');
const { TerminalGame, TRACKS } = require('../web/game.js');
const game = () => { const g = new TerminalGame(); g.submit('1'); return g; };
const input = (g, ...lines) => lines.forEach(line => g.submit(line));
const checks = [];
function test(name, fn) { fn(); checks.push(name); }
test('original welcome, invalid player count, three-player setup, numbered menu', () => {
  const g = new TerminalGame();
  assert.match(g.state.transcript, /How many capitalists will be playing\? 1-3 players allowed/);
  input(g, 'four', '0', '4'); assert.equal(g.state.phase, 'players');
  g.submit('3'); assert.equal(g.state.players.length, 3); assert.equal(g.state.phase, 'menu');
  assert.equal(g.state.market.length, 5); assert.equal(g.state.deck.length, 79);
  g.state.players.forEach(p => assert.equal(Object.values(p.hand).reduce((a,b)=>a+b), 2));
  assert.match(g.state.transcript, /5 - Pick up 1 card from the card row/);
});
test('original ASCII map and score lookup without ending the turn', () => {
  const g = game(); const before = g.state.turn;
  assert.match(g.board(), /\(1\)--2&--\(2\)/);
  assert.match(g.board(), /\(3\)--1r--\(4\)----5&----\(5\)--1g-\(10\)/);
  g.submit('3'); assert.equal(g.state.turn, before); assert.match(g.state.transcript, /15 trains remaining\nWhat else\?/);
});
test('random draw advances the turn; face-up color prompt works', () => {
  const g = new TerminalGame(); g.submit('2');
  g.submit('1'); assert.equal(g.state.turn, 1);
  assert.equal(Object.values(g.state.players[0].hand).reduce((a,b)=>a+b), 4);
  const color = g.state.market[0], previous = g.player().hand[color];
  input(g, '5', color); assert.equal(g.state.turn, 0); assert.equal(g.state.players[1].hand[color], previous + 1);
  assert.equal(g.state.market.length, 5);
  input(g, '5', 'purple'); assert.equal(g.state.phase, 'menu'); assert.equal(g.state.turn, 0);
});
test('city prompts, invalid cities, nonexistent track, costs, duplicate claim', () => {
  const g = game(); g.player().hand = { red: 1, blue: 0, green: 0 };
  input(g, '2', 'nowhere'); assert.equal(g.state.phase, 'from');
  input(g, 'Indianapolis', 'Dayton');
  assert.equal(g.state.claims[3], 0); assert.equal(g.player().trains, 14); assert.equal(g.player().hand.red, 0); assert.equal(g.player().points, 1);
  assert.match(g.board(), /\(3\)--P1--\(4\)/);
  input(g, '2', '3', '4'); assert.match(g.state.transcript, /Line already built/); assert.equal(g.player().trains, 14);
  input(g, '2', 'one', 'ten'); assert.match(g.state.transcript, /There is no direct line/);
  input(g, '2', '1', '3'); assert.match(g.state.transcript, /You don't have the required cards/);
});
test('flexible track prompts each color, preserves pending input across reload', () => {
  const g = game(); g.player().hand = { red: 1, blue: 1, green: 0 };
  input(g, '2', 'Gary', 'Toledo', 'purple', 'red');
  assert.equal(g.state.phase, 'flex'); assert.equal(g.state.payment.length, 1);
  const resumed = new TerminalGame(JSON.parse(JSON.stringify(g.state)));
  resumed.submit('red'); assert.equal(resumed.state.phase, 'flex');
  resumed.submit('blue'); assert.equal(resumed.state.phase, 'menu');
  assert.equal(resumed.state.claims[0], 0); assert.equal(resumed.player().trains, 13); assert.equal(resumed.player().points, 2);
  assert.deepEqual(resumed.player().hand, { red: 0, blue: 0, green: 0 });
});
test('destination limit, completion through connected tracks, scoring once', () => {
  const g = game(); input(g, '4', '4', '4', '4'); assert.equal(g.player().tickets.length, 3);
  assert.match(g.state.transcript, /You can only have 3 rout cards/);
  g.player().tickets = [1];
  g.player().hand = { red: 1, blue: 4, green: 0 };
  input(g, '2', '1', '3', '2', '3', '4');
  assert.equal(g.player().points, 15); assert.equal(g.player().tickets.length, 0);
  g.submit('1'); assert.equal(g.player().points, 15);
});
test('zero trains produces the winner and blocks further play', () => {
  const g = game(); g.player().trains = 1; g.player().hand.red = 1;
  input(g, '2', '3', '4'); assert.equal(g.state.phase, 'over');
  assert.match(g.state.transcript, /player 1 WINS!!!!/);
  const before = g.player().points; g.submit('1'); assert.equal(g.player().points, before);
});
test('no remaining usable track ends the game; tied players share the win', () => {
  const g = new TerminalGame(); g.submit('2'); g.state.claims.fill(0);
  g.submit('1'); assert.equal(g.state.phase, 'over'); assert.match(g.state.transcript, /Players 1 and 2 tie/);
});
test('restart confirmation preserves or resets current prompt', () => {
  const g = game(); input(g, '2', '/restart', 'no'); assert.equal(g.state.phase, 'from');
  input(g, '/restart', 'yes'); assert.equal(g.state.phase, 'players'); assert.equal(g.state.players.length, 0);
});
test('full playthrough using terminal input only reaches a winner', () => {
  const g = game(); let steps = 0;
  while (g.state.phase !== 'over' && steps++ < 1000) {
    const p = g.player(), index = TRACKS.findIndex((t,i) => g.state.claims[i] === null && p.trains >= t[3] &&
      (t[2] === '&' ? Object.values(p.hand).reduce((a,b)=>a+b) >= t[3] : p.hand[t[2]] >= t[3]));
    if (index < 0) { g.submit('1'); continue; }
    const t = TRACKS[index]; input(g, '2', String(t[0]), String(t[1]));
    while (g.state.phase === 'flex') g.submit(['red','blue','green'].find(c => p.hand[c] > g.state.payment.filter(x => x === c).length));
  }
  assert.equal(g.state.phase, 'over'); assert.ok(steps < 1000);
});
console.log('PASS: ' + checks.length + ' terminal game scenarios\n' + checks.join('\n'));
