const mongoose = require('mongoose');
const Buyer = mongoose.model('Buyer', {
  BuyerFullName: {
    type: String,
    required: true,
  },
  BuyerAge: {
    type: Number,
    required: true,
  },
  BuyerEmail: {
    type: String,
    required: true,
    unique: true,
  },
  BuyerPhone: {
    type: String,
    required: true,
  },
  BuyerGender: {
    type: String,
    required: true,
  },
  BuyerPassword: {
    type: String,
    required: true,
  },
  BuyerPhoto: {
    type: String,
    default: 'no-photo.jpg',
  },
});
module.exports = Buyer;
