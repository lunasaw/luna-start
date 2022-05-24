<template>
  <el-tree class="filter-tree"
           :props="props" :data="cascadeList" node-key="id"
           show-checkbox
           :default-expand-all=false
           :expand-on-click-node=false
           :check-on-click-node=true
           :default-expanded-keys="defaultExpandedKeys"
           :draggable='draggable'
           ref="categoryTree"
  >
  </el-tree>
</template>
<script>
import {categoryCascadeList} from "@/api/product/category";

export default {
  name: "Category",
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
  created() {
    categoryCascadeList().then(response => {
      this.categoryList = response.data;
      this.cascadeList = response.data;
    });
  },
  methods: {
    // 查询全部及联列表
    getCategoryCascadeList() {
      categoryCascadeList(this.queryParams).then(response => {
        this.cascadeList = response.data;
      });
    },
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
