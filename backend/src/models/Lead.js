const { Schema, model } = require('mongoose');

module.exports = model('Lead', new Schema({
  name: { type: String, required: true },
  phone: { type: String, required: true },
  source: { type: String, default: 'manual' },
  status: { type: String, enum: ['New', 'Follow-up', 'Site Visit', 'Closed'], default: 'New' },
  assignedTo: { type: Schema.Types.ObjectId, ref: 'User' },
  notes: [{ text: String, at: { type: Date, default: Date.now } }]
}, { timestamps: true }));
