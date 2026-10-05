<!-- 
 CSC 490: The Political Pioneer
 resources.ftlh 
 Last updated: October 5, 2026

 Description: Lists all resources available to users — voter tools, organizing tools,
 and guides — including a link to the Political Questionnaire (questionnaire.ftlh).
-->

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>The Political Pioneer — Resources</title>
<link rel="stylesheet" type="text/css" href="/css/styles.css">
</head>
<body class="page-dashboard page-portal">

  <div class="grid">

    <!-- Top Left Quadrant: Logo -->
    <div class="quadrant q-logo">
      <a href="/">
        <img src="images/deSebastian_TPP_Logo.png" alt="The Political Pioneer Logo">
      </a>
    </div>

    <!-- Bottom Left Quadrant: Account, general info, & site navigation -->
    <div class="quadrant q-side">
      <h2>Account &amp; Site Info</h2>
      <div class="body">
        <div class="account-block">
          <div class="avatar-circle">&#128100;</div>
          <div>
            <p class="welcome-text">Welcome, User!</p>
            <div class="account-links">
              <!-- <a href="/acctlogin">Log In</a> Might be useless if we start at the login page-->
              <a href="/acctlogin">Log Out</a>
              <a href="/account">My Account</a>
            </div>
          </div>
        </div>
        <nav>
          <a href="/home">Home</a>
          <a href="/aboutus">About Us</a>
          <a href="/guide">Guide</a>
          <!-- <a href="#">Settings</a> Unsure if settings may be necessary for the project-->
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

    <!-- Right Quadrant: main portal content -->
    <div class="quadrant q-portal">
      <div class="body">

        <div class="mastbar">
          <div class="logo">TP2 <span>Portal</span></div>
          <nav class="tabs">
            <a href="/home">Home</a>
            <a href="/account">My Account</a>
            <a href="/platform">Platforms</a>
            <a href="/resources" class="active">Resources</a>
            <!--<a href="#">Collaborate</a> might remove as it could be merged with /account or /events-->
            <a href="/events">Events</a>
            <!--<a href="#">Voter Tools</a> may remove as it can be merged with resources-->
            <a href="/admin">Admin</a> <!-- Temporarily available for demonstration purposes-->
          </nav>
          <div class="search">
            <input type="text" placeholder="Search parties, platforms, issues...">
            <button>Search</button>
          </div>
        </div>

        <div class="breadcrumb">Resources &gt; All Resources</div>

        <div class="content-area">

          <div class="module red">
            <h2>Voter Tools</h2>
            <div class="module-body">
              <ul>
                <li><a href="#">Am I Registered? — check your voter registration status</a></li>
                <li><a href="#">Find My Polling Place</a></li>
                <li><a href="#">Ballot Access Guide</a></li>
                <li><a href="/questionnaire">The Political Questionnaire — find your alignment</a></li>
              </ul>
            </div>
          </div>

          <div class="module gold">
            <h2>Organizing Tools</h2>
            <div class="module-body">
              <ul>
                <li><a href="#">Petition Builder</a></li>
                <li><a href="#">Volunteer Matcher</a></li>
                <li><a href="#">Fundraising Toolkit</a></li>
                <li><a href="#">Media Kit Templates</a></li>
              </ul>
            </div>
          </div>

          <div class="module">
            <h2>Guides &amp; Documentation</h2>
            <div class="module-body">
              <ul>
                <li><a href="#">How to Register a Party</a></li>
                <li><a href="/guide">Site Navigation Guide</a></li>
                <li><a href="#">Collaboration Board Guide</a></li>
              </ul>
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
