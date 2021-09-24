const Buyer = require('../model/BuyerModel');
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
describe('Buyer Schema test anything', () => {
  // the code below is for insert testing
  it('Add Buyer testing anything', () => {
    const buyer = {
      BuyerFullName: 'David Beckham',
      BuyerPhone: '987654321',
      BuyerAge: '21',
      BuyerEmail: 'bsal@gmail.com',
      BuyerGender: 'Male',
      BuyerPassword: 'apple',
    };
    return Buyer.create(buyer).then((pro_ret) => {
      expect(pro_ret.BuyerFullName).toEqual('David Beckham');
    });
  });
  // the code below is for delete testing
  it('to test the delete Buyer is working or not', async () => {
    const status = await Buyer.deleteMany();
    expect(status.ok).toBe(1);
  });
  it('to test the update', async () => {
    return Buyer.findOneAndUpdate(
      { _id: Object('606bbfad6808f32ea4103718') },
      { $set: { BuyerFullName: 'David Beckham' } }
    ).then((pp) => {
      expect(pp.BuyerFullName).toEqual('Bishal Jung Thapa');
    });
  });
});
