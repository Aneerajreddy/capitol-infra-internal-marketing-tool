const { Schema, model } = require('mongoose');

module.exports = model('Role', new Schema({
  name: { type: String, required: true, unique: true },
  permissions: [{ type: String }]
}, { timestamps: true }));
