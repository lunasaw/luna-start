<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="100px">
      <el-form-item label="分类" prop="categoryId">
        <el-cascader
          v-model="queryParams.categoryId"
          :options="cascadeList"
          :props="{ multiple: false, emitPath: false, checkStrictly: true,
           placeholder: '请选择分类', expandTrigger: 'hover',label	: 'name',value: 'id',children: 'childCategory' }"
          :show-all-levels="false" clearable filterable
          @keyup.enter.native="handleQuery"></el-cascader>
      </el-form-item>
      <el-form-item label="分类属性名" prop="name" label-width="100px">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入分类属性名"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="属性数量" prop="attributeCount">
        <el-input
          v-model="queryParams.attributeCount"
          placeholder="请输入属性数量"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="参数数量" prop="paramCount">
        <el-input
          v-model="queryParams.paramCount"
          placeholder="请输入参数数量"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="是否删除" prop="navStatus">
        <el-switch v-model="queryParams.deleted" :active-value=getActiveValue(true)
                   :inactive-value=getActiveValue(false)
        ></el-switch>
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
          v-hasPermi="['product:attributeCategory:add']"
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
          v-hasPermi="['product:attributeCategory:edit']"
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
          v-hasPermi="['product:attributeCategory:remove']"
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
          v-hasPermi="['product:attributeCategory:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="attributeCategoryList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="分类属性ID" align="center" prop="id"/>
      <el-table-column label="所属分类" align="center" prop="categoryName"/>
      <el-table-column label="分类属性名" align="center" prop="name"/>
      <el-table-column label="属性数量" align="center" prop="attributeCount" sortable/>
      <el-table-column label="参数数量" align="center" prop="paramCount" sortable/>
      <el-table-column label="备注" align="center" prop="remark"/>
      <el-table-column label="是否删除" align="center" prop="deleted" width="100">
        <template slot-scope="scope">
          <el-switch v-model="scope.row.deleted" :active-value=getActiveValue(true)
                     :inactive-value=getActiveValue(false)
                     @change="deletedSwitchChange(scope.row)"
          ></el-switch>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-s-tools"
            @click="handleRelation(scope.row)"
            v-hasPermi="['product:attributeCategory:list']"
          >关联
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['product:attributeCategory:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['product:attributeCategory:remove']"
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

    <!-- 添加或修改产品属性分类对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="所属分类" prop="categoryId">
          <el-cascader
            v-model="form.categoryId"
            :options="cascadeList"
            :props="{ multiple: false, emitPath: false, checkStrictly: true,
           placeholder: '请选择分类', expandTrigger: 'hover',label	: 'name',value: 'id',children: 'childCategory' }"
            :show-all-levels="false" clearable filterable
            @keyup.enter.native="handleQuery"></el-cascader>
        </el-form-item>
        <el-form-item label="分类属性名" prop="name" label-width="90px">
          <el-input v-model="form.name" placeholder="请输入分类属性名"/>
        </el-form-item>
        <el-form-item label="属性数量" prop="attributeCount">
          <el-input v-model="form.attributeCount" placeholder="请输入属性数量"/>
        </el-form-item>
        <el-form-item label="参数数量" prop="paramCount">
          <el-input v-model="form.paramCount" placeholder="请输入参数数量"/>
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


    <!-- 属性关联对话框-->
    <el-dialog title="属性关联" :visible.sync="openAttr" width="1000px" append-to-body>
      <el-row :gutter="10" class="mb8">
        <el-col :span="1.5">
          <el-button
            type="primary"
            plain
            icon="el-icon-plus"
            size="mini"
            @click="handleRelationAdd"
            v-hasPermi="['product:attributeCategory:add']"
          >新增
          </el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="danger"
            plain
            icon="el-icon-delete"
            size="mini"
            :disabled="multipleRelation"
            @click="handleRelationDelete"
            v-hasPermi="['product:attributeCategory:remove']"
          >删除
          </el-button>
        </el-col>
      </el-row>
      <el-table v-loading="loading" :data="attrList" @selection-change="handleSelectionChangeRelation">

        <el-table-column type="selection" width="55" align="center"/>
        <el-table-column label="属性ID" align="center" prop="id" sortable/>
        <el-table-column label="属性所属分类" align="center" prop="productAttributeCategoryName"/>
        <el-table-column label="属性名称" align="center" prop="name" sortable/>
        <el-table-column label="可选值列表" align="center" prop="inputList"/>
        <el-table-column label="属性的类型" align="center" prop="attrType">
          <template slot-scope="scope">
            <dict-tag :options="dict.type.tb_attribute_type" :value="scope.row.attrType"/>
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="text"
              icon="el-icon-delete"
              @click="handleRelationDelete(scope.row)"
              v-hasPermi="['product:brandRelation:remove']"
            >删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      <pagination
        v-show="relationTotal>0"
        :total="relationTotal"
        :page.sync="queryRelationParams.pageNum"
        :limit.sync="queryRelationParams.pageSize"
        @pagination="handleRelationCategory"
      />
    </el-dialog>

    <!-- 属性关联对话框-->
    <el-dialog title="新增属性关联" :visible.sync="openAddAttr" width="1000px" append-to-body>
      <el-row :gutter="10" class="mb8">
        <el-col :span="1.5">
          <el-button
            type="danger"
            plain
            icon="el-icon-plus"
            size="mini"
            @click="handleRelationEdit"
            v-hasPermi="['product:attribute:edit']"
          >关联
          </el-button>
        </el-col>
      </el-row>
      <el-table v-loading="loading" :data="attrAddList" @selection-change="handleSelectionAddChangeRelation">

        <el-table-column type="selection" width="55" align="center"/>
        <el-table-column label="属性ID" align="center" prop="id" sortable/>
        <el-table-column label="属性所属分类" align="center" prop="productAttributeCategoryName"/>
        <el-table-column label="属性名称" align="center" prop="name" sortable/>
        <el-table-column label="可选值列表" align="center" prop="inputList"/>
        <el-table-column label="属性的类型" align="center" prop="attrType">
          <template slot-scope="scope">
            <dict-tag :options="dict.type.tb_attribute_type" :value="scope.row.attrType"/>
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-button
              type="danger"
              icon="el-icon-edit"
              size="mini"
              @click="handleRelationEdit(scope.row)"
            >关联
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      <pagination
        v-show="relationAddTotal>0"
        :total="relationAddTotal"
        :page.sync="queryAddRelationParams.pageNum"
        :limit.sync="queryAddRelationParams.pageSize"
        @pagination="handleAddRelationCategory"
      />
    </el-dialog>
  </div>
