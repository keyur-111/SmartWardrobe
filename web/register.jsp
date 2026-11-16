<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<!doctype html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Create Account - Smart Wardrobe</title>

    <link rel="stylesheet"
          href="<%= request.getContextPath() %>/css/style.css?v=14">
</head>

<body class="auth-bg">

    <div class="auth-card">

        <div class="brand">
            SW<span> / </span>
        </div>

        <h1>Create your wardrobe</h1>

        <p class="muted">
            Start organizing your clothes digitally.
        </p>

        <% if (request.getAttribute("error") != null) { %>

            <div class="alert danger">
                <%= request.getAttribute("error") %>
            </div>

        <% } %>

        <form method="post"
              action="<%= request.getContextPath() %>/register">

            <label>Full Name</label>
            <input type="text" name="name" required>

            <label>Email</label>
            <input type="email" name="email" required>

            <label>Password</label>
            <input type="password"
                   name="password"
                   minlength="6"
                   required>

            <label>Confirm Password</label>
            <input type="password"
                   name="confirmPassword"
                   minlength="6"
                   required>

            <button type="submit"
                    class="btn primary full">
                Create Account
            </button>

        </form>

        <p class="center muted">
            Already have an account?

            <a href="<%= request.getContextPath() %>/login">
                Sign in
            </a>
        </p>

    </div>

</body>
</html>