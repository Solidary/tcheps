'use strict';

var users = require('../../app/controllers/users.server.controller'),
	problems = require('../../app/controllers/problems.server.controller');


module.exports = function(app) {
	// Routing logic
	// ...

	app.route('/problems')
		.get(problems.list)
		.post(problems.create);

	app.route('/problems/:problemId')
		.get(problems.read)
		.put(problems.update);

	app.route('/problems/:problemId/likes')
		.get(problems.likers)
		.post(problems.like);

	// Finish by binding problem middleware
	app.param('problemId', problems.problemByID);
};
