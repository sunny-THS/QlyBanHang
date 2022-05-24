const User = require('../models/Users');
const bcrypt = require('bcryptjs'); // hash pw
const mailer = require('nodemailer');


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
                    // console.log(user);
                    if (user)
                        return res.status(401).send(MESSAGE_USER_EXISTS);
                    
                    // user is not exists
                    // create new user
                    const newUser = await new User({
                        account: {
                            username: req.body.user.username,
                            password: hashed,
                            image: 'macDinh.png'
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
            res.json({...other, image: authentication.HandleHinhAnh(account.image), username:account.username, birdthDay:authentication.formatDate(birdthDay) }); // return infomation user

        } catch (error) {
            res.status(500).send(error.message)
        }
    },
    checkEmail: async(req, res) => {
        let transporter = mailer.createTransport({
            host: "smtp.office365.com",
            port: 587,
            service: "outlook",
            secure: false,
            auth: {
                user: process.env.ACCOUNT_MAIL_US,
                pass: process.env.ACCOUNT_MAIL_PW
            },
            authentication: 'plan',
            tls: {
                rejectUnauthorized: false
            },
            
        });

        let mailOptions = {
            from: process.env.ACCOUNT_MAIL_US,
            to: req.body.email,
            subject: 'Mã xác nhận email',
            html: `Mã xác nhận: <b>${req.body.code}</b>`
        };

        transporter.sendMail(mailOptions, (err, success) => {
            if (err)
                console.error(err);
            else res.send(success);
        });
    },
    getInfoUser: async(req, res, next) => {
        try {
            const user = await User.findOne({ "account.username": req.body.username });
            if (!user)
                return res.status(404).send(MESSAGE_LOGIN_FAIL);            

            next();

        } catch (error) {
            res.status(500).send(error.message)
        }
    },
    changeInfo_avt: async(req, res) => {
        try {
            const base64Data = req.body.image.replace(/^data:image\/jpg;base64,/, "");

            require("fs").writeFile(`./Image/${req.body.username}_imageAVT.jpeg`, base64Data, 'base64', function(err) {
              console.log(err);
            });

            // cập nhật thông tin avt cho user
            const filter = { 'account.username': req.body.username }
            const update = { 'account.image': `${req.body.username}_imageAVT.jpeg` };
            await User.findOneAndUpdate(filter, update, {
                new: true
            });
            res.send(authentication.HandleHinhAnh(`${req.body.username}_imageAVT.jpeg`));
        } catch (error) {
            res.status(500).send(error.message)
        }
    },
    changeInfo: async(req, res) => {
        try {
            const salt = await bcrypt.genSalt();
            // hash password
            const hashed = await bcrypt.hash(req.body.pw, salt);
            // cập nhật thông tin avt cho user
            const filter = { 'account.username': req.body.username }
            const update = { 
                'account.password': hashed,
                name: req.body.hoTen
            };  
            await User.findOneAndUpdate(filter, update, {
                new: true
            });
            res.send('Cập nhật thông tin thành công');
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
    }, 
    HandleHinhAnh: (image) => {
        return `http://${process.env.HOST}:${process.env.PORT}/images/${image}`;
    }
}

module.exports = authentication;