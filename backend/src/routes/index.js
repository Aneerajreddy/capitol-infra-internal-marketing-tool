const router = require('express').Router();
const multer = require('multer');
const upload = multer({ storage: multer.memoryStorage() });

const auth = require('../middlewares/auth');
const rbac = require('../middlewares/rbac');
const authRoutes = require('./authRoutes');
const resourceRoutes = require('./resourceRoutes');
const { crudFactory } = require('../controllers/crudFactory');
const documentController = require('../controllers/documentController');

const Role = require('../models/Role');
const User = require('../models/User');
const Lead = require('../models/Lead');
const Customer = require('../models/Customer');
const Project = require('../models/Project');
const Inventory = require('../models/Inventory');
const SiteVisit = require('../models/SiteVisit');
const Booking = require('../models/Booking');
const Payment = require('../models/Payment');
const Document = require('../models/Document');
const Notification = require('../models/Notification');

router.use('/auth', authRoutes);
router.use(auth);

router.use('/roles', rbac('admin'), resourceRoutes(crudFactory(Role)));
router.use('/users', rbac('admin'), resourceRoutes(crudFactory(User, 'role')));
router.use('/leads', resourceRoutes(crudFactory(Lead, 'assignedTo')));
router.use('/customers', resourceRoutes(crudFactory(Customer)));
router.use('/projects', resourceRoutes(crudFactory(Project)));
router.use('/inventory', resourceRoutes(crudFactory(Inventory, 'project')));
router.use('/site-visits', resourceRoutes(crudFactory(SiteVisit, 'lead customer')));
router.use('/bookings', resourceRoutes(crudFactory(Booking, 'customer inventory')));
router.use('/payments', resourceRoutes(crudFactory(Payment, 'booking')));
router.use('/documents', resourceRoutes(crudFactory(Document, 'uploadedBy')));
router.post('/documents/upload', upload.single('file'), documentController.upload);
router.use('/notifications', resourceRoutes(crudFactory(Notification, 'user')));

module.exports = router;
