const express = require('express');
const router = express.Router();
const Buyer = require('../model/BuyerModel');
const bcrypt = require('bcryptjs');
const { check, validationResult } = require('express-validator');
const authBuyer = require('../middleware/authentication');
const jwt = require('jsonwebtoken');
const imageupload = require('../middleware/imageupload');
const fs = require('fs');
const { CLIENT_RENEG_LIMIT } = require('tls');

////buyer registration route
router.post('/register/buyer', function (req, res) {
  const errors = validationResult(req);

  if (!errors.isEmpty()) {
    res.status(400).json(errors.array());
  } else {
    const BuyerFullName = req.body.BuyerFullName;
    const BuyerAge = req.body.BuyerAge;
    const BuyerEmail = req.body.BuyerEmail;
    const BuyerPhone = req.body.BuyerPhone;
    const BuyerGender = req.body.BuyerGender;
    const BuyerPassword = req.body.BuyerPassword;
    // const BuyerPhoto = req.body.BuyerPhoto;

    bcrypt.hash(BuyerPassword, 10, function (error, hash) {
      // console.log(hash);
      const Data = new Buyer({
        BuyerFullName: BuyerFullName,
        BuyerAge: BuyerAge,
        BuyerEmail: BuyerEmail,
        BuyerPhone: BuyerPhone,
        BuyerGender: BuyerGender,
        BuyerPassword: hash,
        // BuyerPhoto: BuyerPhoto,
      });
      Data.save()
        .then(function () {
          res
            .status(201)
            .json({ success: true, message: 'Successfully Register as Buyer' });
        })
        .catch(function (e) {
          /////500 vaneko server error ho
          res.status(500).json({ success: false, message: e });
        });
    });
  }
});

////for buyer login
router.post('/buyer/login', function (req, res) {
  const buyerEmail = req.body.BuyerEmail;
  const buyerPassword = req.body.BuyerPassword;

  /////mathe vako suru ma initialize gareko buyer ra ysko name same hun aparxa
  Buyer.findOne({ BuyerEmail: buyerEmail }) ////left ko vaneko database ko right ko vaneko yehi mathe initialize gareko ho
    .then(function (buyerData) {
      if (buyerData === null) {
        ///return will return back if the valid user doesnot exist and preevents from unnecessary codes to run
        return res.status(403).json({
          success: false,
          message: 'Invalid Buyer Credentils!!',
        });
      }
      bcrypt.compare(
        buyerPassword,
        buyerData.BuyerPassword,
        function (error, result) {
          ////mathe left ma vako chai form ma vako password and all the data from database are stored in buyerData
          if (result === false) {
            return res
              .status(403)
              .json({ success: false, message: 'Invalid Credentials!!' });
          }
          // res.send('Valid Credentials!!');

          ///generating tokens

          const buyerToken = jwt.sign({ buyerId: buyerData._id }, 'secretkey');

          return res.status(200).json({
            success: true,
            message: 'Successfully Logged in',
            buyerToken: buyerToken,
          });
        }
      );
    })
    .catch();
});

////buyer image upload route
router.put(
  '/buyer/image/upload',
  authBuyer.verifyBuyer,
  imageupload.single('BuyerPhoto'),
  function (req, res) {
    console.log(req.file);
    if (req.file == undefined) {
      return res.status(400).json({ message: 'invalid file', success: false });
    }
    const buyerid = req.buyer._id;
    Buyer.findOne({ _id: buyerid })
      .then(function (data) {
        var image = data.BuyerPhoto;
        if (image != 'no-photo.jpg') {
          fs.unlinkSync(image, (err) => {
            if (err) {
              res
                .status(400)
                .json({ message: 'error deleting file', success: false });
              return;
            }
          });
        }
      })
      .catch(function (err) {
        res.status(400).json({ message: 'file not found', success: false });
      });

    Buyer.updateOne({ _id: buyerid }, { BuyerPhoto: req.file.filename })
      .then(function (result) {
        res
          .status(200)
          .json({ message: 'Profile update successfully', success: true });
      })
      .catch(function (err) {
        res.status(500).json({
          message: 'Failed to Update Profile Picture',
          success: false,
        });
      });
  }
);

/////buyer detail display route
router.get('/buyer/buyerdetail/', authBuyer.verifyBuyer, function (req, res) {
  const buyerid = req.buyer._id;
  Buyer.findOne({ _id: buyerid })
    .then(function (buyerDisplay) {
      res.status(200).json({
        success: true,
        data: buyerDisplay,
        message: buyerDisplay,
      });
    })
    .catch(function (error) {
      res.status(500).json({ success: false, message: error });
    });
});

//////for update of buyer info
router.put('/buyer/update', authBuyer.verifyBuyer, function (req, res) {
  const BuyerFullName = req.body.BuyerFullName;
  const BuyerAge = req.body.BuyerAge;
  const BuyerPhone = req.body.BuyerPhone;
  const BuyerGender = req.body.BuyerGender;
  // const BuyerPassword = req.body.BuyerPassword;
  // const BuyerPhoto = req.body.BuyerPhoto;
  console.log(req.body);
  // bcrypt.hash(BuyerPassword, 10, function (error, hash) {
  Buyer.updateOne(
    { _id: req.buyer._id },
    {
      BuyerFullName: BuyerFullName,
      BuyerAge: BuyerAge,
      BuyerPhone: BuyerPhone,
      BuyerGender: BuyerGender,
      // BuyerPassword: hash,
      // BuyerPhoto: BuyerPhoto,
    }
  )
    .then(function (buyerupdate) {
      res
        .status(200)
        .json({ success: true, message: 'Buyer Data Updated Successfully' });
    })

    .catch(function (error) {
      res.status(201).json({ succeess: false, message: 'error' });
    });
  // });
});

///buyer password update route
router.put(
  '/buyer/update/password/',
  authBuyer.verifyBuyer,
  function (req, res) {
    const buyerid = req.buyer._id;
    const { BuyerPassword } = req.body;
    Buyer.findOne({ _id: buyerid })
      .then(function (buyerDisplay) {
        bcrypt.hash(BuyerPassword, 10, function (err, hash) {
          Buyer.updateOne({ _id: buyerid }, { BuyerPassword: hash })
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
