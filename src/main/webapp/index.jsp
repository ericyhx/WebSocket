<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>My JSP 'index.jsp' starting page</title>
    <style type="text/css">
        .infos {
            list-style-type: none;
        }

        .infos li {
            border: 1px solid #EEE;
            margin: 2px;
            font-size: 12px;
            line-height: 25px;
            height: 25px;
        }

        .red {
            color: red;
        }

        .green {
            color: green;
        }

        .blue {
            color: blue;
        }
    </style>
    <script type="text/javascript" src="Resource/jquery-2.1.0.js"></script>
    <script type="text/javascript">
        var webSocket = null;
        var flag = true;//全局标记位，标记浏览器是否支持websocket
        $(function() {
            if (!window.WebSocket) {
                $("body").append("<h1>你的浏览器不支持WebSocket</h1>");
                flag = false;
                return;
            }

        });

        function startConnect() {
            alert(flag);
            if (flag == false) {
                return;
            }
            var url = "ws://localhost:8080/WebSocket/websocket2";
            webSocket = new WebSocket(url);

            webSocket.onerror = function(event) {
                onError(event)
            };
            webSocket.onopen = function(event) {
                onOpen(event)
            };
            webSocket.onmessage = function(event) {
                onMessage(event)
            };

            //webSocket.send("客户端给服务端发送消息：hello,start");

        }
        function onMessage(event) {
            var jas= JSON.parse(event.data);
            console.log(event.data);
            console.log("length is:"+jas.length);
            $(".infos").append("<li class='blue'>" + event.data + "</li>");
        }
        function onOpen(event) {
            $(".infos").append("<li class='green'>已连接至服务器</li>");
            $("#startBtn").prop("disabled", true);
            $("#sendMessageBtn").prop("disabled", false);
        }
        function onError(event) {
            $(".infos").append("<li class='red'>连接服务器发生错误</li>");
        }
        function sendMessage() {
            var msg = "[" + $("#username").val() + "]："
                + $("#message_input_id").val();//获取发送信息
            webSocket.send(msg);//向服务器发送消息
            //不需要将此信息追加到列表，由后台统一将消息发送给所有
            //$(".infos").append("<li class='green'>" + msg + "</li>");//将消息添加至本地列表
            $("#message_input_id").val("");//清空消息
        }
    </script>
</head>

<body>

<ul class="infos">
    <li class="red">提示：点击开始连接将连接到服务器</li>
</ul>
<br />
<br />
<input type="button" value="开始连接" id="startBtn"
       onclick="startConnect()" />
<br />
<br /> 输入名称：
<input id="username" value="<%=(int) (Math.random() * 1000)%>"
       style="width: 50px; margin-right: 5px;" />
<input id="message_input_id" />
<input type="button" value="发送消息" id="sendMessageBtn"
       disabled="disabled" onclick="sendMessage()" />

</body>
</html>