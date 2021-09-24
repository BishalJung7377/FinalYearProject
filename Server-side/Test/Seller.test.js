const Seller = require('../model/SellerModel');
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
describe('seller Schema test anything', () => {
  // the code below is for insert testing
  it('Add seller testing anything', () => {
    const seller = {
      SellerFullName: 'Victor Beckham',
      SellerPhone: '9861584551',
      SellerEmail: 'victor@gmail.com',
      SellerGender: 'Female',
      SellerPassword: 'apple',
    };
    return Seller.create(seller).then((pro_ret) => {
      expect(pro_ret.SellerFullName).toEqual('Victor Beckham');
    });
  });
  // the code below is for delete testing
  it('to test the delete Seller is working or not', async () => {
    const status = await Seller.deleteMany();
    expect(status.ok).toBe(1);
  });
  it('to test the update', async () => {
    return Seller.findOneAndUpdate(
      { _id: Object('606bbfad6808f32ea4103718') },
      { $set: { SellerFullName: 'Alphonso Davis' } }
    ).then((pp) => {
      expect(pp.SellerFullName).toEqual('Alphonso Davis');
    });
  });
});
