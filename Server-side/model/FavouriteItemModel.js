const mongoose = require('mongoose');
const FavouriteItem = mongoose.model('FavouriteItem', {
  FavouriteItemid: [
    {
      type: mongoose.Schema.Types.ObjectId,
      ref: 'Product',
    },
  ],
  FavouriteItemUser: [
    {
      type: mongoose.Schema.Types.ObjectId,
      ref: 'Buyer',
    },
  ],
});
module.exports = FavouriteItem;
