<!-- 
 CSC 490: The Political Pioneer
 home.ftlh 

 Description: Public landing page — no account required. Shows a general, site-wide
 activity feed (party updates + user posts) rather than any one user's personal feed.
-->

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>The Political Pioneer — Home</title>
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
            <p class="welcome-text">Welcome, Guest!</p>
            <div class="account-links">
              <a href="/login">Log In</a>
              <a href="/signup">Sign Up</a>
            </div>
          </div>
        </div>
        <nav>
          <a href="/">Home</a>
          <a href="/about">About Us</a>
          <a href="/guide">Guide</a>
          <a href="#">Settings</a>
          <a href="/report-bug">Report a Concern</a>
        </nav>
        <div class="info-text">
          <p style="margin-top:0;">The Political Pioneer is an independent, nonpartisan platform for
            third-party organizing and voter engagement.</p>
          <ul>
            <li><a href="/report-bug">Report a Bug</a> — something broken? tell us</li>
            <li><a href="/contact">Contact the Dev Team</a> — feedback &amp; feature requests</li>
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
            <a href="/" class="active">Home</a>
            <a href="/platforms">Platforms</a>
            <a href="/resources">Resources</a>
            <a href="/events">Events</a>
            <a href="#">Collaborate</a>
            <a href="#">Voter Tools</a>
            <a href="/account">My Account</a>
          </nav>
          <div class="search">
            <input type="text" placeholder="Search parties, platforms, issues...">
            <button>Search</button>
          </div>
        </div>

        <div class="breadcrumb">Home</div>

        <div class="layout">

          <!-- LEFT -->
          <div class="col-left">
            <div class="module">
              <h2>Quick Links</h2>
              <div class="module-body">
                <ul>
                  <li><a href="/platforms">Browse All Platforms</a></li>
                  <li><a href="/resources">Resources &amp; Voter Tools</a></li>
                  <li><a href="/events">Upcoming Events</a></li>
                  <li><a href="/signup">Create an Account</a></li>
                </ul>
              </div>
            </div>

            <div class="module gold">
              <h2>Site Stats</h2>
              <div class="module-body">
                <ul>
                  <li>Active parties: <b>52</b></li>
                  <li>Registered voters: <b>8,204</b></li>
                  <li>Platforms updated this week: <b>14</b></li>
                </ul>
              </div>
            </div>
          </div>

          <!-- CENTER -->
          <div class="col-center">
            <div class="module red">
              <h2>Recent Activity</h2>
              <div class="module-body">
                <ul>
                  <li><b>New Horizon Coalition</b> updated their environmental platform — 2 hours ago</li>
                  <li><b>voter_kelly</b> posted a discussion on Civic Trust Party's board — 5 hours ago</li>
                  <li><b>Open Ledger Party</b> announced a town hall for Sept 20 — yesterday</li>
                  <li><b>m.reyes</b> joined Union of Independent Workers — 2 days ago</li>
                </ul>
              </div>
            </div>

            <div class="module">
              <h2>Featured Parties</h2>
              <div class="module-body" style="padding:0;">
                <div class="party-row">
                  <div class="party-swatch" style="background:#2E7D32;"></div>
                  <div class="party-info">
                    <div class="name">New Horizon Coalition</div>
                    <div class="meta">Environmental &middot; National</div>
                  </div>
                </div>
                <div class="party-row">
                  <div class="party-swatch" style="background:#8E24AA;"></div>
                  <div class="party-info">
                    <div class="name">Union of Independent Workers</div>
                    <div class="meta">Labor &amp; Economy &middot; Midwest</div>
                  </div>
                </div>
              </div>
            </div>
          </div>

          <!-- RIGHT -->
          <div class="col-right">
            <div class="module">
              <h2>Upcoming Events</h2>
              <div class="module-body">
                <ul>
                  <li>Town Hall — Sept 20</li>
                  <li>Voter Registration Drive — Oct 3</li>
                </ul>
              </div>
            </div>

            <div class="module gold">
              <h2>Get Started</h2>
              <div class="module-body">
                <p style="margin-top:0;">New here?</p>
                <a href="/signup" class="btn primary">Create an Account</a><br><br>
                <a href="/login" class="btn">Log In</a>
              </div>
            </div>
          </div>

        </div>

        <div class="footer">
          The Political Pioneer &mdash; a home for independent political organizing &middot;
          <a href="/about">About</a> &middot; <a href="#">Terms</a> &middot; <a href="/contact">Contact</a>
        </div>

      </div>
    </div>

  </div>

</body>
</html>
