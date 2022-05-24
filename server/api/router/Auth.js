const Users = require('../controllers/UserController');
const router = require('express').Router();

router.post('/register', Users.registerUser);
router.post('/login', Users.login);
router.post('/ckMail', Users.checkEmail);
router.post('/EditInfo_avt', Users.changeInfo_avt)
router.post('/EditInfo', Users.changeInfo)
router.get('/ckUsername', Users.getInfoUser, Users.checkEmail)

module.exports = router;
