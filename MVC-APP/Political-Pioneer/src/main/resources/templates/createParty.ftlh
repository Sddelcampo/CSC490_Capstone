<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Start a New Party Page | TP2 Portal</title>
    <link rel="stylesheet" type="text/css" href="/css/stylesll.css">

</head>

<body>
    <div class="page">
       
        <aside class="sidebar">
            <a href="/">
                <div class="logo-box">
                    <img src="images/deSebastian_TPP_Logo.png" alt="The Political Pioneer" />
                </div>
            </a>

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
                        <a href="/search">Find Parties</a>
                    </#if>
                    <a href="/resources">Resources</a>
                    <a href="/events">Events</a>
                    <#if isOwner!false>
                        <a href="/partyManage">Manage Party</a>
                        <a href="/createParty" class="active">Create Party</a>
                    </#if>
                    <#if isAdmin!false>
                        <a href="/admin">Admin Dashboard</a>
                        <a href="/createAdmin">Create Admin</a>
                    </#if>
                </nav>

              
            </header>

            <div class="breadcrumb">Home &gt; Find Parties &gt; Start a New Party Page</div>

           
            <div class="content">
            
                <div class="left-column">
                    <section class="box">
                        <h2 class="box-header">Quick Links</h2>
                        <ul class="box-list">
                            <li><a href="#">How to register a party</a></li>
                            <li><a href="#">Ballot access guide</a></li>
                            <li><a href="#">Collaboration board</a></li>
                        </ul>
                    </section>

                    <section class="box">
                        <h2 class="box-header">What Happens Next</h2>
                        <ol class="box-list">
                            <li>Your party is listed in Find Parties.</li>
                            <li>You are taken to its control panel.</li>
                            <li>
                                From there you review members, post to the forum and publish
                                announcements.
                            </li>
                        </ol>
                    </section>
                </div>

                
                <main class="center-column">
                    <section class="box">
                        <h1 class="box-header">Start a New Party Page</h1>

                        <form id="create-party-form" action="/makeParty" method="post">
                            <p class="form-intro">
                                Set up a page for your party. It is added to Find Parties as soon
                                as you create it. Fields marked <span class="required">*</span>
                                are required.
                            </p>

                            
                            <div class="form-row">
                                <label for="party-name">
                                    Party name <span class="required">*</span>
                                </label>
                                <div class="form-field">
                                    <input type="text" id="party-name" name="partyName" maxlength="255" required />
                                    <p class="hint">The name voters see in Find Parties. Up to 255 characters.</p>
                                </div>
                            </div>

                            
                            <div class="form-row">
                                <label for="party-description">
                                    Description <span class="required">*</span>
                                </label>
                                <div class="form-field">
                                    <textarea id="party-description" name="description" maxlength="255"
                                        required></textarea>
                                    <div class="hint hint-split">
                                        <span>A short summary shown under your party's name.</span>
                                        <span><span id="description-count">0</span> / 255 characters</span>
                                    </div>
                                </div>
                            </div>

                         
                            <div class="form-row">
                                <label for="party-status">Status</label>
                                <div class="form-field">
                                    <select id="party-status" name="status">
                                        <option value="active">active</option>
                                        <option value="inactive">inactive</option>
                                    </select>
                                    <p class="hint">Shown next to your party's name in Find Parties.</p>
                                </div>
                            </div>

                        
                            <div class="form-row">
                                <label for="party-address">
                                    Location <span class="required">*</span>
                                </label>
                                <div class="form-field">
                                    <div class="address-row">
                                        <input type="text" id="party-address" placeholder="Street, city, region" />
                                        <button type="button" id="find-location" class="red-button">Find
                                            Location</button>
                                    </div>

                                    
                                    <div id="location-result" class="location-result hidden">
                                        <p><strong>Location found:</strong> <span id="location-name"></span></p>
                                        <p class="location-coords" id="location-coords"></p>
                                    </div>
                                    <p id="location-error" class="error-text hidden"></p>

                                 
                                    <input type="hidden" name="latitude" value="40.7128" />
                                    <input type="hidden" name="longitude" value="-74.0060" />
                                    <p class="hint">
                                        Voters who search Find Parties near this address will see your
                                        party. Address lookup by
                                        <a href="https://www.openstreetmap.org/copyright">OpenStreetMap
                                            contributors</a>.
                                        Your address is sent to Nominatim for lookup.
                                    </p>
                                </div>
                            </div>


                            <div class="form-row">
                                <p class="row-label">Party owner</p>
                                <div class="form-field">
                                    <p>User (you)</p>
                                    <p class="hint">The account that creates a party page is the one that manages it.
                                    </p>
                                </div>
                            </div>

                            <div class="form-buttons">
                                <button type="submit" class="red-button">Create Party</button>
                                <a href="/parties" class="gray-button">Cancel</a>
                            </div>
                        </form>
                    </section>
                </main>

         
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

    <script src="create-party.js"></script>
</body>

</html>