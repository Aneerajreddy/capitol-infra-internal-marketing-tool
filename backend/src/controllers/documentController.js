const Document = require('../models/Document');
const { storeLocal } = require('../services/storageService');

exports.upload = async (req, res) => {
  const fileUrl = await storeLocal(req.file);
  const doc = await Document.create({
    ...req.body,
    uploadedBy: req.user._id,
    fileName: req.file.originalname,
    fileUrl
  });
  res.status(201).json(doc);
};
