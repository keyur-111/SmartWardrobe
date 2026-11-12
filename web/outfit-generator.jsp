<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%request.setAttribute("pageTitle","Outfit Generator"); %>
<%@ include file="/WEB-INF/jsp-header.jsp" %>
<div class="generator">
<div class="panel">
<span class="eyebrow">STYLE ENGINE</span>
<h3>Build an outfit from your wardrobe</h3>
<p class="muted">Choose an occasion and season. The generator selects clean pieces from your wardrobe and avoids items in laundry or dry-cleaning.</p>
<form method="get" action="<%=request.getContextPath()%>/outfits" class="generator-form">
<div>
<label>Occasion</label>
<select name="occasion">
<%String oo=request.getAttribute("occasion")==null?"Casual":request.getAttribute("occasion").toString();for(String x:new String[]{"Casual","Formal","Party","Travel","Work"}){%>
<option
<%=x.equals(oo)?"selected":""%>
>
<%=x%>
</option>
<%}%>
</select>
</div>
<div>
<label>Season</label>
<select name="season">
<%String ss=request.getAttribute("season")==null?"Summer":request.getAttribute("season").toString();for(String x:new String[]{"Summer","Winter","Monsoon","Spring","All"}){%>
<option
<%=x.equals(ss)?"selected":""%>
>
<%=x%>
</option>
<%}%>
</select>
</div>
<button class="btn primary">Generate Outfit</button>
</form>
</div>
</div>
<%java.util.List generated=(java.util.List)request.getAttribute("generated");if(generated!=null){%>
<div class="panel generated">
<div class="panel-head">
<div>
<span class="eyebrow">YOUR SUGGESTION</span>
<h3>
<%=request.getAttribute("occasion")%>
/
<%=request.getAttribute("season")%>
</h3>
</div>
<span class="pill">CLEAN ITEMS ONLY</span>
</div>
<%if(generated.isEmpty()){%>
<div class="empty">
<div class="empty-icon">OUTFIT</div>
<h3>No complete outfit yet</h3>
<p>Add clean items in Tops, Bottoms and Footwear, then try again.</p>
<a class="btn primary" href="<%=request.getContextPath()%>/wardrobe?action=add">Add Clothing</a>
</div>
<%}else{%>
<div class="outfit-grid">
<%for(Object o:generated){com.smartwardrobe.model.Clothing c=(com.smartwardrobe.model.Clothing)o;%>
<div class="outfit-piece">
<div class="clothing-image outfit-image">
<%if(c.getImage()!=null&&!c.getImage().isEmpty()){%>
<img src="<%=request.getContextPath()%>/uploads/<%=c.getImage()%>
" alt="<%=c.getName()%>
">
<%}else{%>
<span>
<%=c.getCategoryName().substring(0,1)%>
</span>
<%}%>
</div>
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
</div>
<%}%>
</div>
<form method="post" class="save-outfit">
<input type="hidden" name="outfitName" value="<%=request.getAttribute("occasion")%>
look">
<input type="hidden" name="occasion" value="<%=request.getAttribute("occasion")%>
">
<input type="hidden" name="season" value="<%=request.getAttribute("season")%>
">
<%for(Object o:generated){com.smartwardrobe.model.Clothing c=(com.smartwardrobe.model.Clothing)o;%>
<input type="hidden" name="clothingId" value="<%=c.getClothingId()%>
">
<%}%>
<button class="btn primary">Save This Outfit</button>
</form>
<%}%>
</div>
<%}%>
<div class="panel">
<div class="panel-head">
<div>
<span class="eyebrow">SAVED</span>
<h3>My outfit history</h3>
</div>
</div>
<div class="saved-list">
<%for(Object o:(java.util.List)request.getAttribute("outfits")){com.smartwardrobe.model.Outfit x=(com.smartwardrobe.model.Outfit)o;%>
<div class="saved-row">
<div>
<b>
<%=x.getOutfitName()%>
</b>
<small>
<%=x.getOccasion()%>
/
<%=x.getSeason()%>
/
<%=x.getCreatedAt()%>
</small>
</div>
<form method="post">
<input type="hidden" name="action" value="delete">
<input type="hidden" name="id" value="<%=x.getOutfitId()%>
">
<button class="btn small danger-btn">Delete</button>
</form>
</div>
<%}%>
</div>
</div>
<%@ include file="/WEB-INF/jsp-footer.jsp" %>
