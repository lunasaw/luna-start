<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="用户ID" prop="userId">
        <el-input
          v-model="queryParams.userId"
          placeholder="请输入用户ID"
          clearable
          @keyup.enter.native="handleQuery"
        />
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
      <el-form-item label="用户头像" prop="headImg">
        <el-input
          v-model="queryParams.headImg"
          placeholder="请输入用户头像"
          clearable
          @keyup.enter.native="handleQuery"
        />
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
          v-hasPermi="['user:extra:add']"
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
          v-hasPermi="['user:extra:edit']"
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
          v-hasPermi="['user:extra:remove']"
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
          v-hasPermi="['user:extra:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="extraList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="用户ID" align="center" prop="userId" />
      <el-table-column label="删除标志" align="center" prop="delFlag">

        <template slot-scope="scope">
          <el-switch v-model="scope.row.delFlag" active-value="0" inactive-value="1"
                     @change="handleFlagChange(scope.row)"
          ></el-switch>
        </template>
      </el-table-column>

      <el-table-column label="用户头像" align="center" prop="headImg" width="100">
        <template slot-scope="scope">
          <image-preview :src="scope.row.headImg" :width="50" :height="50"/>
        </template>
      </el-table-column>

      <el-table-column label="扩展字段" align="center" prop="extend" />
      <el-table-column label="创建时间" align="center" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime (scope.row.createTime, '{y}-{m}-{d} {h}:{m}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="修改时间" align="center" prop="updateTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime (scope.row.updateTime, '{y}-{m}-{d} {h}:{m}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['user:extra:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['user:extra:remove']"
          >删除
          </el-button>
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

    <!-- 添加或修改会员信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="扩展字段" prop="extend">
          <el-input v-model="form.extend" type="textarea" placeholder="请输入内容" />
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
        <el-form-item label="用户头像">
          <image-upload v-model="form.headImg" :limit="1"/>
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
import { listExtra, getExtra, delExtra, addExtra, updateExtra, changeUserFlag } from '@/api/user/extra'

export default {
  name: 'Extra',
  dicts: ['tb_member_status'],
  data () {
    return {
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
      // 会员信息表格数据
      extraList: [],
      // 弹出层标题
      title: '',
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        userId: null,
        delFlag: null,
        headImg: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
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
    const userId = this.$route.params && this.$route.params.userId;
    if (userId) {
      this.queryParams.userId = userId;
    }
    this.getList ()
  },
  methods: {
    /** 查询会员信息列表 */
    getList () {
      this.loading = true
      listExtra (this.queryParams).then (response => {
        this.extraList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    // 取消按钮
    cancel () {
      this.open = false
      this.reset ()
    },
    // 用户状态修改
    handleFlagChange (row) {
      let text = row.delFlag === '0' ? '启用' : '停用'
      this.$modal.confirm ('确认要"' + text + '""' + row.userId + '"用户吗？').then (function() {
        return changeUserFlag (row.userId, row.delFlag)
      }).then (() => {
        this.$modal.msgSuccess ('成功')
      }).catch (function() {
        row.delFlag = row.delFlag === '0' ? '1' : '0'
      })
    },
    // 表单重置
    reset () {
      this.form = {
        userId: null,
        extend: null,
        delFlag: '0',
        createTime: null,
        updateTime: null,
        headImg: null
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
    /** 新增按钮操作 */
    handleAdd () {
      this.reset ()
      this.open = true
      this.title = '添加会员信息'
    },
    /** 修改按钮操作 */
    handleUpdate (row) {
      this.reset ()
      const userId = row.userId || this.ids
      getExtra (userId).then (response => {
        this.form = response.data
        this.open = true
        this.title = '修改会员信息'
      })
    },
    /** 提交按钮 */
    submitForm () {
      this.$refs['form'].validate (valid => {
        if (valid) {
          if (this.form.userId != null) {
            updateExtra (this.form).then (response => {
              this.$modal.msgSuccess ('修改成功')
              this.open = false
              this.getList ()
            })
          } else {
            addExtra (this.form).then (response => {
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
      this.$modal.confirm ('是否确认删除会员信息编号为"' + userIds + '"的数据项？').then (function() {
        return delExtra (userIds)
      }).then (() => {
        this.getList ()
        this.$modal.msgSuccess ('删除成功')
      }).catch (() => {
      })
    },
    /** 导出按钮操作 */
    handleExport () {
      this.download ('user/extra/export', {
        ...this.queryParams
      }, `extra_${new Date ().getTime ()}.xlsx`)
    }
  }
}
</script>
