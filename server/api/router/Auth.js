const Users = require('../controllers/UserController');
const router = require('express').Router();

router.post('/register', Users.registerUser);
router.post('/login', Users.login);
router.post('/admin', Users.ckAdmin, Users.login);
router.post('/ckMail', Users.checkEmail, Users.sendEmail);
router.post('/EditInfo_avt', Users.changeInfo_avt)
router.post('/EditInfo', Users.changeInfo)
router.post('/EditInfo_Pw', Users.changePw)
router.post('/ckUsername', Users.getInfoUser, Users.sendEmail)

module.exports = router;
