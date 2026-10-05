<!-- 
 CSC 490: The Political Pioneer
 acctlogin.ftlh 
 Last updated: October 5, 2026
 
 Description: Standalone login page. Deliberately outside the 2x2 quadrant grid, since
 the account/nav shell assumes a session that doesn't exist yet at this point.
 Field order: username, password, "Create an Account" link, then the Sign In button.
-->

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>The Political Pioneer — Log In</title>
<link rel="stylesheet" type="text/css" href="/css/styles.css">
</head>
<body class="page-login">

  <div class="login-brand">
    <a href="/">
      <img src="images/deSebastian_TPP_Logo.png" alt="The Political Pioneer Logo" style="max-width:170px;">
    </a>
  </div>

  <div class="login-box">
    <h2>Log In</h2>
    <div class="login-body">
      <form action="/login" method="post">

        <div class="form-field">
          <label for="username">Username</label>
          <input type="text" id="username" name="username" required>
        </div>

        <div class="form-field">
          <label for="password">Password</label>
          <input type="password" id="password" name="password" required>
        </div>

        <div class="divider-link">
          Don't have an account? <a href="/signup">Create an Account</a>
        </div>

        <div class="form-actions">
          <button type="submit" class="btn primary">Sign In</button>
        </div>

      </form>
    </div>
  </div>

</body>
</html>
