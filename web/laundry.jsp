<% request.setAttribute("pageTitle","Laundry Tracker"); %>
<%@ include file="/WEB-INF/jsp-header.jsp" %>
<div class="grid-2">
<div class="panel">
<span class="eyebrow">NEW LAUNDRY CYCLE</span>
<h3>Send an item to laundry</h3>
<p class="muted">The item becomes unavailable for outfit generation until completed.</p>
<form method="post" action="<%=request.getContextPath()%>/laundry">
<div class="field">
<label>Clothing item</label>
<select name="clothingId" required>
<option value="">Select item</option>
<%for(Object o:(java.util.List)request.getAttribute("clothes")){com.smartwardrobe.model.Clothing c=(com.smartwardrobe.model.Clothing)o;%>
<option value="<%=c.getClothingId()%>
">
<%=c.getName()%>
-
<%=c.getColor()%>
</option>
<%}%>
</select>
</div>
<button class="btn primary">Start Laundry Cycle</button>
</form>
</div>
<div class="panel">
<span class="eyebrow">HOW IT WORKS</span>
<h3>Always know what's available.</h3>
<div class="timeline">
<div>
<b>01</b>
<span>Mark an item as In Laundry.</span>
</div>
<div>
<b>02</b>
<span>It disappears from outfit suggestions.</span>
</div>
<div>
<b>03</b>
<span>Complete the cycle to mark it Clean.</span>
</div>
</div>
</div>
</div>
<div class="panel">
<div class="panel-head">
<div>
<span class="eyebrow">HISTORY</span>
<h3>Laundry cycles</h3>
</div>
</div>
<div class="table-wrap">
<table>
<thead>
<tr>
<th>Item</th>
<th>Started</th>
<th>Returned</th>
<th>Status</th>
<th>
</th>
</tr>
</thead>
<tbody>
<%for(Object o:(java.util.List)request.getAttribute("laundry")){com.smartwardrobe.model.Laundry l=(com.smartwardrobe.model.Laundry)o;%>
<tr>
<td>
<b>
<%=l.getClothingName()%>
</b>
</td>
<td>
<%=l.getLaundryDate()%>
</td>
<td>
<%=l.getReturnDate().isEmpty()?"-":l.getReturnDate()%>
</td>
<td>
<span class="status
<%=l.getStatus().toLowerCase().replace(" ","-")%>
">
<%=l.getStatus()%>
</span>
</td>
<td>
<%if(!"Completed".equals(l.getStatus())){%>
<form method="post">
<input type="hidden" name="action" value="complete">
<input type="hidden" name="id" value="<%=l.getLaundryId()%>
">
<button class="btn small primary">Complete</button>
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
