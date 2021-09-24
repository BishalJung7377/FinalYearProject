const express = require('express');
const router = express.Router();
const Seller = require('../model/SellerModel');
const bcrypt = require('bcryptjs');
const { check, validationResult } = require('express-validator');
const authenticateSeller = require('../middleware/authentication');
const jwt = require('jsonwebtoken');

router.post('/register/seller', function (req, res) {
  const errors = validationResult(req);
  console.log(req.body);
  if (!errors.isEmpty()) {
    res.status(400).json(errors.array());
  } else {
    const SellerFullName = req.body.SellerFullName;
    const SellerGender = req.body.SellerGender;
    const SellerEmail = req.body.SellerEmail;
    const SellerPhone = req.body.SellerPhone;
    const SellerPassword = req.body.SellerPassword;

    bcrypt.hash(SellerPassword, 10, function (error, hash) {
      // console.log(hash);
      const Data = new Seller({
        SellerFullName: SellerFullName,
        SellerGender: SellerGender,
        SellerEmail: SellerEmail,
        SellerPhone: SellerPhone,
        SellerPassword: hash,
      });
      Data.save()
        .then(function () {
          res.status(201).json({
            success: true,
            message: 'Successfully Register as Seller',
          });
        })
        .catch(function (e) {
          res.status(500).json({ success: false, message: e });
        });
    });
  }
});

///for seller login

router.post('/seller/login', function (req, res) {
  const sellerEmail = req.body.SellerEmail;
  const sellerPassword = req.body.SellerPassword;

  Seller.findOne({ SellerEmail: sellerEmail })
    .then(function (sellerData) {
      if (sellerData === null) {
        return res
          .status(403)
          .json({ success: false, message: 'Invalid Seller Credentils!!' });
      }
      bcrypt.compare(
        sellerPassword,
        sellerData.SellerPassword,
        function (error, result) {
          if (result === false) {
            return res.status(403).json({
              success: false,
              message: 'Invalid  Seller Credentials!!',
            });
          }
          // res.send('Valid Credentials!!');

          const sellerToken = jwt.sign(
            { sellerId: sellerData._id },
            'secretkey'
          );

          return res.status(200).json({
            success: true,
            message: 'Successfully logged in as seller',
            sellerToken: sellerToken,
          });
        }
      );
    })
    .catch();

  //////for update of buyer info
  router.put(
    '/seller/update',
    authenticateSeller.verifySeller,
    function (req, res) {
      const SellerFullName = req.body.SellerFullName;
      const SellerGender = req.body.SellerGender;
      const SellerEmail = req.body.SellerEmail;
      const SellerPhone = req.body.SellerPhone;
      // const SellerPassword = req.body.SellerPassword;
      console.log(req.body);
      // bcrypt.hash(SellerPassword, 10, function (error, hash) {
      Seller.updateOne(
        { _id: req.seller._id },
        {
          SellerFullName: SellerFullName,
          SellerGender: SellerGender,
          SellerEmail: SellerEmail,
          SellerPhone: SellerPhone,
          // SellerPassword: hash,
        }
      )
        .then(function (sellerupdate) {
          res.status(200).json({
            success: true,
            message: 'Seller Data Updated Successfully',
            data: sellerupdate,
          });
        })

        .catch(function (error) {
          res.status(201).json({ succeess: false, message: 'error' });
        });
      // });
    }
  );
});

router.get(
  '/seller/sellerdetail/',
  authenticateSeller.verifySeller,
  function (req, res) {
    const sellerId = req.seller._id;
    Seller.findOne({ _id: sellerId })
      .then(function (sellerDisplay) {
        res.status(200).json({
          success: true,
          data: sellerDisplay,
          message: sellerDisplay,
        });
      })
      .catch(function (error) {
        res.status(500).json({ success: false, message: error });
      });
  }
);

router.put(
  '/seller/update/password/',
  authenticateSeller.verifySeller,
  function (req, res) {
    const sellerId = req.seller._id;
    const { SellerPassword } = req.body;
    Seller.findOne({ _id: sellerId })
      .then(function (sellerDisplay) {
        bcrypt.hash(SellerPassword, 10, function (err, hash) {
          Seller.updateOne({ _id: sellerId }, { SellerPassword: hash })
            .then(function (result) {
              res.status(200).json({
                message: 'Password Updated Successfully',
                success: true,
              });
            })
            .catch(function (err) {
              res
                .status(500)
                .json({ message: 'Password Update Failed', success: false });
            });
        });
      })
      .catch(function (error) {
        res.status(500).json({ success: false, message: error });
      });
  }
);
module.exports = router;
