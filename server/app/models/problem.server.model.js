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
	},
	author: {
		type: Schema.ObjectId,
		ref: 'User'
	},

	likes: [{
		author: {
			type: Schema.ObjectId,
			ref: 'User'
		},
		liked: {
			type: Date,
			default: Date.now
		}
	}],
	followers: [{
		author: {
			type: Schema.ObjectId,
			ref: 'User'
		},
		followed: {
			type: Date,
			default: Date.now
		}
	}]
});

mongoose.model('Problem', ProblemSchema);
