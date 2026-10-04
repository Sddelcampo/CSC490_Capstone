<!-- 
 CSC 490: The Political Pioneer
 contactdevteam.ftlh 

 Description: Simple form for users to contact the dev team with feedback or feature requests.
-->

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>The Political Pioneer — Contact the Dev Team</title>
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

        <div class="breadcrumb">Contact the Dev Team</div>

        <div class="content-area">

          <div class="module">
            <h2>Contact the Dev Team</h2>
            <div class="module-body">
              <p style="margin-top:0;">Have feedback, a feature request, or a question about the project?
                Send us a message.</p>

              <form action="/contact" method="post">

                <div class="form-field">
                  <label for="name">Name</label>
                  <input type="text" id="name" name="name" required>
                </div>

                <div class="form-field">
                  <label for="email">Email</label>
                  <input type="email" id="email" name="email" required>
                </div>

                <div class="form-field">
                  <label for="message">Message</label>
                  <textarea id="message" name="message" required></textarea>
                </div>

                <div class="form-actions">
                  <button type="submit" class="btn primary">Send Message</button>
                </div>

              </form>
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
