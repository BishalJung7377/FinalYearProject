const mongoose = require('mongoose');
const CartItem = mongoose.model('CartItem', {
  CartItemid: [
    {
      type: mongoose.Schema.Types.ObjectId,
      ref: 'Product',
    },
  ],
  CartItemUser: [
    {
      type: mongoose.Schema.Types.ObjectId,
      ref: 'Buyer',
    },
  ],
});
module.exports = CartItem;
