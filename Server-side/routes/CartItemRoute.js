const express = require('express');
const router = express.Router();
const CartItem = require('../model/CartItemModel');
const { check, validationResult } = require('express-validator');
const authenticateBuyer = require('../middleware/authentication');
// const imageupload = require('../middleware/imageupload');

////post vaneko insert ko lagi
/////inserting product
router.post(
  '/cartitem/insert/:id',
  authenticateBuyer.verifyBuyer,
  function (req, res) {
    const productId = req.params.id;
    const CartItemData = new CartItem({
      CartItemid: productId,
      CartItemUser: req.buyer._id,
    });
    CartItemData.save()
      .then(function (CartItemSuccess) {
        res.status(201).json({
          success: true,
          message: 'CartItem Successfully Added',
          buyerToken: req.buyer._id,
        });
      })
      .catch(function (error) {
        res.status(500).json({ success: false, message: error });
      });
  }
);

router.delete(
  '/cartitem/delete/:Cartitemid',
  authenticateBuyer.verifyBuyer,
  function (req, res) {
    const Cartitemid = req.params.Cartitemid;
    CartItem.deleteOne({ _id: Cartitemid })
      .then(function (Cartitemdelete) {
        res
          .status(201)
          .json({ success: true, message: 'Cartitem Deleted Successfully' });
      })
      .catch(function (error) {
        res.status(200).json({ success: false, message: error });
      });
  }
);

////for retriving data
////retriving all data and displaying
router.get(
  '/cartitem/showall',
  authenticateBuyer.verifyBuyer,
  function (req, res) {
    CartItem.find({ CartItemUser: req.buyer._id })
      .populate('CartItemid')
      .populate('CartItemUser')
      .then(function (CartItemDisplay) {
        res.status(201).json({
          success: true,
          message: CartItemDisplay,
          buyerToken: req.buyer._id,
        });
      })
      .catch(function (error) {
        res.status(500).json({ success: false, message: error });
      });
  }
);

module.exports = router;
