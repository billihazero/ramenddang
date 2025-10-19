export const errorHandler = (err, req, res, next) => {
  console.error('[ERROR]:', err);
  res.status(err.status || 500).json({
    status: false,
    messages: err.message || '서버 오류',
    data: [],
    total: 0,
  });
};
