<!-- 
 CSC 490: The Political Pioneer
 aboutus.ftlh 
 Last Updated: October 5, 2026

 Description: This page contains information on what this project is about.
-->

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>The Political Pioneer — About Us</title>
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
              <a href="/login">Log Out</a>
              <a href="/account">My Account</a>
            </div>
          </div>
        </div>
        <nav>
          <a href="/home">Home</a>
          <a href="/aboutus" class="active">About Us</a>
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
            <a href="/resources">Resources</a>
            <a href="/events">Events</a>
            <!--<a href="#">Collaborate</a> might remove as it could be merged with /account or /events-->
            <!--<a href="#">Voter Tools</a> may remove as it can be merged with resources-->
            <a href="/admin">Admin</a>
          </nav>
          <div class="search">
            <input type="text" placeholder="Search parties, platforms, issues...">
            <button>Search</button>
          </div>
        </div>

        <div class="breadcrumb">About Us</div>

        <div class="content-area">

          <div class="module red">
            <h2>About The Political Pioneer</h2>
            <div class="module-body">
              <p style="margin-top:0;">The Political Pioneer is an independent, nonpartisan platform
                built to help third-party and independent political organizations organize, publish
                their platforms, and connect with voters who are looking for alternatives beyond the
                two major parties.</p>
              <p>This project began as a response to a simple problem: independent parties often have
                the hardest time getting their message in front of voters, despite having real
                platforms and real communities behind them. The Political Pioneer gives any registered
                independent party a home — a directory listing, a space to publish their platform, and
                tools to organize their base.</p>
            </div>
          </div>

          <div class="module gold">
            <h2>Our Mission</h2>
            <div class="module-body">
              <p style="margin-top:0;">To make independent political organizing more visible,
                more accessible, and more collaborative — without favoring any single party or
                ideology.</p>
            </div>
          </div>

          <div class="module">
            <h2>The Project</h2>
            <div class="module-body">
              <p style="margin-top:0;">The Political Pioneer is a student capstone project
                (CSC 490), built as a full CRUD web application with a Java/FreeMarker-based MVC
                backend. It is a work in progress, and the content throughout this site is currently
                placeholder data for development and demonstration purposes.</p>
            </div>
          </div>

        </div>

        <div class="footer">
          The Political Pioneer &mdash; a home for independent political organizing &middot;
          <a href="/aboutus" class="active">About Us</a> &middot; <a href="#">Terms</a> &middot; <a href="/contactdevteam">Contact</a>
        </div>

      </div>
    </div>

  </div>

</body>
</html>
