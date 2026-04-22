const logger = require('../utils/logger');

module.exports = (err, req, res, next) => {
  logger.error({ message: err.message, stack: err.stack, path: req.path });
  res.status(err.status || 500).json({ message: err.message || 'Internal server error' });
};
