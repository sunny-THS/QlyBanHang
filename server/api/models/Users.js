const { Schema, model } = require('mongoose');

const UserSchema = new Schema(
    {
        name: String,
        email: String,
        birdthDay: Date,
        sex: Boolean, // true: male, false: female
        phoneNumbers: String,
        account: {
            username: String,
            password: String,
            image: String
        },
    },
    { timestamps: true }
);

module.exports = model("User", UserSchema);