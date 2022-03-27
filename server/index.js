const express = require('express');
const address = require('address');

if (process.env.NODE_ENV !== 'production')
    require('dotenv').config();

const database = require('./api/database/Mongodb')

const rImg = require('./api/router/Image');
const rProduct = require('./api/router/Product');

const port = process.env.PORT || 5123;

const app = express();

// routes
app.use('/images', rImg);
app.use('/products', rProduct);

app.use((req, res, next) => {
    const err = new Error('not found');
    res.status(404);
    next(err);
});

app.use((err, req, res, next) => {
    res.status(res.statusCode || 500);
    res.json({
        message: err.message
    });
});

// add product ================================================================
const SanPham = require('./api/models/SanPham');
const DanhMucSP = require('./api/models/DanhMucSP');
const LoaiSP = require('./api/models/LoaiSP');
const HangSP = require('./api/models/HangSP');

const addData = require('./addData');

// SanPham.deleteMany({}).then(rs => console.log(rs));
// DanhMucSP.deleteMany({}).then(rs => console.log(rs));
// LoaiSP.deleteMany({}).then(rs => console.log(rs));
// HangSP.deleteMany({}).then(rs => console.log(rs));

// addData.readFileProductPortfolio('danhMuc.txt', (err, data) => {
//     for (const item of data) {
//         const danhMuc = new DanhMucSP(item);
//         danhMuc.save();
//     }
// });

// addData.readFileProductType('LoaiSP.txt', (err, data) => {
//     for (const item of data) {
//         const loai = new LoaiSP(item);
//         loai.save();
//     }
// });

// addData.readFileProductCompany('HangSP.txt', (err, data) => {
//     for (const item of data) {
//         const hang = new HangSP(item);
//         hang.save();
//     }
// });

// addData.readFileProduct('data.txt', (err, data) => {
//     for (const item of data) {
//         const sp = new SanPham(item);
//         sp.save();
//     }
// })

// addData.readFileConfigProduct('cauhinh.txt', async (err, data) => {
//     for (const item of data) {
//         const filter = { ten: item.tenSP }
//         const update = { cauHinh: item.cauHinh };
//         await SanPham.findOneAndUpdate(filter, update, {
//             new: true
//         });
//     }
// })

// add product ================================================================


app.listen(port, () => console.log(`Url: http://${address.ip()}:5123`));