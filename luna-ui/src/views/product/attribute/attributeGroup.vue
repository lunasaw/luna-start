<template>
  <div class="app-container">
    <el-row :gutter="24" class="mb8 ml5">
      <el-col :span="7">
        <category :showCheckBox=true @category-node-click="categoryNodeClick"></category>
      </el-col>
      <el-col :span="17">

        <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="100px">
          <el-form-item label="属性分类" prop="productAttributeCategoryId">
            <el-select v-model="queryParams.productAttributeCategoryId" filterable placeholder="请选择">
              <el-option
                v-for="item in this.categoryAttributeList"
                :key="item.id"
                :label="item.name"
                :value="item.id"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="属性名称" prop="name">
            <el-input
              v-model="queryParams.name"
              placeholder="请输入属性名称"
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
              v-hasPermi="['product:attribute:add']"
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
              v-hasPermi="['product:attribute:edit']"
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
              v-hasPermi="['product:attribute:remove']"
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
              v-hasPermi="['product:attribute:export']"
            >导出
            </el-button>
          </el-col>
          <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
        </el-row>

        <el-table v-loading="loading" :data="attributeList" @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="55" align="center"/>
          <el-table-column label="属性ID" align="center" prop="id" sortable/>
          <el-table-column label="属性所属分类" align="center" prop="productAttributeCategoryName"/>
          <el-table-column label="属性名称" align="center" prop="name" sortable/>
          <el-table-column label="属性选择类型" align="center" prop="selectType">
            <template slot-scope="scope">
              <dict-tag :options="dict.type.tb_product_attribute_type" :value="scope.row.selectType"/>
            </template>
          </el-table-column>
          <el-table-column label="属性录入方式" align="center" prop="inputType">
            <template slot-scope="scope">
              <dict-tag :options="dict.type.tb_product_attribute_input" :value="scope.row.inputType"/>
            </template>
          </el-table-column>
          <el-table-column label="可选值列表" align="center" prop="inputList"/>

          <el-table-column label="属性图标" align="center" prop="icon" width="100">
            <template slot-scope="scope">
              <image-preview :src="scope.row.icon" :width="50" :height="50"/>
            </template>
          </el-table-column>

          <el-table-column label="备注" align="center" prop="remark"/>
          <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
            <template slot-scope="scope">
              <el-button
                size="mini"
                type="text"
                icon="el-icon-edit"
                @click="handleUpdate(scope.row)"
                v-hasPermi="['product:attribute:edit']"
              >修改
              </el-button>
              <el-button
                size="mini"
                type="text"
                icon="el-icon-delete"
                @click="handleDelete(scope.row)"
                v-hasPermi="['product:attribute:remove']"
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

        <!-- 添加或修改商品属性参数对话框 -->
        <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
          <el-form ref="form" :model="form" :rules="rules" label-width="110px" label-position="center">
            <el-form-item label="属性所属分类" prop="categoryId">
              <el-cascader
                v-model="form.categoryId"
                :options="cascadeList"
                :props="{ multiple: false, emitPath: false, checkStrictly: true,
           placeholder: '请选择上级分类', expandTrigger: 'hover',label	: 'name',value: 'id',children: 'childCategory' }"
                :show-all-levels="true" clearable filterable
            ></el-cascader>
            </el-form-item>

            <el-form-item label="属性组名称" prop="productAttributeCategoryId">
              <el-select v-model="form.productAttributeCategoryId" allow-create filterable placeholder="请选择">
                <el-option
                  v-for="item in this.categoryAttributeList"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id"
                ></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="属性名称" prop="name">
              <el-input v-model="form.name" placeholder="请输入属性名称"/>
            </el-form-item>
            <el-form-item label="属性选择类型" prop="selectType">
              <el-select v-model="form.selectType" placeholder="请选择属性选择类型">
                <el-option
                  v-for="dict in dict.type.tb_product_attribute_type"
                  :key="dict.value"
                  :label="dict.label"
                  :value="parseInt(dict.value)"
                ></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="属性录入方式" prop="inputType">
              <el-select v-model="form.inputType" placeholder="请选择属性录入方式">
                <el-option
                  v-for="dict in dict.type.tb_product_attribute_input"
                  :key="dict.value"
                  :label="dict.label"
                  :value="parseInt(dict.value)"
                ></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="可选值列表" prop="inputList">
              <el-input v-model="form.inputList" placeholder="请输入可选值列表"/>
            </el-form-item>
            <el-form-item label="排序" prop="sort">
              <el-input v-model="form.sort" placeholder="请输入排序"/>
            </el-form-item>
            <el-form-item label="分类筛选样式" prop="filterType">
              <el-select v-model="form.filterType" placeholder="请选择分类筛选样式">
                <el-option
                  v-for="dict in dict.type.tb_product_category_select"
                  :key="dict.value"
                  :label="dict.label"
                  :value="parseInt(dict.value)"
                ></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="属性图标">
              <image-upload v-model="form.icon"/>
            </el-form-item>
            <el-form-item label="检索类型" prop="searchType">
              <el-select v-model="form.searchType" placeholder="请选择检索类型">
                <el-option
                  v-for="dict in dict.type.tb_product_attribute_index"
                  :key="dict.value"
                  :label="dict.label"
                  :value="parseInt(dict.value)"
                ></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="相同属性产品是否关联">
              <el-radio-group v-model="form.relatedStatus">
                <el-radio
                  v-for="dict in dict.type.tb_product_attribute_relation"
                  :key="dict.value"
                  :label="parseInt(dict.value)"
                >{{ dict.label }}
                </el-radio>
              </el-radio-group>
            </el-form-item>
            <el-form-item label="支持手动新增">
              <el-radio-group v-model="form.handAddStatus">
                <el-radio
                  v-for="dict in dict.type.tb_product_attribute_support_normal"
                  :key="dict.value"
                  :label="parseInt(dict.value)"
                >{{ dict.label }}
                </el-radio>
              </el-radio-group>
            </el-form-item>
            <el-form-item label="属性的类型" prop="attrType">
              <el-select v-model="form.attrType" placeholder="请选择属性的类型">
                <el-option
                  v-for="dict in dict.type.tb_attribute_type"
                  :key="dict.value"
                  :label="dict.label"
                  :value="parseInt(dict.value)"
                ></el-option>
              </el-select>
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
      </el-col>
    </el-row>
  </div>

