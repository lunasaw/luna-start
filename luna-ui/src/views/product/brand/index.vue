<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="品牌名" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入品牌名"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="首字母" prop="firstLetter">
        <el-input
          v-model="queryParams.firstLetter"
          placeholder="请输入首字母"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="排序" prop="sort">
        <el-input
          v-model="queryParams.sort"
          placeholder="请输入排序"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="产品数量" prop="productCount">
        <el-input
          v-model="queryParams.productCount"
          placeholder="请输入产品数量"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="评论数量" prop="productCommentCount" >
        <el-input
          v-model="queryParams.productCommentCount"
          placeholder="请输入产品评论数量"
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
          v-hasPermi="['product:brand:add']"
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
          v-hasPermi="['product:brand:edit']"
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
          v-hasPermi="['product:brand:remove']"
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
          v-hasPermi="['product:brand:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="brandList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="品牌ID" align="center" prop="id" sortable/>
      <el-table-column label="品牌名" align="center" prop="name"/>
      <el-table-column label="首字母" align="center" prop="firstLetter"/>
      <el-table-column label="排序" align="center" prop="sort" sortable/>
      <el-table-column label="是否为品牌制造商" align="center" prop="factoryStatus" width="100" sortable>
        <template slot-scope="scope">
          <el-switch v-model="scope.row.factoryStatus" :active-value=getActiveValue(true)
                     :inactive-value=getActiveValue(false)
                     @change="factoryStatusSwitchChange(scope.row)"
          ></el-switch>
        </template>
      </el-table-column>
      <el-table-column label="是否展示" align="center" prop="showStatus" width="100" sortable>
        <template slot-scope="scope">
          <el-switch v-model="scope.row.showStatus" active-value="1" inactive-value="0"
                     @change="showStatusSwitchChange(scope.row)"
          ></el-switch>
        </template>
      </el-table-column>
      <el-table-column label="关联分类" align="center" width="100" >
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-share"
            @click="handleRelationCategory(scope.row)"
            v-hasPermi="['product:brand:edit']"
          >关联分类
          </el-button>
        </template>
      </el-table-column>
      <el-table-column label="产品数量" align="center" prop="productCount"/>
      <el-table-column label="产品评论数量" align="center" prop="productCommentCount"/>
      <el-table-column label="品牌logo" align="center" prop="logo" width="100">
        <template slot-scope="scope">
          <image-preview :src="scope.row.logo" :width="50" :height="50"/>
        </template>
      </el-table-column>
      <el-table-column label="专区大图" align="center" prop="bigPic" width="100">
        <template slot-scope="scope">
          <image-preview :src="scope.row.bigPic" :width="50" :height="50"/>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['product:brand:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['product:brand:remove']"
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

    <!-- 添加或修改品牌对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="140px">
        <el-form-item label="品牌名" prop="name">
          <el-input v-model="form.name" placeholder="请输入品牌名"/>
        </el-form-item>
        <el-form-item label="首字母" prop="firstLetter">
          <el-input v-model="form.firstLetter" placeholder="请输入首字母"/>
        </el-form-item>
        <el-form-item label="排序" prop="sort">
          <el-input v-model.number="form.sort" placeholder="请输入排序"/>
        </el-form-item>
        <el-form-item label="是否为品牌制造商" prop="factoryStatus">
          <el-switch v-model="form.factoryStatus" :active-value=getActiveValue(true)
                     :inactive-value=getActiveValue(false)
          ></el-switch>
        </el-form-item>
        <el-form-item label="是否展示" prop="showStatus">
          <el-switch v-model="form.showStatus" active-value="1" inactive-value="0"
          ></el-switch>
        </el-form-item>
        <el-form-item label="产品数量" prop="productCount">
          <el-input v-model="form.productCount" placeholder="请输入产品数量"/>
        </el-form-item>
        <el-form-item label="产品评论数量" prop="productCommentCount">
          <el-input v-model="form.productCommentCount" placeholder="请输入产品评论数量"/>
        </el-form-item>
        <el-form-item label="品牌logo">
          <image-upload v-model="form.logo"/>
        </el-form-item>
        <el-form-item label="专区大图">
          <oss-upload v-model="form.bigPic"/>
        </el-form-item>
        <el-form-item label="品牌故事" prop="brandStory">
          <el-input v-model="form.brandStory" type="textarea" placeholder="请输入内容"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 品牌关联对话框-->
    <el-dialog title="品牌关联" :visible.sync="openRelation" width="700px" append-to-body>
      <el-row :gutter="10" class="mb8">
        <el-col :span="1.5">
          <el-button
            type="primary"
            plain
            icon="el-icon-plus"
            size="mini"
            @click="handleRelationAdd"
            v-hasPermi="['product:brandRelation:add']"
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
            v-hasPermi="['product:brandRelation:remove']"
          >删除
          </el-button>
        </el-col>
      </el-row>
      <el-table v-loading="loading" :data="brandRelationList" @selection-change="handleSelectionChangeRelation">
        <el-table-column type="selection" width="55" align="center"/>
        <el-table-column label="关联ID" align="center" prop="id"/>
        <el-table-column label="品牌名称" align="center" prop="brandName"/>
        <el-table-column label="分类名称" align="center" prop="categoryName"/>
        <el-table-column label="是否删除" align="center" prop="deleted" width="100">
          <template slot-scope="scope">
            <el-switch v-model="scope.row.deleted" :active-value=getActiveValue(true)
                       :inactive-value=getActiveValue(false)
                       @change="deletedSwitchChange(scope.row)"
            ></el-switch>
          </template>
        </el-table-column>
        <el-table-column label="备注" align="center" prop="remark"/>
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

    <!-- 添加或修改品牌分类关联对话框 -->
    <el-dialog title="品牌关联新增" :visible.sync="openRelationAdd" width="500px" append-to-body>
      <el-form ref="relationForm" :model="relationForm" label-width="100px" label-position="left">
        <el-form-item label="品牌名称" prop="name">
          <el-input v-model="relationForm.name" disabled placeholder="请输入品牌名"/>
        </el-form-item>
        <el-form-item label="分类名称" prop="categoryId">
          <el-cascader
            v-model="relationForm.categoryId"
            :options="cascadeList"
            :props="{ multiple: false, emitPath: false, checkStrictly: false,
           placeholder: '请选择上级分类', expandTrigger: 'hover',label	: 'name',value: 'id',children: 'childCategory' }"
            :show-all-levels="true" clearable filterable></el-cascader>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="relationForm.remark" type="textarea" placeholder="请输入内容"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitRelationForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
     </div>
