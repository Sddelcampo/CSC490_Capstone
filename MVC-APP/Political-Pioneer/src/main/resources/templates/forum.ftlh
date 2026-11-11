<!--
 CSC 490: The Political Pioneer
 forum.ftlh
 Last updated: November 9, 2026

 Description: Party forum. Shows the parties the logged-in user belongs to as tabs, and the posts for
 the selected party. Users can start a new post from here and open a post to read/add comments
 (forumpost.ftlh).

 MODEL EXPECTED FROM THE CONTROLLER (GET /forum?party=ID):
   parties       : list of { id: long, name: string }        (only parties the user is a member of; may be empty)
   selectedParty : { id: long, name: string }                (ABSENT when the user has no parties)
   posts         : list of {                                  (newest first; ABSENT when no parties)
                     id           : long
                     title        : string
                     excerpt      : string                    (first ~200 characters of the post body)
                     authorName   : string
                     createdAt    : string                    (already formatted for display, e.g. "Nov 9, 2026 3:15 PM")
                     commentCount : int
                   }
   error         : string  (OPTIONAL flash message, e.g. validation failure after a new-post submit)
   _csrf         : (OPTIONAL) Spring Security CsrfToken, only if CSRF protection is enabled

 FORM THIS PAGE SUBMITS:
   POST /forum/post   fields: partyId, title, body   (+ CSRF token field if enabled)

 WHERE THE DATA COMES FROM (Neon tables):
   parties        <- party_member (party, user, status) joined to the party table for the name
   posts          <- forum_post (column_id -> id, title, content -> excerpt, user -> authorName,
                     created_time -> createdAt) + a count of comment rows per post

 NOTE: FreeMarker comment (not HTML comment) on purpose, so nothing in here gets evaluated.
-->

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>The Political Pioneer — Party Forum</title>
  <link rel="stylesheet" type="text/css" href="/css/styles.css">
  <script src="/js/forum.js" defer></script>
</head>
<body class="page-dashboard page-portal">

  <!-- Creates a grid class to ensure we can split quadrants for logo, navigation, and the portal -->
  <div class="grid">

    <!-- Top Left Quadrant: Logo Implementation -->
    <div class="quadrant q-logo">
      <a href="/">
        <!-- The Political Pioneer Logo: Created by Sebastian Del Campo -->
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
            <p class="welcome-text">Welcome, User!</p>
            <div class="account-links">
              <a href="/acctlogin">Log Out</a>
              <a href="/account">My Account</a>
            </div>
          </div>
        </div>
        <nav>
          <a href="/home">Home</a>
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
            <a href="/home">Home</a>
            <a href="/account">My Account</a>
            <a href="/platform">Platforms</a>
            <a href="/search">Find Parties</a>
            <a href="/forum">Forum</a>
            <a href="/resources">Resources</a>
            <a href="/events">Events</a>
         </nav>
          <div class="search">
            <input type="text" placeholder="Search parties, platforms, issues...">
            <button>Search</button>
          </div>
        </div>

        <div class="breadcrumb">Forum<#if selectedParty??> &gt; ${selectedParty.name}</#if></div>

        <div class="admin-content">

          <#if !(parties?has_content)>

            <!-- The user is not in any party yet -->
            <div class="module red">
              <h2>Party Forum</h2>
              <div class="module-body forum-empty">
                <p style="margin-top:0;">You are not a member of any party yet. Join a party to read and
                  post in its forum.</p>
                <a class="btn primary" href="/search">Find Parties</a>
              </div>
            </div>

          <#else>

            <!-- One tab per party the user belongs to -->
            <ul class="forum-tabs">
              <#list parties as p>
                <li<#if p.id == selectedParty.id> class="current"</#if>>
                  <a href="/forum?party=${p.id?c}">${p.name}</a>
                </li>
              </#list>
            </ul>

            <div class="module red">
              <h2>${selectedParty.name} Forum</h2>
              <div class="module-body">

                <#if error??>
                  <p class="flash-error" role="alert">${error}</p>
                </#if>

                <!-- <details> gives us open/close with no JavaScript -->
                <details class="new-post"<#if error??> open</#if>>
                  <summary class="btn primary">+ New Post</summary>
                  <form action="/forum/post" method="post" class="forum-form">
                    <input type="hidden" name="${(_csrf.parameterName)!'_csrf'}" value="${(_csrf.token)!''}">
                    <input type="hidden" name="partyId" value="${selectedParty.id?c}">

                    <div class="field">
                      <label for="post-title">Title</label>
                      <input type="text" id="post-title" name="title" maxlength="150" required>
                    </div>

                    <div class="field">
                      <label for="post-body">Message</label>
                      <textarea id="post-body" name="body" rows="6" maxlength="5000"
                                data-maxlength="5000" required></textarea>
                      <span class="char-count"></span>
                    </div>

                    <button type="submit" class="btn primary">Post</button>
                  </form>
                </details>

                <#list posts as post>
                  <div class="forum-post">
                    <h3><a href="/forum/post/${post.id?c}">${post.title}</a></h3>
                    <div class="forum-meta">
                      by <strong>${post.authorName}</strong> &middot; ${post.createdAt} &middot;
                      ${post.commentCount} comment<#if post.commentCount != 1>s</#if>
                    </div>
                    <p class="forum-excerpt">${post.excerpt}</p>
                  </div>
                <#else>
                  <p class="forum-empty">No posts yet. Be the first to start a conversation.</p>
                </#list>

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
