<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="登录名" prop="username">
        <el-input
            v-model="queryParams.username"
            placeholder="请输入登录名"
            clearable
            @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="QQ" prop="qqNumber">
        <el-input
            v-model="queryParams.qqNumber"
            placeholder="请输入QQ"
            clearable
            @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="邮箱" prop="email">
        <el-input
            v-model="queryParams.email"
            placeholder="请输入邮箱"
            clearable
            @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="邮件激活" prop="isActive">
        <el-select v-model="queryParams.isActive" placeholder="请选择邮件激活" clearable>
          <el-option
              v-for="dict in dict.type.tb_member_status"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="用户性别" prop="sex">
        <el-select v-model="queryParams.sex" placeholder="请选择用户性别" clearable>
          <el-option
              v-for="dict in dict.type.sys_user_sex"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="手机" prop="telephone">
        <el-input
            v-model="queryParams.telephone"
            placeholder="请输入手机"
            clearable
            @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="学历" prop="education">
        <el-select v-model="queryParams.education" placeholder="请选择学历" clearable>
          <el-option
              v-for="dict in dict.type.tb_education_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="爱好" prop="hobby">
        <el-input
            v-model="queryParams.hobby"
            placeholder="请输入爱好"
            clearable
            @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="帐号状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择帐号状态" clearable>
          <el-option
              v-for="dict in dict.type.tb_member_status"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="删除标志" prop="delFlag">
        <el-select v-model="queryParams.delFlag" placeholder="请选择删除标志" clearable>
          <el-option
              v-for="dict in dict.type.tb_member_status"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
            type="primary"
            plain
            icon="el-icon-plus"
            size="mini"
            @click="handleAdd"
            v-hasPermi="['user:member:add']"
        >新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            type="success"
            plain
            icon="el-icon-edit"
            size="mini"
            :disabled="single"
            @click="handleUpdate"
            v-hasPermi="['user:member:edit']"
        >修改
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            type="danger"
            plain
            icon="el-icon-delete"
            size="mini"
            :disabled="multiple"
            @click="handleDelete"
            v-hasPermi="['user:member:remove']"
        >删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            type="warning"
            plain
            icon="el-icon-download"
            size="mini"
            @click="handleExport"
            v-hasPermi="['user:member:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="memberList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="用户ID" align="center" prop="userId" />
      <el-table-column label="登录名" align="center" prop="username" />
      <el-table-column label="QQ" align="center" prop="qqNumber" />
      <el-table-column label="登录密码" align="center" prop="password" />
      <el-table-column label="邮箱" align="center" prop="email" />
      <el-table-column label="邮件激活" align="center" prop="isActive">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.tb_member_status" :value="scope.row.isActive" />
        </template>
      </el-table-column>
      <el-table-column label="用户性别" align="center" prop="sex">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_user_sex" :value="scope.row.sex" />
        </template>
      </el-table-column>
      <el-table-column label="手机" align="center" prop="telephone" />
      <el-table-column label="学历" align="center" prop="education">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.tb_education_type" :value="scope.row.education" />
        </template>
      </el-table-column>
      <el-table-column label="爱好" align="center" prop="hobby" />
      <el-table-column label="帐号状态" align="center" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.tb_member_status" :value="scope.row.status" />
        </template>
      </el-table-column>
      <el-table-column label="删除标志" align="center" prop="delFlag">
        <template slot-scope="scope">
          <el-switch v-model="scope.row.delFlag" active-value="0" inactive-value="1"
                     @change="handleFlagChange(scope.row)"
          ></el-switch>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
              size="mini"
              type="text"
              icon="el-icon-edit"
              @click="handleUpdate(scope.row)"
              v-hasPermi="['user:member:edit']"
          >修改
          </el-button>
          <el-button
              size="mini"
              type="text"
              icon="el-icon-delete"
              @click="handleDelete(scope.row)"
              v-hasPermi="['user:member:remove']"
          >删除
          </el-button>
          <el-dropdown size="mini" @command="(command) => handleCommand(command, scope.row)" v-hasPermi="['system:role:edit']">
            <span class="el-dropdown-link">
              <i class="el-icon-d-arrow-right el-icon--right"></i>更多
            </span>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item command="handToMemberExtra" icon="el-icon-circle-check"
                                v-hasPermi="['user:extra:list']">会员详情</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </template>
      </el-table-column>
    </el-table>

    <pagination
        v-show="total>0"
        :total="total"
        :page.sync="queryParams.pageNum"
        :limit.sync="queryParams.pageSize"
        @pagination="getList"
    />

    <!-- 添加或修改会员管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="登录名" prop="username">
          <el-input v-model="form.username" placeholder="请输入登录名" />
        </el-form-item>
        <el-form-item label="qqOpenId" prop="qqOpenId">
          <el-input v-model="form.qqOpenId" placeholder="请输入qqOpenId" />
        </el-form-item>
        <el-form-item label="QQ" prop="qqNumber">
          <el-input v-model="form.qqNumber" placeholder="请输入QQ" />
        </el-form-item>
        <el-form-item label="登录密码" prop="password">
          <el-input v-model="form.password" type="password" placeholder="请输入登录密码" />
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="form.email" placeholder="请输入邮箱" />
        </el-form-item>
        <el-form-item label="邮件激活">
          <el-radio-group v-model="form.isActive">
            <el-radio
                v-for="dict in dict.type.tb_member_status"
                :key="dict.value"
                :label="dict.value"
            >{{ dict.label }}
            </el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="用户性别">
          <el-radio-group v-model="form.sex">
            <el-radio
                v-for="dict in dict.type.sys_user_sex"
                :key="dict.value"
                :label="dict.value"
            >{{ dict.label }}
            </el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="手机" prop="telephone">
          <el-input v-model="form.telephone" placeholder="请输入手机" />
        </el-form-item>
        <el-form-item label="学历" prop="education">
          <el-select v-model="form.education" placeholder="请选择学历">
            <el-option
                v-for="dict in dict.type.tb_education_type"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="爱好" prop="hobby">
          <el-input v-model="form.hobby" placeholder="请输入爱好" />
        </el-form-item>
        <el-form-item label="简介" prop="introduce">
          <el-input v-model="form.introduce" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="帐号状态">
          <el-radio-group v-model="form.status">
            <el-radio
                v-for="dict in dict.type.tb_member_status"
                :key="dict.value"
                :label="dict.value"
            >{{ dict.label }}
            </el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="删除标志">
          <el-radio-group v-model="form.delFlag">
            <el-radio
                v-for="dict in dict.type.tb_member_status"
                :key="dict.value"
                :label="dict.value"
            >{{ dict.label }}
            </el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listMember, getMember, delMember, addMember, updateMember, changeUserFlag } from '@/api/user/member'

