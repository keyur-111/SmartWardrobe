<% request.setAttribute("pageTitle","My Wardrobe"); %>
<%@ include file="/WEB-INF/jsp-header.jsp" %>
<div class="panel">
<div class="panel-head">
<div>
<span class="eyebrow">YOUR COLLECTION</span>
<h3>Every piece, one place.</h3>
</div>
<a class="btn primary" href="<%=request.getContextPath()%>/wardrobe?action=add">+ Add Clothing</a>
</div>
<form class="filters" method="get" action="<%=request.getContextPath()%>/wardrobe">
<input name="search" placeholder="Search clothing..." value="<%=request.getParameter("search")==null?"":request.getParameter("search")%>
">
<select name="category">
<option value="">All categories</option>
<%for(Object o:(java.util.List)request.getAttribute("categories")){com.smartwardrobe.model.Category c=(com.smartwardrobe.model.Category)o;%>
<option value="<%=c.getCategoryId()%>
">
<%=c.getCategoryName()%>
</option>
<%}%>
</select>
<select name="status">
<option value="">All status</option>
<option>Clean</option>
<option>In Laundry</option>
<option>Dry Cleaning</option>
</select>
<button class="btn outline">Filter</button>
</form>
<div class="wardrobe-grid">
<%java.util.List items=(java.util.List)request.getAttribute("items");if(items!=null&&!items.isEmpty()){for(Object o:items){com.smartwardrobe.model.Clothing c=(com.smartwardrobe.model.Clothing)o;%>
<div class="clothing-card">
<div class="clothing-image">
<%if(c.getImage()!=null&&!c.getImage().isEmpty()){%>
<img src="<%=request.getContextPath()%>/uploads/<%=c.getImage()%>
" alt="<%=c.getName()%>
">
<%}else{%>
<span>
<%=c.getCategoryName().substring(0,1)%>
</span>
<%}%>
<span class="status
<%=c.getStatus().toLowerCase().replace(" ","-")%>
">
<%=c.getStatus()%>
</span>
</div>
<div class="card-body">
<span class="eyebrow">
<%=c.getCategoryName()%>
</span>
<h4>
<%=c.getName()%>
</h4>
<p>
<%=c.getColor()%>
/
<%=c.getFabric()%>
</p>
<div class="card-meta">
<span>
<%=c.getSeason()%>
</span>
<span>
<%=c.getOccasion()%>
</span>
<span>Worn
<%=c.getUsageCount()%>
x</span>
</div>
<div class="actions">
<a class="btn small outline" href="<%=request.getContextPath()%>/wardrobe?action=edit&id=<%=c.getClothingId()%>
">Edit</a>
<form method="post" action="<%=request.getContextPath()%>/wardrobe" onsubmit="return confirm('Delete this item?')">
<input type="hidden" name="action" value="delete">
<input type="hidden" name="id" value="<%=c.getClothingId()%>
">
<button class="btn small danger-btn">Delete</button>
</form>
<form method="post" action="<%=request.getContextPath()%>/wardrobe">
<input type="hidden" name="action" value="usage">
<input type="hidden" name="id" value="<%=c.getClothingId()%>
">
<button class="btn small ghost">Mark Worn</button>
</form>
</div>
</div>
</div>
<%}}else{%>
<div class="empty">
<div class="empty-icon">
</div>
<h3>Your wardrobe is empty</h3>
<p>Add your first clothing item to start generating outfits.</p>
<a class="btn primary" href="<%=request.getContextPath()%>/wardrobe?action=add">Add Clothing</a>
</div>
<%}%>
</div>
</div>
<%@ include file="/WEB-INF/jsp-footer.jsp" %>
