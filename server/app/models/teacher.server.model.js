'use strict';

/**
 * Module dependencies.
 */
var mongoose = require('mongoose'),
	Schema = mongoose.Schema,

	UserSchema = require('./user.server.model');

/**
 * Teacher Schema
 */
var TeacherSchema = new Schema({
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

// mongoose.model('Teacher', TeacherSchema);
mongoose.model('User').discriminator('teacher', TeacherSchema);
