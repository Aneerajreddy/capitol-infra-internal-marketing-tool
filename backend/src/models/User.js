const { Schema, model } = require('mongoose');

module.exports = model('User', new Schema({
  name: { type: String, required: true },
  email: { type: String, required: true, unique: true },
  mobile: { type: String, required: true, unique: true },
  passwordHash: { type: String, required: true },
  role: { type: Schema.Types.ObjectId, ref: 'Role', required: true },
  isActive: { type: Boolean, default: true }
}, { timestamps: true }));
