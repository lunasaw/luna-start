<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="100px">
      <el-form-item label="商品ID" prop="id">
        <el-input
          v-model="queryParams.id"
          placeholder="请输入商品ID"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="商品名称" prop="spuName">
        <el-input
          v-model="queryParams.spuName"
          placeholder="请输入商品名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>

      <el-form-item label="上级分类" prop="parentId">
        <el-cascader
          v-model="queryParams.categoryId"
          :options="cascadeList"
          :props="{ multiple: false, emitPath: false, checkStrictly: false,
           placeholder: '请选择上级分类', expandTrigger: 'hover',label	: 'name',value: 'id', children: 'childCategory' }"
          :show-all-levels="false" clearable filterable
          @keyup.enter.native="handleQuery"></el-cascader>
      </el-form-item>
      <el-form-item label="品牌" prop="brandId">
        <el-input
          v-model="queryParams.brandId"
          placeholder="请输入品牌"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="重量" prop="weight">
        <el-input
          v-model="queryParams.weight"
          placeholder="请输入重量"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="创建时间" prop="createTime">
        <el-date-picker clearable
                        v-model="queryParams.createTime"
                        type="date"
                        value-format="yyyy-MM-dd"
                        placeholder="请选择创建时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="更新时间" prop="updateTime">
        <el-date-picker clearable
                        v-model="queryParams.updateTime"
                        type="date"
                        value-format="yyyy-MM-dd"
                        placeholder="请选择更新时间">
        </el-date-picker>
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
          v-hasPermi="['product:spuInfo:add']"
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
          v-hasPermi="['product:spuInfo:edit']"
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
          v-hasPermi="['product:spuInfo:remove']"
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
          v-hasPermi="['product:spuInfo:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="spuInfoList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="商品ID" align="center" prop="id"/>
      <el-table-column label="商品名称" align="center" prop="spuName"/>
      <el-table-column label="商品描述" align="center" prop="spuDescription"/>
      <el-table-column label="所属分类" align="center" prop="categoryName"/>
      <el-table-column label="品牌" align="center" prop="brandName"/>
      <el-table-column label="重量" align="center" prop="weight"/>
      <el-table-column label="上架状态" align="center" prop="publishStatus" width="100">
        <template slot-scope="scope">
          <el-switch v-model="scope.row.publishStatus" :active-value=getActiveValue(true)
                     :inactive-value=getActiveValue(false)
                     @change="publishStatusSwitchChange(scope.row)"
          ></el-switch>
        </template>
      </el-table-column>
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
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['product:spuInfo:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['product:spuInfo:remove']"
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

    <!-- 添加或修改商品SPU信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px" label-position="center">
        <el-form-item label="商品名称" prop="spuName">
          <el-input v-model="form.spuName" placeholder="请输入商品名称"/>
        </el-form-item>
        <el-form-item label="商品描述" prop="spuDescription">
          <el-input v-model="form.spuDescription" type="textarea" placeholder="请输入内容"/>
        </el-form-item>
        <el-form-item label="所属分类" prop="parentId">
          <el-cascader
            v-model="form.categoryId"
            :options="cascadeList"
            :props="{ multiple: false, emitPath: false, checkStrictly: true,
           placeholder: '请选择上级分类', expandTrigger: 'hover',label	: 'name',value: 'id',children: 'childCategory' }"
            :show-all-levels="true" clearable filterable
            @change="handleChange" @keyup.enter.native="handleQuery"></el-cascader>
        </el-form-item>
        <el-form-item label="品牌" prop="brandId">
          <el-input v-model="form.brandId" placeholder="请输入品牌"/>
        </el-form-item>
        <el-form-item label="重量" prop="weight">
          <el-input v-model="form.weight" placeholder="请输入重量"/>
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
  listSpuInfo,
  getSpuInfo,
  delSpuInfo,
  addSpuInfo,
  addListSpuInfo,
  listPageSpuInfo,
  spuInfoListByIds,
  spuInfoListAll,
  deleteSpuInfo,
  deleteBatchSpuInfo,
  updateSpuInfo,
  updateListSpuInfo
}
  from
    "@/api/product/spuInfo";
import {publishStatusSwitchChange} from "@/api/product/spuInfo";
import {deletedSwitchChange} from "@/api/product/spuInfo";
import {categoryCascadeList} from "@/api/product/category";

export default {
  name: "SpuInfo",
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
      // 商品SPU信息表格数据
      spuInfoList: [],
      cascadeList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        id: null,
        spuName: null,
        spuDescription: null,
        categoryId: null,
        brandId: null,
        weight: null,
        publishStatus: null,
        deleted: null,
        createTime: null,
        updateTime: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        spuName: [
          {required: true, message: "商品名称不能为空", trigger: "blur"}
        ],
        categoryId: [
          {required: true, message: "所属分类不能为空", trigger: "blur"}
        ],
        brandId: [
          {required: true, message: "品牌不能为空", trigger: "blur"}
        ],
        publishStatus: [
          {required: true, message: "上架状态不能为空", trigger: "blur"}
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
  created() {
    this.getList();
    this.getCategoryCascadeList();
  },
  methods: {
    // 查询全部及联列表
    getCategoryCascadeList() {
      categoryCascadeList().then(response => {
        this.cascadeList = response.data;
      });
    },
    /** 查询商品SPU信息列表 */
    getList() {
      this.loading = true;
      listSpuInfo(this.queryParams).then(response => {
        this.spuInfoList = response.rows;
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
        spuName: null,
        spuDescription: null,
        categoryId: null,
        brandId: null,
        weight: null,
        publishStatus: null,
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
    publishStatusSwitchChange(row) {
      let text = row.publishStatus === 1 ? '启用' : '停用'
      this.$modal.confirm('确认要"' + text + '""' + row.id + '"吗？').then(function () {
        return publishStatusSwitchChange(row.id, row.publishStatus)
      }).then(() => {
        this.$modal.msgSuccess('成功')
      }).catch(function () {
        row.publishStatus = row.publishStatus === 1 ? 0 : 1
      })
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
      this.title = "添加商品SPU信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getSpuInfo(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改商品SPU信息";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateSpuInfo(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addSpuInfo(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除商品SPU信息编号为"' + ids + '"的数据项？').then(function () {
        return delSpuInfo(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('product/spuInfo/export', {
        ...this.queryParams
      }, `spuInfo_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
