<% request.setAttribute("pageTitle","Categories"); %>
<%@ include file="/WEB-INF/jsp-header.jsp" %>
<div class="grid-2">
<div class="panel">
<span class="eyebrow">NEW CATEGORY</span>
<h3>Add category</h3>
<form method="post">
<input type="hidden" name="action" value="create">
<div class="field">
<label>Name</label>
<input name="name" required>
</div>
<div class="field">
<label>Description</label>
<input name="description">
</div>
<button class="btn primary">Create Category</button>
</form>
</div>
<div class="panel">
<span class="eyebrow">CATALOG</span>
<h3>Categories</h3>
<div class="saved-list">
<%for(Object o:(java.util.List)request.getAttribute("categories")){com.smartwardrobe.model.Category c=(com.smartwardrobe.model.Category)o;%>
<div class="saved-row">
<div>
<b>
<%=c.getCategoryName()%>
</b>
<small>
<%=c.getDescription()==null?"":c.getDescription()%>
</small>
</div>
<form method="post" onsubmit="return confirm('Delete category?')">
<input type="hidden" name="action" value="delete">
<input type="hidden" name="id" value="<%=c.getCategoryId()%>
">
<button class="btn small danger-btn">Delete</button>
</form>
</div>
<%}%>
</div>
</div>
</div>
<%@ include file="/WEB-INF/jsp-footer.jsp" %>
