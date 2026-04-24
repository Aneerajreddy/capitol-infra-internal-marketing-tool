const { Schema, model } = require('mongoose');

module.exports = model('Inventory', new Schema({
  project: { type: Schema.Types.ObjectId, ref: 'Project', required: true },
  unitCode: { type: String, required: true, unique: true },
  unitType: { type: String, enum: ['Plot', 'Flat'], required: true },
  area: Number,
  price: Number,
  availability: { type: String, enum: ['Available', 'Booked', 'Blocked'], default: 'Available' }
}, { timestamps: true }));
