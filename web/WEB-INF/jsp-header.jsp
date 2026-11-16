<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%if(session.getAttribute("userId")==null){ response.sendRedirect(request.getContextPath()+"/login"); return; } %>
<!doctype html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>
<%=request.getAttribute("pageTitle")==null?"Smart Wardrobe":request.getAttribute("pageTitle")%>
</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css?v=14">
</head>
<body>
<div class="app-shell">
<aside class="sidebar">
<a class="logo" href="<%=request.getContextPath()%>/dashboard">SW<span> / </span>
</a>
<button class="mobile-menu-toggle" type="button"
        aria-label="Open profile and navigation options"
        aria-haspopup="true" aria-expanded="false"
        aria-controls="primary-navigation">
<svg viewBox="0 0 24 24" fill="none" stroke="currentColor"
     stroke-width="1.8" width="22" height="22" aria-hidden="true">
<circle cx="12" cy="8" r="3.5"></circle>
<path d="M5 20a7 7 0 0 1 14 0"></path>
</svg>
</button>
<nav class="sidebar-nav" id="primary-navigation" aria-label="Primary navigation">
<div class="side-label">Workspace</div>
<a href="<%=request.getContextPath()%>/dashboard">
<span>Dashboard</span>
</a>
<a href="<%=request.getContextPath()%>/wardrobe">
<span>My Wardrobe</span>
</a>
<a href="<%=request.getContextPath()%>/outfits">
<span>Outfit Generator</span>
</a>
<a href="<%=request.getContextPath()%>/laundry">
<span>Laundry Tracker</span>
</a>
<a href="<%=request.getContextPath()%>/profile.jsp">
<span>Profile</span>
</a>
<%if("ADMIN".equals(session.getAttribute("role"))){ %>
<div class="side-label">Administration</div>
<a href="<%=request.getContextPath()%>/admin">
<span>Admin Dashboard</span>
</a>
<a href="<%=request.getContextPath()%>/admin/categories">
<span>Categories</span>
</a>
<%} %>
<div class="side-bottom">
<a href="<%=request.getContextPath()%>/logout">
<span>Logout</span>
</a>
</div>
</nav>
</aside>
<main class="content">
<div class="topbar">
<div>
<span class="eyebrow">SMART WARDROBE</span>
<h2>
<%=request.getAttribute("pageTitle")==null?"Your style, organized.":request.getAttribute("pageTitle")%>
</h2>
</div>
<div class="user-chip">
<span class="avatar">
<%=String.valueOf(session.getAttribute("userName")).substring(0,1).toUpperCase()%>
</span>
<%=session.getAttribute("userName")%>
</div>
</div>
