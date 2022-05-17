<template>
  <div class="app-container">

    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="100px"
             label-position="left">
      <el-form-item label="上级分类" prop="parentId">
        <el-cascader
          v-model="queryParams.parentId"
          :options="categoryList"
          :props="{ multiple: false, emitPath: false, checkStrictly: true,
           placeholder: '请选择上级分类', expandTrigger: 'hover',label	: 'name',value: 'id',children: 'childCategory' }"
          :show-all-levels="false" clearable filterable
          @change="handleChange" @keyup.enter.native="handleQuery"></el-cascader>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>


    <el-row :gutter="10" class="mb8 ml5">

      <el-col :span="8">
        <el-tree class="filter-tree"
                 :props="props" :data="cascadeList" node-key="id"
                 show-checkbox
                 :default-expand-all=false
                 :expand-on-click-node=false
                 :check-on-click-node=true
                 :default-expanded-keys="defaultExpandedKeys"
                 :draggable=true
                 :allow-drop='allowDrop'
                 @check-change="handleCheckChange">

    <span class="custom-tree-node" slot-scope="{ node, data }">
        <span>{{ node.label }}</span>
        <span>
          <el-button v-if="node.level <= 2"
                     type="text"
                     size="mini"
                     @click="() => append(data)">
            添加
          </el-button>
          <el-button
            type="text"
            size="mini"
            @click="() => handleUpdate(data)">
            修改
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
      </el-col>
    </el-row>


    <!-- 添加或修改产品分类对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="上级分类" prop="parentId">
          <el-cascader disabled
                       v-model="form.parentId"
                       :options="cascadeList"
                       :props="{ multiple: false, emitPath: false, checkStrictly: true,
           placeholder: '请选择上级分类', expandTrigger: 'hover',label	: 'name',value: 'id',children: 'childCategory' }"
                       :show-all-levels="true" clearable filterable
                       @change="handleChange" @keyup.enter.native="handleQuery"></el-cascader>
        </el-form-item>
        <el-form-item label="分类名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入分类名称"/>
        </el-form-item>
        <el-form-item label="分类级别" prop="level">
          <el-select v-model="form.level" placeholder="请选择分类级别" disabled>
            <el-option
              v-for="dict in dict.type.tb_product_level"
              :key="dict.value"
              :label="dict.label"
              :value="parseInt(dict.value)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="产品数量" prop="productCount">
          <el-input v-model="form.productCount" placeholder="请输入产品数量"/>
        </el-form-item>
        <el-form-item label="分类属性单位" prop="productUnit">
          <el-input v-model="form.productUnit" placeholder="请输入分类属性单位"/>
        </el-form-item>
        <el-form-item label="显示在导航" prop="navStatus">
          <el-switch v-model="form.navStatus" :active-value="getActiveValue(true)"
                     :inactive-value="getActiveValue(false)"
          ></el-switch>
        </el-form-item>
        <el-form-item label="显示状态" prop="showStatus">
          <el-switch v-model="form.showStatus" :active-value="getActiveValue(true)"
                     :inactive-value="getActiveValue(false)"
          ></el-switch>
        </el-form-item>
        <el-form-item label="排序" prop="sort">
          <el-input v-model="form.sort" placeholder="请输入排序"/>
        </el-form-item>
        <el-form-item label="图标">
          <image-upload v-model="form.icon"/>
        </el-form-item>
        <el-form-item label="关键词" prop="keywords">
          <el-input v-model="form.keywords" placeholder="请输入关键词"/>
        </el-form-item>
        <el-form-item label="描述" prop="description">
          <el-input v-model="form.description" type="textarea" placeholder="请输入内容"/>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容"/>
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
  deleteCategory,
  updateListCategory
} from "@/api/product/category";
import {navStatusSwitchChange} from "@/api/product/category";
import {showStatusSwitchChange} from "@/api/product/category";
import {deletedSwitchChange} from "@/api/product/attribute";

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
      // 默认展开的节点
      defaultExpandedKeys: [],
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
        parentId: undefined,
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
    categoryCascadeList().then(response => {
      this.categoryList = response.data;
      this.cascadeList = response.data;
    });
  },
  methods: {
    append(data) {
      this.handleAdd(data);
    },
    remove(node, data) {
      console.log(node, data)
      let param = {
        id: data.id,
        parentId: data.parentId
      };
      this.$modal.confirm('确认要删除【"' + data.name + '"】吗？').then(function () {
        return deleteCategory(param)
      }).then(() => {
        this.$modal.msgSuccess('删除成功')
        this.getCategoryCascadeList();
        this.defaultExpandedKeys = [node.parent.data.id];
      }).catch(function () {

      })
    },
    // 判断是否能拖拽
    allowDrop(draggingNode, dropNode, type) {
      let maxLevel = this.countNodeLevel(dropNode);
      console.log(maxLevel);
      console.log('============' + draggingNode.data.name);
      console.log(draggingNode);
      console.log('============' + dropNode.data.name);
      console.log(dropNode);
      // 深度 = 最大深度 - 当前深度 + 1
      let dep = maxLevel - draggingNode.data.level + 1;
      if (type === 'inner') {
        // 目标深度 + 深度 <= 3
        return (dep + dropNode.level) <= 3;
      } else {
        return (dep + dropNode.parent.level) <= 3;
      }
    },
    countNodeLevel(node) {
      // 找到所有子节点，求出最大深度
      let maxLevel = node.level;
      if (node.childCategory && node.childCategory.length > 0) {
        node.childCategory.forEach(item => {
          let level = this.countNodeLevel(item);
          if (level > maxLevel) {
            maxLevel = level;
          }
        });
      }
      return maxLevel + 1;
    },
    handleCheckChange() {
      console.log(this.ids);
    },
    // 查询全部及联列表
    getCategoryCascadeList() {
      categoryCascadeList(this.queryParams).then(response => {
        this.cascadeList = response.data;
      });
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
      this.getCategoryCascadeList();
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
    handleAdd(data) {
      this.reset();
      this.open = true;
      this.form.parentId = data.id;
      this.form.level = data.level + 1;
      this.form.navStatus = 1;
      this.form.showStatus = 1;
      this.form.sort = 0;
      this.title = "添加产品分类";
    },
    /** 修改按钮操作 */
    handleUpdate(data) {
      this.reset();
      const id = data.id
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
              this.getCategoryCascadeList();
              this.defaultExpandedKeys = [this.form.parentId];
            });
          } else {
            addCategory(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getCategoryCascadeList();
              this.defaultExpandedKeys = [this.form.parentId];
            });
          }
        }
      });
    }
  }
};
</script>
<style lang="scss" scoped>
.custom-tree-node {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: space-between;
  font-size: 14px;
  padding-right: 8px;
}
</style>

