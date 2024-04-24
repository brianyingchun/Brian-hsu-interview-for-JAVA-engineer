<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Welcome</title>
    <style>
        table {
            border-collapse: collapse;
        }
        
        th, td {
            border: 1px solid black;
            padding: 8px;
            text-align: left;
            width: 200px;
        }
        
        th {
            background-color: #f2f2f2;
        }
    </style>
</head>
<script>
    function validateForm() {
        var chzCName = document.getElementById("chzCName").value;
        var regex = /^[\u4e00-\u9fa5]+$/; // 使用正則表達式篩選中文
    
        if (!regex.test(chzCName)) {
            alert("請輸入中文名稱");
            return false;
        }
    
        return true;
    }

    function validateUpdate() {
        var inputupdate = document.getElementById("inputupdate").value;
        var update = document.getElementById("update").value;
        var regex = /^[\u4e00-\u9fa5]+$/; // 使用正則表達式篩選中文
        
        if (regex.test(inputupdate)) {
            if(!regex.test(update)) {
                alert("請在中文名稱輸入中文");
                return false;
            }

        }
        if (regex.test(update)) {
            if(!regex.test(inputupdate)) {
                alert("請只在中文名稱輸入中文");
                return false;
            }

        }
        alert("驗證通過，送出資料");
        return true;
    }

    function validateDelete () {
        var inputdelete = document.getElementById("inputdelete").value;
        var integerRegex = /^[1-9]\d*$/; // 檢查是否為非零開頭的正整数
        if (!integerRegex.test(inputdelete)) {
            alert("請輸入ID，ID為非零開頭的正整數");
            return false;
        }
    }
    </script>
<body>
    <h1>Coindesk API測試</h1>
    <h2 style="color: Green;">按下按鈕，取得API JSON字串:</h2>
    <form action="/coindeskapi" method="get" >
        <button type="submit">Call API</button>
    </form>
    <p>結果: ${result}</p>
    <h2 style="color: Green;">按下按鈕，取得資料轉換後新API結果:</h2>
    <form action="/coindeskapiformat" method="get" >
        <button type="submit">Call API & Format</button>
    </form>
<br>
<br>
    <table>
        <thead>
            <tr>
                <th>幣種</th>
                <th>幣種中文名稱</th>
                <th>匯率</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${currencyList}" var="currency">
                <tr>
                    <td>${currency.code}</td>
                    <td>${currency.chz}</td>
                    <td>${currency.rate}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <h1>Coindesk 資料庫功能測試</h1>
    <h2 style="color: blue;">輸入ID, 幣種英文名或中文名作搜尋:</h2>
    <h4>英文名稱可為數字，但是檢索時若有重複以ID為優先</h4>
    <form action="/coins" method="get">
        <label for="coinId">Enter Coin ID:</label>
        <input type="text" id="input" name="input" required>
        <button type="submit">Get Coin</button>
    </form>
    
    <%-- 檢查模型中是否有coin物件 --%>
    <c:if test="${not empty coin}">      
        <p>ID: ${coin.id}</p>
        <p>Coin Name: ${coin.cname}</p>
        <p>Coin Chinese Name: ${coin.chzcname}</p>
    </c:if>

    <h2 style="color: blue;">輸入新的幣種(中文名稱只能輸入中文否則無法提交):</h2>
    <form action="/coins" method="post" onsubmit="return validateForm()">
        <label for="coinId">輸入幣種英文名稱 :</label>
        <input type="text" id="cName" name="cName" required>
        <label for="coinId">輸入幣種中文名稱 :</label>
        <input type="text" id="chzCName" name="chzCName" required>
        <button type="submit">Get Coin</button>
    </form>
    <p>結果: ${response}</p>

    <h2 style="color: blue;">輸入要更改的幣種欄位:</h2>
    <form action="/coins/updateCoins" method="post" onsubmit="return validateUpdate()">
        <label for="coinId">輸入要更改的欄位(無法更改ID):</label>                 
        <input type="text" id="inputupdate" name="inputupdate" required>
        <label for="coinId">輸入要將欄位改成(若要更改幣種中文名稱，則只能改為中文) :</label>
        <input type="text" id="update" name="update" required>
        <button type="submit">update</button>
    </form>
    <p>結果: ${update_response}</p>

    <h2 style="color: blue;">輸入ID, 刪除已建立的資料:</h2>
    <form action="/coins/deleteCoins" method="post" onsubmit="return validateDelete()">
        <label for="coinId">Enter Coin ID:</label>
        <input type="text" id="inputdelete" name="inputdelete" required>
        <button type="submit">Get Coin</button>
    </form>
    <p>結果: ${delete_response}</p>
</body>
</html>