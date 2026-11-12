<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Party Control Panel | TP2 Portal</title>
    <link rel="stylesheet" type="text/css" href="/css/stylesll.css">
</head>

<body>
    <div class="page">

        <aside class="sidebar">
            <div class="logo-box">
                <img src="images/deSebastian_TPP_Logo.png" alt="The Political Pioneer" />
            </div>

            <div class="account-box">
                <h2 class="box-header">Account &amp; Site Info</h2>

                <div class="user-row">
                    <img class="user-avatar" src="/images/user-avatar.png" alt="" />
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


        <div class="main">

            <header class="navbar">
                <div class="brand">TP2 <span>Portal</span></div>

                <nav class="nav-links">
                     <a href="/home">Home</a>
                      <#if Session.SPRING_SECURITY_CONTEXT??>
             <a href="/account">My Account</a>
            </#if>

                     <#if isUser!false>
               <a href="/parties">Find Parties</a>
            </#if>
              <a href="/resources">Resources</a>
                <a href="/events">Events</a>
                <#if isOwner!false><a href="/createFP">Create Forum Post</a></#if>
                <#if isOwner!false>
                     <a href="/partyManage" class="active">Manage Party</a>
            </#if>


            <#if isOwner!false>
              <a href="/createParty">Create Party</a>
              
            </#if>
                  
                   
                        <#if isAdmin!false>
              <a href="/admin">Admin Dashboard</a>
              <a href="/createAdmin">Create Admin</a>
            </#if>
               </nav>
                    
                
            </header>

            <div class="breadcrumb">My Account &gt; My Parties &gt; Riverbend Neighbors Party</div>


            <div class="content">

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
                            <li class="current-item">Control panel</li>
                            <li><a href="#">New forum post</a></li>
                            <li><a href="#">Publish announcement</a></li>
                           
                        </ul>
                    </section>
                </div>


                <main class="center-column">

                    <div class="stats-bar">
                        <span>Members: <strong>22</strong></span>
                        <span>Forum posts: <strong>8</strong></span>
                        <span>Announcements: <strong>2</strong></span>
                    </div>


                    <section class="box">
                        <h1 class="box-header">Party Control Panel</h1>
                        <div class="party-summary">
                            <div class="color-square"></div>
                            <div>
                                <p class="party-title">${partyName!"N/A"}</p>
                                <p class="meta-text">Status: ${status!"N/A"} · Owner: ${lastName!"N/A"}</p>
                                <p class="summary-text">
                                    ${description! "No Description"}
                                </p>
                                <div class="link-row">
                                   
                                </div>
                            </div>
                        </div>
                    </section>


                    <section class="box">
                        <h2 class="box-header">Members</h2>

                        <form class="toolbar" action="#" method="get">
                          
                           
                            <span class="toolbar-count toolbar-count-left">${memberCount} members</span>
                        </form>

                        <div class="table-scroll">
                            <table class="data-table">
                                <thead>
                                    <tr>
                                        <th>Member</th>

                                        <th>Status</th>
                                        <th>Action</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <#list pm as member>
                                        <tr>
                                            <td><a href="#">${member.user.firstName} ${member.user.lastName}</a></td>
                                            <td class="status-red">${member.status}</td>
                                            <td class="no-wrap">
                                                <form action="/partyManage/members/${member.user.userId}/remove"
                                                      method="post" class="inline-form">
                                                    <button type="submit" class="red-button">Remove</button>
                                                </form>
                                            </td>
                                        </tr>
                                    </#list>
                                   
                                    
                                    
                                </tbody>
                            </table>
                        </div>

                       
                    </section>

                    <!-- Forum posts -->
                    <section class="box">
                        <h2 class="box-header">Forum Posts</h2>

                        <div class="toolbar">
                            <a href="/createFP" class="red-button">+ New Forum Post</a>
                            <span class="toolbar-count">${forumPostCount} posts · ${hiddenForumPostCount} hidden</span>
                        </div>

                        <div class="table-scroll">
                            <table class="data-table">
                                <thead>
                                    <tr>
                                        <th>Title</th>
                                        <th>Author</th>
                                        <th>Posted</th>
                                        <th>Visibility</th>
                                        <th>Action</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <#list forumPosts as post>
                                        <tr>
                                            <td>${post.title!"Untitled"}</td>
                                            <td class="no-wrap">${post.author!"Unknown"}</td>
                                            <td class="no-wrap">${post.createdAt!""}</td>
                                            <td<#if post.visible?? && !post.visible> class="status-red"</#if>>${post.visibility!"unknown"}</td>
                                            <td class="no-wrap">
                                                <form action="/partyManage/forum-posts/${post.id?c}/delete"
                                                      method="post" class="inline-form">
                                                    <button type="submit" class="red-button small-delete-button">Remove</button>
                                                </form>
                                            </td>
                                        </tr>
                                    <#else>
                                        <tr><td colspan="5">No forum posts yet.</td></tr>
                                    </#list>
                                </tbody>
                            </table>
                        </div>

                        <div class="box-footer">
                            <span class="muted">Showing ${forumPostCount} of ${forumPostCount}</span>
                            <a href="#">View all forum posts »</a>
                        </div>
                    </section>


                    <section class="box">
                        <h2 class="box-header">Announcements</h2>

                        <div class="toolbar">
                            <a href="#" class="red-button">+ Publish Announcement</a>
                            <span class="toolbar-count">${announcementCount} published</span>
                        </div>

                        <#list announcements as announcement>
                            <div class="announcement">
                                <p class="party-title">${announcement.title!"Untitled"}</p>
                                <p class="meta-text">Published ${announcement.createdAt!""}</p>
                                <p class="summary-text">${announcement.description!""}</p>
                                <div class="link-row">
                                    <a href="#">Edit</a>
                                    <span>|</span>
                                    <form action="/partyManage/announcements/${announcement.id?c}/delete"
                                          method="post" class="inline-form">
                                        <button type="submit" class="red-button small-delete-button">Delete</button>
                                    </form>
                                </div>
                            </div>
                        <#else>
                            <p class="summary-text">No announcements yet.</p>
                        </#list>
                    </section>
                </main>

                <!-- Right column -->
                <div class="right-column">
                    <section class="box">
                        <h2 class="box-header">Resources</h2>
                        <ul class="box-list">
                            <li><a href="#">Petition builder</a></li>
                            <li><a href="#">Volunteer matcher</a></li>
                            <li><a href="#">Fundraising toolkit</a></li>
                            <li><a href="#">Media kit templates</a></li>
                        </ul>
                    </section>

                    <section class="box">
                        <h2 class="box-header">Collaboration Board</h2>
                        <p class="box-text">Parties looking for partners on shared initiatives:</p>
                        <ul class="box-list">
                            <li><a href="#">Water rights coalition (3 parties)</a></li>
                            <li><a href="#">Small business relief (5 parties)</a></li>
                        </ul>
                    </section>

                    <div class="ticker">
                        <strong>Ticker:</strong> New voter guide published · 3 parties merged
                        resources this week · Registration deadline in 12 days
                    </div>
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