<#ftl output_format="HTML" auto_esc=true>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Party Info | TP2 Portal</title>
    <link rel="stylesheet" type="text/css" href="/css/stylesll.css">
</head>

<body>
    <div class="page">
        <!-- ========== LEFT SIDEBAR (same on every page) ========== -->
        <aside class="sidebar">
            <div class="logo-box">
                <img src="/images/deSebastian_TPP_Logo.png" alt="The Political Pioneer" />
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
                    <a href="/account">My Account</a>
                    <a href="/platforms">Platforms</a>
                    <a href="/parties" class="active">Find Parties</a>
                    <a href="/resources">Resources</a>
                    <a href="/events">Events</a>
                    <#if isOwner!false><a href="/createFP">Create Forum Post</a></#if>
                </nav>

                <form class="search-form" action="/search" method="get">
                    <input type="text" name="q" placeholder="Search parties, platforms, events" />
                    <button type="submit">Search</button>
                </form>
            </header>

            <div class="breadcrumb">Home &gt; Find Parties &gt; Green Valley Civic Alliance</div>

            <!-- ========== ONE COLUMN (no side columns on this page) ========== -->
            <div class="content">
                <main class="center-column">
                    <!-- Counts -->
                    <div class="stats-bar">
                        <span>Members: <strong>34</strong></span>
                        <span>Forum posts:
                            <strong><#if canViewForumPosts!false>${forumPostCount!0}<#else>Members only</#if></strong>
                        </span>
                        <span>Announcements: <strong>${announcementCount!0}</strong></span>
                    </div>

                    <!-- Party info + join -->
                    <section class="box">
                        <h1 class="box-header">Party Info</h1>

                        <div class="party-header">
                            <div class="color-square-large"></div>
                            <div>



                                <p class="party-name-large">${party.partyName!"No Name"}</p>
                                <p class="meta-text">Status: ${party.status!"N/A"} · Party owner: ${lastName!"N/A"}</p>
                                <p class="party-description">
                                    ${party.description!"No description yet."}
                                </p>
                            </div>
                        </div>


                        <div class="action-bar">
                            <#if canJoinParty!false>
                                <form action="/joinParty/${party.partyId?c}" method="post">
                                    <button type="submit" class="red-button">Join This Party</button>
                                </form>
                                <span class="action-note">Feel free to be part of our movement!</span>
                            <#elseif isMember!false>
                                <span class="member-note">You are already a member of this party.</span>
                            </#if>
                            <a href="/search" class="back-link">« Back to Find Parties</a>
                        </div>

                       
              <!-- <div class="action-bar">
                <span class="request-note"><strong>Request sent.</strong> Waiting for the party owner to approve it.</span>
                <form action="/parties/cancel-request" method="post">
                  <input type="hidden" name="partyId" value="1" />
                  <button type="submit" class="gray-button">Cancel Request</button>
                </form>
                <a href="/parties" class="back-link">« Back to Find Parties</a>
              </div> -->
              

                        <!-- 3. Already a member
              <div class="action-bar">
                <span class="member-note">You are a member of this party.</span>
                <form action="/parties/leave" method="post">
                  <input type="hidden" name="partyId" value="1" />
                  <button type="submit" class="gray-button">Leave Party</button>
                </form>
                <a href="/parties" class="back-link">« Back to Find Parties</a>
              </div>
              -->
                    </section>


                    <section class="box">
                        <h2 class="box-header">Announcements</h2>

                        <#list announcements as announcement>
                            <div class="announcement">
                                <p class="party-title">${announcement.title!"Untitled"}</p>
                                <p class="meta-text">Published ${announcement.createdAt!""}</p>
                                <p class="summary-text">${announcement.description!""}</p>
                            </div>
                        <#else>
                            <p class="party-empty-state">No announcements have been published yet.</p>
                        </#list>
                    </section>

                    <!-- Recent forum posts -->
                    <section class="box">
                        <h2 class="box-header">Recent Forum Posts</h2>
                        <#if canViewForumPosts!false>
                            <#if forumPosts?has_content>
                                <ul class="box-list">
                                    <#list forumPosts as post>
                                        <li>
                                            <a href="/forum/post/${post.id?c}">${post.title}</a>
                                            — ${post.author}, ${post.createdAt}
                                        </li>
                                    </#list>
                                </ul>
                                <#if forumPostCount gt forumPosts?size>
                                    <div class="box-footer">
                                        <span class="muted">Showing ${forumPosts?size} of ${forumPostCount}</span>
                                        <a href="/forum">View all forum posts »</a>
                                    </div>
                                </#if>
                            <#else>
                                <p class="party-empty-state">This party has not made any forum posts yet.</p>
                            </#if>
                        <#else>
                            <p class="party-empty-state">Join this party to view its forum posts.</p>
                        </#if>
                    </section>
                </main>
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