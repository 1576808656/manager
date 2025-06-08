const { createApp } = Vue;

createApp({
    data() {
        return {
            account: '',
            password: ''
        };
    },
    mounted(){
        this.validateToken();
    },
    methods: {
        handleLogin() {
            // 前端验证
            if (!this.account || !this.password) {
                alert('用户名和密码不能为空');
                return;
            }

            // 发送请求到后端验证用户名和密码
            axios.post('/api/managerLogin', {
                account: this.account,
                password: this.password
            })
           .then(response => {
                if (response.data.success) {
                    alert('登录成功！');
                    localStorage.setItem('token', response.data.token);
                    window.location.href = '/main';
                } else {
                    alert('登录失败，请检查用户名和密码');
                }
            })
           .catch(error => {
                console.error('登录请求出错:', error);
                alert('登录请求出错，请稍后重试');
            });
        }
    }
}).mount('#app');