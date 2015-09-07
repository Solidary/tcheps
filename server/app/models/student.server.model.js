'use strict';

/**
 * Module dependencies.
 */
var mongoose = require('mongoose'),
	Schema = mongoose.Schema,

	UserSchema = require('./user.server.model')();

/**
 * Student Schema
 */
var StudentSchema = new UserSchema({
	// Student model fields
	// ...

	level: {
		type: String,
		required: true,
		trim: true
	},

	school: {
		type: String,
		required: true,
		trim: true
	}
});

// mongoose.model('Student', StudentSchema);
mongoose.model('User').discriminator('student', StudentSchema);
