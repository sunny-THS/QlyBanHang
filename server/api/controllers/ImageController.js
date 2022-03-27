const fs = require('fs');

const img = {
    getImage: (req, res) => {
        console.log(req.params.fileName);

        fs.readFile(`./Image/${req.params.fileName}`, (err, data) => {
            if (err) {
                res.statusCode = 404;
                res.json(err);
            } 
            res.writeHead(200, {'Content-Type': 'image/jpeg'});
            res.end(data); // Send the file data to the browser.
        });
    },
};

module.exports = img;