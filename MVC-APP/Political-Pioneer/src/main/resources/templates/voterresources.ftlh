<!-- 
 CSC 490: The Political Pioneer
 home.ftlh 
 Last updated: October 5, 2026

 Description: This is the main file for The Political Pioneer Web App, this will contain the
 main structure including navigation, user information and login on the left side of the page, and 
 the dashboard and main content of the app on the bottom right quadrant of the page.
-->

<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8">
  <title>The Political Pioneer — User Homepage</title>
  <!-- <link rel="stylesheet" href="styles.css"> -->
  <!-- <link rel="stylesheet" type="text/css" href="/"> -->
  <link rel="stylesheet" type="text/css" href="/css/styles.css">

  <!-- <script src="script.js" defer></script> -->
</head>

<body class="page-dashboard page-portal">

  <!-- Creates a grid class to ensure we can split quadrants for logo, navigation, and the portal -->
  <div class="grid">

    <!-- Top Left Quadrant: Logo Implementation -->
    <div class="quadrant q-logo">
      <a href="/home">
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
           
         
          

            
            <!--<a href="#">Collaborate</a> might remove as it could be merged with /account or /events-->
    
            <!--<a href="#">Voter Tools</a> may remove as it can be merged with resources-->

             <#if Session.SPRING_SECURITY_CONTEXT??>
              <a href="/account">My Account</a>
            </#if>
              <a href="/resources" class="active">Resources</a>
               <a href="/platform">Platforms</a>
            
          


           
            <#if isUser!false>
               <a href="/search">Find Parties</a>
            </#if>

         
          
           
            <a href="/events">Events</a>


            <#if isOwner!false>
               <a href="/partyManage">Manage Party</a>
            </#if>
           

         


            <#if isAdmin!false>
              <a href="/admin">Admin Dashboard</a>
              <a href="/createAdmin">Create Admin</a>
            </#if>
            <#if isOwner!false>
              <a href="/createParty">Create Party</a>
              
            </#if>

            <#if isAdmin!false>
              <a href="/admin">Admin Dashboard</a>
              <a href="/createAdmin">Create Admin</a>
            </#if>


          </nav>
          <div class="search">
            <input type="text" placeholder="Search parties, platforms, issues...">
            <button>Search</button>
          </div>
        </div>

        <!-- Provies a way to navigate within the portal -->
        <div class="breadcrumb">Home &gt; Directory</div>

        <!-- Ensures that the portal is formatted in a layout where you can read within 3 column blocks -->
        <div class="layout">

          <!-- Left Sidebar -->
          <div class="col-left">
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

          <!-- Center Bar -->
          <div class="col-center">

            <div class="module red voter-tools-module">
              <h2>Voter Tools</h2>
              <div class="module-body">
                <p class="voter-tools-intro">Choose a voter service to get started:</p>
                <div class="voter-tools-grid">
                  <a class="voter-tool-card" href="/resources/voter-lookup" aria-label="Voter lookup API link">
                    <span class="voter-tool-icon" aria-hidden="true">&#128269;</span>
                    <span class="voter-tool-title">Look Up Voter Information</span>
                    <span class="voter-tool-description">Check voter registration or related voter information.</span>
                    <span class="voter-tool-action">Go to voter lookup &raquo;</span>
                  </a>
                  <a class="voter-tool-card" href="/resources/voter-registration" aria-label="Voter registration API link">
                    <span class="voter-tool-icon" aria-hidden="true">&#9998;</span>
                    <span class="voter-tool-title">Register to Vote</span>
                    <span class="voter-tool-description">Open the voter registration service.</span>
                    <span class="voter-tool-action">Go to registration &raquo;</span>
                  </a>
                  <a class="voter-tool-card" href="/resources/polling-places" aria-label="Polling place lookup API link">
                    <span class="voter-tool-icon" aria-hidden="true">&#9873;</span>
                    <span class="voter-tool-title">Find a Polling Place</span>
                    <span class="voter-tool-description">Find where to vote using your location or address.</span>
                    <span class="voter-tool-action">Go to polling place lookup &raquo;</span>
                  </a>
                  <a class="voter-tool-card" href="/questionnaire">
                    <span class="voter-tool-icon" aria-hidden="true">&#x1f4dd;</span>
                    <span class="voter-tool-title">Take The Political Questionnaire</span>
                    <span class="voter-tool-description">Want to know what you what you belive? Take our original questionnaire!</span>
                    <span class="voter-tool-action">Go to Questionnaire &raquo;</span>
                  </a>
                </div>
              </div>
            </div>

          </div>

          <!-- Right Sidebar -->
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
              <b>Ticker:</b> New voter guide published &middot; 3 parties merged resources this week
              &middot; Registration deadline in 12 days
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