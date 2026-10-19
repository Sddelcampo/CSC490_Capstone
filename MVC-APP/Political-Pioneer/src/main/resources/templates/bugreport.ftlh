<!-- 
 CSC 490: The Political Pioneer
 bugreport.ftlh 
 Last updated: October 5, 2026

 Description: Simple form for reporting bugs on the website where
 user will enter their username (if they so choose), the type of bug
 and a description of what occured
-->

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>The Political Pioneer — Report a Bug</title>
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
            <li><a href="/bugreport" class="active">Report a Bug</a> — something broken? tell us</li>
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

        <div class="breadcrumb">Report a Bug</div>

        <div class="content-area">

          <div class="module red">
            <h2>Report a Bug</h2>
            <div class="module-body">
              <p style="margin-top:0;">Found something broken? Let us know and we'll look into it.</p>

              <form action="/report-bug" method="post">

                <div class="form-field">
                  <label for="username">Your Username (optional)</label>
                  <input type="text" id="username" name="username">
                </div>

                <div class="form-field">
                  <label for="page">Page or Feature</label>
                  <input type="text" id="page" name="page" placeholder="e.g. Platform Directory search" required>
                </div>

                <div class="form-field">
                  <label for="description">What happened?</label>
                  <textarea id="description" name="description" placeholder="Describe what you expected and what actually happened" required></textarea>
                </div>

                <div class="form-actions">
                  <button type="submit" class="btn primary">Submit Report</button>
                </div>

              </form>
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
