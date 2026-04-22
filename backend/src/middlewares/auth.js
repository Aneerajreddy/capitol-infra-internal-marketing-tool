const jwt = require('jsonwebtoken');
const env = require('../config/env');
const User = require('../models/User');

module.exports = async (req, res, next) => {
  const header = req.headers.authorization;
  if (!header || !header.startsWith('Bearer ')) return res.status(401).json({ message: 'Unauthorized' });

  try {
    const token = header.replace('Bearer ', '');
    const payload = jwt.verify(token, env.jwtSecret);
    const user = await User.findById(payload.sub).populate('role');
    if (!user) return res.status(401).json({ message: 'Unauthorized' });
    req.user = user;
    next();
  } catch (e) {
    return res.status(401).json({ message: 'Invalid token' });
  }
};
