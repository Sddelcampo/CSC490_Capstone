/*
 * CSC 490: The Political Pioneer
 * questionnaire.js
 *
 * Auto-saves each Likert answer on the questionnaire page. When a radio button is clicked,
 * the answer is POSTed to /questionnaire/answer as JSON: { questionId, value }.
 *
 * Expects (all rendered by questionnaire.ftlh):
 *   - radios inside #questionnaire-form, each with data-question-id and a value of 1-5
 *   - a #save-status element for feedback messages
 *   - optional <meta name="_csrf"> / <meta name="_csrf_header"> tags for Spring Security CSRF
 */
(function () {
  var form = document.getElementById('questionnaire-form');
  var statusEl = document.getElementById('save-status');
  if (!form || !statusEl) { return; }

  var csrfToken  = (document.querySelector('meta[name="_csrf"]') || {}).content;
  var csrfHeader = (document.querySelector('meta[name="_csrf_header"]') || {}).content;
  var clearTimer = null;

  function setStatus(text, isError) {
    statusEl.textContent = text;
    statusEl.className = 'save-status' + (isError ? ' error' : '');
    clearTimeout(clearTimer);
    if (!isError) {
      clearTimer = setTimeout(function () { statusEl.textContent = ''; }, 2000);
    }
  }

  function saveAnswer(questionId, value) {
    var headers = { 'Content-Type': 'application/json' };
    if (csrfToken) { headers[csrfHeader] = csrfToken; }

    setStatus('Saving...', false);
    return fetch('/questionnaire/answer', {
      method: 'POST',
      headers: headers,
      credentials: 'same-origin',
      body: JSON.stringify({ questionId: questionId, value: value })
    }).then(function (res) {
      if (!res.ok) { throw new Error('HTTP ' + res.status); }
      setStatus('Saved', false);
    }).catch(function () {
      setStatus('Could not save that answer. Please click it again.', true);
    });
  }

  form.querySelectorAll('input[type="radio"]').forEach(function (radio) {
    radio.addEventListener('change', function () {
      saveAnswer(Number(radio.dataset.questionId), Number(radio.value));
    });
  });
})();