</template>

<script>
import {listBrand, getBrand, delBrand, addBrand, updateBrand} from "@/api/product/brand";
import {factoryStatusSwitchChange} from "@/api/product/brand";
import {showStatusSwitchChange} from "@/api/product/brand";
import {getOssPolicy} from "@/api/monitor/server";
import ossUpload from "@/components/ImageUpload/ossUpload";
import {
  addBrandRelation,
  delBrandRelation,
  deletedSwitchChange,
  listBrandRelation,
  updateBrandRelation
} from "@/api/product/brandRelation";
import {categoryCascadeList} from "@/api/product/category";

export default {
  name: "Brand",
  dicts: ['tb_product_status'],
  components: {
    ossUpload,
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
      // 非多个禁用
      multipleRelation: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 关联总条数
      relationTotal: 0,
      // 品牌表格数据
      brandList: [],
      // 品牌分类关联表格数据
      brandRelationList: [],
      // 及联列表
      cascadeList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 是否显示关联弹出层
      openRelation: false,
      // 临时操作品牌Id
      tempBrandId: "",
      // 是否显示关联新增弹出层
      openRelationAdd: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: null,
        firstLetter: null,
        sort: null,
        factoryStatus: null,
        showStatus: null,
        productCount: null,
        productCommentCount: null,
      },
      queryRelationParams: {
        pageNum: 1,
        pageSize: 10,
      },
      // 表单参数
      form: {},
      // 表单参数
      relationForm: {},
      // 表单校验
      rules: {
        name: [
          {required: true, message: "品牌名称不能为空", trigger: "blur"}
        ],
        firstLetter: [
          {
            validator: (rule, value, callback) => {
              if (value === '') {
                callback(new Error('首字母不能为空'));
              } else if (!/^[a-zA-Z]*$/.test(value)) {
                callback(new Error('首字母只能是字母'));
              } else {
                callback();
              }
            },
            trigger: "blur"
          }
        ],
        sort: [
          {
            validator: (rule, value, callback) => {
              if (value === '') {
                callback(new Error('排序不能为空'));
              } else if (!/^[0-9]*$/.test(value)) {
                callback(new Error('排序只能是数字'));
              } else {
                callback();
              }
            }, trigger: "blur"
          }
        ],
        showStatus: [
          {required: true, message: "是否展示不能为空", trigger: "blur"}
        ],
      },
    };
  },
  created() {
    this.getList();
    this.getCategoryCascadeList();
  }
  ,
  methods: {
    // 查询全部及联列表
    getCategoryCascadeList() {
      categoryCascadeList().then(response => {
        this.cascadeList = response.data;
      });
    },
    getBrandList(value) {
      if (!value) {
        this.brandList = [];
        return;
      }
      let query = {
        name : value
      }
      listBrand(query).then(res => {
        this.brandList = res.rows;
      }).catch(() => {
        this.brandList = [];
      });
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
    handleRelationCategory(row) {
      this.queryRelationParams.brandId = row.id;
      this.relationForm.brandId = row.id;
      this.relationForm.name = row.name;
      listBrandRelation(this.queryRelationParams).then(res => {
        this.brandRelationList = res.rows;
        this.relationTotal = res.total;
        this.openRelation = true;
      })
    },
    uploadOss(row) {
      let data = {
        ileName: "luna.png",
        expireTime: 3000,
      };
      console.log(row);
      getOssPolicy(data).then(res => {
        console.log(res);
      });
    },
    /** 查询品牌列表 */
    getList() {
      this.loading = true;
      listBrand(this.queryParams).then(response => {
        this.brandList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.openRelationAdd = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        name: null,
        firstLetter: null,
        sort: null,
        factoryStatus: null,
        showStatus: null,
        productCount: null,
        productCommentCount: null,
        logo: null,
        bigPic: null,
        brandStory: null
      };
      this.relationForm.categoryId = null;
      this.resetForm("form");
      this.resetForm("relationForm");
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

    handleSelectionChangeRelation(selection) {
      this.idsRelation = selection.map(item => item.id)
      this.multipleRelation = !selection.length
    },
    // 状态修改
    factoryStatusSwitchChange(row) {
      let text = row.factoryStatus === 1 ? '启用' : '停用'
      this.$modal.confirm('确认要"' + text + '""' + row.name + '"吗？').then(function () {
        return factoryStatusSwitchChange(row.id, row.factoryStatus)
      }).then(() => {
        this.$modal.msgSuccess('成功')
      }).catch(function () {
        row.factoryStatus = row.factoryStatus === 1 ? 0 : 1
      })
    },
    // 状态修改
    showStatusSwitchChange(row) {
      let text = row.showStatus === '1' ? '启用' : '停用'
      this.$modal.confirm('确认要"' + text + '""' + row.name + '"吗？').then(function () {
        return showStatusSwitchChange(row.id, row.showStatus)
      }).then(() => {
        this.$modal.msgSuccess('成功')
      }).catch(function () {
        row.showStatus = row.showStatus === '1' ? '0' : '1'
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
      this.title = "添加品牌";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getBrand(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改品牌";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateBrand(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addBrand(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },

    /** 提交按钮 */
    submitRelationForm() {
      console.log(this.relationForm)
      this.$refs["relationForm"].validate(valid => {
        if (valid) {
          if (this.relationForm.brandId != null) {
            addBrandRelation(this.relationForm).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.openRelationAdd = false;
              this.handleRelationCategory({
                id: this.relationForm.brandId
              })
            });
          }
        }
      });
    },
    /** 新增按钮操作 */
    handleRelationAdd() {
      this.reset();
      this.openRelationAdd = true;
    },
    /** 删除关联按钮操作 */
    handleRelationDelete(row) {
      const ids = row.id || this.idsRelation;
      this.$modal.confirm('是否确认删除品牌分类关联编号为"' + ids + '"的数据项？').then(function () {
        return delBrandRelation(ids);
      }).then(() => {
        this.listBrandRelation({
          brandId: this.relationForm.brandId
        });
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除品牌编号为"' + ids + '"的数据项？').then(function () {
        return delBrand(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('product/brand/export', {
        ...this.queryParams
      }, `brand_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
