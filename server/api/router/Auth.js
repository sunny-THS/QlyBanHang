const Users = require('../controllers/UserController');
const router = require('express').Router();

router.post('/register', Users.registerUser);
router.post('/login', Users.login)

module.exports = router;
