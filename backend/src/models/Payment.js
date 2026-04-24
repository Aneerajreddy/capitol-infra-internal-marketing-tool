const { Schema, model } = require('mongoose');

module.exports = model('Payment', new Schema({
  booking: { type: Schema.Types.ObjectId, ref: 'Booking', required: true },
  amount: { type: Number, required: true },
  mode: { type: String, enum: ['Cash', 'Bank', 'UPI'], required: true },
  paidAt: { type: Date, default: Date.now },
  receiptNumber: { type: String, required: true, unique: true }
}, { timestamps: true }));
