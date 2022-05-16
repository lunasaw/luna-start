<template>
  <div class="app-container">

    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="100px" label-position="left">
      <el-form-item label="上级分类" prop="parentId">
        <el-cascader
          v-model="queryParams.parentId"
          :options="cascadeList"
          :props="{ multiple: false, emitPath: false, checkStrictly: true,
           placeholder: '请选择上级分类', expandTrigger: 'hover',label	: 'name',value: 'id',children: 'childCategory' }"
          :show-all-levels="false" clearable filterable
          @change="handleChange" @keyup.enter.native="handleQuery"></el-cascader>
      </el-form-item>
      <el-form-item label="分类名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入分类名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="分类级别" prop="level">
        <el-select v-model="queryParams.level" placeholder="请选择分类级别" clearable>
          <el-option
            v-for="dict in dict.type.tb_product_level"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="产品数量" prop="productCount">
        <el-input
          v-model="queryParams.productCount"
          placeholder="请输入产品数量"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="分类属性单位" prop="productUnit">
        <el-input
          v-model="queryParams.productUnit"
          placeholder="请输入分类属性单位"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="显示在导航" prop="navStatus">
        <el-select v-model="queryParams.navStatus" placeholder="请选择显示在导航" clearable>
          <el-option
            v-for="dict in dict.type.tb_normal_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="显示状态" prop="showStatus">
        <el-select v-model="queryParams.showStatus" placeholder="请选择显示状态" clearable>
          <el-option
            v-for="dict in dict.type.tb_normal_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="排序" prop="sort">
        <el-input
          v-model="queryParams.sort"
          placeholder="请输入排序"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="关键词" prop="keywords">
        <el-input
          v-model="queryParams.keywords"
          placeholder="请输入关键词"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>


    <el-row :gutter="10" class="mb8 ml5">
      <el-tree
        :props="props" :data="cascadeList" node-key="id"
        show-checkbox
        :default-expand-all=false
        :expand-on-click-node=false
        :check-on-click-node=true
        @check-change="handleCheckChange">

    <span class="custom-tree-node fixed-width right" slot-scope="{ node, data }">
        <span>{{ node.label }}</span>
        <span>
          <el-button v-if="node.level <= 2"
                     type="text"
                     size="mini"
                     @click="() => append(data)">
            添加
          </el-button>
          <el-button v-if="node.childNodes.length === 0"
                     type="text"
                     size="mini"
                     @click="() => remove(node, data)">
            删除
          </el-button>
        </span>
      </span>
      </el-tree>

    </el-row>
  </div>

</template>

<script>
import {
  listCategory,
  getCategory,
  delCategory,
  addCategory,
  addListCategory,
  listPageCategory,
  categoryListByIds,
  categoryListAll,
  categoryCascadeList,
  updateCategory,
  updateListCategory
} from "@/api/product/category";
import {navStatusSwitchChange} from "@/api/product/category";
import {showStatusSwitchChange} from "@/api/product/category";

export default {
  name: "Category",
  dicts: ['tb_product_level', 'tb_normal_status'],
  data() {
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
      // 产品分类表格数据
      categoryList: [],
      // 及联列表
      cascadeList: [],
      props: {
        children: 'childCategory',
        label: 'name',
        value: 'id',
      },
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        parentId: null,
        name: null,
        level: null,
        productCount: null,
        productUnit: null,
        navStatus: null,
        showStatus: null,
        sort: null,
        icon: null,
        keywords: null,
        description: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        navStatus: [
          {required: true, message: "显示在导航不能为空", trigger: "blur"}
        ],
        showStatus: [
          {required: true, message: "显示状态不能为空", trigger: "blur"}
        ],
        createTime: [
          {required: true, message: "创建时间不能为空", trigger: "blur"}
        ],
        updateTime: [
          {required: true, message: "更新时间不能为空", trigger: "blur"}
        ],
      }
    };
  },
  created() {
    this.getList();
    this.getCategoryCascadeList();
  },
  methods: {
    append(data) {
      console.log(data)
    },

    remove(node, data) {
      console.log(data)
    },
    handleCheckChange() {
      console.log(this.ids);
    },
    /** 查询产品分类列表 */
    getList() {
      this.loading = true;
      listCategory(this.queryParams).then(response => {
        this.categoryList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 查询全部及联列表
    getCategoryCascadeList() {
      categoryCascadeList().then(response => {
        this.cascadeList = response.data;
      });
    },
    // 查询下级
    selectLowerLevel(row) {
      this.queryParams.parentId = row.id;
      this.getList();
    },
    handleChange(val) {
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        parentId: null,
        name: null,
        level: null,
        productCount: null,
        productUnit: null,
        navStatus: null,
        showStatus: null,
        sort: null,
        icon: null,
        keywords: null,
        description: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        remark: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    // 状态修改
    navStatusSwitchChange(row) {
      let text = row.navStatus === 1 ? '启用' : '停用'
      this.$modal.confirm('确认要"' + text + '""' + row.id + '"吗？').then(function () {
        return navStatusSwitchChange(row.id, row.navStatus)
      }).then(() => {
        this.$modal.msgSuccess('成功')
      }).catch(function () {
        row.navStatus = row.navStatus === 1 ? 0 : 1
      })
    },
    // 状态修改
    showStatusSwitchChange(row) {
      let text = row.showStatus === 1 ? '启用' : '停用'
      this.$modal.confirm('确认要"' + text + '""' + row.id + '"吗？').then(function () {
        return showStatusSwitchChange(row.id, row.showStatus)
      }).then(() => {
        this.$modal.msgSuccess('成功')
      }).catch(function () {
        row.showStatus = row.showStatus === 1 ? 0 : 1
      })
    },
    getActiveValue(value) {
      if (value) {
        return Number("1")
      } else {
        return Number("0")
      }
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加产品分类";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getCategory(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改产品分类";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateCategory(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addCategory(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除产品分类编号为"' + ids + '"的数据项？').then(function () {
        return delCategory(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('product/category/export', {
        ...this.queryParams
      }, `category_${new Date().getTime()}.xlsx`)
    },
    /** 菜单维护按钮操作 */
    handleEdit() {
      this.$router.push("/category/category-tree");
    }
  }
};
</script>
