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

                        
                            <div class="form-row location-form-row">
                                <label for="party-address">
                                    Headquarters location <span class="required">*</span>
                                </label>
                                <div class="form-field">
                                    <div class="location-picker">
                                        <input type="text" id="party-address" maxlength="300"
                                            autocomplete="street-address" placeholder="Street, city, region" />
                                        <div class="location-actions">
                                            <button type="button" id="find-location" class="red-button">
                                                Find Location
                                            </button>
                                            <button type="button" id="use-browser-location" class="gray-button">
                                                Use My Location
                                            </button>
                                        </div>
                                        <p id="location-status" class="location-status" role="status"
                                            aria-live="polite"></p>
                                        <p id="location-error" class="location-error" role="alert" hidden></p>
                                        <div id="location-result" class="location-result" hidden>
                                            <strong>Selected location</strong>
                                            <p id="location-name"></p>
                                        </div>
                                        <input type="hidden" id="party-latitude" name="latitude" />
                                        <input type="hidden" id="party-longitude" name="longitude" />
                                        <p class="geocoder-attribution">
                                            Voters who search Find Parties near this address will see your party.
                                            Address lookup by
                                            <a href="https://www.openstreetmap.org/copyright" target="_blank"
                                                rel="noopener noreferrer">OpenStreetMap contributors</a>.
                                            Your address is sent to Nominatim for lookup.
                                        </p>
                                    </div>
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

    <script>
        const partyForm = document.getElementById("create-party-form");
        const addressInput = document.getElementById("party-address");
        const latitudeInput = document.getElementById("party-latitude");
        const longitudeInput = document.getElementById("party-longitude");
        const findLocationButton = document.getElementById("find-location");
        const useBrowserLocationButton = document.getElementById("use-browser-location");
        const locationResult = document.getElementById("location-result");
        const locationName = document.getElementById("location-name");
        const locationStatus = document.getElementById("location-status");
        const locationError = document.getElementById("location-error");
        let locationRequest = 0;

        function setLocationButtonsDisabled(disabled) {
            findLocationButton.disabled = disabled;
            useBrowserLocationButton.disabled = disabled;
        }

        function clearSelectedLocation() {
            locationRequest++;
            setLocationButtonsDisabled(false);
            latitudeInput.value = "";
            longitudeInput.value = "";
            locationResult.hidden = true;
            locationError.hidden = true;
            locationStatus.textContent = "";
        }

        function setSelectedLocation(latitude, longitude, label) {
            if (!Number.isFinite(latitude) || !Number.isFinite(longitude)
                || latitude < -90 || latitude > 90
                || longitude < -180 || longitude > 180) {
                throw new Error("The location lookup returned invalid coordinates.");
            }

            latitudeInput.value = String(latitude);
            longitudeInput.value = String(longitude);
            locationName.textContent = label;
            locationResult.hidden = false;
            locationError.hidden = true;
            locationStatus.textContent = "Location selected.";
        }

        function showLocationError(message) {
            locationError.textContent = message;
            locationError.hidden = false;
            locationStatus.textContent = "";
        }

        addressInput.addEventListener("input", clearSelectedLocation);
        addressInput.addEventListener("keydown", function (event) {
            if (event.key === "Enter") {
                event.preventDefault();
                findLocationButton.click();
            }
        });

        findLocationButton.addEventListener("click", async function () {
            const address = addressInput.value.trim();
            clearSelectedLocation();
            if (!address) {
                showLocationError("Enter the party headquarters address first.");
                addressInput.focus();
                return;
            }

            setLocationButtonsDisabled(true);
            const requestId = locationRequest;
            locationStatus.textContent = "Looking up address...";

            try {
                const geocoderUrl = new URL("https://nominatim.openstreetmap.org/search");
                geocoderUrl.searchParams.set("format", "jsonv2");
                geocoderUrl.searchParams.set("limit", "1");
                geocoderUrl.searchParams.set("q", address);

                const response = await fetch(geocoderUrl, {
                    headers: { "Accept": "application/json" }
                });
                if (!response.ok) {
                    throw new Error("The address lookup service returned an error. Try again.");
                }

                const results = await response.json();
                if (requestId !== locationRequest) {
                    return;
                }
                if (!Array.isArray(results) || results.length === 0) {
                    throw new Error("No matching address was found. Add a city or region and try again.");
                }

                setSelectedLocation(
                    Number(results[0].lat),
                    Number(results[0].lon),
                    results[0].display_name || address
                );
            } catch (error) {
                if (requestId === locationRequest) {
                    showLocationError(error.message || "Address lookup failed. Please try again.");
                }
            } finally {
                if (requestId === locationRequest) {
                    setLocationButtonsDisabled(false);
                }
            }
        });

        useBrowserLocationButton.addEventListener("click", function () {
            clearSelectedLocation();
            if (!navigator.geolocation) {
                showLocationError("This browser does not support location access. Enter the address instead.");
                return;
            }

            setLocationButtonsDisabled(true);
            const requestId = locationRequest;
            locationStatus.textContent = "Requesting your browser location...";
            navigator.geolocation.getCurrentPosition(
                function (position) {
                    if (requestId !== locationRequest) {
                        return;
                    }
                    try {
                        setSelectedLocation(
                            position.coords.latitude,
                            position.coords.longitude,
                            "Location from this browser"
                        );
                    } catch (error) {
                        showLocationError(error.message);
                    } finally {
                        setLocationButtonsDisabled(false);
                    }
                },
                function (error) {
                    if (requestId !== locationRequest) {
                        return;
                    }
                    const message = error.code === error.PERMISSION_DENIED
                        ? "Location access was denied. Allow it in your browser or enter the address."
                        : error.code === error.POSITION_UNAVAILABLE
                            ? "Your browser could not determine your location. Enter the address instead."
                            : "Location request timed out. Try again or enter the address.";
                    showLocationError(message);
                    setLocationButtonsDisabled(false);
                },
                { enableHighAccuracy: false, timeout: 10000, maximumAge: 60000 }
            );
        });

        partyForm.addEventListener("submit", function (event) {
            if (!latitudeInput.value || !longitudeInput.value) {
                event.preventDefault();
                showLocationError("Find the party address or use your browser location before continuing.");
            }
        });
    </script>
</body>

</html>