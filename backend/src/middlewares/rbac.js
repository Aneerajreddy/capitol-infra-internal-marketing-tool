module.exports = (...allowedRoles) => (req, res, next) => {
  const roleName = req.user?.role?.name;
  if (!roleName || !allowedRoles.includes(roleName)) {
    return res.status(403).json({ message: 'Forbidden' });
  }
  next();
};
