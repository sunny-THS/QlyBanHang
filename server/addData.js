// @tenSP NVARCHAR(MAX),
// @tenHang NVARCHAR(20), -- tên hãng sản phẩm
// @soLuong INT,
// @gia FLOAT,
// @nxs NVARCHAR(30),
// @urlImage VARCHAR(50),
// @tenLSP NVARCHAR(50)

const fs = require('fs');
const { getgid } = require('process');

module.exports = {
    readFileProduct: async (filePath, callback) => {
        try {
            await fs.readFile(filePath, "utf8", (err, data) => {
                if (err) return callback(err);

                let infoProduct = data.split('\n').map(item => {
                    let data = item.split(',');
                    return {
                        ten: data[0].trim(),
                        soLuong: parseInt(data[2].trim()),
                        nsx: data[4].trim(),
                        hinhAnh: data[5].trim(),
                        loaiSP: data[6].trim(),
                        gia: parseFloat(data[3].trim()),
                        hangSP: data[1].trim()
                    }
                });
                callback(null, infoProduct)
            });
        } catch (error) {
            console.error(`Got an error trying to read the file: ${error.message}`);
        }
    },
    readFileConfigProduct: async (filePath, callback) => {
        try {
            await fs.readFile(filePath, "utf8", (err, data) => {
                if (err) return callback(err);
    
                let configProduct = data.split('~').map(item => {
                    const tenSP = item.substring(0, item.indexOf(',')).trim();
                    return {
                        tenSP,
                        cauHinh: item.trim().split('\n').map(tt => {
                            return {
                                tenCH: tt.split(',')[1].trim(),
                                moTaCH: tt.split(`${tt.split(',')[1]},`)[1].trim()
                            }
                        })
                    }
                })
                callback(null, configProduct);
            });
        } catch (error) {
            console.error(`Got an error trying to read the file: ${error.message}`);
        }
    },
    readFileProductPortfolio: async (filePath, callback) => {
        try {
            await fs.readFile(filePath, "utf8", (err, data) => {
                if (err) return callback(err);
    
                let ProductPortfolio = data.split(',').map(item => {
                    return {
                        ten: item.trim()
                    }
                })
                callback(null, ProductPortfolio);
            });
        } catch (error) {
            console.error(`Got an error trying to read the file: ${error.message}`);
        }
    },
    readFileProductType: async (filePath, callback) => {
        try {
            await fs.readFile(filePath, "utf8", (err, data) => {
                if (err) return callback(err);
    
                let ProductPortfolio = data.split('\n').map(item => {
                    const data = item.split(',');
                    return {
                        ten: data[0].trim(),
                        tenDanhMuc: data[1].trim()
                    }
                })
                callback(null, ProductPortfolio);
            });
        } catch (error) {
            console.error(`Got an error trying to read the file: ${error.message}`);
        }
    },
    readFileProductCompany: async (filePath, callback) => {
        try {
            await fs.readFile(filePath, "utf8", (err, data) => {
                if (err) return callback(err);
    
                let ProductPortfolio = data.split(',').map(item => {
                    return {
                        ten: item.trim(),
                    }
                })
                callback(null, ProductPortfolio);
            });
        } catch (error) {
            console.error(`Got an error trying to read the file: ${error.message}`);
        }
    }
}