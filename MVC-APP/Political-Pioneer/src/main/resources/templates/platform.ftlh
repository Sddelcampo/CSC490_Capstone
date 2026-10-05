<!-- 
 CSC 490: The Political Pioneer
 platform.ftlh 
 Last updated: October 5, 2026

 Description: Displays all independent party platforms, with a search bar to find
 parties by name, category, or region. This is the full party directory.
-->

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>The Political Pioneer — Platforms</title>
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
            <a href="/platform" class="active">Platforms</a>
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

        <div class="breadcrumb">Platforms &gt; Browse All Independent Parties</div>

        <div class="layout">

          <!-- LEFT SIDEBAR -->
          <div class="col-left">
            <div class="module">
              <h2>Browse by Category</h2>
              <div class="module-body">
                <ul>
                  <li><a href="#">Environmental (12)</a></li>
                  <li><a href="#">Labor &amp; Economy (9)</a></li>
                  <li><a href="#">Civil Liberties (7)</a></li>
                  <li><a href="#">Rural &amp; Agriculture (5)</a></li>
                  <li><a href="#">Tech &amp; Innovation (4)</a></li>
                  <li><a href="#">Regional / Local (18)</a></li>
                </ul>
              </div>
            </div>

            <div class="module gold">
              <h2>By Region</h2>
              <div class="module-body">
                <ul>
                  <li><a href="#">Northeast</a></li>
                  <li><a href="#">Midwest</a></li>
                  <li><a href="#">South</a></li>
                  <li><a href="#">West</a></li>
                  <li><a href="#">National</a></li>
                </ul>
              </div>
            </div>

            <div class="module">
              <h2>Quick Links</h2>
              <div class="module-body">
                <ul>
                  <li><a href="#">How to register a party</a></li>
                  <li><a href="#">Ballot access guide</a></li>
                  <li><a href="#">Collaboration board</a></li>
                </ul>
              </div>
            </div>
          </div>

          <!-- CENTER -->
          <div class="col-center">

            <div class="stat-strip">
              <span>Active parties: <b>52</b></span>
              <span>Platforms updated this week: <b>14</b></span>
              <span>Voters engaged: <b>8,204</b></span>
            </div>

            <div class="module red">
              <h2>Featured Independent Parties</h2>
              <div class="module-body" style="padding:0;">

                <div class="party-row">
                  <div class="party-swatch" style="background:#2E7D32;"></div>
                  <div class="party-info">
                    <div class="name">New Horizon Coalition</div>
                    <div class="meta">Environmental &middot; National &middot; Founded 2019</div>
                    <div class="tagline">Community-first climate policy and local resource cooperatives.</div>
                    <a href="#">View platform</a> &nbsp;|&nbsp; <a href="#">Join discussion</a>
                  </div>
                </div>

                <div class="party-row">
                  <div class="party-swatch" style="background:#8E24AA;"></div>
                  <div class="party-info">
                    <div class="name">Union of Independent Workers</div>
                    <div class="meta">Labor &amp; Economy &middot; Midwest &middot; Founded 2021</div>
                    <div class="tagline">Cooperative ownership models and fair-wage advocacy.</div>
                    <a href="#">View platform</a> &nbsp;|&nbsp; <a href="#">Join discussion</a>
                  </div>
                </div>

                <div class="party-row">
                  <div class="party-swatch" style="background:#EF6C00;"></div>
                  <div class="party-info">
                    <div class="name">Open Ledger Party</div>
                    <div class="meta">Tech &amp; Innovation &middot; National &middot; Founded 2023</div>
                    <div class="tagline">Transparent governance tools and civic tech infrastructure.</div>
                    <a href="#">View platform</a> &nbsp;|&nbsp; <a href="#">Join discussion</a>
                  </div>
                </div>

              </div>
            </div>

            <div class="module">
              <h2>Recently Updated Platforms</h2>
              <div class="module-body">
                <ul>
                  <li><a href="#">Rural Renewal Alliance</a> updated their agriculture policy — 2 hours ago</li>
                  <li><a href="#">Civic Trust Party</a> added a new resource guide — yesterday</li>
                  <li><a href="#">Coastal Futures</a> posted an event: town hall, Sept 20 — 2 days ago</li>
                </ul>
              </div>
            </div>

            <div style="text-align:center; margin: 10px 0;">
              <a href="#" class="btn primary">+ Start a New Party Page</a>
              <a href="#" class="btn">Browse All 52 Parties &raquo;</a>
            </div>

          </div>

          <!-- RIGHT SIDEBAR -->
          <div class="col-right">
            <div class="module">
              <h2>Resources</h2>
              <div class="module-body">
                <ul>
                  <li><a href="#">Petition builder</a></li>
                  <li><a href="#">Volunteer matcher</a></li>
                  <li><a href="#">Fundraising toolkit</a></li>
                  <li><a href="#">Media kit templates</a></li>
                </ul>
              </div>
            </div>

            <div class="module gold">
              <h2>Collaboration Board</h2>
              <div class="module-body">
                <p style="margin-top:0;">Parties looking for partners on shared initiatives:</p>
                <ul>
                  <li><a href="#">Water rights coalition (3 parties)</a></li>
                  <li><a href="#">Small business relief (5 parties)</a></li>
                </ul>
              </div>
            </div>

            <div class="ticker">
              <b>Ticker:</b> New voter guide published &middot; 3 parties merged resources this week &middot; Registration deadline in 12 days
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
