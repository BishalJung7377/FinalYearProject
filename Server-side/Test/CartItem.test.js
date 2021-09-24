const CartItem = require('../model/CartItemModel');
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
    const cart = {
      CartItemid: '606c4a6b436ec7379cb8321a',
      CartItemUser: '606bbfad6808f32ea4103718',
    };
    return CartItem.create(cart).then((pro_ret) => {
      expect(pro_ret.CartItemid).toEqual('606c4a6b436ec7379cb8321a');
    });
  });
  // the code below is for delete testing
  it('to test the delete Product is working or not', async () => {
    const status = await CartItem.deleteMany();
    expect(status.ok).toBe(1);
  });
  //   it('to test the update', async () => {
  //     return CartItem.findOneAndUpdate(
  //       { _id: Object('606bbfad6808f32ea4103718') },
  //       { $set: { CartItemUser: '606bbfad6808f32ea4103718' } }
  //     ).then((pp) => {
  //       expect(pp.CartItemUser).toEqual('606bbfad6808f32ea4103721');
  //     });
  //   });
});
