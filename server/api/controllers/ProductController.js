const SanPham = require('../models/SanPham');

const sp = {
    getProduct: async (req, res) => {
        await SanPham.find({})
            .then(data => {
                res.json(sp.HandleHinhAnh(data));
            })
            .catch(err => res.json(err));
    },
    getProduct_type: async(req, res) => {
        await SanPham.find({ loaiSP: req.params.tenLoaiSP })
            .then(data => {
                res.json(sp.HandleHinhAnh(data));
            })
            .catch(err => res.json(err));
    },
    getSomeProduct: async (req, res) => {
        const filter = { ten: {$regex: req.params.name, $options:"$i"}};

        await SanPham.find(filter)
            .then(data => {
                res.json(sp.HandleHinhAnh(data));
            })
            .catch(err => res.json(err));
    }, 
    HandleHinhAnh: (data) => {
        data.forEach(item => {
            item.hinhAnh = `http://${process.env.HOST}:${process.env.PORT}/images/${item.hinhAnh}`
        });
        return data;
    }
}

module.exports = sp;