<template>
    <div>
        <input v-model="msg"><button @click="send">发消息</button>
    </div>
</template>

<script>
export default {
    data () {
        return {
            path:"ws://127.0.0.1:8080/imserver/5?message=你好",
            socket:"",
            params:{
                "toUserId":5,
                "contentText":"你好"
            },
            msg:''
        }
    },
    mounted () {
        // 初始化
        this.init()
    },
    methods: {
        init: function () {
            if (typeof (WebSocket) === "undefined"){
                alert("您的浏览器不支持socket")
            } else {
                // 实例化socket
                this.socket = new WebSocket(this.path)
                // 监听socket连接
                this.socket.onopen = this.open
                // 监听socket错误信息
                this.socket.onerror = this.error
                // 监听socket消息
                this.socket.onmessage = this.getMessage
            }
        },
        open: function () {
            console.log("socket连接成功");
        },
        error: function () {
            console.log("连接错误")
        },
        getMessage: function (msg) {
            console.log(msg.data)
            this.msg = msg.data
        },
        send: function () {
            this.socket.send(JSON.stringify(this.params))
        },
        close: function () {
            console.log("socket已经关闭")
        }
    },
    destroyed () {
        // 销毁监听
        this.socket.onclose = this.close
    }
}
</script>

<style>

</style>