const product = require('../controllers/ProductController');
const danhMuc = require('../controllers/DanhMucSPController');
const loaiSP = require('../controllers/LoaiSPController');
const hangSP = require('../controllers/HangSPController');
const router = require('express').Router();

router.get('/', product.getProduct);

router.get('/:name', product.getSomeProduct);

router.get('/category', danhMuc.getCategory);

router.get('/category/:tenDanhMuc', loaiSP.getType);

router.get('/company', hangSP.getProductCompany);

// router.post('')

module.exports = router;