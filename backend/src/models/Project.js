const { Schema, model } = require('mongoose');

module.exports = model('Project', new Schema({
  name: { type: String, required: true },
  location: { type: String, required: true },
  description: String,
  totalUnits: { type: Number, default: 0 }
}, { timestamps: true }));
