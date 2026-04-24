const { Schema, model } = require('mongoose');

module.exports = model('Booking', new Schema({
  customer: { type: Schema.Types.ObjectId, ref: 'Customer', required: true },
  inventory: { type: Schema.Types.ObjectId, ref: 'Inventory', required: true },
  bookingAmount: { type: Number, required: true },
  status: { type: String, enum: ['Initiated', 'Confirmed', 'Cancelled'], default: 'Initiated' },
  receiptNumber: { type: String, required: true, unique: true }
}, { timestamps: true }));
