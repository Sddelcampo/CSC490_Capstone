<!-- 
 CSC 490: The Political Pioneer
 partysearch.ftlh 
 Last updated: November 8, 2026

 Description: This is the main file for The Political Pioneer search feature. This will contain the
 main structure including navigation, user information and login on the left side of the page, and 
 the search results of the app on the bottom right quadrant of the page.
-->

<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8">
  <title>The Political Pioneer — User Homepage</title>
  <!-- <link rel="stylesheet" href="styles.css"> -->
  <!-- <link rel="stylesheet" type="text/css" href="/"> -->
  <link rel="stylesheet" type="text/css" href="/css/styles.css">

  <!-- <script src="script.js" defer></script> -->
</head>

<body class="page-dashboard page-portal">

  <!-- Creates a grid class to ensure we can split quadrants for logo, navigation, and the portal -->
  <div class="grid">

    <!-- Top Left Quadrant: Logo Implementation -->
    <div class="quadrant q-logo">
      <a href="/home">
        <!-- The Political Pioneer Logo: Created by Sebastian Del Campo -->
        <img src="images/deSebastian_TPP_Logo.png" alt="The Political Pioneer Logo">
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
              <!-- <a href="/acctlogin">Log In</a> Might be useless if we start at the login page-->
               <#if Session.SPRING_SECURITY_CONTEXT??>
              <a href="/logout">Log Out</a>
              <a href="/account">My Account</a>
              <#else>
              <a href="/acctlogin">Log In</a>
              <a href="/signup">Sign Up</a>
              </#if>
            </div>
          </div>
        </div>
        <nav>
          <a href="/home">Home</a>
          <a href="/aboutus">About Us</a>
          <a href="/guide">Guide</a>
          <!-- <a href="#">Settings</a> Unsure if settings may be necessary for the project-->
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

    <!-- Right Quadrant: The Political Pioneer Portal (TP2 Portal)
     (largest area — Provides portal navigation and user/party usage) -->
    <div class="quadrant q-portal">
      <div class="body">

        <!--Provides information for navigation within the portal along with a search bar and button-->
        <div class="mastbar">
          <div class="logo">TP2 <span>Portal</span></div>
          <nav class="tabs">
            <a href="/home">Home</a>
            <a href="/account">My Account</a>
            <a href="/platform">Platforms</a>
            <a href="/search" class="active">Find Parties</a>
            <a href="/resources">Resources</a>
            <!--<a href="#">Collaborate</a> might remove as it could be merged with /account or /events-->
            <a href="/events">Events</a>
            <#if isOwner!false><a href="/createFP">Create Forum Post</a></#if>
            <!--<a href="#">Voter Tools</a> may remove as it can be merged with resources-->


            <#if isAdmin!false>
              <a href="/admin">Admin Dashboard</a>
              <a href="/createAdmin">Create Admin</a>
            </#if>


          </nav>
          <div class="search">
            <input type="text" placeholder="Search parties, platforms, issues...">
            <button>Search</button>
          </div>
        </div>

        <!-- Provies a way to navigate within the portal -->
        <div class="breadcrumb">Home &gt; Directory</div>

        <!-- Ensures that the portal is formatted in a layout where you can read within 3 column blocks -->
        <div class="layout">

          <!-- Left Sidebar -->
          <div class="col-left">
            <div class="module gold">
              <h2>Find Parties Near an Address</h2>
              <div class="module-body">
                <form id="address-search-form" class="address-search-form">
                  <label for="address">Enter an address</label>
                  <input id="address" name="address" type="text" maxlength="300" required
                         placeholder="Street, city, region">
                  <button class="btn primary" type="submit">Find Location</button>
                  <p id="address-status" role="status" aria-live="polite"></p>
                </form>
                <p class="geocoder-attribution">
                  Address lookup by
                  <a href="https://www.openstreetmap.org/copyright" target="_blank" rel="noopener noreferrer">OpenStreetMap contributors</a>.
                  Your address is sent to Nominatim for lookup.
                </p>
              </div>
            </div>
          </div>

          <!-- Center Bar -->
          <div class="col-center">

            <div class="module red">
              <h2>Parties Matching Your Alignment</h2>
              <form id="sort-form" class="party-sort-form" method="get" action="/search">
                <label for="sort">Sort parties by:</label>
                <select id="sort" name="sort">
                  <option value="alignment"<#if sort == "alignment"> selected</#if>>Alignment</option>
                  <option value="location"<#if sort == "location"> selected</#if>>Location</option>
                  <option value="default"<#if sort == "default"> selected</#if>>Default</option>
                </select>
                <div id="radius-field" class="radius-field"<#if sort != "location" && sort != "default"> hidden</#if>>
                  <label for="radiusKm">Search radius:</label>
                  <span class="radius-select-wrap">
                    <select id="radiusKm" name="radiusKm" class="radius-select">
                      <option value="1"<#if radiusKm == 1> selected</#if>>1 Km</option>
                      <option value="10"<#if radiusKm == 10> selected</#if>>10 Km</option>
                      <option value="100"<#if radiusKm == 100> selected</#if>>100 Km</option>
                      <option value="1000"<#if radiusKm == 1000> selected</#if>>1000 Km</option>
                      <option value="10000"<#if radiusKm == 10000> selected</#if>>10000 Km</option>
                    </select>
                  </span>
                </div>
                <input type="hidden" id="lng" name="lng" value="${lng!}">
                <input type="hidden" id="lat" name="lat" value="${lat!}">
                <button class="btn primary" type="submit">Apply</button>
                <span id="location-status" role="status" aria-live="polite"></span>
              </form>
              <div class="module-body" style="padding:0;">

                <#if searchError??>
                  <p style="padding: 12px;">${searchError}</p>
                <#elseif parties?? && parties?has_content>
                  <#list parties as party>
                    <div class="party-row">
                      <div class="party-swatch" style="background:#2E7D32;"></div>
                      <div class="party-info">
                        <a href="/partyDetail/${party.partyId?c}"> <div class="name">${party.partyName!"Unnamed party"}</div></a>
                       
                        <div class="meta">Status: ${party.status!"Unknown"}</div>
                        <div class="tagline">${party.description!""}</div>
                      </div>
                    </div>
                  </#list>
                <#else>
                  <p style="padding: 12px;">No parties were found for this sorting option.</p>
                </#if>

              </div>
            </div>

            <div class="module">
              <h2>Recently Updated Platforms</h2>
              <div class="module-body">
                <ul>
                  <li><a href="#">Rural Renewal Alliance</a> updated their agriculture policy — 2 hours ago</li>
                  <li><a href="#">Civic Trust Party</a> added a new resource guide — yesterday</li>
                  <li><a href="#">Coastal Futures</a> posted an event: town hall, Sept 20 — 2 days ago</li>
                </ul>
              </div>
            </div>

            <!-- <div style="text-align:center; margin: 10px 0;">
              <a href="#" class="btn primary">+ Start a New Party Page</a>
              <a href="#" class="btn">Browse All 52 Parties &raquo;</a>
            </div> -->

          </div>

          <!-- Right Sidebar -->
          <div class="col-right">
            <div class="module">
              <h2>Resources</h2>
              <div class="module-body">
                <ul>
                  <li><a href="#">Petition builder</a></li>
                  <li><a href="#">Volunteer matcher</a></li>
                  <li><a href="#">Fundraising toolkit</a></li>
                  <li><a href="#">Media kit templates</a></li>
                </ul>
              </div>
            </div>

            <div class="module gold">
              <h2>Collaboration Board</h2>
              <div class="module-body">
                <p style="margin-top:0;">Parties looking for partners on shared initiatives:</p>
                <ul>
                  <li><a href="#">Water rights coalition (3 parties)</a></li>
                  <li><a href="#">Small business relief (5 parties)</a></li>
                </ul>
              </div>
            </div>

            <div class="ticker">
              <b>Ticker:</b> New voter guide published &middot; 3 parties merged resources this week
              &middot; Registration deadline in 12 days
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

  <script>
    const sortForm = document.getElementById("sort-form");
    const sortSelect = document.getElementById("sort");
    const radiusField = document.getElementById("radius-field");
    const locationStatus = document.getElementById("location-status");
    const addressSearchForm = document.getElementById("address-search-form");
    const addressInput = document.getElementById("address");
    const addressStatus = document.getElementById("address-status");

    sortSelect.addEventListener("change", () => {
      radiusField.hidden = sortSelect.value !== "location" && sortSelect.value !== "default";
    });

    addressSearchForm.addEventListener("submit", async (event) => {
      event.preventDefault();
      const address = addressInput.value.trim();
      if (!address) {
        addressStatus.textContent = "Enter an address to look it up.";
        return;
      }

      const submitButton = addressSearchForm.querySelector("button[type='submit']");
      submitButton.disabled = true;
      addressStatus.textContent = "Looking up address...";

      try {
        const geocoderUrl = new URL("https://nominatim.openstreetmap.org/search");
        geocoderUrl.searchParams.set("format", "jsonv2");
        geocoderUrl.searchParams.set("limit", "1");
        geocoderUrl.searchParams.set("q", address);

        const response = await fetch(geocoderUrl, {
          headers: { "Accept": "application/json" }
        });
        if (!response.ok) {
          throw new Error("The address lookup service returned an error.");
        }

        const results = await response.json();
        if (!Array.isArray(results) || results.length === 0) {
          addressStatus.textContent = "No matching address was found. Try adding a city or region.";
          return;
        }

        const latitude = Number(results[0].lat);
        const longitude = Number(results[0].lon);
        if (!Number.isFinite(latitude) || !Number.isFinite(longitude)
            || latitude < -90 || latitude > 90 || longitude < -180 || longitude > 180) {
          throw new Error("The address lookup returned invalid coordinates.");
        }

        document.getElementById("lng").value = longitude;
        document.getElementById("lat").value = latitude;
        sortSelect.value = "location";
        sortForm.submit();
      } catch (error) {
        addressStatus.textContent = error.message || "Address lookup failed. Please try again.";
      } finally {
        submitButton.disabled = false;
      }
    });

    sortForm.addEventListener("submit", (event) => {
      if (sortSelect.value === "alignment"
          || (document.getElementById("lng").value && document.getElementById("lat").value)) {
        return;
      }

      event.preventDefault();
      if (!navigator.geolocation) {
        locationStatus.textContent = "This browser does not support location access.";
        return;
      }

      locationStatus.textContent = "Getting your location...";
      navigator.geolocation.getCurrentPosition(
        (position) => {
          document.getElementById("lng").value = position.coords.longitude;
          document.getElementById("lat").value = position.coords.latitude;
          sortForm.submit();
        },
        () => {
          locationStatus.textContent =
            "Location access is required for this sort. Check your browser permission and try again.";
        }
      );
    });
  </script>

</body>

</html>