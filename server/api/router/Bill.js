const hoaDon = require('../controllers/HoaDonController');
const router = require('express').Router();

router.post('/us', hoaDon.getBills_user);
router.get('/id/:id', hoaDon.getBill_id);
router.post('/xacNhanDon', hoaDon.xacNhanHD);
router.post('/', hoaDon.getBills);
router.post('/state', hoaDon.getBills_state);

module.exports = router;
