(function () {
  'use strict';
  // Older open versions may still write v2. Migrate once to a separate save key.
  const key = 'ticket-to-ride-terminal-v3';
  const legacyKey = 'ticket-to-ride-terminal-v2';
  const output = document.getElementById('output');
  const scroll = document.getElementById('output-scroll');
  const answer = document.getElementById('answer');
  const form = document.getElementById('input-form');
  const hint = document.getElementById('save-hint');
  const history = [];
  let historyIndex = 0, draft = '', game, lastSaved = null, busy = true;
  let saving = Boolean(globalThis.navigator && navigator.locks);
  answer.disabled = true;
  function read(k) {
    try { return localStorage.getItem(k); }
    catch (_) { saving = false; return null; }
  }
  function restore(raw) {
    let saved;
    try { saved = JSON.parse(raw); } catch (_) { /* Start fresh if the save is unreadable. */ }
    game = new TerminalGame(saved);
    lastSaved = raw;
  }
  function persist() {
    if (!saving) return;
    const raw = JSON.stringify(game.state);
    try { localStorage.setItem(key, raw); lastSaved = raw; }
    catch (_) { saving = false; }
  }
  async function exclusive(action) {
    if (!saving) { action(); return; }
    let ran = false;
    try {
      await navigator.locks.request(key, function () { ran = true; action(); });
    } catch (error) {
      if (ran) throw error;
      // Play without saving rather than risk overwriting another tab's progress.
      saving = false;
      action();
    }
  }
  function render(message) {
    // Use text only: player input is never interpreted as HTML.
    const next = game.state.transcript;
    if (next.startsWith(output.textContent)) output.append(document.createTextNode(next.slice(output.textContent.length)));
    else output.textContent = next;
    if (message) output.append(document.createTextNode('\n[' + message + ']\n'));
    scroll.scrollTop = scroll.scrollHeight;
    hint.textContent = saving
      ? 'Type your answer and press Enter. /restart starts a new game. Your progress saves in this browser.'
      : 'Type your answer and press Enter. /restart starts a new game. Saving is unavailable; keep this tab open to continue playing.';
  }
  form.addEventListener('submit', async function (event) {
    event.preventDefault();
    const value = answer.value.trim();
    if (!value || busy) return;
    busy = true; answer.disabled = true;
    try {
      await exclusive(function () {
        const latest = saving ? read(key) : lastSaved;
        answer.value = '';
        if (saving && latest !== lastSaved) {
          restore(latest);
          render('Another tab updated this game. Your answer was not applied. Read the latest prompt and enter it again.');
          return;
        }
        history.push(value); historyIndex = history.length; draft = '';
        game.submit(value);
        persist();
        render();
      });
    } finally { busy = false; answer.disabled = false; answer.focus(); }
  });
  answer.addEventListener('keydown', function (event) {
    if (event.key !== 'ArrowUp' && event.key !== 'ArrowDown') return;
    event.preventDefault();
    if (historyIndex === history.length) draft = answer.value;
    historyIndex = Math.max(0, Math.min(history.length, historyIndex + (event.key === 'ArrowUp' ? -1 : 1)));
    answer.value = historyIndex === history.length ? draft : history[historyIndex];
  });
  globalThis.addEventListener('storage', async function (event) {
    if (event.key !== key || !saving || busy) return;
    busy = true; answer.disabled = true;
    try {
      await exclusive(function () {
        const latest = read(key);
        if (saving && latest !== lastSaved) {
          restore(latest); answer.value = '';
          render('Game updated in another tab. Enter your answer to the latest prompt.');
        }
      });
    } finally { busy = false; answer.disabled = false; }
  });
  async function initialize() {
    try {
      await exclusive(function () {
        const current = read(key);
        restore(current === null ? read(legacyKey) : current);
        if (saving && current === null) persist();
      });
      render();
    } finally { busy = false; answer.disabled = false; answer.focus(); }
  }
  initialize();
})();
