const { Schema, model } = require('mongoose');

const LoaiSanPhamSchema = new Schema(
    {
        ten: String,
        tenDanhMuc: String,
    },
    { timestamps: true }
);

module.exports = model("LoaiSP", LoaiSanPhamSchema);