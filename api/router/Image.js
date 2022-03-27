const img = require('../controllers/ImageController');
const router = require('express').Router();

router.get('/:fileName', img.getImage);

module.exports = router;