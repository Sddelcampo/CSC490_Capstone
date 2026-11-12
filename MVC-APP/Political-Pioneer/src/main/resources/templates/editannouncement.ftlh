<#ftl output_format="HTML" auto_esc=true>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Edit Announcement | TP2 Portal</title>
    <link rel="stylesheet" type="text/css" href="/css/stylesll.css">
</head>

<body>
    <div class="page">
        <aside class="sidebar">
            <a href="/">
                <div class="logo-box">
                    <img src="/images/deSebastian_TPP_Logo.png" alt="The Political Pioneer" />
                </div>
            </a>

            <div class="account-box">
                <h2 class="box-header">Account &amp; Site Info</h2>
                <div class="user-row">
                    <img class="user-avatar" src="/images/user-avatar.png" alt="" />
                    <div>
                        <p class="welcome-text">Welcome, Party Owner!</p>
                        <div class="user-links">
                            <a href="/logout">Log Out</a>
                            <a href="/account">My Account</a>
                        </div>
                    </div>
                </div>

                <nav class="side-nav">
                    <a href="/home">Home</a>
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
                        <li><a href="/contact">Contact the Dev Team</a> — feedback &amp; feature requests</li>
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
                        <a href="/partyManage" class="active">Manage Party</a>
                    </#if>
                    <#if isAdmin!false>
                        <a href="/admin">Admin Dashboard</a>
                    </#if>
                </nav>
            </header>

            <div class="breadcrumb">
                <a href="/home">Home</a> &gt; <a href="/partyManage">Manage Party</a> &gt; Edit Announcement
            </div>

            <div class="content">
                <div class="left-column">
                    <section class="box">
                        <h2 class="box-header">Party Tools</h2>
                        <ul class="box-list">
                            <li><a href="/partyManage">Party control panel</a></li>
                            <li class="current-item">Edit announcement</li>
                        </ul>
                    </section>

                    <section class="box">
                        <h2 class="box-header">Announcement Tips</h2>
                        <ul class="box-list">
                            <li>Use a clear, specific title.</li>
                            <li>Include dates, locations, and next steps when relevant.</li>
                            <li>Keep the message useful to your party members.</li>
                        </ul>
                    </section>
                </div>

                <main class="center-column">
                    <section class="box">
                        <h1 class="box-header">Edit Announcement</h1>
                        <p class="form-intro">
                            Update this announcement for members of <strong>${partyName!"your party"}</strong>.
                            Changes appear on your party page.
                        </p>

                        <#if announcementError??>
                            <p class="form-intro status-red" role="alert">${announcementError}</p>
                        </#if>

                        <form action="/partyManage/announcements/${announcementId?c}/edit" method="post">
                            <div class="form-row">
                                <label for="announcement-title">
                                    Announcement title <span class="required">*</span>
                                </label>
                                <div class="form-field">
                                    <input type="text" id="announcement-title" name="title"
                                        value="${announcementTitle!''}" maxlength="120" required />
                                    <p class="hint">A short title members can scan quickly. Up to 120 characters.</p>
                                </div>
                            </div>

                            <div class="form-row">
                                <label for="announcement-description">
                                    Message <span class="required">*</span>
                                </label>
                                <div class="form-field">
                                    <textarea id="announcement-description" name="description"
                                        maxlength="5000" rows="8" required>${announcementDescription!''}</textarea>
                                    <p class="hint">This message will be published on your party page for members to read.</p>
                                </div>
                            </div>

                            <div class="form-row">
                                <p class="row-label">Recipients</p>
                                <div class="form-field">
                                    <p>All members of ${partyName!"your party"}</p>
                                    <p class="hint">${memberCount!0} current members</p>
                                </div>
                            </div>

                            <div class="form-buttons">
                                <button type="submit" class="red-button">Save Changes</button>
                                <a href="/partyManage" class="gray-button">Cancel</a>
                            </div>
                        </form>
                    </section>
                </main>

                <div class="right-column">
                    <section class="box">
                        <h2 class="box-header">What Happens Next</h2>
                        <p class="box-text">
                            Your changes will appear in the party's published announcements list.
                        </p>
                    </section>
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
