<%@page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="app.shopmanagement.bean.*" %>

<!DOCTYPE html>
<html lang="ja">
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <meta http-equiv="content-Style-Type" content="text/css" />
  <link href="/ShopManagement/css/style.css" rel="stylesheet" type="text/css" />
  <title>店舗情報検索画面</title>
 </head>
 
 <%
 int displayLimit = (int)session.getAttribute("displayLimit");
 int[] displayLimitList = (int[])session.getAttribute("displayLimitList");
 String keywordHistory = (String)session.getAttribute("keywordHistory");
 if(keywordHistory == null) {
 	keywordHistory = "";
 }
 List<Shop> shopListForDisplay = (List)request.getAttribute("shopListForDisplay");
 int pageNum = (int)request.getAttribute("pageNum");
 
 int openCount = (int)request.getAttribute("statusOpenCount");
 int closedCount = (int)request.getAttribute("statusClosedCount");
 int othersCount = (int)request.getAttribute("statusOthersCount");
 %>

 <body>
 	<div class="header">
 		<h1 class="page-title">店舗情報検索</h1>
 		<ul class="right">
 		<li>OPEN:<%=openCount%></li>
 		<li>CLOSED:<%=closedCount%></li>
 		<li>OTHERS:<%=othersCount%></li>
 		</ul>
	</div>
	

	
	<div class="main">
		<div class="search-area">
			<form action="Search.action" class="search_form" name="search_form" method="post">
 			 <div class="item">
 			   店舗名 <input type="text" name="searchKeyword" value="<%=keywordHistory%>">
 			 </div>
 			 <button class="search submit_button" type="submit" value="検索">検索</button>
 	        </form>
 	        
 	        <form action="ProduceSearchView.action" class="search_form" method="post">
 			   表示件数
 			   <select class="pulldown" name="requestDisplayLimit" onchange="this.form.submit()">
 			   <%
 			     for (int i = 0; i < displayLimitList.length; i++) {
 			   %>
 			       <option value="<%=displayLimitList[i]%>" <%
 			       if(displayLimit == displayLimitList[i]) {%>
 			       	selected
 			       <%}
 			       %>><%=displayLimitList[i]%>件</option>
 			   <%}%>
 			   </select>
 	        </form>
		</div>
		<!--　検索結果を表示するようにかいてください -->
		<!-- 検索ボタンをクリックしたあと、該当する商品情報を出力させる-->
		<%
		  
 	      if(shopListForDisplay != null && shopListForDisplay.size() > 0) {
		%>
		<table class="search_result_table">
 		<tr>
 		 <th width="100px">ID</th><th width="100px">ステータス</th><th width="300px">店舗名</th>
 	    </tr>
 	    <!--　この部分を繰り返す -->
 	    <%
 	      
 	        for (int i = 0; i < shopListForDisplay.size(); i++) {
 	        
 	      %>
 	      <tr>
 	        <td><%=shopListForDisplay.get(i).getShopId()%></td>
 	        <td><%=shopListForDisplay.get(i).getStatus()%></td>
 	        <td><%=shopListForDisplay.get(i).getShopName()%></td>
 	     </tr>
 	    <%
 	        }
 	    %>
 	 </table>
 	 <%
 	 }
 	 %>
 	 
 	 <%
 	 //次のページ、前のページの有無を確認する。
 	 //現在のページ(pageNum)が1より大きければ、前のページがある。
 	 //店舗リストのサイズ - 表示上限 * 現在のページが0より大きければ次のページがある。
 	 List<Shop> shopList = (List)session.getAttribute("filteredShopList");
 	 if(pageNum > 1) {
 	 %>
 	 
 	 <form action="ProduceSearchView.action" class="form_back" method="post">
 	   <input type="hidden" name="requestPageNum" value="<%=(pageNum-1)%>">
       <input class="submit_button" type="submit" value="前のページ">
     </form>
     
     <%}
     
     if ((shopList.size() - displayLimit * pageNum) > 0) {
     %>
     
 	 <form action="ProduceSearchView.action" class="form_back" method="post">
 	   <input type="hidden" name="requestPageNum" value="<%=(pageNum+1)%>">
       <input class="submit_button" type="submit" value="次のページ">
     </form>
     
     <%}%>

 </body>
</html>