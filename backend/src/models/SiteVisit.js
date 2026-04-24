const { Schema, model } = require('mongoose');

module.exports = model('SiteVisit', new Schema({
  lead: { type: Schema.Types.ObjectId, ref: 'Lead', required: true },
  customer: { type: Schema.Types.ObjectId, ref: 'Customer' },
  scheduledAt: { type: Date, required: true },
  gpsLat: Number,
  gpsLng: Number,
  status: { type: String, enum: ['Scheduled', 'Completed', 'Cancelled'], default: 'Scheduled' },
  feedback: String
}, { timestamps: true }));
