const SanPham = require('../models/SanPham');

const sp = {
    getProduct: async (req, res) => {
        await SanPham.find()
            .then(data => {
                res.json(data);
            })
            .catch(err => res.json(err));
    },
    getSomeProduct: async (req, res) => {
        const filter = { ten: {$regex: req.params.name, $options:"$i"}};

        await SanPham.find(filter)
            .then(data => {
                res.json(data);
            })
            .catch(err => res.json(err));
    }
}

module.exports = sp;