const bcrypt = require('bcryptjs');
const connectDb = require('../config/db');
const Role = require('../models/Role');
const User = require('../models/User');

(async () => {
  await connectDb();
  await Promise.all([Role.deleteMany({}), User.deleteMany({})]);

  const [adminRole, managerRole, associateRole] = await Role.insertMany([
    { name: 'admin', permissions: ['*'] },
    { name: 'manager', permissions: ['leads:all', 'projects:read', 'bookings:all'] },
    { name: 'associate', permissions: ['leads:own', 'projects:read'] }
  ]);

  const hash = await bcrypt.hash('Admin@123', 10);
  await User.create({
    name: 'System Admin',
    email: 'admin@crm.local',
    mobile: '9999999999',
    passwordHash: hash,
    role: adminRole._id
  });

  console.log('Seed completed. Login mobile: 9999999999 password: Admin@123');
  process.exit(0);
})();
