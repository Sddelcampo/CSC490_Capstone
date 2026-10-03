<!-- 
 CSC 490: The Political Pioneer
 questionnaire.ftlh 
 Last updated: September 30, 2026

 Description: This is the questionnaire file for The Political Pioneer Web App, this will contain the
 questionnaire which will be used to determine a user's alignments in political ideologies this will
 then be processed by the backend to give a user scores depending on their answers to the questions.
-->

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>The Political Pioneer — Political Questionnaire</title>
  <link rel="stylesheet" type="text/css" href="/css/styles.css">

<!-- <script src="script.js" defer></script> -->
</head>
<body class="page-dashboard page-portal">

  <!-- Creates a grid class to ensure we can split quadrants for logo, navigation, and the portal -->
  <div class="grid">

    <!-- Top Left Quadrant: Logo Implementation -->
    <div class="quadrant q-logo">
      <a href="/admin">
        <!-- The Political Pioneer Logo: Created by Sebastian Del Campo -->
        <img src="images/deSebastian_TPP_Logo.png" alt="The Political Pioneer Logo">
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
              <a href="#">Log In</a>
              <a href="#">Log Out</a>
              <a href="#">My Profile</a>
            </div>
          </div>
        </div>
        <nav>
          <a href="#">Home</a>
          <a href="#">About Us</a>
          <a href="#">Guide</a>
          <a href="#">Settings</a>
          <a href="#">Report a Concern</a>
        </nav>
        <div class="info-text">
          <p style="margin-top:0;">The Political Pioneer is an independent, nonpartisan platform for
             third-party organizing and voter engagement.</p>
          <ul>
            <li><a href="#">Report a Bug</a> — something broken? tell us</li>
            <li><a href="#">Contact the Dev Team</a> — feedback &amp; feature requests</li>
          </ul>
        </div>
      </div>
    </div>

    <!-- Right Quadrant: The Political Pioneer Portal (TP2 Portal)
     (largest area — Provides portal navigation and user/party usage) -->
    <div class="quadrant q-portal">
      <div class="body">

        <!--Provides information for navigation within the portal along with a search bar and button-->
        <div class="mastbar">
          <div class="logo">TP2 <span>Portal</span></div>
          <nav class="tabs">
            <a href="#">Directory</a>
            <a href="/account">My Account</a>
            <a href="#">Platforms</a>
            <a href="#" class="active">Resources</a>
            <a href="#">Collaborate</a>
            <a href="#">Events</a>
            <a href="#">Voter Tools</a>
            <a href="/admin">Admin</a>
          </nav>
          <div class="search">
            <input type="text" placeholder="Search parties, platforms, issues...">
            <button>Search</button>
          </div>
        </div>

        <!-- Provies a way to navigate within the portal -->
        <div class="breadcrumb">Resources &gt; Political Questionnaire</div>

        <!-- Questionnaire block: 5 Likert-scale questions (strongly disagree -> strongly agree),
             submitted to the backend for ideology-alignment scoring -->
        <div class="admin-content">

          <div class="module red">
            <h2>Political Alignment Questionnaire</h2>
            <div class="module-body">
              <p style="margin-top:0;">Answer each statement below based on how strongly you agree or
                disagree. Your responses are used to estimate your alignment across a range of issues.</p>

              <#--
                TODO: once wired to the backend/model, replace the 5 hardcoded <fieldset> blocks below
                with a <#list questions as q> loop driven by the model's question list, e.g.:

                <#list questions as q>
                  <fieldset class="question-block">
                    <legend>${q.index}. ${q.text}</legend>
                    <div class="scale"> ... name="q${q.id}" ... </div>
                  </fieldset>
                </#list>

                NOTE: this block uses FreeMarker's own comment syntax , not an HTML comment
                . FreeMarker still parses directives inside HTML comments, so any example FTL
                code written there gets executed, not just displayed — that was the cause of the 500 here.
              -->

              <form action="/questionnaire/submit" method="post" class="questionnaire-form">

                <fieldset class="question-block">
                  <legend>1. The government should play an active role in reducing economic inequality.</legend>
                  <div class="scale">
                    <label class="scale-option"><input type="radio" name="q1" value="1" required><span>Strongly Disagree</span></label>
                    <label class="scale-option"><input type="radio" name="q1" value="2"><span>Disagree</span></label>
                    <label class="scale-option"><input type="radio" name="q1" value="3"><span>Neutral</span></label>
                    <label class="scale-option"><input type="radio" name="q1" value="4"><span>Agree</span></label>
                    <label class="scale-option"><input type="radio" name="q1" value="5"><span>Strongly Agree</span></label>
                  </div>
                </fieldset>

                <fieldset class="question-block">
                  <legend>2. Individual liberty should be prioritized over collective security, even during a crisis.</legend>
                  <div class="scale">
                    <label class="scale-option"><input type="radio" name="q2" value="1" required><span>Strongly Disagree</span></label>
                    <label class="scale-option"><input type="radio" name="q2" value="2"><span>Disagree</span></label>
                    <label class="scale-option"><input type="radio" name="q2" value="3"><span>Neutral</span></label>
                    <label class="scale-option"><input type="radio" name="q2" value="4"><span>Agree</span></label>
                    <label class="scale-option"><input type="radio" name="q2" value="5"><span>Strongly Agree</span></label>
                  </div>
                </fieldset>

                <fieldset class="question-block">
                  <legend>3. Environmental regulations are necessary to protect the long-term public interest, even if they raise costs for businesses.</legend>
                  <div class="scale">
                    <label class="scale-option"><input type="radio" name="q3" value="1" required><span>Strongly Disagree</span></label>
                    <label class="scale-option"><input type="radio" name="q3" value="2"><span>Disagree</span></label>
                    <label class="scale-option"><input type="radio" name="q3" value="3"><span>Neutral</span></label>
                    <label class="scale-option"><input type="radio" name="q3" value="4"><span>Agree</span></label>
                    <label class="scale-option"><input type="radio" name="q3" value="5"><span>Strongly Agree</span></label>
                  </div>
                </fieldset>

                <fieldset class="question-block">
                  <legend>4. Immigration policy should prioritize inclusivity and clear pathways to citizenship.</legend>
                  <div class="scale">
                    <label class="scale-option"><input type="radio" name="q4" value="1" required><span>Strongly Disagree</span></label>
                    <label class="scale-option"><input type="radio" name="q4" value="2"><span>Disagree</span></label>
                    <label class="scale-option"><input type="radio" name="q4" value="3"><span>Neutral</span></label>
                    <label class="scale-option"><input type="radio" name="q4" value="4"><span>Agree</span></label>
                    <label class="scale-option"><input type="radio" name="q4" value="5"><span>Strongly Agree</span></label>
                  </div>
                </fieldset>

                <fieldset class="question-block">
                  <legend>5. Decisions on local issues should be made primarily by local communities rather than centralized authorities.</legend>
                  <div class="scale">
                    <label class="scale-option"><input type="radio" name="q5" value="1" required><span>Strongly Disagree</span></label>
                    <label class="scale-option"><input type="radio" name="q5" value="2"><span>Disagree</span></label>
                    <label class="scale-option"><input type="radio" name="q5" value="3"><span>Neutral</span></label>
                    <label class="scale-option"><input type="radio" name="q5" value="4"><span>Agree</span></label>
                    <label class="scale-option"><input type="radio" name="q5" value="5"><span>Strongly Agree</span></label>
                  </div>
                </fieldset>

                <div style="text-align:center; margin-top:14px;">
                  <button type="submit" class="btn primary">Submit Questionnaire</button>
                </div>

              </form>
            </div>
          </div>

        </div>

        <div class="footer">
          The Political Pioneer &mdash; a home for independent political organizing &middot;
          <a href="#">About</a> &middot; <a href="#">Terms</a> &middot; <a href="#">Contact</a>
        </div>

      </div>
    </div>

  </div>

</body>
</html>
