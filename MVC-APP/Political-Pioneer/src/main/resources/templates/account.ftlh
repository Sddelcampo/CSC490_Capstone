<!-- 
 CSC 490: The Political Pioneer
 account.ftlh 
 Last updated: October 5, 2026

 Description: This is the user account file for The Political Pioneer. This is the user portal: 
 It contains their Political Questionnaire scores on the left, their bio and posts
 in the center, and their site activity on the right.
-->

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>The Political Pioneer — My Account</title>
<link rel="stylesheet" type="text/css" href="/css/styles.css">
</head>
<body class="page-dashboard page-portal">

  <!-- Creates a grid class to ensure we can split quadrants for logo, navigation, and the portal -->
  <div class="grid">

    <!-- Top Left Quadrant: Logo Implementation -->
    <div class="quadrant q-logo">
      <a href="/">
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

    <!-- Right Quadrant: The Political Pioneer Portal (TP2 Portal)
     (largest area — Provides portal navigation and user/party usage) -->
    <div class="quadrant q-portal">
      <div class="body">

        <!--Provides information for navigation within the portal along with a search bar and button-->
        <div class="mastbar">
          <div class="logo">TP2 <span>Portal</span></div>
          <nav class="tabs">
            <a href="/home">Home</a>
            <a href="/account" class="active">My Account</a>
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

        <!-- Provies a way to navigate within the portal -->
        <div class="breadcrumb">My Account &gt; Dashboard</div>

        <!-- Ensures that the portal is formatted in a layout where you can read within 3 column blocks -->
        <div class="layout">

          <!-- LEFT: Political Questionnaire scores & data -->
          <div class="col-left">
            <div class="module">
              <h2>My Alignment Scores</h2>
              <div class="module-body">
                <ul>
                  <li>Economic Policy: <b>62% Progressive</b></li>
                  <li>Civil Liberties: <b>74% Libertarian</b></li>
                  <li>Environmental Policy: <b>81% Pro-Regulation</b></li>
                  <li>Immigration Policy: <b>58% Inclusive</b></li>
                  <li>Governance Structure: <b>66% Decentralized</b></li>
                </ul>
                <p style="margin:8px 0 0;"><a href="/questionnaire">Retake the Questionnaire &raquo;</a></p>
              </div>
            </div>

            <div class="module gold">
              <h2>Matched Parties</h2>
              <div class="module-body">
                <ul>
                  <li><a href="#">New Horizon Coalition</a> — 88% match</li>
                  <li><a href="#">Open Ledger Party</a> — 76% match</li>
                </ul>
              </div>
            </div>
          </div>

          <!-- CENTER: Bio & posts -->
          <div class="col-center">

            <div class="stat-strip">
              <span>Posts: <b>12</b></span>
              <span>Top Match: <b>88%</b></span>
              <span>Member since: <b>2022</b></span>
            </div>

            <div class="module red">
              <h2>My Bio</h2>
              <div class="module-body">
                <form action="/account/bio" method="post">
                  <div class="form-field">
                    <textarea name="bio" placeholder="Tell other users a bit about yourself...">Longtime advocate for renewable energy policy and civic tech. Based in the Pacific Northwest.</textarea>
                  </div>
                  <div class="form-actions">
                    <button type="submit" class="btn primary">Save Bio</button>
                  </div>
                </form>
              </div>
            </div>

            <div class="module">
              <h2>Share an Update</h2>
              <div class="module-body">
                <form action="/account/post" method="post">
                  <div class="form-field">
                    <textarea name="postContent" placeholder="What's on your mind?" required></textarea>
                  </div>
                  <div class="form-actions">
                    <button type="submit" class="btn primary">Post</button>
                  </div>
                </form>
              </div>
            </div>

            <div class="module">
              <h2>My Posts</h2>
              <div class="module-body">
                <ul>
                  <li>"Thoughts on the new ballot access guide" — 3 hours ago</li>
                  <li>"Looking for volunteers in the Midwest" — 2 days ago</li>
                  <li>"Excited for the Sept 20 town hall!" — 5 days ago</li>
                </ul>
              </div>
            </div>

          </div>

          <!-- RIGHT: Site activity -->
          <div class="col-right">
            <div class="module">
              <h2>My Activity</h2>
              <div class="module-body">
                <ul>
                  <li>Completed the Political Questionnaire — 1 week ago</li>
                  <li>New Horizon Coalition replied to your post — 1 hour ago</li>
                  <li>You were mentioned by <b>t_nguyen</b> — yesterday</li>
                  <li>Joined Union of Independent Workers — 2 days ago</li>
                  <li>Your "Report a Concern" ticket was resolved — 4 days ago</li>
                </ul>
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
