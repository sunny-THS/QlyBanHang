const { Schema, model } = require('mongoose');

const SanPhamSchema = new Schema(
    {
        ten: String,
        soLuong: Number,
        nsx: String,
        hinhAnh: String,
        hangSP: String,
        loaiSP: String,
        cauHinh: [{
            tenCH: String,
            moTaCH: String
        }],
        gia: Number
    },
    { timestamps: true }
);

module.exports = model("Product", SanPhamSchema);