const { Schema, model } = require('mongoose');

// hãng sản phẩm
const HangSanPhamSchema = new Schema( 
    {
        ten: String,
    },
    { timestamps: true }
);

module.exports = model("HangSP", HangSanPhamSchema);