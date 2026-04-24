exports.crudFactory = (Model, populate = '') => ({
  list: async (req, res) => {
    const page = Number(req.query.page || 1);
    const pageSize = Number(req.query.pageSize || 20);
    const skip = (page - 1) * pageSize;
    const [items, total] = await Promise.all([
      Model.find().populate(populate).sort({ createdAt: -1 }).skip(skip).limit(pageSize),
      Model.countDocuments()
    ]);
    res.json({ items, page, pageSize, total });
  },
  getById: async (req, res) => {
    const item = await Model.findById(req.params.id).populate(populate);
    if (!item) return res.status(404).json({ message: 'Not found' });
    res.json(item);
  },
  create: async (req, res) => {
    const item = await Model.create(req.body);
    res.status(201).json(item);
  },
  update: async (req, res) => {
    const item = await Model.findByIdAndUpdate(req.params.id, req.body, { new: true, runValidators: true });
    if (!item) return res.status(404).json({ message: 'Not found' });
    res.json(item);
  },
  remove: async (req, res) => {
    const item = await Model.findByIdAndDelete(req.params.id);
    if (!item) return res.status(404).json({ message: 'Not found' });
    res.status(204).send();
  }
});
