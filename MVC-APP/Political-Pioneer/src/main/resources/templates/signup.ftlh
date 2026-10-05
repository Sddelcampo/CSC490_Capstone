<!-- 
 CSC 490: The Political Pioneer
 signup.ftlh 

 Description: Standalone account-creation page. First/last name, username, password.
-->

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>The Political Pioneer — Create an Account</title>
<link rel="stylesheet" type="text/css" href="/css/styles.css">
</head>
<body class="page-login">

  <div class="login-brand">
    <a href="/">
      <img src="images/deSebastian_TPP_Logo.png" alt="The Political Pioneer Logo" style="max-width:170px;">
    </a>
  </div>

  <div class="login-box">
    <h2>Create an Account</h2>
    <div class="login-body">
      <form action="/signup" method="post">

        <div class="form-field">
          <label for="firstName">First Name</label>
          <input type="text" id="firstName" name="firstName" required>
        </div>

        <div class="form-field">
          <label for="lastName">Last Name</label>
          <input type="text" id="lastName" name="lastName" required>
        </div>

        <div class="form-field">
          <label for="username">Username</label>
          <input type="text" id="username" name="username" required>
        </div>

        <div class="form-field">
          <label for="password">Password</label>
          <input type="password" id="password" name="password" required>
        </div>

        <div class="divider-link">
          Already have an account? <a href="/login">Log In</a>
        </div>

        <div class="form-actions">
          <button type="submit" class="btn primary">Create Account</button>
        </div>

      </form>
    </div>
  </div>

</body>
</html>
