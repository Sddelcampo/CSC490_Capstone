<!--
 CSC 490: The Political Pioneer
 questionnaireresults.ftlh
 Last updated: November 9, 2026

 Description: Results page for the political questionnaire. Shows, for each of the 7 sections, the
 user's category score and every statement with the answer they gave. Unanswered questions are shown
 as such and contribute 0 to the section score.

 MODEL EXPECTED FROM THE CONTROLLER (GET /questionnaire/results):
   totalAnswered   : int
   totalQuestions  : int
   sections        : list of {
                       title    : string
                       answered : int      (how many of this section's questions were answered)
                       total    : int      (questions in the section, normally 5)
                       score    : number   (0.00 - 5.00; unanswered questions contribute 0)
                       percent  : int      (0 - 100, position of the score on the bar: score / 5 * 100)
                       questions: list of {
                                    text        : string
                                    answer      : int     (1-5, ABSENT if the question was not answered)
                                    answerLabel : string  (ABSENT if not answered, e.g. "Strongly Agree")
                                  }
                     }

 NOTE: FreeMarker comment (not HTML comment) on purpose, so nothing in here gets evaluated.
-->

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>The Political Pioneer — Questionnaire Results</title>
  <link rel="stylesheet" type="text/css" href="/css/styles.css">
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

        <div class="breadcrumb">Resources &gt; Political Questionnaire &gt; Results</div>

        <div class="admin-content">

          <!-- Summary -->
          <div class="module red">
            <h2>Your Questionnaire Results</h2>
            <div class="module-body">
              <div class="results-summary">
                <p style="margin-top:0;">
                  You answered <strong>${totalAnswered}</strong> of <strong>${totalQuestions}</strong> questions.
                  Each section is averaged across its five questions. A skipped question contributes 0,
                  so you can go back at any time and finish or change your answers.
                </p>
                <div class="page-nav">
                  <a class="btn" href="/questionnaire?page=1">&laquo; Back to Questionnaire</a>
                  <a class="btn primary" href="/search">Find Parties That Match &raquo;</a>
                </div>
              </div>
            </div>
          </div>

          <!-- One module per questionnaire section -->
          <#list sections as s>
            <div class="module">
              <h2>${s?index + 1}. ${s.title}</h2>
              <div class="module-body">

                <div class="score-line">
                  <span>Score: <strong><#if s.answered == 0>0.00<#else>${s.score?string("0.00")}</#if></strong> / 5</span>
                  <br>
                  <span>${s.answered} of ${s.total} answered</span>
                </div>

                <table class="results-table">
                  <thead>
                    <tr>
                      <th>#</th>
                      <th>Statement</th>
                      <th>Your Answer</th>
                    </tr>
                  </thead>
                  <tbody>
                    <#list s.questions as q>
                      <tr<#if !q.answer??> class="unanswered"</#if>>
                        <td class="num">${q?index + 1}</td>
                        <td>${q.text}</td>
                        <td class="answer">
                          <#if q.answer??>${q.answerLabel}<#else>Not answered</#if>
                        </td>
                      </tr>
                    </#list>
                  </tbody>
                </table>

                <div class="results-actions">
                  <a class="btn" href="/questionnaire?page=${s?index + 1}">
                    <#if s.answered lt s.total>Finish this section<#else>Change answers</#if>
                  </a>
                </div>

              </div>
            </div>
          </#list>

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
