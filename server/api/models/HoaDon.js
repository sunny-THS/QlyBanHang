const { Schema, model } = require('mongoose');

const HoaDonSchema = new Schema(
    {
        donGia: Number,
        tenKH: String,
        diaChi: String,
        chiTietDonHang: [{
            tenSP: String,
            soLuong: Number,
            gia: Number
        }],
        TrangThai: String
    },
    { timestamps: true }
);

module.exports = model("HoaDonSP", HoaDonSchema);