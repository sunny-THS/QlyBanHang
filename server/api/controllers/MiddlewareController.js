const Users = require('../models/Users');

const middleware = {
    verifyAccount: async (req, res, next) => {
        await Users.find({ "account.username": req.body.user.username, email: req.body.user.email, phoneNumbers: req.body.user.phoneNumbers })
            .then(user => {
                req.body.tenKH = user[0].name;
                req.body.user = null;
                next();
            }).catch(err => {
                res.json(err);
            })
    },
}

module.exports = middleware;