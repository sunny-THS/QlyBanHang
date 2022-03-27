const { Schema, model } = require('mongoose');

const DanhMucSanPhamSchema = new Schema(
    {
        ten: String
    },
    { timestamps: true }
);

module.exports = model("DanhMucSP", DanhMucSanPhamSchema);