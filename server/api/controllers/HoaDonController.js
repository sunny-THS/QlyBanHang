const HoaDon = require('../models/HoaDon');
const SanPham = require('../models/SanPham');

const hoaDon = {
    getBills: async (req, res) => {
        await HoaDon.find({})
            .then(data => {
                res.json(data);
            })
            .catch(err => res.json(err));
    },
    getBills_user: async (req, res) => {
        await HoaDon.find({ tenKH: req.body.username })
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

        const chiTietDH = req.body.chiTietDonHang;
        const donGiaHD = chiTietDH.map(item => item.gia * item.soLuong);
        
        // cập nhât & check số lượng
        for (const it of chiTietDH) {
            const sp = await SanPham.findOne({ ten: it.tenSP})
            sp.soLuong -= it.soLuong;

            sp.save();
        }

        // tạo hóa đơn
        const newHD = await new HoaDon({
            donGia: donGiaHD.reduce((partialSum, a) => partialSum + a, 0),
            tenKH: req.body.tenKH,
            diaChi: req.body.diaChi,
            chiTietDonHang: chiTietDH
        });
        
        // save to mongodb
        await newHD.save();


        res.json(req.body);
        
    }
}

module.exports = hoaDon;