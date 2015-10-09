'use strict';

/**
 * Module dependencies.
 */
var mongoose = require('mongoose'),
	errorHandler = require('./errors.server.controller'),
    Problem = mongoose.model('Problem'),
    _ = require('lodash');

/**
 * Create a Problem
 */
exports.create = function(req, res) {
    var problem = new Problem(req.body);
    problem.author = req.user;

    problem.save(function(err, ps) {
        if (err) {
			return res.status(400).send({
				message: errorHandler.getErrorMessage(err)
			});
		} else {
            problem.populate('author');
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
        .populate('author')
        .populate('likes.author')
        .populate('follows.author')
        .exec(function(err, problems) {
    		if (err) {
    			return res.status(400).send({
    				message: errorHandler.getErrorMessage(err)
    			});
    		} else {
				console.log(problems);
    			res.json(problems);
    		}
	});
};

/**
 *
 */
exports.like = function(req, res) {
	var problem = req.problem;
	var user = req.user;
	var state = true;

	for(var i = 0; i < problem.likes.length; i++) {
		var like = problem.likes[i];
		if (like.author.toString() === user._id.toString()) {
			problem.likes.id(like.id).remove();

			state = false;
			break;
		}
	}

	if (state) {
		problem.likes.push({
			author: user
		});
	}

	problem.save(function(err) {
		if (err) {
			return res.status(400).send({
				message: errorHandler.getErrorMessage(err)
			});
		}
		res.json(state);
	})

}

/**
 *
 */
exports.likers = function(req, res) {

}

/**
 *
 */
exports.follow = function(req, res) {
	var problem = req.problem;
	var user = req.user;
	var state = true;

	for(var i = 0; i < problem.followers.length; i++) {
		var follower = problem.followers[i];
		if (follower.author.toString() === user._id.toString()) {
			problem.followers.id(follower.id).remove();

			state = false;
			break;
		}
	}

	if (state) {
		problem.followers.push({
			author: user
		});
	}

	problem.save(function(err) {
		if (err) {
			return res.status(400).send({
				message: errorHandler.getErrorMessage(err)
			});
		}
		res.json(state);
	})
}

/**
 *
 */
exports.followers = function(req, res) {

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
