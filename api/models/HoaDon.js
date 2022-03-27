const { Schema, model } = require('mongoose');

const HoaDonSchema = new Schema(
    {
        donGia: Number,
        idKH: String,
        chiTietDonHang: {
            tenSP: String,
            soLuong: Number,
            gia: Number
        }
    },
    { timestamps: true }
);

module.exports = model("HoaDonSP", HoaDonSchema);