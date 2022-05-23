<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="100px">
      <el-form-item label="id" prop="id"  >
        <el-input
            v-model="queryParams.id"
            placeholder="请输入id"
            clearable
            @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="SPU_ID" prop="spuId"   label-width="110px" >
        <el-input
            v-model="queryParams.spuId"
            placeholder="请输入SPU_ID"
            clearable
            @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="图片名" prop="imgName"  >
        <el-input
            v-model="queryParams.imgName"
            placeholder="请输入图片名"
            clearable
            @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="顺序" prop="imgSort"  >
        <el-input
            v-model="queryParams.imgSort"
            placeholder="请输入顺序"
            clearable
            @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="创建时间" prop="createTime" >
        <el-date-picker clearable
                        v-model="queryParams.createTime"
                        type="date"
                        value-format="yyyy-MM-dd"
                        placeholder="请选择创建时间">
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
          v-hasPermi="['product:spuImages:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['product:spuImages:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['product:spuImages:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['product:spuImages:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="spuImagesList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="id" align="center" prop="id" />
      <el-table-column label="SPU_ID" align="center" prop="spuId" />
      <el-table-column label="图片名" align="center" prop="imgName" />
      <el-table-column label="图片地址" align="center" prop="imgUrl" width="100" >
        <template slot-scope="scope">
          <image-preview :src="scope.row.imgUrl" :width="50" :height="50"/>
        </template>
      </el-table-column>
      <el-table-column label="顺序" align="center" prop="imgSort" />
      <el-table-column label="是否默认图" align="center" prop="defaultImg" width="100" >
        <template slot-scope="scope">
          <el-switch v-model="scope.row.defaultImg" :active-value=getActiveValue(true)
                     :inactive-value=getActiveValue(false)
                     @change="defaultImgSwitchChange(scope.row)"
          ></el-switch>
        </template>
      </el-table-column>
      <el-table-column label="是否删除" align="center" prop="deleted" width="100" >
        <template slot-scope="scope">
          <el-switch v-model="scope.row.deleted" :active-value=getActiveValue(true)
                     :inactive-value=getActiveValue(false)
                     @change="deletedSwitchChange(scope.row)"
          ></el-switch>
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['product:spuImages:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['product:spuImages:remove']"
          >删除</el-button>
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

    <!-- 添加或修改SPU图片对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px" label-position="left">
      <el-form-item label="SPU_ID" prop="spuId">
        <el-input v-model="form.spuId" placeholder="请输入SPU_ID"/>
      </el-form-item>
      <el-form-item label="图片名" prop="imgName">
        <el-input v-model="form.imgName" placeholder="请输入图片名"/>
      </el-form-item>
      <el-form-item label="图片地址">
        <image-upload v-model="form.imgUrl"/>
      </el-form-item>
      <el-form-item label="顺序" prop="imgSort">
        <el-input v-model="form.imgSort" placeholder="请输入顺序"/>
      </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
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
    listSpuImages,
    getSpuImages,
    delSpuImages,
    addSpuImages,
    addListSpuImages,
    listPageSpuImages,
    spuImagesListByIds,
    spuImagesListAll,
    deleteSpuImages,
    deleteBatchSpuImages,
    updateSpuImages,
    updateListSpuImages
  }
    from
        "@/api/product/spuImages";
          import {defaultImgSwitchChange} from "@/api/product/spuImages";
          import {deletedSwitchChange} from "@/api/product/spuImages";

  export default {
    name: "SpuImages",
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
      // SPU图片表格数据
      spuImagesList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        id: null,
        spuId: null,
        imgName: null,
        imgUrl: null,
        imgSort: null,
        defaultImg: null,
        deleted: null,
        createTime: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        imgName: [
          { required: true, message: "图片名不能为空", trigger: "blur" }
        ],
        imgUrl: [
          { required: true, message: "图片地址不能为空", trigger: "blur" }
        ],
        deleted: [
          { required: true, message: "是否删除不能为空", trigger: "blur" }
        ],
        createTime: [
          { required: true, message: "创建时间不能为空", trigger: "blur" }
        ],
        updateTime: [
          { required: true, message: "更新时间不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询SPU图片列表 */
    getList() {
      this.loading = true;
      listSpuImages(this.queryParams).then(response => {
        this.spuImagesList = response.rows;
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
        spuId: null,
        imgName: null,
        imgUrl: null,
        imgSort: null,
        defaultImg: null,
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
                  defaultImgSwitchChange(row) {
              let text = row.defaultImg === 1 ? '启用' : '停用'
              this.$modal.confirm ('确认要"' + text + '""' + row.id + '"吗？').then(function () {
                return defaultImgSwitchChange(row.id, row.defaultImg)
              }).then(() => {
                this.$modal.msgSuccess ('成功')
              }).catch(function () {
                row.defaultImg = row.defaultImg === 1 ? 0 : 1
              })
            },
            // 状态修改
                  deletedSwitchChange(row) {
              let text = row.deleted === 1 ? '启用' : '停用'
              this.$modal.confirm ('确认要"' + text + '""' + row.id + '"吗？').then(function () {
                return deletedSwitchChange(row.id, row.deleted)
              }).then(() => {
                this.$modal.msgSuccess ('成功')
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
      this.title = "添加SPU图片";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getSpuImages(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改SPU图片";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateSpuImages(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addSpuImages(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除SPU图片编号为"' + ids + '"的数据项？').then(function() {
        return delSpuImages(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('product/spuImages/export', {
        ...this.queryParams
      }, `spuImages_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
