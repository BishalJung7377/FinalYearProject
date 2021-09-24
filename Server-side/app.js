const express = require('express');
const bodyParser = require('body-parser');
const database = require('./database/database');
const logger = require('morgan');
const path = require('path');
var app = express();
const cors = require('cors');

app.use(cors());
app.use(express.json());
app.use(bodyParser.urlencoded({ extended: false }));

const BuyerRoutes = require('./routes/BuyerRouter');
app.use(BuyerRoutes);

const SellerRoutes = require('./routes/SellerRouter');
app.use(SellerRoutes);

const ContactRoute = require('./routes/ContactUsRoute');
app.use(ContactRoute);

const FavouriteItem = require('./routes/FavouriteItemRoute');
app.use(FavouriteItem);

const CartItem = require('./routes/CartItemRoute');
app.use(CartItem);
///app.js ma call gareko
const ProductRoutes = require('./routes/ProductRoute');
const { static } = require('express');
app.use(ProductRoutes);
app.use(express.static(path.join(__dirname, 'productimages')));

app.use(logger('dev'));

app.listen(90);
