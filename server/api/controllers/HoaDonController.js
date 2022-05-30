const HoaDon = require('../models/HoaDon');
const SanPham = require('../models/SanPham');

const hoaDon = {
    getBills: async (req, res) => {
        await HoaDon.find({}).sort({ createdAt: -1 })
            .then(data => {
                data = data.map(hd => {
                    return {
                        _id:hd._id,
                        donGia:hd.donGia,
                        ngayLap:hoaDon.formatDate(hd.createdAt),
                        TrangThai: hd.TrangThai,
                        diaChi: hd.diaChi
                    };
                }).filter(hd => hd.ngayLap.includes(req.body.year))

                const countCN = data.filter(d=>d.TrangThai == "Chưa xác nhận").length;
                const countGH = data.filter(d=>d.TrangThai == "Đang giao hàng").length;
                const countHT = data.filter(d=>d.TrangThai == "Hoàn thành").length;

                const totalBills = data.reduce((t, { donGia }) => t + donGia, 0)
                
                res.json({ HT: countHT, GH: countGH, CN: countCN, totalBills, data });
            })
            .catch(err => res.json(err));
    },
    getBills_state: async (req, res) => {
        await HoaDon.find({ TrangThai: req.body.state }).sort({ createdAt: -1 })
            .then(data => {
                data = data.map(hd => {
                    return {
                        _id:hd._id,
                        donGia:hd.donGia,
                        ngayLap:hoaDon.formatDate(hd.createdAt),
                        TrangThai: hd.TrangThai,
                        diaChi: hd.diaChi
                    };
                }).filter(hd => hd.ngayLap.includes(req.body.year))

                const countCN = data.filter(d=>d.TrangThai == "Chưa xác nhận").length;
                const countGH = data.filter(d=>d.TrangThai == "Đang giao hàng").length;
                const countHT = data.filter(d=>d.TrangThai == "Hoàn thành").length;

                const totalBills = data.reduce((t, { donGia }) => t + donGia, 0)
                
                res.json({ HT: countHT, GH: countGH, CN: countCN, totalBills, data });
            })
            .catch(err => res.json(err));
    },
    getBills_user: async (req, res) => {
        await HoaDon.find({ tenKH: req.body.username }).sort({ createdAt: -1 })
            .then(data => {
                data = data.map(hd => {
                    return {
                        _id:hd._id,
                        donGia:hd.donGia,
                        ngayLap:hoaDon.formatDate(hd.createdAt),
                        TrangThai: hd.TrangThai
                    };
                })
                res.json(data); // chỉnh thông tin
            })
            .catch(err => res.json(err));
    },
    getBill_id: async (req, res) => {
        await HoaDon.find({ _id: req.params.id })
        .then(data => {
            data = data.map(hd => {
                return {
                    _id:hd._id,
                    donGia:hd.donGia,
                    ngayLap:hoaDon.formatDate(hd.createdAt),
                    TrangThai: hd.TrangThai,
                    chiTietDonHang: hd.chiTietDonHang,
                    tenKH: hd.tenKH,
                    diaChi: hd.diaChi
                };
            })
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
            chiTietDonHang: chiTietDH,
            TrangThai: "Chưa xác nhận"
        });
        
        // save to mongodb
        await newHD.save();

        res.json(req.body);
    },
    xacNhanHD: async (req, res) => {
        const filter = { _id: req.body.id }
        const update = { TrangThai: req.body.trangThai == null ? "Đang giao hàng" : "Hoàn thành" };
        await HoaDon.findOneAndUpdate(filter, update, {
            new: true
        });

        res.send("Đơn hàng xác nhận thành công")
    },
    padTo2Digits: (num) => {
        return num.toString().padStart(2, '0');
    },
    formatDate: (date) => {
        return [
            hoaDon.padTo2Digits(date.getDate()),
            hoaDon.padTo2Digits(date.getMonth() + 1),
            date.getFullYear(),
        ].join('/');
    }
}

module.exports = hoaDon;