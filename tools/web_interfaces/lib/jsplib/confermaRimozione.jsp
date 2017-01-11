<%--
 * OpenSPCoop - Customizable API Gateway 
 * http://www.openspcoop2.org
 * 
 * Copyright (c) 2005-2017 Link.it srl (http://link.it). 
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 3, as published by
 * the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
--%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html; charset=UTF-8"%>
<%@page import="java.util.*, org.openspcoop2.web.lib.mvc.*" session="true"%>

<%
String params = (String) request.getAttribute("params");
if(params == null) params="";
GeneralData gd = (GeneralData) session.getAttribute("GeneralData");

ListElement listElement =
          (org.openspcoop2.web.lib.mvc.ListElement) session.getValue("ListElement");

String nomeServlet = listElement.getOggetto()+".do";
%>

<html>
<head>
<jsp:include page="/jsplib/browserUtils.jsp" flush="true" />
<SCRIPT>
var nomeServlet_Custom = '<%= nomeServlet %>';
</SCRIPT>


<jsp:include page="/jsp/confermaRimozioneCustom.jsp" flush="true" />

<SCRIPT LANGUAGE = JavaScript1.2>
var params = '<%= params %>';

function EseguiOp() {
    document.location='<%= request.getContextPath() %>/'+nomeServlet_Custom+'?'+params;
};

function Annulla() {
    document.location='<%= request.getContextPath() %>/'+nomeServlet_Custom;
};

</SCRIPT>
<title><%= gd.getTitle() %></title>
<link rel=stylesheet href=images/<%= gd.getCss() %> type=text/css>
<script type="text/javascript" src="js/webapps.js"></script>
<link rel="shortcut icon" href="images/favicon.ico" type="image/x-icon" />
</head>
<body marginwidth=0 marginheight=0 onLoad="focusText(document.form);">
<table border=0 cellspacing=0 cellpadding=0 width=100%>

<jsp:include page="/jsplib/templateHeader.jsp" flush="true" />

<tr>

<jsp:include page="/jsplib/menu.jsp" flush="true" />
<jsp:include page="/jsplib/info-page.jsp" flush="true" />
<jsp:include page="/jsplib/templateFooter.jsp" flush="true" />

</tr>
</table>
</body>
</html>

