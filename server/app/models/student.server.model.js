'use strict';

/**
 * Module dependencies.
 */
var mongoose = require('mongoose'),
	extend = require('mongoose-schema-extend'),
	Schema = mongoose.Schema,

	UserSchema = require('./user.server.model');

/**
 * Student Schema
 */
var StudentSchema = UserSchema.extend({
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

mongoose.model('Student', StudentSchema);
// mongoose.model('User').discriminator('student', StudentSchema);
