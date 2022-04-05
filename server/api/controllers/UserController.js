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

const authentication = {
    // REGISTER
    registerUser: async(req, res) => {
        try {
            console.log(req.body);
            const salt = await bcrypt.genSalt();
            // hash password
            const hashed = await bcrypt.hash(req.body.user.pw, salt);
            // check exists of user
            await User.findOne({ account: {username: req.body.user.username }}) // err
                .then( async (user) => {
                    console.log(user);
                    if (user)
                        return res.status(401).json("Username is exists!");
                    
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
                    res.json('Successful');
                })
        } catch (error) {
            res.status(500).json(error.message);
        }
    },
    // LOGIN
    login: async(req, res) => {
        try {
            const user = await User.findOne({ username: req.body.username });
            if (!user)
                return res.status(404).json('Wrong username');

            const validPw = await bcrypt.compare(req.body.pw, user.password);
            if (!validPw)
                return res.status(404).json('Wrong password');

            // successful
            // const accessToken = authentication.generateAccessToken(user);

            const {password, ...other} = user._doc;
            // res.json({ ...other, accessToken });
            res.json(...other); // return infomation user

        } catch (error) {
            res.json(error.message)
        }
    }
}

module.exports = authentication;