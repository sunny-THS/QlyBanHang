const hoaDon = require('../controllers/HoaDonController');
const router = require('express').Router();

router.post('/us', hoaDon.getBills_user);
router.get('/', hoaDon.getBills);

module.exports = router;
