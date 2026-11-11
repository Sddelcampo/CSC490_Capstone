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

     
      <div class="main">
        
        <header class="navbar">
          <div class="brand">TP2 <span>Portal</span></div>

          <nav class="nav-links">
            <a href="/">Home</a>
            <a href="/account" >My Account</a>
            <a href="/platforms">Platforms</a>
            <a href="/parties">Find Parties</a>
            <a href="/resources">Resources</a>
            <a href="/events">Events</a>
             <a href="/partyManage" class="active">Manage Party</a>
          </nav>

          <form class="search-form" action="/search" method="get">
            <input type="text" name="q" placeholder="Search parties, platforms, events" />
            <button type="submit">Search</button>
          </form>
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
                <li><a href="#">Edit party details</a></li>
                <li><a href="#">View public page</a></li>
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
                  <p class="party-title">Riverbend Neighbors Party</p>
                  <p class="meta-text">Status: active · Owner: User (you)</p>
                  <p class="summary-text">
                    A neighbor-led party working on safer streets, well-funded
                    libraries and open city budgets in the Riverbend district.
                  </p>
                  <div class="link-row">
                    <a href="#">View public page</a>
                    <span>|</span>
                    <a href="#">Edit party details</a>
                  </div>
                </div>
              </div>
            </section>

         
            <section class="box">
              <h2 class="box-header">Members</h2>

              <form class="toolbar" action="#" method="get">
                <label for="member-filter">Show:</label>
                <select id="member-filter" name="memberStatus">
                  <option value="all">All members</option>
                  <option value="active">Active members</option>
                  <option value="pending">Pending requests</option>
                </select>
                <button type="submit" class="red-button">Apply</button>
                <span class="toolbar-count">22 members · 2 pending requests</span>
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
                    <tr>
                      <td><a href="#">d.castellano</a></td>
                     
                      <td class="status-red">pending</td>
                      <td class="no-wrap"><a href="#">Approve</a> | <a href="#">Decline</a></td>
                    </tr>
                    <tr>
                      <td><a href="#">priya_raman</a></td>
                      
                      <td class="status-red">pending</td>
                      <td class="no-wrap"><a href="#">Approve</a> | <a href="#">Decline</a></td>
                    </tr>
                    <tr>
                      <td><strong>User (you)</strong></td>
                    
                      <td>active</td>
                      <td class="no-wrap muted">Party owner</td>
                    </tr>
                    <tr>
                      <td><a href="#">t_nguyen</a></td>
                     
                      <td>active</td>
                      <td class="no-wrap"><a href="#">Remove</a></td>
                    </tr>
                    <tr>
                      <td><a href="#">m_okafor</a></td>
                    
                      <td>active</td>
                      <td class="no-wrap"><a href="#">Remove</a></td>
                    </tr>
                  </tbody>
                </table>
              </div>

              <div class="box-footer">
                <span class="muted">Showing 5 of 24</span>
                <a href="#">View all members »</a>
              </div>
            </section>

            <!-- Forum posts -->
            <section class="box">
              <h2 class="box-header">Forum Posts</h2>

              <div class="toolbar">
                <a href="#" class="red-button">+ New Forum Post</a>
                <span class="toolbar-count">8 posts · 1 hidden</span>
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
                    <tr>
                      <td><a href="#">Volunteers needed for Saturday street cleanup</a></td>
                      <td class="no-wrap">m_okafor</td>
                      <td class="no-wrap">Nov 9, 2026</td>
                      <td>visible</td>
                      <td class="no-wrap"><a href="#">Hide</a></td>
                    </tr>
                    <tr>
                      <td><a href="#">Notes from the October library budget hearing</a></td>
                      <td class="no-wrap">User (you)</td>
                      <td class="no-wrap">Nov 7, 2026</td>
                      <td>visible</td>
                      <td class="no-wrap"><a href="#">Edit</a> | <a href="#">Hide</a></td>
                    </tr>
                    <tr>
                      <td><a href="#">Draft wording for the crosswalk petition</a></td>
                      <td class="no-wrap">User (you)</td>
                      <td class="no-wrap">Nov 2, 2026</td>
                      <td class="status-red">hidden</td>
                      <td class="no-wrap"><a href="#">Edit</a> | <a href="#">Show</a></td>
                    </tr>
                  </tbody>
                </table>
              </div>

              <div class="box-footer">
                <span class="muted">Showing 3 of 8</span>
                <a href="#">View all forum posts »</a>
              </div>
            </section>

           
            <section class="box">
              <h2 class="box-header">Announcements</h2>

              <div class="toolbar">
                <a href="#" class="red-button">+ Publish Announcement</a>
                <span class="toolbar-count">2 published</span>
              </div>

              <div class="announcement">
                <p class="party-title">November general meeting: Nov 18 at 7:00 PM</p>
                <p class="meta-text">Published Nov 6, 2026</p>
                <p class="summary-text">
                  Join us at Riverbend Community Hall. On the agenda: the winter
                  volunteer drive and committee sign-ups.
                </p>
                <div class="link-row">
                  <a href="#">Edit</a>
                  <span>|</span>
                  <a href="#">Delete</a>
                </div>
              </div>

              <div class="announcement">
                <p class="party-title">Our party page is live</p>
                <p class="meta-text">Published Oct 28, 2026</p>
                <p class="summary-text">
                  Riverbend Neighbors Party is now listed on The Political Pioneer.
                  Invite a neighbor to join and say hello in the forum.
                </p>
                <div class="link-row">
                  <a href="#">Edit</a>
                  <span>|</span>
                  <a href="#">Delete</a>
                </div>
              </div>
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