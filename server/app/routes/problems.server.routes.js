'use strict';

var users = require('../../app/controllers/users.server.controller'),
	problems = require('../../app/controllers/problems.server.controller');


module.exports = function(app) {
	// Routing logic
	// ...

	app.route('/problems')
		.get(users.requiresLoginToken, problems.list)
		.post(users.requiresLoginToken, problems.create);

	app.route('/problems/:problemId')
		.get(users.requiresLoginToken, problems.read)
		.put(users.requiresLoginToken, problems.hasAuthorization, problems.update);

	app.route('/problems/:problemId/likes')
		.get(users.requiresLoginToken, problems.likers)
		.post(users.requiresLoginToken, problems.like);

	app.route('/problems/:problemId/followers')
		.get(users.requiresLoginToken, problems.followers)
		.post(users.requiresLoginToken, problems.follow);
		
	// Finish by binding problem middleware
	app.param('problemId', problems.problemByID);
};
