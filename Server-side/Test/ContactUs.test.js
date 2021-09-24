const Contact = require('../model/ContactModel');
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
describe('Contact Schema test anything', () => {
  // the code below is for insert testing
  it('Add Contact testing anything', () => {
    const contact = {
      CustomerFullName: 'Bishal Jung Thapa',
      CustomerPhone: '9861584551',
      CustomerEmail: 'bsal@gmail.com',
      CustomerSubject: 'test',
      CustomerMessage: 'Test Message Data',
    };
    return Contact.create(contact).then((pro_ret) => {
      expect(pro_ret.CustomerFullName).toEqual('Bishal Jung Thapa');
    });
  });
  // the code below is for delete testing
  it('to test the delete Seller is working or not', async () => {
    const status = await Contact.deleteMany();
    expect(status.ok).toBe(1);
  });
  it('to test the update', async () => {
    return Contact.findOneAndUpdate(
      { _id: Object('606bbfad6808f32ea4103718') },
      { $set: { CustomerFullName: 'Alphonso Davis' } }
    ).then((pp) => {
      expect(pp.CustomerFullName).toEqual('Alphonso Davis');
    });
  });
});
