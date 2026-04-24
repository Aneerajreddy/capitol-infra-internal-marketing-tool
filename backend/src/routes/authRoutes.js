const router = require('express').Router();
const { body } = require('express-validator');
const controller = require('../controllers/authController');
const validate = require('../middlewares/validate');

router.post('/login', [body('mobile').notEmpty(), body('password').notEmpty()], validate, controller.login);
router.post('/refresh', [body('refreshToken').notEmpty()], validate, controller.refresh);

module.exports = router;