</template>

<script>
import {
  listAttributeCategory,
  getAttributeCategory,
  delAttributeCategory,
  addAttributeCategory,
  addListAttributeCategory,
  listPageAttributeCategory,
  attributeCategoryListByIds,
  attributeCategoryListAll,
  deleteAttributeCategory,
  updateAttributeCategory,
  updateListAttributeCategory
}
  from
    "@/api/product/attributeCategory";
import {deletedSwitchChange} from "@/api/product/attributeCategory";
import {categoryCascadeList} from "@/api/product/category";
import {
  delAttribute,
  fixCategory,
  listAttribute,
  listPageAttribute,
  listPageFilterAttribute
} from "@/api/product/attribute";
import {listBrandRelation} from "@/api/product/brandRelation";

export default {
  name: "AttributeCategory",
  dicts: ['tb_product_level', 'tb_normal_status', 'tb_product_category_select', 'tb_product_attribute_support_normal', 'tb_product_attribute_index', 'tb_product_attribute_relation', 'tb_product_attribute_type', 'tb_product_attribute_input', 'tb_attribute_type'],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 属性选中列表
      idsRelation: [],
      idsRelationAdd: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 属性列表非多个禁用
      multipleRelation: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 关联总条数
      relationTotal: 0,
      // 关联属性总条数
      relationAddTotal: 0,
      // 产品属性分类表格数据
      attributeCategoryList: [],
      // 及联列表
      cascadeList: [],
      // 属性列表
      attrList: [],
      // 候选属性列表
      attrAddList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 是否显示关联弹出框
      openAttr: false,
      // 是否显示新增关联弹出框
      openAddAttr: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        categoryId: null,
        name: null,
        attributeCount: null,
        paramCount: null,
        deleted: null
      },
      queryRelationParams: {
        pageNum: 1,
        pageSize: 10,
      },
      queryAddRelationParams: {
        pageNum: 1,
        pageSize: 10,
      },
      // 表单参数
      form: {},
      // 表单参数
      relationForm: {},
      // 表单参数
      relationAddForm: {},
      // 表单校验
      rules: {
        createTime: [
          {required: true, message: "创建时间不能为空", trigger: "blur"}
        ],
        updateTime: [
          {required: true, message: "更新时间不能为空", trigger: "blur"}
        ],
        deleted: [
          {required: true, message: "是否删除不能为空", trigger: "blur"}
        ]
      }
    };
  },
  created() {
    this.getList();
    this.getCategoryCascadeList();
  },
  methods: {
    handleRelationCategory(row) {
      this.queryRelationParams.productAttributeCategoryId = row.id;
      this.relationForm.categortyId = row.categortyId;
      this.relationForm.categortyName = row.categortyName;
      listBrandRelation(this.queryRelationParams).then(res => {
        this.attrList = res.rows;
        this.relationTotal = res.total;
        this.openRelation = true;
      })
    },
    handleAddRelationCategory(row) {
      let query = {
        categoryId: this.queryRelationParams.categoryId,
        productAttributeCategoryId: this.attrCateId,
      }
      listPageFilterAttribute(query).then(response => {
        this.attrAddList = response.rows;
        this.relationAddTotal = response.total;
        console.log(this.attrAddList);
      });
    },
    handleSelectionChangeRelation(selection) {
      this.idsRelation = selection.map(item => item.id)
      this.multipleRelation = !selection.length
    },
    handleSelectionAddChangeRelation(selection) {
      this.idsRelationAdd = selection.map(item => item.id)
    },
    /** 查询产品属性分类列表 */
    getList() {
      this.loading = true;
      listAttributeCategory(this.queryParams).then(response => {
        this.attributeCategoryList = response.rows;
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
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        categoryId: null,
        name: null,
        attributeCount: null,
        paramCount: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        remark: null,
        deleted: null
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
      this.title = "添加产品属性分类";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getAttributeCategory(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改产品属性分类";
      });
    },
    /** 属性关联按钮操作 */
    handleRelation(row) {
      // 列出当前分组所有属性
      let attrCateId = row.id;
      this.queryRelationParams.categoryId = row.categoryId;
      this.queryRelationParams.productAttributeCategoryId = attrCateId;
      let query = {
        productAttributeCategoryId: attrCateId,
      }
      listAttribute(query).then(response => {
        this.attrList = response.rows;
        this.relationTotal = response.total;
        console.log(this.queryRelationParams);
        this.attrCateId = attrCateId;
        this.openAttr = true;
      });
    },
    handleRelationAdd() {
      // 弹出添加对话框
      this.openAddAttr = true;
      // 查询分组所属分类的全部属性
      let query = {
        categoryId: this.queryRelationParams.categoryId,
        productAttributeCategoryId: this.attrCateId,
      }
      listPageFilterAttribute(query).then(response => {
        this.attrAddList = response.rows;
        this.relationAddTotal = response.total;
        console.log(this.attrAddList);
      });
    },
    handleRelationDelete(row) {
      const ids = row.id || this.idsRelation;
      this.$modal.confirm('是否确认删除商品属性参数编号为"' + ids + '"的数据项？').then(function () {
        return delAttribute(ids);
      }).then(() => {
        let query = {
          productAttributeCategoryId: this.queryRelationParams.productAttributeCategoryId
        }
        listAttribute(query).then(response => {
          this.attrList = response.rows;
          this.relationTotal = response.total;
          console.log(this.queryRelationParams);
          this.attrCateId = attrCateId;
          this.openAttr = true;
        });
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    handleRelationEdit(row) {
      const ids = row.id || this.idsRelationAdd;
      if (ids.length < 0) {
        return;
      }
      console.log(ids)
      this.$modal.confirm('是否确认关联产品属性编号为"' + ids + '"的数据项？').then(function () {
      }).then(() => {
        let data = [];
        if (ids.length >= 1) {
          data = ids;
        } else {
          data.push(ids);
        }
        let query = {
          attrIds: data,
          productAttributeCategoryId: this.queryRelationParams.productAttributeCategoryId
        }
        fixCategory(query).then(res => {
          if (res.data < 0) {
            return;
          }
          this.openAddAttr = false;
          let queryList = {
            productAttributeCategoryId: this.attrCateId,
          }
          listAttribute(queryList).then(response => {
            this.attrList = response.rows;
            this.relationTotal = response.total;
            console.log(this.attrAddList);
          });
        })

      }).catch(() => {
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateAttributeCategory(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addAttributeCategory(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除产品属性分类编号为"' + ids + '"的数据项？').then(function () {
        return delAttributeCategory(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('product/attributeCategory/export', {
        ...this.queryParams
      }, `attributeCategory_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
