const User = require('../models/Users');
const bcrypt = require('bcryptjs'); // hash pw

// name: String,
// email: String,
// birdthDay: Date,
// sex: Boolean, // true: male, false: female
// phoneNumbers: String,
// account: {
//     username: String,
//     password: String
// },

const MESSAGE_LOGIN_FAIL = "Thông tin đăng nhập không chính xác!";
const MESSAGE_USER_EXISTS = "Tên đăng nhập đã tồn tại!";
const MESSAGE_SIGNIN_SUCCESS = "Đăng ký thành công";

const authentication = {
    // REGISTER
    registerUser: async(req, res) => {
        try {
            const salt = await bcrypt.genSalt();
            // hash password
            const hashed = await bcrypt.hash(req.body.user.pw, salt);
            // check exists of user
            await User.findOne({ "account.username": req.body.user.username })
                .then( async (user) => {
                    console.log(user);
                    if (user)
                        return res.status(401).send(MESSAGE_USER_EXISTS);
                    
                    // user is not exists
                    // create new user
                    const newUser = await new User({
                        account: {
                            username: req.body.user.username,
                            password: hashed
                        },
                        name: req.body.user.name,
                        email: req.body.user.email,
                        birdthDay: req.body.user.birdthDay,
                        sex: req.body.user.sex, // true: male, false: female
                        phoneNumbers: req.body.user.phoneNumbers,
                    });
                    
                    // save to mongodb
                    await newUser.save();
                    res.send(MESSAGE_SIGNIN_SUCCESS);
                })
        } catch (error) {
            res.status(500).json(error.message);
        }
    },
    // LOGIN
    login: async(req, res) => {
        try {
            const user = await User.findOne({ "account.username": req.body.username });
            if (!user)
                return res.status(404).send(MESSAGE_LOGIN_FAIL);
            
            const validPw = await bcrypt.compare(req.body.pw, user.account.password);
            if (!validPw)
                return res.status(404).send(MESSAGE_LOGIN_FAIL);

            // successful
            // const accessToken = authentication.generateAccessToken(user);

            const {account, birdthDay, ...other} = user._doc;
            // res.json({ ...other, accessToken });
            res.json({...other, username:account.username, birdthDay:authentication.formatDate(birdthDay) }); // return infomation user

        } catch (error) {
            res.status(500).send(error.message)
        }
    },
    padTo2Digits: (num) => {
        return num.toString().padStart(2, '0');
    },
      
    formatDate: (date) => {
        return [
            authentication.padTo2Digits(date.getDate()),
            authentication.padTo2Digits(date.getMonth() + 1),
            date.getFullYear(),
        ].join('/');
    }
}

module.exports = authentication;