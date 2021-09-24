const express = require('express');
const router = express.Router();
const Contact = require('../model/ContactModel');
const { check, validationResult } = require('express-validator');
const authenticateSeller = require('../middleware/authentication');

////post vaneko insert ko lagi
/////inserting Contact
router.post('/contact/insert', function (req, res) {
  const CustomerFullName = req.body.CustomerFullName;
  const CustomerEmail = req.body.CustomerEmail;
  const CustomerPhone = req.body.CustomerPhone;
  const CustomerSubject = req.body.CustomerSubject;
  const CustomerMessage = req.body.CustomerMessage;

  const ContactData = new Contact({
    CustomerFullName,
    CustomerEmail,
    CustomerPhone,
    CustomerSubject,
    CustomerMessage,
  });
  ////then vaneko success vayo vaney
  ////catch vanya error vayo vaney
  ContactData.save()
    .then(function (ContactSuccess) {
      res.status(201).json({ message: 'Contact Successfully Added' });
    })
    .catch(function (error) {
      res.status(500).json({ message: error });
    });
});

router.get(
  '/message/showall',
  authenticateSeller.verifySeller,
  function (req, res) {
    Contact.find()
      .then(function (ContactData) {
        res.status(200).json({
          success: true,
          message: ContactData,
        });
      })
      .catch(function (error) {
        res.status(500).json({ success: false, message: error });
      });
  }
);

module.exports = router;
