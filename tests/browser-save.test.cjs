const fs = require('node:fs');
const vm = require('node:vm');
const assert = require('node:assert/strict');
const { TerminalGame } = require('../web/game.js');
const client = fs.readFileSync(__dirname + '/../web/terminal.js', 'utf8');
const key = 'ticket-to-ride-terminal-v3', legacy = 'ticket-to-ride-terminal-v2';
const tick = () => new Promise(resolve => setImmediate(resolve));
function environment(options = {}) {
  const data = new Map(); const queues = new Map();
  const storage = {
    getItem(k) { if (options.storageFails) throw Error('Storage denied'); return data.get(k) ?? null; },
    setItem(k,v) { if (options.storageFails) throw Error('Storage denied'); data.set(k,v); }
  };
  const locks = { request(k, fn) {
    if (options.lockFails) return Promise.reject(Error('Lock unavailable'));
    const pending = (queues.get(k) || Promise.resolve()).then(fn);
    queues.set(k, pending.catch(() => {})); return pending;
  }};
  async function tab() {
    const elements = {}, events = {};
    for (const id of ['output','output-scroll','answer','input-form','save-hint']) elements[id] = {
      textContent: '', value: '', scrollTop: 0, scrollHeight: 10, handlers: {},
      append(node) { this.textContent += node.textContent; },
      addEventListener(event, callback) { this.handlers[event] = callback; }, focus() {}
    };
    const context = vm.createContext({TerminalGame, localStorage: storage,
      navigator: options.noLocks ? {} : {locks}, addEventListener: (event, callback) => events[event] = callback,
      document: {getElementById: id => elements[id], createTextNode: value => ({textContent: value})}
    });
    vm.runInContext(client, context); await tick();
    return {elements, async enter(value) {
      elements.answer.value = value;
      await elements['input-form'].handlers.submit({preventDefault(){}});
    }, async refresh() { await events.storage({key}); }};
  }
  return {data, tab, snapshot: () => JSON.parse(data.get(key))};
}
const count = state => Object.values(state.players[0].hand).reduce((x,y) => x+y,0);
(async function () {
  let env = environment(), a = await env.tab(); await a.enter('2'); let b = await env.tab();
  await a.enter('1'); await b.enter('3');
  assert.equal(env.snapshot().turn, 1); assert.equal(count(env.snapshot()), 4);
  assert.match(b.elements.output.textContent, /Your answer was not applied/);
  await b.enter('3'); assert.equal(env.snapshot().turn, 1); assert.equal(count(env.snapshot()), 4);

  env = environment(); a = await env.tab(); await a.enter('2'); b = await env.tab();
  await Promise.all([a.enter('1'), b.enter('1')]);
  assert.equal(env.snapshot().turn, 1); assert.equal(count(env.snapshot()), 4);
  assert.equal(Object.values(env.snapshot().players[1].hand).reduce((x,y)=>x+y), 2);

  env = environment(); a = await env.tab(); await a.enter('2'); b = await env.tab();
  b.elements.answer.value = '2'; await a.enter('1'); await b.refresh();
  assert.equal(b.elements.answer.value, ''); assert.match(b.elements.output.textContent, /Game updated in another tab/);
  await b.enter('1'); assert.equal(env.snapshot().turn, 0);
  assert.equal(Object.values(env.snapshot().players[1].hand).reduce((x,y)=>x+y), 4);

  env = environment(); const oldGame = new TerminalGame(); oldGame.submit('2');
  env.data.set(legacy, JSON.stringify(oldGame.state));
  a = await env.tab(); assert.equal(count(env.snapshot()), 2);
  await a.enter('1'); const migrated = env.data.get(key);
  env.data.set(legacy, JSON.stringify(oldGame.state)); // An old page still saving v2.
  b = await env.tab(); assert.equal(env.data.get(key), migrated);
  assert.equal(count(env.snapshot()), 4);

  for (const options of [{noLocks:true}, {lockFails:true}, {storageFails:true}]) {
    env = environment(options); a = await env.tab();
    await a.enter('1'); await a.enter('1');
    assert.match(a.elements.output.textContent, /would you like to/);
    assert.match(a.elements['save-hint'].textContent, /Saving is unavailable/);
    assert.equal(env.data.has(key), false);
  }
  env = environment({noLocks:true}); env.data.set(key, migrated);
  a = await env.tab(); await a.enter('1'); assert.equal(env.data.get(key), migrated);

  env = environment(); a = await env.tab(); await a.enter('2'); b = await env.tab();
  await a.enter('/restart'); await b.enter('yes');
  assert.equal(env.snapshot().phase, 'restart'); // Stale input wasn't reinterpreted as confirmation.
  await b.enter('yes'); assert.equal(env.snapshot().phase, 'players');
  await a.refresh(); assert.match(a.elements.output.textContent, /1-3 players allowed/);
  console.log('PASS: browser saves — stale tabs, simultaneous input, live updates, migration, old pages, unavailable storage/locks, restart');
})().catch(error => { console.error(error); process.exitCode = 1; });
