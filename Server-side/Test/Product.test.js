const Product = require('../model/ProductModel');
const mongoose = require('mongoose');
// use the new name of the database
const url = 'mongodb://localhost:27017/new_database_name';
beforeAll(async () => {
  await mongoose.connect(url, {
    useNewUrlParser: true,
    useCreateIndex: true,
  });
});
afterAll(async () => {
  await mongoose.connection.close();
});
describe('product Schema test anything', () => {
  // the code below is for insert testing
  it('Add Product testing anything', () => {
    const product = {
      ProductName: 'Jeans',
      ProductSize: 'X',
      ProductColor: 'Black',
      ProductType: 'Jeans',
      ProductPrice: '1000',
      ProductDescription: 'Test Product Data',
      createdBy: 'Vetments',
      ProductImage: 'no-photo.jpg',
    };
    return Product.create(product).then((pro_ret) => {
      expect(pro_ret.ProductName).toEqual('Jeans');
    });
  });
  // the code below is for delete testing
  it('to test the delete Product is working or not', async () => {
    const status = await Product.deleteMany();
    expect(status.ok).toBe(1);
  });
  it('to test the update', async () => {
    return Product.findOneAndUpdate(
      { _id: Object('606bbfad6808f32ea4103718') },
      { $set: { ProductName: 'Jeans' } }
    ).then((pp) => {
      expect(pp.ProductName).toEqual('Tshirt');
    });
  });
});