export default {
  name: 'Member',
  dicts: ['tb_education_type', 'sys_user_sex', 'tb_member_status'],
  data () {
    return {
      // 默认密码
      initPassword: undefined,
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 会员管理表格数据
      memberList: [],
      // 弹出层标题
      title: '',
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        username: null,
        qqNumber: null,
        email: null,
        isActive: null,
        sex: null,
        telephone: null,
        education: null,
        hobby: null,
        status: null,
        delFlag: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        username: [
          { required: true, message: '登录名不能为空', trigger: 'blur' }
        ],
        sex: [
          { required: true, message: '用户性别不能为空', trigger: 'blur' }
        ],
        telephone: [
          { required: true, message: '请输入正确的手机号码', pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/, trigger: 'blur' }
        ],
        email: [
          {
            type: "email",
            message: "请输入正确的邮箱地址",
            trigger: ["blur", "change"]
          }
        ],
        delFlag: [
          { required: true, message: '删除标志不能为空', trigger: 'blur' }
        ],
        createTime: [
          { required: true, message: '创建时间不能为空', trigger: 'blur' }
        ],
        updateTime: [
          { required: true, message: '修改时间不能为空', trigger: 'blur' }
        ]
      }
    }
  },
  created () {
    this.getList ()
    this.getConfigKey ('sys.user.initPassword').then (response => {
      this.initPassword = response.msg
    })
  },

  methods: {
    /** 查询会员管理列表 */
    getList () {
      this.loading = true
      listMember (this.queryParams).then (response => {
        this.memberList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    // 取消按钮
    cancel () {
      this.open = false
      this.reset ()
    },
    // 表单重置
    reset () {
      this.form = {
        userId: null,
        username: null,
        qqOpenId: null,
        qqNumber: null,
        password: null,
        email: null,
        isActive: '0',
        sex: '0',
        telephone: null,
        education: null,
        hobby: null,
        introduce: null,
        status: '0',
        delFlag: '0',
        createTime: null,
        updateTime: null
      }
      this.resetForm ('form')
    },
    /** 搜索按钮操作 */
    handleQuery () {
      this.queryParams.pageNum = 1
      this.getList ()
    },
    /** 重置按钮操作 */
    resetQuery () {
      this.resetForm ('queryForm')
      this.handleQuery ()
    },
    // 多选框选中数据
    handleSelectionChange (selection) {
      this.ids = selection.map (item => item.userId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    // 更多操作触发
    handleCommand(command, row) {
      console.log (command, row)
      switch (command) {
        case "handToMemberExtra":
          this.handToMemberExtra(row);
          break;
        default:
          break;
      }
    },
    handToMemberExtra (row) {
      console.log (row)
      this.$router.push("/user/extra/" + row.userId);
    },
    /** 新增按钮操作 */
    handleAdd () {
      this.reset ()
      this.open = true
      this.title = '添加会员管理'
      this.form.password = this.initPassword
    },
    /** 修改按钮操作 */
    handleUpdate (row) {
      this.reset ()
      const userId = row.userId || this.ids
      getMember (userId).then (response => {
        this.form = response.data
        this.open = true
        this.title = '修改会员管理'
      })
    },
    // 用户状态修改
    handleFlagChange (row) {
      let text = row.delFlag === '0' ? '启用' : '停用'
      this.$modal.confirm ('确认要"' + text + '""' + row.username + '"用户吗？').then (function() {
        return changeUserFlag (row.userId, row.delFlag)
      }).then (() => {
        this.$modal.msgSuccess ('成功')
      }).catch (function() {
        row.delFlag = row.delFlag === '0' ? '1' : '0'
      })
    },
    /** 提交按钮 */
    submitForm () {
      this.$refs['form'].validate (valid => {
        if (valid) {
          if (this.form.userId != null) {
            updateMember (this.form).then (response => {
              this.$modal.msgSuccess ('修改成功')
              this.open = false
              this.getList ()
            })
          } else {
            addMember (this.form).then (response => {
              this.$modal.msgSuccess ('新增成功')
              this.open = false
              this.getList ()
            })
          }
        }
      })
    },
    /** 删除按钮操作 */
    handleDelete (row) {
      const userIds = row.userId || this.ids
      this.$modal.confirm ('是否确认删除会员管理编号为"' + userIds + '"的数据项？').then (function() {
        return delMember (userIds)
      }).then (() => {
        this.getList ()
        this.$modal.msgSuccess ('删除成功')
      }).catch (() => {
      })
    },
    /** 导出按钮操作 */
    handleExport () {
      this.download ('user/member/export', {
        ...this.queryParams
      }, `member_${new Date ().getTime ()}.xlsx`)
    }
  }
}
</script>
