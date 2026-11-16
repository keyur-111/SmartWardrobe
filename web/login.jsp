<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<!doctype html>
<html>
<head>
<meta charset="UTF-8">
<title>Login  /  Smart Wardrobe</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css?v=11">
</head>
<body class="auth-bg">
<div class="auth-card">
<div class="brand">SW<span> / </span>
</div>
<h1>Welcome back</h1>
<p class="muted">Sign in to manage your digital wardrobe.</p>
<%if(request.getAttribute("error")!=null){ %>
<div class="alert danger">
<%=request.getAttribute("error")%>
</div>
<%} if(request.getParameter("registered")!=null){ %>
<div class="alert success">Account created. You can sign in now.</div>
<%} %>
<form method="post" action="${pageContext.request.contextPath}/login">
<label>Email</label>
<input type="email" name="email" required>
<label>Password</label>
<input type="password" name="password" required>
<button class="btn primary full">Sign In</button>
</form>
<p class="center muted">New here? <a href="${pageContext.request.contextPath}/register">Create an account</a>
</p>
</div>
</body>
</html>
