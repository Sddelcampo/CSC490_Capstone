<!-- 
 CSC 490: The Political Pioneer
 guide.ftlh 

 Description: Informational page explaining how to navigate the site.
-->

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>The Political Pioneer — Site Guide</title>
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
          <a href="/guide" class="active">Guide</a>
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

        <div class="breadcrumb">Guide</div>

        <div class="content-area">

          <div class="module">
            <h2>Site Guide</h2>
            <div class="module-body">
              <p style="margin-top:0;">Here's how to get around The Political Pioneer:</p>
              <ul>
                <li><b>Home</b> — a general, public overview of recent activity across parties and
                  users. No account required.</li>
                <li><b>Platforms</b> — browse and search every independent party registered on the
                  platform.</li>
                <li><b>Resources</b> — voter tools, organizing tools, and guides, including the
                  Political Questionnaire.</li>
                <li><b>Events</b> — upcoming town halls, voter drives, and workshops posted by
                  parties.</li>
                <li><b>My Account</b> — your personal dashboard: your posts, affiliated parties, and
                  recent activity. Requires logging in.</li>
              </ul>
            </div>
          </div>

          <div class="module gold">
            <h2>Account &amp; Site Info Panel</h2>
            <div class="module-body">
              <p style="margin-top:0;">The panel in the bottom-left corner of every page gives you
                quick access to your account, the site nav (Home, About Us, Guide, Settings, Report a
                Concern), and links to report a bug or contact the dev team.</p>
            </div>
          </div>

          <div class="module red">
            <h2>Need More Help?</h2>
            <div class="module-body">
              <p style="margin-top:0;">If something isn't working the way you expect, you can
                <a href="/report-bug">report a bug</a> or <a href="/contact">contact the dev team</a>
                directly.</p>
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
