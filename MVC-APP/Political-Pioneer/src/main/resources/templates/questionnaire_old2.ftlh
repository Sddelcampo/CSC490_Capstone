<!--
 CSC 490: The Political Pioneer
 questionnaire.ftlh
 Last updated: November 9, 2026

 Description: Paginated questionnaire. Each page shows ONE section (5 questions). The moment a user
 clicks a Likert option, the answer is POSTed to /questionnaire/answer and scored by the backend.
 Unanswered questions simply stay at 0, so users can leave early and still keep their score.

 NOTE: this is a FreeMarker comment (not an HTML comment) on purpose. FreeMarker parses ${...} and
 <#...> inside HTML comments, which is what caused the earlier 500.

 MODEL EXPECTED FROM THE CONTROLLER (GET /questionnaire?page=N):
   pageNumber    : int (1-based current page)
   totalPages    : int (7)
   sectionTitles : list of strings, in page order (used for the step indicator)
   section       : { title: string, questions: [ { id: long, text: string, answer: int (optional, 1-5) } ] }
   _csrf         : (optional) Spring Security CsrfToken, only if CSRF protection is enabled
-->

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>The Political Pioneer — Political Questionnaire</title>
  <link rel="stylesheet" type="text/css" href="/css/styles.css">

  <!-- CSRF values (safe if Spring Security CSRF is off: they just come out empty) -->
  <meta name="_csrf" content="${(_csrf.token)!''}">
  <meta name="_csrf_header" content="${(_csrf.headerName)!'X-CSRF-TOKEN'}">

  <!-- Small additions for pagination. Move these into /css/styles.css when convenient. -->
  <style>
    .q-progress { display:flex; gap:6px; margin:0 0 14px 0; padding:0; list-style:none; }
    .q-progress li { flex:1; }
    .q-progress a, .q-progress span {
      display:block; text-align:center; padding:6px 4px; font-size:12px;
      border:1px solid #999; text-decoration:none; color:inherit;
    }
    .q-progress li.current a, .q-progress li.current span { font-weight:bold; border-width:2px; }
    .q-meta { display:flex; justify-content:space-between; align-items:center; margin-bottom:10px; }
    .save-status { font-size:12px; min-height:1em; }
    .save-status.error { color:#b00020; font-weight:bold; }
    .page-nav { display:flex; justify-content:space-between; align-items:center; margin-top:14px; }
    .page-nav .spacer { min-width:120px; }
    .btn.disabled { pointer-events:none; opacity:.4; }
  </style>
</head>
<body class="page-dashboard page-portal">

  <!-- Creates a grid class to ensure we can split quadrants for logo, navigation, and the portal -->
  <div class="grid">

    <!-- Top Left Quadrant: Logo Implementation -->
    <div class="quadrant q-logo">
      <a href="/">
        <!-- The Political Pioneer Logo: Created by Sebastian Del Campo -->
        <img src="/images/deSebastian_TPP_Logo.png" alt="The Political Pioneer Logo">
      </a>
    </div>

    <!-- Bottom Left Quadrant: Account, general info, & site navigation links -->
    <div class="quadrant q-side">
      <h2>Account &amp; Site Info</h2>
      <div class="body">
        <div class="account-block">
          <div class="avatar-circle">&#128100;</div>
          <div>
            <p class="welcome-text">Welcome, User!</p>
            <div class="account-links">
              <a href="/acctlogin">Log Out</a>
              <a href="/account">My Account</a>
            </div>
          </div>
        </div>
        <nav>
          <a href="/home">Home</a>
          <a href="/aboutus">About Us</a>
          <a href="/guide">Guide</a>
          <a href="/contactdevteam">Report a Concern</a>
        </nav>
        <div class="info-text">
          <p style="margin-top:0;">The Political Pioneer is an independent, nonpartisan platform for
             third-party organizing and voter engagement.</p>
          <ul>
            <li><a href="/bugreport">Report a Bug</a> — something broken? tell us</li>
            <li><a href="/contactdevteam">Contact the Dev Team</a> — feedback &amp; feature requests</li>
          </ul>
        </div>
      </div>
    </div>

    <!-- Right Quadrant: The Political Pioneer Portal (TP2 Portal) -->
    <div class="quadrant q-portal">
      <div class="body">

        <div class="mastbar">
          <div class="logo">TP2 <span>Portal</span></div>
          <nav class="tabs">
            <a href="/home">Home</a>
            <a href="/account">My Account</a>
            <a href="/platform">Platforms</a>
            <a href="/search">Find Parties</a>
            <a href="/resources">Resources</a>
            <a href="/events">Events</a>
            <#if isOwner!false><a href="/createFP">Create Forum Post</a></#if>
         </nav>
          <div class="search">
            <input type="text" placeholder="Search parties, platforms, issues...">
            <button>Search</button>
          </div>
        </div>

        <div class="breadcrumb">Resources &gt; Political Questionnaire &gt; ${section.title}</div>

        <div class="admin-content">

          <div class="module red">
            <h2>Political Alignment Questionnaire</h2>
            <div class="module-body">

              <#-- Step indicator: jump to any section. Answers are already saved, so jumping is safe. -->
              <ol class="q-progress">
                <#list sectionTitles as title>
                  <li<#if title?index + 1 == pageNumber> class="current"</#if>>
                    <a href="/questionnaire?page=${title?index + 1}" title="${title}">${title?index + 1}</a>
                  </li>
                </#list>
              </ol>

              <div class="q-meta">
                <strong>${section.title} (Page ${pageNumber} of ${totalPages})</strong>
                <span id="save-status" class="save-status" aria-live="polite"></span>
              </div>

              <p style="margin-top:0;">Select how strongly you agree or disagree with each statement.
                Your answer is saved as soon as you click it. You can skip questions or stop at any
                time, and any unanswered question counts as 0.</p>

              <div class="questionnaire-form" id="questionnaire-form">

                <#list section.questions as q>
                  <fieldset class="question-block">
                    <legend>${q?index + 1}. ${q.text}</legend>
                    <div class="scale">
                      <#assign labels = ["Strongly Disagree", "Disagree", "Neutral", "Agree", "Strongly Agree"]>
                      <#list labels as label>
                        <label class="scale-option">
                          <input type="radio"
                                 name="q${q.id?c}"
                                 value="${label?index + 1}"
                                 data-question-id="${q.id?c}"
                                 <#if (q.answer!0) == label?index + 1>checked</#if>>
                          <span>${label}</span>
                        </label>
                      </#list>
                    </div>
                  </fieldset>
                </#list>

                <div class="page-nav">
                  <#if pageNumber gt 1>
                    <a class="btn" href="/questionnaire?page=${pageNumber - 1}">&laquo; Previous</a>
                  <#else>
                    <span class="spacer"></span>
                  </#if>

                  <#if pageNumber lt totalPages>
                    <a class="btn primary" href="/questionnaire?page=${pageNumber + 1}">Next &raquo;</a>
                  <#else>
                    <a class="btn primary" href="/questionnaire/results">Finish &amp; View Results</a>
                  </#if>
                </div>

                <!-- Let users bail out early; their scores so far are already stored. -->
                <p style="text-align:center; margin-top:10px;">
                  <a href="/questionnaire/results">Done for now? View my results so far</a>
                </p>

              </div>
            </div>
          </div>

        </div>

        <div class="footer">
          The Political Pioneer &mdash; a home for independent political organizing &middot;
          <a href="/aboutus">About Us</a> &middot; <a href="#">Terms</a> &middot; <a href="/contactdevteam">Contact</a>
        </div>

      </div>
    </div>

  </div>

  <!--
    Auto-save script. IMPORTANT: no JS template literals (backtick + dollar-brace) in here.
    FreeMarker would try to evaluate them, so string concatenation is used instead.
  -->
  <script>
    (function () {
      var statusEl = document.getElementById('save-status');
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

      document.querySelectorAll('#questionnaire-form input[type="radio"]').forEach(function (radio) {
        radio.addEventListener('change', function () {
          saveAnswer(Number(radio.dataset.questionId), Number(radio.value));
        });
      });
    })();
  </script>

</body>
</html>
