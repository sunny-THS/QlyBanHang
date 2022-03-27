const HoaDon = require('../models/HoaDon');

const hoaDon = {
    getBills: async (req, res) => {
        await HoaDon.find()
            .then(data => {
                res.json(data);
            })
            .catch(err => res.json(err));
    },
    getBills_user: async (req, res) => {
        await HoaDon.find({ idKH: req.params.idKH })
            .then(data => {
                res.json(data);
            })
            .catch(err => res.json(err));
    },
    setupBill: async (req, res) => {
        // method: post
        // input: lst products & idKH
        // handle: 
        // create new bill, save infomation into database
        // output: bill
        
    }

}

module.exports = hoaDon;