<% request.setAttribute("pageTitle","Admin Dashboard"); %>
<%@ include file="/WEB-INF/jsp-header.jsp" %>
<div class="hero admin-hero">
<div>
<span class="pill">ADMIN CONTROL CENTER</span>
<h1>Keep the wardrobe system healthy.</h1>
<p>Manage users, categories and clothing records.</p>
</div>
</div>
<div class="stats">
<div class="stat">
<span>Users</span>
<strong>
<%=((java.util.List)request.getAttribute("users")).size()%>
</strong>
<small>Registered accounts</small>
</div>
<div class="stat">
<span>Clothing</span>
<strong>
<%=((java.util.List)request.getAttribute("items")).size()%>
</strong>
<small>All user items</small>
</div>
<div class="stat">
<span>Categories</span>
<strong>
<%=((java.util.List)request.getAttribute("categories")).size()%>
</strong>
<small>Available groups</small>
</div>
</div>
<div class="panel">
<div class="panel-head">
<div>
<span class="eyebrow">USERS</span>
<h3>User management</h3>
</div>
</div>
<div class="table-wrap">
<table>
<thead>
<tr>
<th>Name</th>
<th>Email</th>
<th>Role</th>
<th>
</th>
</tr>
</thead>
<tbody>
<%for(Object o:(java.util.List)request.getAttribute("users")){com.smartwardrobe.model.User u=(com.smartwardrobe.model.User)o;%>
<tr>
<td>
<b>
<%=u.getName()%>
</b>
</td>
<td>
<%=u.getEmail()%>
</td>
<td>
<span class="pill">
<%=u.getRole()%>
</span>
</td>
<td>
<%if(!"ADMIN".equals(u.getRole())){%>
<form method="post" onsubmit="return confirm('Delete this user and their wardrobe?')">
<input type="hidden" name="action" value="deleteUser">
<input type="hidden" name="id" value="<%=u.getUserId()%>
">
<button class="btn small danger-btn">Delete</button>
</form>
<%}%>
</td>
</tr>
<%}%>
</tbody>
</table>
</div>
</div>
<%@ include file="/WEB-INF/jsp-footer.jsp" %>
