<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>New Forum Post | TP2 Portal</title>
    <link rel="stylesheet" type="text/css" href="/css/stylesll.css">
  </head>
  <body>
    <div class="page">
      <!-- ========== LEFT SIDEBAR (same on every page) ========== -->
      <aside class="sidebar">
        <div class="logo-box">
          <img src="images/deSebastian_TPP_Logo.png" alt="The Political Pioneer" />
        </div>

        <div class="account-box">
          <h2 class="box-header">Account &amp; Site Info</h2>

          <div class="user-row">
            <img class="user-avatar" src="images/user-avatar.png" alt="" />
            <div>
              <p class="welcome-text">Welcome, User!</p>
              <div class="user-links">
                <a href="/logout">Log Out</a>
                <a href="/account">My Account</a>
              </div>
            </div>
          </div>

          <nav class="side-nav">
            <a href="/">Home</a>
            <a href="/about">About Us</a>
            <a href="/guide">Guide</a>
            <a href="/report">Report a Concern</a>
          </nav>

          <div class="side-about">
            <p>
              The Political Pioneer is an independent, nonpartisan platform for
              third-party organizing and voter engagement.
            </p>
            <ul>
              <li><a href="/bug">Report a Bug</a> — something broken? tell us</li>
              <li>
                <a href="/contact">Contact the Dev Team</a> — feedback &amp;
                feature requests
              </li>
            </ul>
          </div>
        </div>
      </aside>

      <!-- ========== MAIN AREA ========== -->
      <div class="main">
        <!-- Navbar (same on every page) -->
        <header class="navbar">
          <div class="brand">TP2 <span>Portal</span></div>

          <nav class="nav-links">
            <a href="/">Home</a>
            <a href="/account" >My Account</a>
            <a href="/platforms">Platforms</a>
            <a href="/parties">Find Parties</a>
            <a href="/resources">Resources</a>
            <a href="/events">Events</a>
            <#if isOwner!false><a href="/createFP" class="active">Create Forum Post</a></#if>
          </nav>

          
        </header>

        <div class="breadcrumb">
          My Account &gt; My Parties &gt; Riverbend Neighbors Party &gt; New Forum Post
        </div>

        <!-- ========== THE THREE COLUMNS ========== -->
        <div class="content">
          <!-- Left column -->
          <div class="left-column">
            <section class="box">
              <h2 class="box-header">My Parties</h2>
              <ul class="box-list">
                <li class="current-item">Riverbend Neighbors Party</li>
                <li><a href="#">Harborfront Progress Coalition</a></li>
              </ul>
              <p class="box-link"><a href="/parties/create">Start a new party page »</a></p>
            </section>

            <section class="box">
              <h2 class="box-header">Party Tools</h2>
              <ul class="box-list">
                <li><a href="#">Control panel</a></li>
                <li class="current-item">New forum post</li>
                <li><a href="#">Publish announcement</a></li>
                <li><a href="#">Edit party details</a></li>
                <li><a href="#">View public page</a></li>
              </ul>
            </section>
          </div>

          <!-- Center column: the form -->
          <main class="center-column">
            <section class="box">
              <h1 class="box-header">New Forum Post</h1>

              <form id="create-forum-post-form" action="/forum/post" method="post">
                <p class="form-intro">
                  Start a discussion in your party's forum. Fields marked
                  <span class="required">*</span> are required.
                </p>

                <!-- Which party this post belongs to (party column) -->
                <!-- Posting in (not an input: the server uses the party and the logged-in user) -->
                <div class="form-row">
                  <p class="row-label">Posting in</p>
                  <div class="form-field">
                    <p>${partyName} forum, as <strong>${authorName}</strong></p>
                  </div>
                </div>

                <!-- Title -->
                <div class="form-row">
                  <label for="post-title">
                    Title <span class="required">*</span>
                  </label>
                  <div class="form-field">
                    <input type="text" id="post-title" name="title" maxlength="255" required />
                    <p class="hint">Up to 255 characters.</p>
                  </div>
                </div>

                <!-- Content -->
                <div class="form-row">
                  <label for="post-content">
                    Content <span class="required">*</span>
                  </label>
                  <div class="form-field">
                    <textarea id="post-content" name="content" maxlength="255" required></textarea>
                    <p class="hint">
                      What would you like to tell or ask your members? Up to 255
                      characters.
                    </p>
                  </div>
                </div>

                <div class="form-row">
                  <p class="row-label">Visibility</p>
                  <div class="form-field">
                    <p>Active — this post will be visible to everyone viewing the forum.</p>
                  </div>
                </div>

                <div class="form-buttons">
                  <button type="submit" class="red-button">Post to Forum</button>
                  <a href="/partyManage" class="gray-button">Cancel</a>
                </div>
              </form>
            </section>

            <!-- Recent posts -->
            <section class="box">
              <h2 class="box-header">Recent Posts in This Forum</h2>
              <ul class="box-list">
                <#list recentPosts as post>
                  <li><a href="/forum/post/${post.id?c}">${post.title}</a> — ${post.authorName}, ${post.createdAt}</li>
                <#else>
                  <li>No posts yet.</li>
                </#list>
              </ul>
              <p class="box-link"><a href="/partyManage">« Back to the control panel</a></p>
            </section>
          </main>

          <!-- Right column -->
          <div class="right-column">
          </div>
        </div>

        <footer class="footer">
          The Political Pioneer — a home for independent political organizing ·
          <a href="/about">About Us</a> · <a href="/terms">Terms</a> ·
          <a href="/contact">Contact</a>
        </footer>
      </div>
    </div>
  </body>
</html>