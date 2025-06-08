// 页面加载时检查认证状态
function checkAuth() {
/*    const token = localStorage.getItem('token');
    if (!token) {
        window.location.href = '/login';
        return;
    }*/
    
/*    axios.get('/api/protected', {
        headers: {
            'Authorization': 'Bearer ' + token
        }
    }).catch(error => {
        if (error.response.status === 401) {
            window.location.href = '/login';
        }
    });*/
}

// 人事管理功能
document.addEventListener('DOMContentLoaded', function() {
    // 绑定人事管理菜单点击事件
    document.querySelector('nav ul li:first-child a').addEventListener('click', function(e) {
        e.preventDefault();
        document.getElementById('personnel-management').style.display = 'block';
        loadEmployees();
    });
});

let currentEmployee = null;

// 定义模块列配置
const moduleColumns = {
    personnel: [  // 人事管理列配置
        { key: 'pid', title: '工号' },
        { key: 'name', title: '姓名' },
        { key: 'sex', title: '性别' },
        { key: 'age', title: '年龄' },
        { key: 'nationality', title: '民族' },
        { key: 'birthday', title: '出生日期' },
        { key: 'idcard', title: '身份证号' },
        { key: 'college', title: '毕业学校' },
        { key: 'address', title: '住址' },
        { key: 'tele', title: '电话' },
        { key: 'job', title: '岗位' },
        { key: 'jobtime', title: '入职时间' },
        { key: 'photo', title: '照片' }
    ],
    signIn: [  // 考勤管理列配置
        { key: 'pid', title: '工号' },
        { key: 'name', title: '姓名' },
        { key: 'date', title: '日期' },
        { key: 'signInTime', title: '签到时间' },
        { key: 'signOutTime', title: '签退时间' }
    ]
};

let currentModule = 'personnel';  // 当前激活模块

// 修改showPersonnelManagement函数
function showPersonnelManagement() {
    currentModule = 'personnel';
    // 隐藏主内容
    document.querySelector('main').style.display = 'none';
    document.getElementById('sign-in-management').style.display = 'none';
    document.getElementById('sign-in-search-input').style.display = 'none';
    // 显示人事管理区域
    document.getElementById('personnel-management').style.display = 'block';
    // 加载员工数据
    loadEmployees();
}

// 修改showSignInManagement函数（需要补充考勤数据加载逻辑）
function showSignInManagement() {
    currentModule = 'signIn';
    // 隐藏主内容
    document.querySelector('main').style.display = 'none';
    // 隐藏人事管理区域
    document.getElementById('personnel-management').style.display = 'none';
    document.getElementById('personal-input').style.display = 'none';
    // 显示考勤管理区域
    document.getElementById('sign-in-management').style.display = 'block';
    // 加载考勤数据
    loadSignInData();
}

// 动态列生成
function loadEmployees(name = '') {
    const columns = moduleColumns[currentModule];
    const thead = document.getElementById('dynamic-thead');
    const tbody = document.querySelector('#employee-table tbody');

    // 清空原有内容
    thead.innerHTML = '';
    tbody.innerHTML = '';

    // 生成列头（包含全选复选框）
    const headerRow = document.createElement('tr');
    headerRow.innerHTML = `<th><input type="checkbox" id="select-all"></th>`;
    columns.forEach(col => {
        const th = document.createElement('th');
        th.textContent = col.title;
        headerRow.appendChild(th);
    });
    thead.appendChild(headerRow);

    // 新增：在复选框生成后立即绑定事件
    document.getElementById('select-all').addEventListener('click', function() {
        const checkboxes = document.querySelectorAll('.employee-checkbox');
        checkboxes.forEach(checkbox => {
            checkbox.checked = this.checked;
        });
    });

    axios.get('/api/employees', {
        params: { name }
    }).then(response => {
        const tableBody = document.querySelector('#employee-table tbody');
        tableBody.innerHTML = '';
        
        response.data.forEach(employee => {
            const row = document.createElement('tr');
            row.innerHTML = `<td><input type="checkbox" class="employee-checkbox" data-pid="${employee.pid}"></td>`;
            
            columns.forEach(col => {
                let tdContent;
                if (col.key === 'photo') {  // 处理照片列
                    tdContent = `<img src="/photo/${employee.photo}.jpg" alt="员工照片" style="width: 90px; height: 100px;">`;
                } else {
                    tdContent = employee[col.key] || '';  // 处理可能不存在的字段
                }
                row.innerHTML += `<td>${tdContent}</td>`;
            });

            // 添加双击事件，排除最后一列（照片列）
            row.querySelectorAll('td:not(:last-child)').forEach(td => {
                td.addEventListener('dblclick', () => makeEditable(td));
            });
            tableBody.appendChild(row);
        });
    });
}

// 新增考勤数据加载函数（示例）
function loadSignInData(name = '') {
    const columns = moduleColumns[currentModule];
    const thead = document.getElementById('dynamic-thead');
    const tbody = document.querySelector('#employee-table tbody');

    // 清空原有内容
    thead.innerHTML = '';
    tbody.innerHTML = '';

    // 生成考勤列头
    const headerRow = document.createElement('tr');
    headerRow.innerHTML = `<th><input type="checkbox" id="select-all"></th>`;
    columns.forEach(col => {
        const th = document.createElement('th');
        th.textContent = col.title;
        headerRow.appendChild(th);
    });
    thead.appendChild(headerRow);

    // 考勤数据加载
    axios.get('/getWorkTimeInfo', { params: { name } })
        .then(response => {
            response.data.forEach(record => {
                const row = document.createElement('tr');
                row.innerHTML = `<td><input type="checkbox" class="sign-in-checkbox" data-pid="${record.pid}"></td>`;
                
                columns.forEach(col => {
                    row.innerHTML += `<td>${record[col.key] || ''}</td>`;
                });
                tbody.appendChild(row);
            });
        });
}

