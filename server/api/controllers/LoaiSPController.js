const LoaiSP = require('../models/LoaiSP');

const loai = {
        getType: async (req, res) => {
        await LoaiSP.find({ tenDanhMuc: req.params.tenDanhMuc })
            .then(data => {
                res.json(data);
            })
            .catch(err => res.json(err));
    },
}

module.exports = loai;