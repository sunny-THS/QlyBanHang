const HangSP = require('../models/HangSP');

const hang = {
    getProductCompany: async (req, res) => {
        await HangSP.find()
            .then(data => {
                res.json(data);
            })
            .catch(err => res.json(err));
    },
}

module.exports = hang;