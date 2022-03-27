const mongoose = require('mongoose');

// connect to mongodb
mongoose.connect(process.env.URL_MONGOURI, () => console.log('Connected to mongodb'))

module.exports = mongoose;