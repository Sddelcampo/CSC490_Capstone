(function () {
  var statusElement = document.getElementById('save-status');
  var scoreElement = document.getElementById('section-score');
  var csrfToken = (document.querySelector('meta[name="_csrf"]') || {}).content;
  var csrfHeader = (document.querySelector('meta[name="_csrf_header"]') || {}).content;
  var saveQueue = [];
  var saving = false;
  var statusTimer;

  function setStatus(message, isError) {
    statusElement.textContent = message;
    statusElement.className = 'save-status' + (isError ? ' error' : '');
    window.clearTimeout(statusTimer);
    if (!isError) {
      statusTimer = window.setTimeout(function () {
        statusElement.textContent = '';
      }, 2000);
    }
  }

  function saveNext() {
    var answer = saveQueue[0];
    var headers = { 'Content-Type': 'application/json' };
    if (csrfToken) {
      headers[csrfHeader] = csrfToken;
    }

    setStatus('Saving...', false);
    fetch('/questionnaire/answer', {
      method: 'POST',
      headers: headers,
      credentials: 'same-origin',
      body: JSON.stringify(answer)
    }).then(function (response) {
      if (!response.ok || response.redirected) {
        throw new Error('The answer could not be saved.');
      }
      return response.json();
    }).then(function (result) {
      scoreElement.textContent = result.score;
      saveQueue.shift();
      if (saveQueue.length > 0) {
        saveNext();
      } else {
        saving = false;
        setStatus('Saved', false);
      }
    }).catch(function () {
      saving = false;
      setStatus('Could not save that answer. Please try again.', true);
    });
  }

  document.querySelectorAll('#questionnaire-form input[type="radio"]').forEach(function (radio) {
    radio.addEventListener('change', function () {
      saveQueue.push({
        questionId: Number(radio.dataset.questionId),
        value: Number(radio.value)
      });
      if (!saving) {
        saving = true;
        saveNext();
      }
    });
  });
})();
