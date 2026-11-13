<#--
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
   section       : { title: string, score: int, questions: [ { id: long, text: string, answer: int (optional, 1-5) } ] }
   _csrf         : (optional) Spring Security CsrfToken, only if CSRF protection is enabled
-->

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>The Political Pioneer — ${section.title}</title>
  <link rel="stylesheet" type="text/css" href="/css/styles.css">

  <!-- CSRF values (safe if Spring Security CSRF is off: they just come out empty) -->
  <meta name="_csrf" content="${(_csrf.token)!''}">
  <meta name="_csrf_header" content="${(_csrf.headerName)!'X-CSRF-TOKEN'}">

  <script src="/js/questionnaire.js" defer></script>
</head>
<body class="page-dashboard page-portal">

  <!-- Creates a grid class to ensure we can split quadrants for logo, navigation, and the portal -->
  <div class="grid">

    <!-- Top Left Quadrant: Logo Implementation -->
    <div class="quadrant q-logo">
      <a href="/">
        <#-- The Political Pioneer Logo: Created by Sebastian Del Campo -->
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
            <h2>${section.title}</h2>
            <div class="module-body">

              <!-- Step indicator: jump to any section. Answers are already saved, so jumping is safe. -->
              <ol class="q-progress">
                <#list sectionTitles as title>
                  <li<#if title?index + 1 == pageNumber> class="current"</#if>>
                    <a href="/questionnaire?page=${title?index + 1}" title="${title}">${title}</a>
                  </li>
                </#list>
              </ol>

              <div class="q-meta">
                <strong>Page ${pageNumber} of ${totalPages}</strong>
                <span>Section score: <strong id="section-score">${section.score}</strong></span>
                <span id="save-status" class="save-status" aria-live="polite"></span>
              </div>

              <p style="margin-top:0;">Select how strongly you agree or disagree with each statement.
                Your answer and section score are saved as soon as you click it. You can skip questions
                or stop at any time, and unanswered questions count as 0.</p>

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


</body>
</html>
