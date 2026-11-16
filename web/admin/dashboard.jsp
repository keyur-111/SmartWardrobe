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
<%if(request.getParameter("updated")!=null){%>
<div class="alert success">Account changes saved.</div>
<%}%>
<div class="table-wrap">
<table>
<thead>
<tr>
<th>User account</th>
<th>Role</th>
<th>Actions</th>
</tr>
</thead>
<tbody>
<%for(Object o:(java.util.List)request.getAttribute("users")){com.smartwardrobe.model.User u=(com.smartwardrobe.model.User)o;%>
<tr>
<td>
<%if(!"ADMIN".equals(u.getRole())){%>
<form method="post" class="admin-user-edit">
<input type="hidden" name="action" value="updateUser">
<input type="hidden" name="csrfToken" value="<%=request.getAttribute("csrfToken")%>">
<input type="hidden" name="id" value="<%=u.getUserId()%>">
<div class="field">
<label>Name</label>
<input name="name" value="<%=com.smartwardrobe.util.WebUtil.html(u.getName())%>" maxlength="100" required>
</div>
<div class="field">
<label>Email</label>
<input type="email" name="email" value="<%=com.smartwardrobe.util.WebUtil.html(u.getEmail())%>" maxlength="100" required>
</div>
<button class="btn small primary">Save</button>
</form>
<%}else{%>
<b>
<%=com.smartwardrobe.util.WebUtil.html(u.getName())%>
</b>
<br><%=com.smartwardrobe.util.WebUtil.html(u.getEmail())%>
<%}%>
</td>
<td>
<span class="pill">
<%=com.smartwardrobe.util.WebUtil.html(u.getRole())%>
</span>
</td>
<td>
<%if(!"ADMIN".equals(u.getRole())){%>
<form method="post" onsubmit="return confirm('Delete this user and their wardrobe?')">
<input type="hidden" name="action" value="deleteUser">
<input type="hidden" name="csrfToken" value="<%=request.getAttribute("csrfToken")%>">
<input type="hidden" name="id" value="<%=u.getUserId()%>">
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
<div class="panel">
<div class="panel-head">
<div>
<span class="eyebrow">ADMIN ACCOUNT</span>
<h3>Change admin password</h3>
</div>
</div>
<p class="muted">Use a unique password with at least 16 characters.</p>
<form method="post" class="form-grid">
<input type="hidden" name="action" value="changeAdminPassword">
<input type="hidden" name="csrfToken" value="<%=request.getAttribute("csrfToken")%>">
<div class="field">
<label>New password</label>
<input type="password" name="newPassword" minlength="16" autocomplete="new-password" required>
</div>
<div class="field">
<label>Confirm password</label>
<input type="password" name="confirmPassword" minlength="16" autocomplete="new-password" required>
</div>
<div class="field full">
<button class="btn primary">Update admin password</button>
</div>
</form>
</div>
<%@ include file="/WEB-INF/jsp-footer.jsp" %>
