const express = require('express');
const router = express.Router();
const Product = require('../model/ProductModel');
const { check, validationResult } = require('express-validator');
const authenticateSeller = require('../middleware/authentication');
const imageupload = require('../middleware/imageupload');

////post vaneko insert ko lagi
/////inserting product
router.post(
  '/product/insert',
  authenticateSeller.verifySeller,
  imageupload.single('ProductImage'),
  function (req, res) {
    if (req.file == undefined) {
      return res.status(400).json({ message: 'Invalid Image Format' });
    } else if (req.file.size >= 7340032) {
      return res.status(400).json({ message: 'Invalid Image size' });
    } else {
      console.log(req.file);
      const ProductName = req.body.ProductName;
      const ProductSize = req.body.ProductSize;
      const ProductColor = req.body.ProductColor;
      const ProductImage = req.file.filename;
      const ProductType = req.body.ProductType;
      const ProductPrice = req.body.ProductPrice;
      const ProductDescription = req.body.ProductDescription;
      const createdAt = req.body.createdAt;
      const CreatedBy = req.body.CreatedBy;

      const ProductData = new Product({
        ProductName,
        ProductSize,
        ProductColor,
        ProductImage,
        ProductType,
        ProductPrice,
        ProductDescription,
        createdAt,
        CreatedBy,
      });
      ////then vaneko success vayo vaney
      ////catch vanya error vayo vaney
      ProductData.save()
        .then(function (productSuccess) {
          res.status(201).json({ message: 'Product Successfully Added' });
        })
        .catch(function (error) {
          res.status(500).json({ message: error });
        });
    }
  }
);

router.delete(
  '/product/delete/:productid',
  authenticateSeller.verifySeller,
  function (req, res) {
    const productid = req.params.productid;
    Product.deleteOne({ _id: productid })
      .then(function (productdelete) {
        res
          .status(201)
          .json({ success: true, message: 'Product Deleted Successfully' });
      })
      .catch(function (error) {
        res.status(500).json({ success: false, message: error });
      });
  }
);

//////for update
router.put(
  '/product/update/:productid',
  authenticateSeller.verifySeller,
  function (req, res) {
    const ProductName = req.body.ProductName;
    const ProductSize = req.body.ProductSize;
    const ProductColor = req.body.ProductColor;
    // const ProductImage = req.body.ProductImage;
    const ProductType = req.body.ProductType;
    const ProductPrice = req.body.ProductPrice;
    const ProductDescription = req.body.ProductDescription;
    const ProductId = req.params.productid;
    const createdAt = req.body.createdAt;
    const CreatedBy = req.body.CreatedBy;

    Product.updateOne(
      { _id: ProductId },
      {
        $set: {
          ProductName: ProductName,
          ProductSize: ProductSize,
          ProductColor: ProductColor,
          // ProductImage: ProductImage,
          ProductType: ProductType,
          ProductPrice: ProductPrice,
          ProductDescription: ProductDescription,
          createdAt: createdAt,
          CreatedBy: CreatedBy,
        },
      }
    )
      .then(function (productupdate) {
        res
          .status(200)
          .json({ success: true, message: 'Product Updated Successfully' });
      })
      .catch(function (error) {
        res.status(500).json({ success: false, message: error });
      });
  }
);

////for retriving data
////retriving all data and displaying
router.get('/product/showall', function (req, res) {
  Product.find()
    .then(function (productdisplay) {
      res.status(200).json({
        success: true,
        message: productdisplay,
        ProductData: productdisplay,
      });
    })
    .catch(function (error) {
      res.status(500).json({ success: false, message: error });
    });
});

router.get(
  '/product/singleitem/:productid',
  authenticateSeller.verifySeller,
  function (req, res) {
    const productid = req.params.productid;
    Product.findOne({ _id: productid })
      .then(function (productdisplay) {
        res.status(200).json({ message: productdisplay });
      })
      .catch(function (error) {
        res.status(500).json({ message: error });
      });
  }
);
router.get('/product/singleitem/buyer/:productid', function (req, res) {
  const productid = req.params.productid;
  Product.findOne({ _id: productid })
    .then(function (productdisplay) {
      res.status(200).json({ message: productdisplay });
    })
    .catch(function (error) {
      res.status(500).json({ message: error });
    });
});

module.exports = router;
