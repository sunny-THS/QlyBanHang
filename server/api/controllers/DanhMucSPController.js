const DanhMucSP = require('../models/DanhMucSP');

const danhMuc = {
    getCategory: async (req, res) => {
        await DanhMucSP.find()
            .then(data => {
                res.json(data);
            })
            .catch(err => res.json(err));
    },
}

module.exports = danhMuc;