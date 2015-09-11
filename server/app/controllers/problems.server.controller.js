'use strict';

/**
 * Module dependencies.
 */
var mongoose = require('mongoose'),
    Problem = mongoose.model('Problem'),
    _ = require('lodash');

/**
 * Create a Problem
 */
exports.create = function(req, res) {
    var problem = new Problem(req.body);
    // problem.author = req.user;

    problem.save(function(err) {
        if (err) {
			return res.status(400).send({
				message: errorHandler.getErrorMessage(err)
			});
		} else {
            problem.id = problem._id;
			res.json(problem);
		}
    });
};

/**
 * Show the current Problem
 */
exports.read = function(req, res) {
    res.json(req.problem);
};

/**
 * Update a Problem
 */
exports.update = function(req, res) {
    var problem = req.problem;

	problem = _.extend(problem, req.body);

	problem.save(function(err) {
		if (err) {
			return res.status(400).send({
				message: errorHandler.getErrorMessage(err)
			});
		} else {
			res.json(problem);
		}
	});
};

/**
 * Delete an Problem
 */
exports.delete = function(req, res) {

};

/**
 * List of Problems
 */
exports.list = function(req, res) {
    Problem.find()
        .sort('-created')
        // .populate('user', 'displayName')
        .exec(function(err, problems) {
    		if (err) {
    			return res.status(400).send({
    				message: errorHandler.getErrorMessage(err)
    			});
    		} else {
    			res.json(problems);
    		}
	});
};

/**
 *
 */
exports.like = function(req, res) {

}

/**
 *
 */
exports.follow = function(req, res) {

}

/**
 * Problem middleware
 */
exports.problemByID = function(req, res, next, id) {
	Problem.findById(id)
    //.populate('user', 'displayName')
    .exec(function(err, problem) {
		if (err) return next(err);
		if (!problem) return next(new Error('Failed to load problem ' + id));
		req.problem = problem;
		next();
	});
};

/**
 * Problem authorization middleware
 */
exports.hasAuthorization = function(req, res, next) {
	if (req.problem.user.id !== req.user.id) {
		return res.status(403).send({
			message: 'User is not authorized'
		});
	}
	next();
};
