'use strict';

/**
 * Module dependencies.
 */
var mongoose = require('mongoose'),
	Schema = mongoose.Schema;

/**
 * Problem Schema
 */
var ProblemSchema = new Schema({
	// Problem model fields
	// ...

	description: {
		type: String,
		required: true,
		trim: true
	},
	subject: {
		type: String,
		required: true
	},


	created: {
		type: Date,
		default: Date.now
	}
});

mongoose.model('Problem', ProblemSchema);
