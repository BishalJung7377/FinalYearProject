const multer = require('multer');

// var maxSize = 1 * 1000 * 1000;
const productimagestorage = multer.diskStorage({
  destination: function (req, file, cb) {
    // console.log(file);
    cb(null, './productimages');
  },
  filename: function (req, file, cb) {
    cb(null, Date.now() + file.originalname);
  },
});
const maxSize = 5 * 1024 * 1024;
// const imageupload = multer({ storage: productimagestorage });

const filefilter = function (req, file, cb) {
  if (
    file.mimetype == 'image/png' ||
    file.mimetype == 'image/jpg' ||
    file.mimetype == 'image/jpeg'
  ) {
    cb(null, true);
  } else {
    cb(null, false);
    //return cb(new Error('Only .png, .jpg and .jpeg format allowed!'));
  }
};
0;
const imageupload = multer({
  storage: productimagestorage,
  fileFilter: filefilter,
  limits: { fileSize: maxSize }, ///7MB
});

module.exports = imageupload;
