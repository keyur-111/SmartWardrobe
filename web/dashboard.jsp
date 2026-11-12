<% request.setAttribute("pageTitle", "Dashboard"); %>
<%@ include file="/WEB-INF/jsp-header.jsp" %>

<div class="hero">
    <div>
        <span class="pill">TODAY'S STYLE HUB</span>

        <h1>
            Good morning,
            <%= session.getAttribute("userName") %>
        </h1>

        <p>
            Keep your wardrobe fresh, available and ready for the next outfit.
        </p>
    </div>

    <a class="btn light"
       href="<%= request.getContextPath() %>/wardrobe?action=add">
        + Add Clothing
    </a>
</div>

<%
    java.util.List clothes =
            (java.util.List) request.getAttribute("clothes");

    java.util.List laundry =
            (java.util.List) request.getAttribute("laundry");

    int clean = 0;
    int inLaundry = 0;

    if (clothes != null) {
        for (Object o : clothes) {
            com.smartwardrobe.model.Clothing c =
                    (com.smartwardrobe.model.Clothing) o;

            if ("Clean".equals(c.getStatus())) {
                clean++;
            } else {
                inLaundry++;
            }
        }
    }
%>

<div class="stats">

    <div class="stat">
        <span>Wardrobe pieces</span>
        <strong>
            <%= clothes == null ? 0 : clothes.size() %>
        </strong>
        <small>Total items</small>
    </div>

    <div class="stat">
        <span>Ready to wear</span>
        <strong>
            <%= clean %>
        </strong>
        <small>Clean items</small>
    </div>

    <div class="stat">
        <span>In laundry</span>
        <strong>
            <%= inLaundry %>
        </strong>
        <small>Unavailable now</small>
    </div>

    <div class="stat">
        <span>Tracked cycles</span>
        <strong>
            <%= laundry == null ? 0 : laundry.size() %>
        </strong>
        <small>Laundry records</small>
    </div>

</div>

<div class="grid-2">

    <div class="panel">

        <div class="panel-head">
            <div>
                <span class="eyebrow">QUICK ACTION</span>
                <h3>Build an outfit</h3>
            </div>

            <span class="icon-box" aria-hidden="true">
                <svg viewBox="0 0 24 24"
                     fill="none"
                     stroke="currentColor"
                     stroke-width="1.8"
                     width="22"
                     height="22">
                    <path d="M20 20H4l2.2-2.1c1.1-1 2.4-1.8 3.9-2.3l1.1-.4c.5-.2.8-.7.8-1.2v-.4"/>
                    <path d="M10 8.2a2 2 0 1 1 4 0c0 1.4-1 2.1-2 2.7"/>
                </svg>
            </span>
        </div>

        <p class="muted">
            Filter by occasion and season. Smart Wardrobe only considers
            clean pieces.
        </p>

        <a class="btn primary"
           href="<%= request.getContextPath() %>/outfits">
            Generate Outfit ->
        </a>

    </div>

    <div class="panel">

        <div class="panel-head">
            <div>
                <span class="eyebrow">WARDROBE HEALTH</span>
                <h3>Keep clothes available</h3>
            </div>

            <span class="icon-box" aria-hidden="true">
                <svg viewBox="0 0 24 24"
                     fill="none"
                     stroke="currentColor"
                     stroke-width="1.8"
                     width="22"
                     height="22">
                    <path d="M7 4h10l1 4H6l1-4Z"/>
                    <path d="M6 8h12v10.5A1.5 1.5 0 0 1 16.5 20h-9A1.5 1.5 0 0 1 6 18.5V8Z"/>
                    <path d="M9 12h6M9 15h6"/>
                </svg>
            </span>
        </div>

        <p class="muted">
            Mark worn clothes as in-laundry so they never appear in
            outfit suggestions.
        </p>

        <a class="btn primary"
           href="<%= request.getContextPath() %>/laundry">
            Open Laundry Tracker
        </a>

    </div>

</div>

