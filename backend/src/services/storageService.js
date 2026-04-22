const fs = require('fs');
const path = require('path');

const uploadDir = path.join(process.cwd(), 'backend', 'uploads');
if (!fs.existsSync(uploadDir)) fs.mkdirSync(uploadDir, { recursive: true });

const storeLocal = async (file) => {
  const target = path.join(uploadDir, `${Date.now()}-${file.originalname}`);
  fs.writeFileSync(target, file.buffer);
  return `/uploads/${path.basename(target)}`;
};

module.exports = { storeLocal };
