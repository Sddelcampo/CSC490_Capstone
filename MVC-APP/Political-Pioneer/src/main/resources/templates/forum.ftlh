<#ftl output_format="HTML" auto_esc=true>
<!--
 CSC 490: The Political Pioneer
 forum.ftlh
 Last updated: November 9, 2026

  Description: Lists visible forum posts from parties the signed-in user belongs to or owns.
  Owners can create posts and edit their own.

  MODEL EXPECTED FROM THE CONTROLLER (GET /forum):
    isOwner       : boolean
    canCreatePost  : boolean (only when the owner has a party)
    ownerPartyName : string (when canCreatePost is true)
    postCount      : int
    posts          : list of {                                 (visible associated-party posts, newest first)
                     id           : long
                      partyId      : long
                      partyName    : string
                     title        : string
                     excerpt      : string                    (first ~200 characters of the post body)
                     authorName   : string
                     createdAt    : string                    (already formatted for display, e.g. "Nov 9, 2026 3:15 PM")
                     commentCount : int
                      canEdit      : boolean (owner-authors only)
                   }
   error         : string  (OPTIONAL flash message, e.g. validation failure after a new-post submit)
   _csrf         : (OPTIONAL) Spring Security CsrfToken, only if CSRF protection is enabled

 FORM THIS PAGE SUBMITS:
    POST /forum/post   fields: title, content   (+ CSRF token field if enabled)

 WHERE THE DATA COMES FROM (Neon tables):
    posts          <- visible forum_post rows for associated parties, with party name, author, and comment count

 NOTE: FreeMarker comment (not HTML comment) on purpose, so nothing in here gets evaluated.
-->

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>The Political Pioneer — Party Forum</title>
  <link rel="stylesheet" type="text/css" href="/css/styles.css">
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
            <#if Session.SPRING_SECURITY_CONTEXT??>
              <p class="welcome-text">Welcome, ${name !"Guest!"}</p>
            <#else>
              <p class="welcome-text">Welcome, Guest!</p>
            </#if>
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
            <#if isOwner!false><a href="/createFP">Create Forum Post</a></#if>
         </nav>
          <div class="search">
            <input type="text" placeholder="Search parties, platforms, issues...">
            <button>Search</button>
          </div>
        </div>

        <div class="breadcrumb">Forum &gt; Your party posts</div>

        <div class="admin-content">
          <div class="module red">
            <h2>Your Party Forum Posts</h2>
            <div class="module-body">
              <p class="forum-intro">
                <#if isOwner!false>
                  Browse visible conversations from your owned party.
                <#else>
                  Browse visible conversations from parties you belong to.
                </#if>
                Open a post to read the full message and comments.
              </p>

              <#if error??>
                <p class="flash-error" role="alert">${error}</p>
              </#if>

              <#if isOwner!false>
                <#if canCreatePost!false>
                  <details class="new-post"<#if error??> open</#if>>
                    <summary class="btn primary">+ New Post for ${ownerPartyName}</summary>
                    <form action="/forum/post" method="post" class="forum-form">
                      <input type="hidden" name="${(_csrf.parameterName)!'_csrf'}" value="${(_csrf.token)!''}">

                      <div class="field">
                        <label for="post-title">Title</label>
                        <input type="text" id="post-title" name="title" maxlength="150" required>
                      </div>

                      <div class="field">
                        <label for="post-body">Message</label>
                        <textarea id="post-body" name="content" rows="6" maxlength="5000" required></textarea>
                      </div>

                      <button type="submit" class="btn primary">Post</button>
                    </form>
                  </details>
                <#else>
                  <p class="forum-empty">
                    Create a party before posting to its forum.
                    <a href="/createParty">Create a party</a>
                  </p>
                </#if>
              </#if>

              <p class="forum-meta">${postCount} visible post<#if postCount != 1>s</#if></p>
              <#list partyGroups as partyGroup>
                <section class="forum-party-group">
                  <h3 class="forum-party-heading">
                    <a href="/partyDetail/${partyGroup.partyId?c}">${partyGroup.partyName}</a>
                  </h3>
                  <#list partyGroup.posts as post>
                    <article class="forum-post">
                      <h3><a href="/forum/post/${post.id?c}">${post.title}</a></h3>
                      <div class="forum-meta">
                        by <strong>${post.authorName}</strong>
                        &middot; ${post.createdAt} &middot;
                        ${post.commentCount} comment<#if post.commentCount != 1>s</#if>
                      </div>
                      <p class="forum-excerpt">${post.excerpt}</p>
                      <#if post.canEdit!false>
                        <a class="btn" href="/forum/post/${post.id?c}?edit=true#edit-post">Edit Post</a>
                      </#if>
                    </article>
                  </#list>
                </section>
              <#else>
                <p class="forum-empty">No visible forum posts are available from your associated parties.</p>
              </#list>
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
