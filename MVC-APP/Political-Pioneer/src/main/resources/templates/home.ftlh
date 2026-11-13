<#--
 CSC 490: The Political Pioneer
 home.ftlh
 Last updated: November 12, 2026

 Description: Landing page. Content changes depending on who is viewing it:
   - Guest (not logged in): description of the app + log in / sign up buttons
   - Logged-in user WITH a party: greeting, short description, and a party summary
   - Logged-in user WITHOUT a party: greeting, short description, and options to take the
     questionnaire (/questionnaire) or search for a party (/search)

 MODEL EXPECTED FROM THE CONTROLLER:
   loggedIn        : boolean (true only for a real authenticated user, never anonymous)
   party           : Party (optional, only present when the logged-in user belongs to a party)
   name            : string (optional, added by AuthenticatedNameModel)
   isAdmin / isOwner / isUser / canCreateParty : booleans (added by addNavigationRoleAttributes)

 NOTE: FreeMarker comment (not an HTML comment) on purpose, so ${...} and <#...> are never parsed.-->

<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8">
  <title>The Political Pioneer — Home</title>
  <link rel="stylesheet" type="text/css" href="/css/styles.css">
</head>

<body class="page-dashboard page-portal">

  <div class="grid">

    <!-- Top Left Quadrant: Logo Implementation -->
    <div class="quadrant q-logo">
      <a href="/home">
        <#-- The Political Pioneer Logo: Created by Sebastian Del Campo -->
        <img src="/images/deSebastian_TPP_Logo.png" alt="The Political Pioneer Logo">
      </a>
    </div>

    <!-- Bottom Left Quadrant: Account, general info, & site navigation links -->
    <div class="quadrant q-side">
      <h2>Account &amp; Site Info</h2>
      <div class="body">
        <div class="account-block">
          <div class="avatar-circle">&#128100;</div>
          <div>
            <#if Session.SPRING_SECURITY_CONTEXT??>
              <p class="welcome-text">Welcome, ${name!"User"}</p>
            <#else>
              <p class="welcome-text">Welcome, Guest!</p>
            </#if>
            <div class="account-links">
              <#if Session.SPRING_SECURITY_CONTEXT??>
                <a href="/logout">Log Out</a>
                <a href="/account">My Account</a>
              <#else>
                <a href="/acctlogin">Log In</a>
                <a href="/signup">Sign Up</a>
              </#if>
            </div>
          </div>
        </div>
        <nav>
          <a href="/home" class="active">Home</a>
          <a href="/aboutus">About Us</a>
          <a href="/guide">Guide</a>
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

    <!-- Right Quadrant: The Political Pioneer Portal (TP2 Portal) -->
    <div class="quadrant q-portal">
      <div class="body">

        <div class="mastbar">
          <div class="logo">TP2 <span>Portal</span></div>
          <nav class="tabs">
            <a href="/home" class="active">Home</a>

            <#if Session.SPRING_SECURITY_CONTEXT??>
              <a href="/account">My Account</a>
            </#if>

            <#if isUser!false>
              <a href="/search">Find Parties</a>
            </#if>

            <a href="/resources">Resources</a>
            <a href="/events">Events</a>

            <#if isOwner!false>
              <a href="/createFP">Create Forum Post</a>
              <a href="/partyManage">Manage Party</a>
            </#if>

            <#if isAdmin!false>
              <a href="/admin">Admin Dashboard</a>
              <a href="/createAdmin">Create Admin</a>
            </#if>

            <#if canCreateParty!false>
              <a href="/createParty">Create Party</a>
            </#if>
          </nav>
        </div>

        <div class="breadcrumb">Home</div>

        <div class="content-area">

          <#if !Session.SPRING_SECURITY_CONTEXT??>
            <!-- ================= GUEST VIEW ================= -->

            <div class="module red">
              <h2>Welcome to The Political Pioneer</h2>
              <div class="module-body">
                <p style="margin-top:0;">The Political Pioneer is an independent, nonpartisan platform
                  built to help third-party and independent political organizations organize, publish
                  their platforms, and connect with voters who are looking for alternatives beyond the
                  two major parties.</p>
                <p>Independent parties often have the hardest time getting their message in front of
                  voters, despite having real platforms and real communities behind them. The Political
                  Pioneer gives any registered independent party a home — a directory listing, a space
                  to publish their platform, and tools to organize their base.</p>
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
              <h2>Get Started</h2>
              <div class="module-body">
                <p style="margin-top:0;">Create a free account to take the political questionnaire,
                  find parties that line up with your views, and join the conversation.</p>
                <div style="text-align:center; margin: 10px 0;">
                  <a href="/signup" class="btn primary">Sign Up</a>
                  <a href="/acctlogin" class="btn">Log In</a>
                </div>
                <p style="text-align:center; margin-bottom:0;">
                  Want to know more? <a href="/aboutus">Read About Us</a> or
                  <a href="/guide">view the Guide</a>.
                </p>
              </div>
            </div>

          <#else>
            <!-- ================= LOGGED-IN VIEW ================= -->

            <div class="module red">
              <h2>Welcome back, ${name!"User"}!</h2>
              <div class="module-body">
                <p style="margin-top:0;">The Political Pioneer is an independent, nonpartisan platform
                  that helps voters discover third-party and independent organizations, and helps those
                  organizations publish their platforms and connect with their communities.</p>
              </div>
            </div>

            <#if party??>
              <!-- ---- User belongs to a party ---- -->
              <div class="module gold">
                <h2>Your Party</h2>
                <div class="module-body" style="padding:0;">
                  <div class="party-row">
                    <div class="party-info">
                      <div class="name">${party.name!"Your Party"}</div>
                      <#if (party.description)?has_content>
                        <div class="tagline">${party.description}</div>
                      </#if>
                      <#if isOwner!false>
                        <a href="/partyManage">Manage party</a> &nbsp;|&nbsp;
                        <a href="/createFP">Create forum post</a>
                      <#else>
                        <a href="/events">Party events</a> &nbsp;|&nbsp;
                        <a href="/search">Explore other parties</a>
                      </#if>
                    </div>
                  </div>
                </div>

            <#else>
              <!-- ---- User does NOT belong to a party yet ---- -->
              <div class="module gold">
                <h2>You're not part of a party yet</h2>
                <div class="module-body">
                  <p style="margin-top:0;">Not sure where you fit? Here are two ways to get started:</p>
                  <ul>
                    <li>
                      <a href="/questionnaire"><b>Take the Political Questionnaire</b></a>
                      — answer a few statements and find your alignment.
                    </li>
                    <li>
                      <a href="/search"><b>Search for a Party</b></a>
                      — browse parties by alignment or by location.
                    </li>
                    <#if canCreateParty!false>
                      <li>
                        <a href="/createParty"><b>Start your own Party</b></a>
                        — create a page for your organization.
                      </li>
                    </#if>
                  </ul>
                  <div style="text-align:center; margin: 10px 0;">
                    <a href="/questionnaire" class="btn primary">Take the Questionnaire</a>
                    <a href="/search" class="btn">Find a Party &raquo;</a>
                  </div>
                </div>
              </div>
            </#if>

            <div class="module">
              <h2>Quick Links</h2>
              <div class="module-body">
                <ul>
                  <li><a href="/resources">Voter Resources</a></li>
                  <li><a href="/events">Upcoming Events</a></li>
                  <li><a href="/guide">Site Navigation Guide</a></li>
                </ul>
              </div>
            </div>

          </#if>

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
