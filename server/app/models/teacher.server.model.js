'use strict';

/**
 * Module dependencies.
 */
var mongoose = require('mongoose'),
	extend = require('mongoose-schema-extend'),
	Schema = mongoose.Schema,

	UserSchema = require('./user.server.model');

/**
 * Teacher Schema
 */
var TeacherSchema = UserSchema.extend({
	// Teacher model fields
	// ...

	placeType: {
		type: String,
		required: true,
		trim: true
	},

	placeName: {
		type: String,
		required: true,
		trim: true
	},

	subject: {
		type: String,
		required: true,
		trim: true
	}
});

mongoose.model('Teacher', TeacherSchema);
// mongoose.model('User').discriminator('teacher', TeacherSchema);
