<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Admin – Users &amp; Owners – TP2 Portal</title>
    <link rel="stylesheet" href="/css/stylesll.css">
  
</head>
<body>
<div class="page">

  <aside class="sidebar">
    <div class="logo-box">
      <img src="images/deSebastian_TPP_Logo.png" alt="The Political Pioneer">
    </div>

    <div class="account-box">
      <h2 class="box-header">Account &amp; Site Info</h2>
      <div class="user-row">
        <svg class="user-avatar" viewBox="0 0 46 46" aria-hidden="true">
          <circle cx="23" cy="23" r="21.5" fill="#ffffff" stroke="#4a7cc0" stroke-width="2"></circle>
          <circle cx="23" cy="18" r="6.5" fill="#5f7d9e"></circle>
          <path d="M11 35c1.5-6.5 6.5-10 12-10s10.5 3.5 12 10z" fill="#5f7d9e"></path>
        </svg>
        <div>
          <p class="welcome-text">Welcome, Admin!</p>
          <div class="user-links">
            <a href="/logout">Log Out</a>
            <a href="/account">My Account</a>
          </div>
        </div>
      </div>
      <nav class="side-nav" aria-label="Site">
        <a href="/">Home</a>
        <a href="/about">About Us</a>
        <a href="/guide">Guide</a>
        <a href="/report">Report a Concern</a>
      </nav>
      <div class="side-about">
        <p>The Political Pioneer is an independent, nonpartisan platform for third-party organizing and voter engagement.</p>
       
      </div>
    </div>
  </aside>


  <div class="main">

    <header class="navbar">
      <div class="brand">TP2 <span>Portal</span></div>
      <nav class="nav-links" aria-label="Main">
        <a href="/">Home</a>
        <a href="/account">My Account</a>
      

      <#if !(isAdmin!false)>
      <a href="/resources" class="active">Resources</a>
      </#if>
                
       
   
        <a href="/events">Events</a>
        <a href="/admin" class="active" aria-current="page">Admin Dashboard</a>
        <a href="/createAdmin" aria-current="page">Create Admin</a>


      </nav>
      <form class="search-form" action="/search" method="get" role="search">
        <input type="search" name="q" placeholder="Search parties, platforms, events" aria-label="Search parties, platforms, events">
        <button type="submit">Search</button>
      </form>
    </header>

    <div class="breadcrumb"><a href="/">Home</a> &gt; <a href="/admin">Admin</a> &gt; Users &amp; Owners</div>

    <main class="content">

    

      <!-- Center column -->
      <div class="center-column">


    
        <section class="box" id="owners">
          <h2 class="box-header box-header-split">
            <span>Party Owners</span>
            <span class="box-header-count">4 accounts</span>
          </h2>
          <form class="toolbar" action="/admin/accounts" method="get">
            <input type="hidden" name="type" value="owner">
            <label for="owner-search">Search:</label>
            <input type="search" id="owner-search" name="q" placeholder="Username, email or party">
            <label for="owner-sort">Sort by:</label>
            <select id="owner-sort" name="sort">
            
              <option>Username (A–Z)</option>
            </select>
            <button type="submit" class="gray-button">Filter</button>
            <span class="toolbar-count">Showing 4 of 4 owners</span>
          </form>
          <div class="table-scroll">
            <table class="data-table admin-table">
              <caption class="visually-hidden">Party owner accounts</caption>
              <thead>
                <tr>
                  <th scope="col">Username</th>
                  <th scope="col">Email</th>
                  <th scope="col">Party Owned</th>
                 
                 
                  <th scope="col">Status</th>
                  <th scope="col" class="actions-cell">Action</th>
                </tr>
              </thead>
              <tbody>

                 <#list owner as o>
                                        
                      


                <tr>
                  <td><a href="/admin/accounts/o101">${o.userName!"N/A"}</a></td>
                  <td>${o.email!"N/A"}</td>

                 
                  <td><a href="/parties/new-horizon-coalition">${partyNameByOwnerId[o.userId?c]!"N/A"}</a></td>
             
               
                 
                  <td class="status-green">${o.status!"N/A"}</td>
                  <td class="actions-cell">
                    <form class="inline-form" action="/admin/accounts/delete" method="post" onsubmit="return confirm('Permanently delete the owner account dwhitfield (owner of New Horizon Coalition)? This cannot be undone.');">
                      <input type="hidden" name="csrf_token" value="">
                      <input type="hidden" name="account_id" value="o101">
                      <input type="hidden" name="account_type" value="owner">
                      <button type="submit" class="red-button small-delete-button" aria-label="Delete owner dwhitfield">Delete Owner</button>
                    </form>
                  </td>
                </tr>
                
                   </#list>
                
              </tbody>
            </table>
          </div>
          <div class="box-footer">
           
            <span><a href="#"></span>
          </div>
        </section>

        <!-- Users -->
        <section class="box" id="users">
          <h2 class="box-header box-header-split">
            <span>Users</span>
            <span class="box-header-count">6 accounts</span>
          </h2>
          <form class="toolbar" action="/admin/accounts" method="get">
            <input type="hidden" name="type" value="user">
            <label for="user-search">Search:</label>
            <input type="search" id="user-search" name="q" placeholder="Username or email">
            <label for="user-sort">Sort by:</label>
            <select id="user-sort" name="sort">
              <option>Username (A–Z)</option>
            </select>
            <button type="submit" class="gray-button">Filter</button>
            <span class="toolbar-count">Showing 6 of 6 users</span>
          </form>
          <div class="table-scroll">
            <table class="data-table admin-table">
              <caption class="visually-hidden">User accounts</caption>
              <thead>
                <tr>
                  <th scope="col">Username</th>
                  <th scope="col">Email</th>
              
                
                  <th scope="col">Status</th>
                  <th scope="col" class="actions-cell">Action</th>
                </tr>
              </thead>
              <tbody>

                   <#list user as u>

                <tr>
                  <td><a href="/admin/accounts/u201">${u.userName!"N/A"}</a></td>
                  <td>${u.email!"N/A"}</td>
               
                 
                 
                  <td class="status-green">${u.status!"N/A"}</td>
                  <td class="actions-cell">
                    <form class="inline-form" action="/admin/accounts/delete" method="post" onsubmit="return confirm('Permanently delete the user account jrivera? This cannot be undone.');">
                      <input type="hidden" name="csrf_token" value="">
                      <input type="hidden" name="account_id" value="u201">
                      <input type="hidden" name="account_type" value="user">
                      <button type="submit" class="red-button small-delete-button" aria-label="Delete user jrivera">Delete User</button>
                    </form>
                  </td>
                </tr>
                 </#list>
                
              </tbody>
            </table>
          </div>
          <div class="box-footer">
          
            <span><a href="#"></span>
          </div>
        </section>
      </div>

   
      <div class="right-column">
        <section class="box">
          <h2 class="box-header">Before You Delete</h2>
          <p class="admin-warning">Deleting an account is permanent and cannot be undone.</p>
          <ul class="box-list">
            <li>For owners, decide first whether their party should be handed to another member.</li>
           
          </ul>
        </section>

       
      </div>

    </main>

    <footer class="footer">
      The Political Pioneer — a home for independent political organizing ·
      <a href="/about">About Us</a> · <a href="/terms">Terms</a> · <a href="/contact">Contact</a>
    </footer>
  </div>

</div>
</body>
</html>
