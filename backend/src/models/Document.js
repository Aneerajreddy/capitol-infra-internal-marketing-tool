const { Schema, model } = require('mongoose');

module.exports = model('Document', new Schema({
  entityType: { type: String, enum: ['Project', 'Booking', 'Customer'], required: true },
  entityId: { type: Schema.Types.ObjectId, required: true },
  category: { type: String, enum: ['RERA', 'DTCP', 'Agreement', 'Other'], required: true },
  fileName: { type: String, required: true },
  fileUrl: { type: String, required: true },
  uploadedBy: { type: Schema.Types.ObjectId, ref: 'User', required: true }
}, { timestamps: true }));