function makeEditable(element) {
    const field = element.id;
    const value = element.textContent;
    element.innerHTML = `<input type="text" value="${value}" data-field="${field}">`;
    element.classList.add('editable');
    element.querySelector('input').focus();
}

function saveEmployee() {
    const rows = document.querySelectorAll('#employee-table tbody tr');
    const employees = [];

    rows.forEach(row => {
        const cells = row.querySelectorAll('td:not(:last-child)');
        const employee = {
            pid: parseInt(cells[1].querySelector('input') ? cells[1].querySelector('input').value : cells[1].textContent),
            name: cells[2].querySelector('input') ? cells[2].querySelector('input').value.trim() : cells[2].textContent.trim(),
            sex: cells[3].querySelector('input') ? cells[3].querySelector('input').value : cells[3].textContent,
            age: parseInt(cells[4].querySelector('input') ? cells[4].querySelector('input').value : cells[4].textContent),
            nationality: cells[5].querySelector('input') ? cells[5].querySelector('input').value : cells[5].textContent,
            birthday: (() => {
                const dateStr = cells[6].querySelector('input') ? cells[6].querySelector('input').value : cells[6].textContent;
                const date = new Date(dateStr);
                return isNaN(date.getTime()) ? '' : date.toISOString().slice(0, 10);
            })(),
            idcard: cells[7].querySelector('input') ? cells[7].querySelector('input').value : cells[7].textContent,
            college: cells[8].querySelector('input') ? cells[8].querySelector('input').value : cells[8].textContent,
            address: cells[9].querySelector('input') ? cells[9].querySelector('input').value : cells[9].textContent,
            tele: cells[10].querySelector('input') ? cells[10].querySelector('input').value : cells[10].textContent,
            job: cells[11].querySelector('input') ? cells[11].querySelector('input').value : cells[11].textContent,
            jobtime: (() => {
                const dateStr = cells[12].querySelector('input') ? cells[12].querySelector('input').value : cells[12].textContent;
                const date = new Date(dateStr);
                return isNaN(date.getTime()) ? '' : date.toISOString().slice(0, 10);
            })(),
            photo: `${cells[1].querySelector('input') ? cells[1].querySelector('input').value : cells[1].textContent}_${cells[2].querySelector('input') ? cells[2].querySelector('input').value : cells[2].textContent}`
        };
        employees.push(employee);
    });

    // 发送数据到后端
    axios.post('/api/updateEmployees', employees)
        .then(response => {
            if (response.data.message) {
                alert('保存成功');
                loadEmployees(); // 重新加载数据
            } else {
                alert('保存失败');
            }
        })
        .catch(error => {
            console.error('保存出错:', error);
            alert('保存出错，请稍后重试');
        });
}

function searchEmployee() {
    const keyword = document.getElementById('search-input').value;
    loadEmployees(keyword);
}

function addEmployee() {
    // 实现添加逻辑...
}

function deleteEmployee() {
    const selectedPids = [];
    document.querySelectorAll('.employee-checkbox:checked').forEach(checkbox => {
        selectedPids.push(checkbox.dataset.pid);
    });

    if (selectedPids.length === 0) {
        alert('请先选择要删除的员工');
        return;
    }

    if (confirm('确定要删除选中的员工吗？')) {
        axios.post('/api/deleteEmployees', { pids: selectedPids })
            .then(() => {
                alert('删除成功');
                loadEmployees();
            })
            .catch(error => {
                alert('删除失败: ' + error.message);
            });
    }
}

function resetForm() {
    // 重置表单时恢复所有可编辑单元格
    const editableCells = document.querySelectorAll('.editable');
    editableCells.forEach(cell => {
        const input = cell.querySelector('input');
        if (input) {
            cell.textContent = input.value;
            cell.classList.remove('editable');
        }
    });
    // 重新加载员工数据
    loadEmployees();
}

// 初始化检查
checkAuth();


function showPersonnelManagement() {
    // 隐藏主内容
    document.querySelector('main').style.display = 'none';
    document.getElementById('sign-in-management').style.display = 'none';
    document.getElementById('sign-in-search-input').style.display = 'none';
    // 显示人事管理区域
    document.getElementById('personnel-management').style.display = 'block';
    // 加载员工数据
    loadEmployees();
}

function showSignInManagement() {
    currentModule = 'signIn';
    // 隐藏主内容
    document.querySelector('main').style.display = 'none';
    // 隐藏人事管理区域
    document.getElementById('personnel-management').style.display = 'none';
    // 修正：将 getElementsById 改为 getElementById
    document.getElementById('personal-input').style.display = 'none';
    // 显示考勤管理区域
    document.getElementById('sign-in-management').style.display = 'block';
    // 加载考勤数据
    loadSignInData();
}

// 可以添加返回主界面的函数
function showMainContent() {
    document.querySelector('main').style.display = 'block';
    document.getElementById('personnel-management').style.display = 'none';
}