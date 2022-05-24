<template>
  <div class="app-container">



    <el-row :gutter="24" class="mb8 ml5">
      <el-col :span="12">
        <category  :value="cascadeList" :draggable="draggable"></category>
      </el-col>
      <el-col :span="14" >
        <el-table v-loading="loading" :data="attributeList" @selection-change="handleSelectionChange">
        </el-table>
      </el-col>
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
  deleteCategory,
  updateListCategory, deleteBatchCategory
} from "@/api/product/category";
import {navStatusSwitchChange} from "@/api/product/category";
import {showStatusSwitchChange} from "@/api/product/category";
import {deletedSwitchChange} from "@/api/product/attribute";
import category from "@/views/product/common/category";
import {listAttribute} from "@/api/product/attribute";
export default {
  name: "CategoryGroup",
  components: {
    category
  },
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
      // 是否支持拖拽
      draggable: false,
      // 总条数
      total: 0,
      // 商品属性参数表格数据
      attributeList: [],
      // 产品分类表格数据
      categoryList: [],
      // 及联列表
      cascadeList: [],
      // 更新节点
      updateNodes: [],
      // 操作节点
      parentId: [],
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
        draggable: [
          {
            required: true, message: "请先保存数据", trigger: "blur", validator: (rule, value, callback) => {
              if (this.draggable) {
                if (this.form.id) {
                  callback();
                } else {
                  callback(new Error("请先保存数据"));
                }
              } else {
                callback();
              }
            }
          },
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
  watch: {
    "queryParams.parentId": function (val) {
      this.queryParams.parentId = val;
      this.getCategoryCascadeList();
    },
    "draggable": function (val) {
      if (!val) {
        this.getCategoryCascadeList();
      }
    }
  },
  created() {
    this.getList();
    categoryCascadeList().then(response => {
      this.categoryList = response.data;
      this.cascadeList = response.data;
    });
  },
  methods: {
    /** 查询商品属性参数列表 */
    getList() {
      this.loading = true;
      listAttribute(this.queryParams).then(response => {
        this.attributeList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    append(data) {
      this.handleAdd(data);
    },
    deleteBatch: function () {
      let checkNodes = this.$refs.categoryTree.getCheckedNodes();

      let checkedKeys = this.$refs.categoryTree.getCheckedKeys();
      this.$modal.confirm('确认要删除【' + checkedKeys + '】吗？').then(function () {
        return deleteBatchCategory(checkedKeys);
      }).then(() => {
        this.$message({
          message: "删除成功",
          type: "success"
        });
        this.getCategoryCascadeList();
      }).catch(function () {
        this.$message({
          message: "删除失败",
          type: "error"
        });
      })
      console.log("被选中的元素", checkNodes);
    },
    remove(node, data) {
      console.log(node, data)
      let param = {
        id: data.id,
        parentId: data.parentId
      };
      this.$modal.confirm('确认要删除【' + data.name + '】吗？').then(function () {
        return deleteCategory(param)
      }).then(() => {
        this.$modal.msgSuccess('删除成功')
        this.getCategoryCascadeList();
        this.defaultExpandedKeys = [node.parent.data.id];
      }).catch(function () {

      })
    },
    saveBatch() {
      updateListCategory(this.updateNodes).then(() => {
        this.$modal.msgSuccess('更新成功');
        this.getCategoryCascadeList();
        this.defaultExpandedKeys = this.parentId;
        this.updateNodes = [];
      });
    },
    handleDrop(draggingNode, dropNode, dropType, ev) {
      console.log('tree drop: ', '当前拖拽节点', draggingNode, '当前拖拽目的节点', dropNode, dropType);
      // 当前节点对父节点
      let parentId = this.parentId;
      if (dropType === 'inner') {
        parentId = dropNode.data.id;
      } else {
        parentId = dropNode.parent.data.id || 0;
      }
      // 最新顺序
      // 1. 获取所有兄弟节点
      let childNodes;
      if (dropType === 'inner') {
        childNodes = dropNode.childNodes;
      } else {
        childNodes = dropNode.parent.childNodes;
      }
      console.log("childNodes", childNodes);
      // 2. 获取当前节点的顺序
      let index = 0;
      childNodes.forEach(e => {
        index++;
        if (e.data.id === draggingNode.data.id) {
          // 如果是当前正在拖拽对节点
          let catLevel = draggingNode.data.level;
          console.log('当前节点等级', e.level, '目标节点等级', catLevel);
          if (e.level !== catLevel) {
            console.log('当前节点等级', e.level, '目标节点等级', catLevel);
            catLevel = e.level;
            // 是外部的话 还需要修改子节点层级
            this.updateChildNodeLevel(e);
          }
          // 放入更新节点level
          this.updateNodes.push({
            id: e.data.id,
            sort: index,
            parentId: parentId,
            level: catLevel
          });
          return;
        } else {
          this.updateNodes.push({
            id: e.data.id,
            sort: index
          });
        }
      });
      // 当前节点对最近节点
      // 当前节点对最近节点的父节点
      this.parentId.push(parentId);
      console.log(this.updateNodes);
    },
    updateChildNodeLevel(node) {
      console.log('更新子节点层级', node);
      node.childNodes.forEach(e => {
        this.updateNodes.push({
          id: e.data.id,
          sort: e.data.sort,
          parentId: node.data.id,
          level: e.level
        });
        this.updateChildNodeLevel(e);
      });
    },
    // 判断是否能拖拽
    allowDrop(draggingNode, dropNode, type) {
      // 拖拽节点 拖拽到目标节点 的某个位置
      let maxLevel = this.countNodeLevel(draggingNode);
      console.log(maxLevel);
      console.log('============' + draggingNode.data.name);
      console.log(draggingNode);
      console.log('============' + dropNode.data.name);
      console.log(dropNode);
      // 深度 = 最大深度 - 当前深度 + 1
      let dep = Math.abs(maxLevel - draggingNode.level + 1);
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
      if (node.childNodes && node.childNodes.length > 0) {
        node.childNodes.forEach(item => {
          let level = this.countNodeLevel(item);
          if (level > maxLevel) {
            maxLevel = level;
          }
        });
      }
      return maxLevel;
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
          if (this.draggable || this.updateNodes.length > 0) {
            this.$modal.msgSuccess('请先保存数据或关闭拖拽');
            return;
          }
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

