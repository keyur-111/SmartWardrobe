<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%request.setAttribute("pageTitle",request.getAttribute("item")==null?"Add Clothing":"Edit Clothing"); %>
<%@ include file="/WEB-INF/jsp-header.jsp" %>
<%com.smartwardrobe.model.Clothing item=(com.smartwardrobe.model.Clothing)request.getAttribute("item"); boolean edit=item!=null; %>
<div class="form-wrap">
<div class="panel">
<div class="panel-head">
<div>
<span class="eyebrow">WARDROBE ITEM</span>
<h3>
<%=edit?"Update clothing":"Add a new piece"%>
</h3>
</div>
</div>
<form method="post" enctype="multipart/form-data" action="<%=request.getContextPath()%>/wardrobe" class="form-grid">
<input type="hidden" name="action" value="<%=edit?"update":"create"%>">
<%if(edit){%>
<input type="hidden" name="id" value="<%=item.getClothingId()%>">
<input type="hidden" name="existingImage" value="<%=item.getImage()==null?"":item.getImage()%>">
<%}%>
<div class="field full">
<label>Clothing name</label>
<input name="name" required value="<%=edit?item.getName():""%>
" placeholder="e.g. White Oxford Shirt">
</div>
<div class="field">
<label>Category</label>
<select name="categoryId" required>
<option value="">Select category</option>
<%for(Object o:(java.util.List)request.getAttribute("categories")){com.smartwardrobe.model.Category c=(com.smartwardrobe.model.Category)o;%>
<option value="<%=c.getCategoryId()%>
"
<%=edit&&item.getCategoryId()==c.getCategoryId()?"selected":""%>
>
<%=c.getCategoryName()%>
</option>
<%}%>
</select>
</div>
<div class="field">
<label>Color</label>
<input name="color" value="<%=edit?item.getColor():""%>
" placeholder="White">
</div>
<div class="field">
<label>Fabric</label>
<input name="fabric" value="<%=edit?item.getFabric():""%>
" placeholder="Cotton">
</div>
<div class="field">
<label>Season</label>
<select name="season">
<%String[] seasons={"All","Summer","Winter","Monsoon","Spring"};for(String x:seasons){%>
<option
<%=edit&&x.equals(item.getSeason())?"selected":""%>
>
<%=x%>
</option>
<%}%>
</select>
</div>
<div class="field">
<label>Occasion</label>
<select name="occasion">
<%String[] occ={"Any","Casual","Formal","Party","Travel","Work"};for(String x:occ){%>
<option
<%=edit&&x.equals(item.getOccasion())?"selected":""%>
>
<%=x%>
</option>
<%}%>
</select>
</div>
<div class="field">
<label>Status</label>
<select name="status">
<%String[] st={"Clean","In Laundry","Dry Cleaning"};for(String x:st){%>
<option
<%=edit&&x.equals(item.getStatus())?"selected":""%>
>
<%=x%>
</option>
<%}%>
</select>
</div>
<div class="field full">
<label>Clothing image</label>
<input type="file" name="imageFile" accept="image/png,image/jpeg,image/webp">
<small>Upload JPG, PNG or WEBP (max 5 MB). Leave empty when editing to keep the existing image.</small>
<%if(edit&&item.getImage()!=null&&!item.getImage().isEmpty()){%>
<div class="current-image">
<img src="<%=request.getContextPath()%>/uploads/<%=item.getImage()%>
" alt="Current clothing image">
<span>Current image</span>
</div>
<%}%>
</div>
<div class="field full">
<div class="form-actions">
<a class="btn outline" href="<%=request.getContextPath()%>/wardrobe">Cancel</a>
<button class="btn primary">
<%=edit?"Save Changes":"Add to Wardrobe"%>
</button>
</div>
</div>
</form>
</div>
</div>
<%@ include file="/WEB-INF/jsp-footer.jsp" %>
