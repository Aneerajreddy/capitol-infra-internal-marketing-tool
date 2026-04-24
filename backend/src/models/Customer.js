const { Schema, model } = require('mongoose');

module.exports = model('Customer', new Schema({
  name: { type: String, required: true },
  phone: { type: String, required: true },
  email: String,
  address: String,
  interactions: [{
    mode: String,
    note: String,
    createdBy: { type: Schema.Types.ObjectId, ref: 'User' },
    createdAt: { type: Date, default: Date.now }
  }]
}, { timestamps: true }));
