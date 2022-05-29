<template>
  <el-row :gutter="10" type="flex" class="row-bg" justify="center">
    <product-detail :is-edit='false'></product-detail>
  </el-row>
</template>
<script>
import ProductDetail from './components/ProductDetail'
export default {
  name: 'goodsDetail',
  components: { ProductDetail }
}
</script>
<style>
</style>
