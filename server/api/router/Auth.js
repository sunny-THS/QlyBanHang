const Users = require('../controllers/UserController');
const router = require('express').Router();

router.get('/register', Users.registerUser);

module.exports = router;
