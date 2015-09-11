'use strict';

/**
 * Module dependencies.
 */
var passport = require('passport'),
	LocalStrategy = require('passport-local').Strategy,
	User = require('mongoose').model('User'),

	Student = require('mongoose').model('Student');

module.exports = function() {
	// Use local strategy
	passport.use(new LocalStrategy({
			// usernameField: 'username',
			usernameField: 'email',
			passwordField: 'password'
		},
		// function(username, password, done) {
		function(email, password, done) {
			console.log('passport ... local ... ' + email + ' ... ' + password);
			// User.findOne({
			User.findOne({
				// username: username
				email: email
			}, function(err, user) {
				console.log('passport ... local ... after User.findOne');
				if (err) {
					console.log('passport ... local ... after User.findOne ... error');
					return done(err);
				}
				console.log('passport ... local ... after User.findOne ... no error');
				if (!user) {
					return done(null, false, {
						message: 'Unknown user or invalid password'
					});
				}
				if (!user.authenticate(password)) {
					return done(null, false, {
						message: 'Unknown user or invalid password'
					});
				}

				return done(null, user);
			});
		}
	));
};
