'use strict';

var cors = require('cors');


module.exports = function(app) {
    
    app.use(cors());
    app.use(function(req, res, next) {
        // Website you wish to allow to connect
        res.set('Access-Control-Allow-Origin', '*');
        // Request methods you wish to allow
        res.set('Access-Control-Allow-Methods', 'GET, POST, DELETE, PUT');
        // Request headers you wish to allow
        res.set('Access-Control-Allow-Headers', 'Origin, Accept, Content-Type, X-Requested-With, X-CSRF-Token');

        // Pass to next layer of middleware
        next();
    });

}