</template>

<script>
import category from "@/views/product/common/category";
import attribute from "@/views/product/attribute/index";
import {attributeCategoryListAll} from "@/api/product/attributeCategory";
import {
  addAttribute,
  delAttribute,
  deletedSwitchChange,
  getAttribute,
  listAttribute, listPageAttribute,
  updateAttribute
} from "@/api/product/attribute";
import {categoryCascadeList} from "@/api/product/category";

export default {
  name: "CategoryGroup",
  components: {
    category,
    attribute
  },
  dicts: ['tb_product_category_select', 'tb_product_attribute_support_normal', 'tb_product_attribute_index', 'tb_product_attribute_relation', 'tb_product_attribute_type', 'tb_product_attribute_input', 'tb_attribute_type'],
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
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 商品属性参数表格数据
      attributeList: [],
      // 属性分类列表
      categoryAttributeList: [],
      // 及联列表
      cascadeList: [],
      // 当前点击的属性分类
      clickedNode: {},
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        id: null,
        productAttributeCategoryId: null,
        name: null,
        selectType: null,
        inputType: null,
        inputList: null,
        sort: null,
        filterType: null,
        icon: null,
        searchType: null,
        relatedStatus: null,
        handAddStatus: null,
        attrType: null,
        deleted: null,
        createTime: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        filterType: [
          {required: true, message: "分类筛选样式不能为空", trigger: "change"}
        ],
        searchType: [
          {required: true, message: "检索类型不能为空", trigger: "change"}
        ],
        relatedStatus: [
          {required: true, message: "相同属性产品是否关联不能为空", trigger: "blur"}
        ],
        handAddStatus: [
          {required: true, message: "支持手动新增不能为空", trigger: "blur"}
        ],
        attrType: [
          {required: true, message: "属性的类型不能为空", trigger: "change"}
        ],
        deleted: [
          {required: true, message: "是否删除不能为空", trigger: "blur"}
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

  },
  created() {
    this.getList();
    this.getCategoryAttributeList();
    this.getCategoryCascadeList();
  },
  methods: {
    categoryNodeClick(data, node, instance) {
      // 获取分类Id传入，查询
      this.clickedNode = data;
      this.getCategoryAttributeList({
        categoryId: data.id
      });
      this.queryParams.categoryId = data.id;
      this.handleQuery();
    },
    getCategoryAttributeList(data) {
      attributeCategoryListAll(data).then(res => {
        this.categoryAttributeList = res.data;
      });
    },
    // 查询全部及联列表
    getCategoryCascadeList() {
      categoryCascadeList().then(response => {
        this.cascadeList = response.data;
      });
    },
    /** 查询商品属性参数列表 */
    getList() {
      this.loading = true;
      listPageAttribute(this.queryParams).then(response => {
        this.attributeList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
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
        productAttributeCategoryId: null,
        name: null,
        selectType: null,
        inputType: null,
        inputList: null,
        sort: null,
        filterType: null,
        icon: null,
        searchType: null,
        relatedStatus: 0,
        handAddStatus: 0,
        attrType: null,
        deleted: null,
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
    deletedSwitchChange(row) {
      let text = row.deleted === 1 ? '启用' : '停用'
      this.$modal.confirm('确认要"' + text + '""' + row.id + '"吗？').then(function () {
        return deletedSwitchChange(row.id, row.deleted)
      }).then(() => {
        this.$modal.msgSuccess('成功')
      }).catch(function () {
        row.deleted = row.deleted === 1 ? 0 : 1
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
      this.title = "添加商品属性参数";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getAttribute(id).then(response => {
        this.form = response.data;
        this.form.categoryId = this.clickedNode.id;
        this.open = true;
        this.title = "修改商品属性参数";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateAttribute(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            if (!this.form.productAttributeCategoryId.isNumber()) {
              this.form.productAttributeCategoryName = this.form.productAttributeCategoryId;
              this.form.productAttributeCategoryId = null;
            }
            if (!this.form.categoryId){
              this.$modal.msgSuccess("请选择商品分类");
              return;
            }
            addAttribute(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除商品属性参数编号为"' + ids + '"的数据项？').then(function () {
        return delAttribute(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('product/attribute/export', {
        ...this.queryParams
      }, `attribute_${new Date().getTime()}.xlsx`)
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

