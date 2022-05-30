<template>
  <div style="margin-top: 50px">
    <el-form :model="value" :rules="rules" ref="productInfoForm" label-width="120px" style="width: 600px" size="small">
      <el-form-item label="商品分类：" prop="productCategoryId">
        <el-cascader
          v-model="value.productCategoryId"
          :options="cascadeList"
          @change="getBrandListWithCategory"
          :props="{ multiple: false, emitPath: false, checkStrictly: false,
           placeholder: '请选择上级分类', expandTrigger: 'hover',label	: 'name',value: 'id',children: 'childCategory' }"
          :show-all-levels="true" clearable filterable
        ></el-cascader>
      </el-form-item>
      <el-form-item label="商品名称：" prop="name">
        <el-input v-model="value.name"></el-input>
      </el-form-item>
      <el-form-item label="副标题：" prop="subTitle">
        <el-input v-model="value.subTitle"></el-input>
      </el-form-item>
      <el-form-item label="商品品牌：" prop="brandId">
        <el-select v-model="value.brandId"
                   filterable
                   placeholder="请选择品牌名称">
          <el-option
            v-for="item in this.brandList"
            :key="item.brandId"
            :label="item.brandName"
            :value="item.brandId"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="商品介绍：">
        <el-input
          :autoSize="true"
          v-model="value.description"
          type="textarea"
          placeholder="请输入内容"></el-input>
      </el-form-item>
      <el-form-item label="商品货号：">
        <el-input v-model="value.productSn"></el-input>
      </el-form-item>
      <el-form-item label="商品售价：">
        <el-input v-model="value.price"></el-input>
      </el-form-item>
      <el-form-item label="市场价：">
        <el-input v-model="value.originalPrice"></el-input>
      </el-form-item>
      <el-form-item label="商品库存：">
        <el-input v-model="value.stock"></el-input>
      </el-form-item>
      <el-form-item label="计量单位：">
        <el-input v-model="value.unit"></el-input>
      </el-form-item>
      <el-form-item label="商品重量：">
        <el-input v-model="value.weight" style="width: 300px"></el-input>
        <span style="margin-left: 20px">克</span>
      </el-form-item>
      <el-form-item label="排序">
        <el-input v-model="value.sort"></el-input>
      </el-form-item>
      <el-form-item style="text-align: center">
        <el-button type="primary" size="medium" @click="handleNext('productInfoForm')">下一步，填写商品促销</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>
<script>
import {categoryCascadeList} from "@/api/product/category";
import {listBrand} from "@/api/product/brand";
import {listBrandRelation} from "@/api/product/brandRelation";

export default {
  name: 'ProductInfoDetail',
  props: {
    value: Object,
    isEdit: {
      type: Boolean,
      default: false
    }
  },
  data() {
    return {
      // 商品分类
      cascadeList: [],
      // 品牌列表
      brandList: [],
      rules: {
        // name: [
        //   {required: true, message: '请输入商品名称', trigger: 'blur'},
        //   {min: 2, max: 140, message: '长度在 2 到 140 个字符', trigger: 'blur'}
        // ],
        // subTitle: [{required: true, message: '请输入商品副标题', trigger: 'blur'}],
        // productCategoryId: [{required: true, message: '请选择商品分类', trigger: 'blur'}],
        // brandId: [{required: true, message: '请选择商品品牌', trigger: 'blur'}],
        // description: [{required: true, message: '请输入商品介绍', trigger: 'blur'}],
        // requiredProp: [{required: true, message: '该项为必填项', trigger: 'blur'}]
      }
    }
  },
  created() {
    this.getCategoryCascadeList();
  },
  methods: {
    // 获取及联分类
    getCategoryCascadeList() {
      categoryCascadeList().then(response => {
        this.cascadeList = response.data;
      });
    },
    getBrandListWithCategory(value) {
      listBrandRelation({
        categoryId: value
      }).then(response => {
        this.brandList = response.rows;
      });
    },
    getBrandList(value) {
      if (!value) {
        this.brandList = [];
        return;
      }
      let query = {
        name: value
      }
      listBrand(query).then(res => {
        this.brandList = res.rows;
      }).catch(() => {
        this.brandList = [];
      });
    },
    handleNext(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.$emit('nextStep');
        } else {
          this.$message({
            message: '验证失败',
            type: 'error',
            duration: 1000
          });
          return false;
        }
      });
    },
  }
}
</script>
<style scoped>
</style>