<div class="panel dashboard-recent-panel">

    <div class="panel-head">
        <div>
            <span class="eyebrow">RECENT ITEMS</span>
            <h3>Your wardrobe</h3>
        </div>

        <a href="<%= request.getContextPath() %>/wardrobe"
           class="text-link">
            View all
        </a>
    </div>

    <div
        class="dashboard-recent-grid"
        style="
            display:grid;
            grid-template-columns:repeat(3,minmax(0,1fr));
            gap:12px;
            width:100%;
            margin:0;
            padding:0;
        "
    >

        <%
            if (clothes != null) {

                int n = 0;

                for (Object o : clothes) {

                    if (n++ >= 6) {
                        break;
                    }

                    com.smartwardrobe.model.Clothing c =
                            (com.smartwardrobe.model.Clothing) o;
        %>

        <div
            class="dashboard-recent-card"
            style="
                width:100%;
                min-width:0;
                height:auto;
                overflow:hidden;
                background:#ffffff;
                border:1px solid #e5ddd6;
                border-radius:12px;
                box-sizing:border-box;
                box-shadow:none;
            "
        >

            <div
                class="dashboard-recent-image"
                style="
                    width:100%;
                    height:80px;
                    min-height:80px;
                    max-height:80px;
                    display:flex;
                    align-items:center;
                    justify-content:center;
                    overflow:hidden;
                    background:#f8f5f1;
                    box-sizing:border-box;
                    padding:4px;
                "
            >

                <%
                    if (c.getImage() != null
                            && !c.getImage().trim().isEmpty()) {
                %>

                <img
                    src="<%= request.getContextPath() %>/uploads/<%= c.getImage().trim() %>"
                    alt="<%= c.getName() %>"
                    style="
                        display:block;
                        width:auto;
                        height:auto;
                        max-width:90%;
                        max-height:72px;
                        object-fit:contain;
                        margin:auto;
                    "
                >

                <%
                    } else {
                %>

                <span
                    style="
                        font-size:20px;
                        font-weight:700;
                        color:#76503d;
                    "
                >
                    <%= c.getCategoryName() != null
                            && !c.getCategoryName().trim().isEmpty()
                            ? c.getCategoryName().trim().substring(0, 1)
                            : "C" %>
                </span>

                <%
                    }
                %>

            </div>

            <div
                class="dashboard-recent-info"
                style="
                    width:100%;
                    min-width:0;
                    padding:7px 9px 9px;
                    box-sizing:border-box;
                    overflow:hidden;
                "
            >

                <span
                    style="
                        display:block;
                        margin-bottom:2px;
                        font-size:8px;
                        line-height:1.2;
                        font-weight:700;
                        letter-spacing:1.2px;
                        text-transform:uppercase;
                        color:#b47d61;
                        white-space:nowrap;
                        overflow:hidden;
                        text-overflow:ellipsis;
                    "
                >
                    <%= c.getCategoryName() == null
                            ? ""
                            : c.getCategoryName() %>
                </span>

                <strong
                    style="
                        display:block;
                        font-size:13px;
                        line-height:1.3;
                        color:#171717;
                        white-space:nowrap;
                        overflow:hidden;
                        text-overflow:ellipsis;
                    "
                >
                    <%= c.getName() %>
                </strong>

                <small
                    style="
                        display:block;
                        margin-top:3px;
                        font-size:9px;
                        line-height:1.3;
                        color:#766b65;
                        white-space:nowrap;
                        overflow:hidden;
                        text-overflow:ellipsis;
                    "
                >
                    <%= c.getColor() %> / <%= c.getStatus() %>
                </small>

            </div>

        </div>

        <%
                }
            }
        %>

    </div>

</div>

<style>
    .dashboard-recent-grid {
        grid-template-columns: repeat(3, minmax(0, 1fr)) !important;
    }

    .dashboard-recent-card {
        width: 100% !important;
        min-width: 0 !important;
        height: auto !important;
    }

    .dashboard-recent-image {
        height: 100px !important;
        min-height: 80px !important;
        max-height: 80px !important;
    }

    .dashboard-recent-image img {
        width: auto !important;
        height: auto !important;
        max-width: 90% !important;
        max-height: 72px !important;
        object-fit: contain !important;
    }

    @media (max-width: 900px) {
        .dashboard-recent-grid {
            grid-template-columns: repeat(2, minmax(0, 1fr)) !important;
        }
    }

    @media (max-width: 600px) {
        .dashboard-recent-grid {
            grid-template-columns: 1fr !important;
        }
    }
</style>

<%@ include file="/WEB-INF/jsp-footer.jsp" %>