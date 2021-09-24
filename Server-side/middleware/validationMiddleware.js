const { check, validationResult } = require('express-validator');
const authBuyer = function (req, res, next) {
  check('BuyerFullName', 'Buyer Full name must be mentioned').not().isEmpty(),
    check('BuyerAge', 'Buyer must be included').not().isEmpty(),
    //.isLength({ min: 12 }),
    check('BuyerEmail', 'Email must be unique').not().isEmail(),
    check('BuyerPhone', 'Phone must be included').not().isMobilePhone(),
    check('BuyerGender', 'Gander must be included').not().isEmpty(),
    check('BuyerPassword', 'Password must be alphanumeric').not(),
    next();
};
module.exports = authBuyer;

const authenticateSeller = function (req, res, next) {
  check('SellerFullName', 'Full name must be mentioned').not().isEmpty(),
    check('SellerGender', 'Gender must be included').not().isEmpty(),
    check('SellerEmail', 'Email must be unique').not().isEmail(),
    check('SellerPhone', 'Phone must be included').not().isMobilePhone(),
    check('SellerPassword', 'Password must be alphanumeric').not(),
    next();
};
module.exports = authenticateSeller;
