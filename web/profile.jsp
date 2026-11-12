<% request.setAttribute("pageTitle","Profile"); %>
<%@ include file="/WEB-INF/jsp-header.jsp" %>
<div class="profile-card panel">
<div class="profile-avatar">
<%=String.valueOf(session.getAttribute("userName")).substring(0,1).toUpperCase()%>
</div>
<div>
<span class="eyebrow">ACCOUNT</span>
<h3>
<%=session.getAttribute("userName")%>
</h3>
<p class="muted">
<%=session.getAttribute("email")%>
</p>
<span class="pill">
<%=session.getAttribute("role")%>
</span>
</div>
</div>
<%@ include file="/WEB-INF/jsp-footer.jsp" %>
