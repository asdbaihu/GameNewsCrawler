<%--
  Created by IntelliJ IDEA.
  User: Yodes
  Date: 2017/1/18
  Time: 20:14
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!doctype html>
<html>
<head>
    <title>定时网页抓取任务列表</title>
    <%@include file="../../commons/header.jsp" %>
    <script type="application/javascript">
        function checkAll() {
            $('input:checkbox').each(function () {
                $(this).attr('checked', true);
            });
        }
    </script>
</head>

<body>
<%@include file="../../commons/head.jsp" %>
<div class="container">
    <br>
    <div class="row">
        <button type="button" onclick="batchDeleteQuartzJob()">【删除】选择的定时任务</button>
        <button type="button" onclick="checkAll()">全选</button>
    </div>
    <div class="row">
        <table>
            <thead>
            <tr>
                <th>#</th>
                <th>网站名称</th>
                <th>上次执行时间</th>
                <th>下次执行时间</th>
                <th>创建时间</th>
                <th>删除任务</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${list}" var="entry">
                    <tr>
                        <th><label>
                            <input type="checkbox" data-listkey="${entry.key}">&emsp;
                        </label></th>
                        <td>${entry.value.left.siteName}&emsp;</td>
                        <td> <fmt:formatDate value="${entry.value.right.previousFireTime}"
                                             pattern="yyyy/MM/dd HH:mm:ss"/>&emsp;
                        </td>
                        <td> <fmt:formatDate value="${entry.value.right.nextFireTime}"
                                             pattern="yyyy/MM/dd HH:mm:ss"/>&emsp;
                        </td>
                        <td> <fmt:formatDate value="${entry.value.right.startTime}"
                                             pattern="yyyy/MM/dd HH:mm:ss"/>&emsp;
                        </td>
                        <td>
                            <button onclick="deleteQuartzJob('${entry.key}')" class="am-btn am-btn-default">删除定时任务
                            </button>
                        </td>
                    </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>

<%@include file="../../commons/minScript.jsp" %>
<script>
    function deleteQuartzJob(spiderInfoId) {
        var cc = confirm("是否要删除定时任务");
        if (cc) {
            $.get('${pageContext.request.contextPath}/commons/spider/removeQuartzJob', {spiderInfoId: spiderInfoId}, function () {
                alert("删除定时任务成功");
            });
        }
    }
</script>
<script>
    function batchDeleteQuartzJob() {
        var cc = confirm("【WARNING】是否要删除已选定的定时任务");
        if (cc) {
            var keyList = [];
            $("input:checkbox:checked").each(function () {
                keyList.push($(this).attr('data-listkey'));
            });
            $.get('${pageContext.request.contextPath}/commons/spider/batchRemoveQuartzJob', {spiderInfoIdList: keyList.join(',')}, function () {
                alert("删除选定的定时任务成功");
            });
        }
    }
</script>
</body>

</html>