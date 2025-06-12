const { createApp } = Vue;
createApp({
    data() {
        return {
            host: 'http://localhost:8090',
            worker: {
                pid: '',
                name: '',
                sex: '',
                age: '',
                nationality: '',
                idcard: '',
                birthday: '',
                college: '',
                address: '',
                tele: '',
                job: '',
                jobtime: '',
                photo: ''
            },
            searchName: '',
            workers: [],    //用于存储所有员工数据
            username: '管理员',
            activeMenu: 'personnel',
            name: '',
            employDatas: null,
            selectedPids: [], // 新增：存储选中的员工ID
            selectAll: false, // 新增：全选状态
            personnalColumns: [
                { key: 'pid', title: '员工ID' },
                { key: 'name', title: '姓名' },
                { key: 'sex', title: '性别' },
                { key: 'age', title: '年龄' },
                { key: 'nationality', title: '民族' },
                { key: 'idcard', title: '身份证号' },
                { key: 'birthday', title: '出生日期' },
                { key: 'college', title: '毕业学校' },
                { key: 'address', title: '住址' },
                { key: 'tele', title: '电话' },
                { key: 'job', title: '岗位' },
                { key: 'jobtime', title: '入职时间' },
                { key: 'photo', title: '照片' }
            ],
            employees: [],
            editingCells: {}  //跟踪单元格编辑状态 { [pid]: { [field]: boolean } }
        }
    },
    methods: {
        async personnal(){
            const res = await fetch(this.host+'/api/employees',{
                method:'GET',
                headers:{'Content-Type':'application/json'},
                name: this.searchName
            });
            const data = await res.json();
            this.employees = data;
        },
        
        // 新增重置方法
        handleReset() {
            // 清除所有编辑状态
            this.editingCells = {};
            
            // 重置复选框状态
            this.selectedPids = [];
            this.selectAll = false;
            
            // 重新获取原始数据
            this.personnal();
            
            alert('已重置所有编辑');
        },
    
        // 新增方法：使单元格可编辑
        // 新增方法：处理复选框变化
        handleCheckboxChange(pid) {
            if (this.editingCells[pid]) {
                for (const field in this.editingCells[pid]) {     // 遍历所有编辑状态
                    if (this.editingCells[pid][field]) {
                        this.selectedPids.push(pid);
                        break;
                    }
                }
            }
        },
        
        // 修改makeEditable方法
        makeEditable(event, pid, field) {
            // 照片列不可编辑
            if (field === 'photo') return;
            
            // 避免事件冒泡
            event.stopPropagation();
            
            // 设置当前编辑的单元格
            if (!this.editingCells[pid]) {    // 确保对象存在
                this.editingCells[pid] = {};
            }
            this.editingCells[pid][field] = true;
            
            // 自动勾选该行
            if (!this.selectedPids.includes(pid)) {
                this.selectedPids.push(pid);    // 确保选中
            }
        },
    
        // 新增方法：完成编辑
        finishEdit(pid, field) {
            if (this.editingCells[pid]) {
                this.editingCells[pid][field] = false;
            }
        },
    
        //检查单元格是否可编辑
        isEditable(pid, field) {
            return this.editingCells[pid] && this.editingCells[pid][field];
        }
    }
}).mount('#app');