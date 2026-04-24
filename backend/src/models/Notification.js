const { Schema, model } = require('mongoose');

module.exports = model('Notification', new Schema({
  title: { type: String, required: true },
  message: { type: String, required: true },
  user: { type: Schema.Types.ObjectId, ref: 'User' },
  readAt: Date
}, { timestamps: true }));
