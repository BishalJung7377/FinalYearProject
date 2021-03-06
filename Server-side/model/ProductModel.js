const mongoose = require('mongoose');
const Product = mongoose.model('Product', {
  ProductName: {
    type: String,
    require: true,
  },
  ProductSize: {
    type: String,
    require: true,
  },
  ProductColor: {
    type: String,
    require: true,
  },
  ProductImage: {
    type: String,
    require: true,
  },
  ProductType: {
    type: String,
    require: true,
  },
  ProductPrice: {
    type: String,
    require: true,
  },
  ProductDescription: {
    type: String,
    require: true,
  },
  createdAt: {
    type: Date,
    default: Date.now,
  },
  CreatedBy: {
    type: String,
    require: true,
  },
});
module.exports = Product;
