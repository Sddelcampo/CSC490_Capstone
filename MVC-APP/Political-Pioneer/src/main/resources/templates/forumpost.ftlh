<#ftl output_format="HTML" auto_esc=true>
<!--
 CSC 490: The Political Pioneer
 forumpost.ftlh
 Last updated: November 9, 2026

 Description: A single forum post with its comments and a form to add a comment.

 MODEL EXPECTED FROM THE CONTROLLER (GET /forum/post/{id}):
   post     : {
                id         : long
                partyId    : long
                partyName  : string
                title      : string
                body       : string
                authorName : string
                createdAt  : string   (already formatted for display)
              }
   comments : list of {               (oldest first; may be empty)
                id         : long
                authorName : string
                body       : string
                createdAt  : string   (already formatted for display)
              }
   error    : string  (OPTIONAL flash message, e.g. an empty comment)
   _csrf    : (OPTIONAL) Spring Security CsrfToken, only if CSRF protection is enabled

 FORM THIS PAGE SUBMITS:
   POST /forum/post/{id}/comment   field: body   (+ CSRF token field if enabled)

 WHERE THE DATA COMES FROM (Neon tables):
   post     <- forum_post (column_id -> id, party -> partyId / partyName, title, content -> body,
               user -> authorName, created_time -> createdAt)
   comments <- comment (commment_id -> id, content -> body, user -> authorName,
               created_date -> createdAt) where comment.post = this post

 NOTE: post and comment bodies are printed inside a single line of markup on purpose, because the
 .post-body class uses white-space: pre-wrap to keep the author's line breaks (any template
 indentation inside the tag would show up on screen).
-->

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>The Political Pioneer — ${post.title}</title>
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

        <div class="breadcrumb">
          <a href="/forum">Forum</a> &gt;
          <a href="/forum?party=${post.partyId?c}">${post.partyName}</a> &gt; Post
        </div>

        <div class="admin-content">

          <!-- The post itself -->
          <div class="module red">
            <h2>${post.title}</h2>
            <div class="module-body">
              <div class="forum-meta">
                Posted by <strong>${post.authorName}</strong> in ${post.partyName} &middot; ${post.createdAt}
              </div>
              <p class="post-body">${post.body}</p>
              <#if canEditPost!false>
                <details id="edit-post" class="post-edit"<#if editError?? || editPostOpen!false> open</#if>>
                  <summary>Edit your forum post</summary>
                  <div class="post-edit-body">
                    <#if editError??>
                      <p class="flash-error" role="alert">${editError}</p>
                    </#if>
                    <form action="/forum/post/${post.id?c}/edit" method="post" class="post-edit-form">
                      <label for="post-title">Title</label>
                      <input type="text" id="post-title" name="title"
                             value="${editTitle!post.title}" maxlength="255" required>
                      <label for="post-content">Post</label>
                      <textarea id="post-content" name="content" rows="8"
                                maxlength="10000" required>${editContent!post.body}</textarea>
                      <input type="hidden" name="${(_csrf.parameterName)!'_csrf'}" value="${(_csrf.token)!''}">
                      <button type="submit" class="btn primary">Save Changes</button>
                    </form>
                  </div>
                </details>
              </#if>
              <p style="margin-bottom:0;">
                <a class="btn" href="/forum?party=${post.partyId?c}">&laquo; Back to ${post.partyName} Forum</a>
              </p>
            </div>
          </div>

          <!-- Comments -->
          <section class="module">
            <h2>Comments (${comments?size})</h2>
            <div class="module-body">
              <#if error??>
                <p class="flash-error" role="alert">${error}</p>
              </#if>
              <#if commentPosted??>
                <p class="comment-success" role="status">Your comment was posted.</p>
              </#if>

              <details class="comment-form-collapse"<#if error??> open</#if>>
                <summary>Add a comment</summary>
                <div class="comment-form-body">
                  <form action="/forum/post/${post.id?c}/comment" method="post" class="forum-form">
                    <input type="hidden" name="${(_csrf.parameterName)!'_csrf'}" value="${(_csrf.token)!''}">
                    <div class="field">
                      <label for="comment-body">Comment</label>
                      <textarea id="comment-body" name="body" rows="4" maxlength="2000"
                                required>${commentBody!''}</textarea>
                    </div>
                    <button type="submit" class="btn primary">Post Comment</button>
                  </form>
                </div>
              </details>

              <div class="forum-comments">
                <#list comments as c>
                  <div class="forum-comment">
                    <div class="forum-meta"><strong>${c.authorName}</strong> &middot; ${c.createdAt}</div>
                    <p class="post-body">${c.body}</p>
                  </div>
                <#else>
                  <p class="forum-empty">No comments yet. Add the first one above.</p>
                </#list>
              </div>
            </div>
          </section>

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
