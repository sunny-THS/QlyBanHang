const { json } = require('express');
const Users = require('../models/Users');

const middleware = {
    verifyAccount: async (req, res, next) => {
        // console.log(req.body);
        await Users.find({ 
            "account.username": req.body.user.username, 
            email: req.body.user.email, 
            phoneNumbers: req.body.user.phoneNumbers,
            _id:  req.body.user.id
        })
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