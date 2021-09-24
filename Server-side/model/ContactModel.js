const mongoose = require('mongoose');
const Contact = mongoose.model('Contact', {
  CustomerFullName: {
    type: String,
    required: true,
  },
  CustomerEmail: {
    type: String,
    required: true,
  },
  CustomerPhone: {
    type: String,
    required: true,
  },
  CustomerSubject: {
    type: String,
    required: true,
  },
  CustomerMessage: {
    type: String,
    required: true,
  },
});
module.exports = Contact;
