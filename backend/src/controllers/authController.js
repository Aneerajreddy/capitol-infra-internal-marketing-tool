const bcrypt = require('bcryptjs');
const jwt = require('jsonwebtoken');
const User = require('../models/User');
const { signAccessToken, signRefreshToken } = require('../utils/tokens');
const env = require('../config/env');

exports.login = async (req, res) => {
  const { mobile, password } = req.body;
  const user = await User.findOne({ mobile }).populate('role');
  if (!user) return res.status(401).json({ message: 'Invalid credentials' });

  const ok = await bcrypt.compare(password, user.passwordHash);
  if (!ok) return res.status(401).json({ message: 'Invalid credentials' });

  const payload = { sub: user._id.toString(), role: user.role.name };
  res.json({
    accessToken: signAccessToken(payload),
    refreshToken: signRefreshToken(payload),
    user: { id: user._id, name: user.name, role: user.role.name }
  });
};

exports.refresh = async (req, res) => {
  const { refreshToken } = req.body;
  const decoded = jwt.verify(refreshToken, env.jwtRefreshSecret);
  const accessToken = signAccessToken({ sub: decoded.sub, role: decoded.role });
  res.json({ accessToken });
};
