<!-- 
 CSC 490: The Political Pioneer
 events.ftlh 

 Description: Displays a list of upcoming events that parties have posted (town halls,
 voter drives, workshops, etc.).
-->

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>The Political Pioneer — Events</title>
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
              <a href="/login">Log In</a>
              <a href="#">Log Out</a>
              <a href="/account">My Profile</a>
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
            <a href="/">Home</a>
            <a href="/platforms">Platforms</a>
            <a href="/resources">Resources</a>
            <a href="/events" class="active">Events</a>
            <a href="#">Collaborate</a>
            <a href="#">Voter Tools</a>
            <a href="/account">My Account</a>
          </nav>
          <div class="search">
            <input type="text" placeholder="Search parties, platforms, issues...">
            <button>Search</button>
          </div>
        </div>

        <div class="breadcrumb">Events &gt; Upcoming Events</div>

        <div class="content-area">

          <div class="module red">
            <h2>Upcoming Events</h2>
            <div class="module-body" style="padding:0;">

              <div class="event-row">
                <div class="event-date-badge"><div class="month">SEP</div><div class="day">20</div></div>
                <div class="event-info">
                  <div class="name">Town Hall: Coastal Climate Policy</div>
                  <div class="meta">Hosted by New Horizon Coalition &middot; 6:00 PM &middot; Portland, OR</div>
                  <div class="desc">An open discussion on the party's updated environmental platform.</div>
                </div>
              </div>

              <div class="event-row">
                <div class="event-date-badge"><div class="month">OCT</div><div class="day">03</div></div>
                <div class="event-info">
                  <div class="name">Voter Registration Drive</div>
                  <div class="meta">Hosted by Union of Independent Workers &middot; All Day &middot; Midwest Region</div>
                  <div class="desc">Volunteers needed to help register voters ahead of the deadline.</div>
                </div>
              </div>

              <div class="event-row">
                <div class="event-date-badge"><div class="month">OCT</div><div class="day">12</div></div>
                <div class="event-info">
                  <div class="name">Civic Tech Policy Workshop</div>
                  <div class="meta">Hosted by Open Ledger Party &middot; 1:00 PM &middot; Virtual</div>
                  <div class="desc">A workshop on transparent governance tools and how to get involved.</div>
                </div>
              </div>

              <div class="event-row">
                <div class="event-date-badge"><div class="month">OCT</div><div class="day">18</div></div>
                <div class="event-info">
                  <div class="name">Community Canvass Day</div>
                  <div class="meta">Hosted by Rural Renewal Alliance &middot; 9:00 AM &middot; Rural South Region</div>
                  <div class="desc">Door-to-door outreach on the agriculture policy platform.</div>
                </div>
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
