const express = require('express');
const router = express.Router();
const FavouriteItem = require('../model/FavouriteItemModel');
const { check, validationResult } = require('express-validator');
const authenticateBuyer = require('../middleware/authentication');
// const imageupload = require('../middleware/imageupload');

////post vaneko insert ko lagi
/////inserting product
router.post(
  '/favouriteitem/insert/:id',
  authenticateBuyer.verifyBuyer,
  function (req, res) {
    const productId = req.params.id;
    const FavouriteItemData = new FavouriteItem({
      FavouriteItemid: productId,
      // FavouriteItemName,
      FavouriteItemUser: req.buyer._id,
    });
    ////then vaneko success vayo vaney
    ////catch vanya error vayo vaney
    FavouriteItemData.save()
      .then(function (FavouriteItemSuccess) {
        res
          .status(201)
          .json({ success: true, message: 'FavouriteItem Successfully Added' });
      })
      .catch(function (error) {
        res.status(500).json({ success: false, message: error });
      });
  }
);

router.delete(
  '/favouriteitem/delete/:favouriteitemid',
  authenticateBuyer.verifyBuyer,
  function (req, res) {
    const favouriteitemid = req.params.favouriteitemid;
    FavouriteItem.deleteOne({ _id: favouriteitemid })
      .then(function (favouriteitemdelete) {
        res.status(201).json({
          success: true,
          message: 'Favouriteitem Deleted Successfully',
        });
      })
      .catch(function (error) {
        res.status(500).json({ success: false, message: error });
      });
  }
);

////for retriving data
////retriving all data and displaying
router.get(
  '/favouriteitem/showall',
  authenticateBuyer.verifyBuyer,
  function (req, res) {
    FavouriteItem.find({ FavouriteItemUser: req.buyer._id })
      .populate('FavouriteItemid')
      .populate('FavouriteItemUser')
      .then(function (FavouriteItemDisplay) {
        res
          .status(200)
          .json({
            success: true,
            message: FavouriteItemDisplay,
            buyerToken: req.buyer._id,
          });
      })
      .catch(function (error) {
        res.status(500).json({ success: false, message: error });
      });
  }
);

module.exports = router;
